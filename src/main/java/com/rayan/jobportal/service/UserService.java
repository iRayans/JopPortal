package com.rayan.jobportal.service;

import com.rayan.jobportal.entity.JobSeekerProfile;
import com.rayan.jobportal.entity.RecruiterProfile;
import com.rayan.jobportal.entity.User;
import com.rayan.jobportal.repository.JobSeekerProfileRepository;
import com.rayan.jobportal.repository.RecruiterProfileRepository;
import com.rayan.jobportal.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerRepository;
    private final RecruiterProfileRepository recruiterRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JobSeekerProfileRepository jobSeekerRepository, RecruiterProfileRepository recruiterRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterRepository = recruiterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addNewUser(User user) {
        // Encrypt user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        User savedUser = userRepository.save(user);
        int userTypeId = user.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            recruiterRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerRepository.save(new JobSeekerProfile(savedUser));

        }
        return savedUser;
    }


    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found " + "user"));
            int userId = user.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                return recruiterRepository.findById(userId).orElse(new RecruiterProfile());
            } else {
                return jobSeekerRepository.findById(userId).orElse(new JobSeekerProfile());
            }
        }

        return null;
    }

}
