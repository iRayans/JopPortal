package com.rayan.jobportal.controller;

import com.rayan.jobportal.entity.RecruiterProfile;
import com.rayan.jobportal.entity.User;
import com.rayan.jobportal.service.RecruiterProfileService;
import com.rayan.jobportal.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UserService userService;
    private final RecruiterProfileService recruiterProfileService;


    public RecruiterProfileController(UserService userService, RecruiterProfileService recruiterProfileService) {
        this.userService = userService;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String RecruiterProfile(Model model) {
        // get logged-in user information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.findUserByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException("Could not found user."));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());

            recruiterProfile.ifPresent(profile -> model.addAttribute("recruiterProfile", profile));
        }
        return "recruiter_profile";
    }


}
