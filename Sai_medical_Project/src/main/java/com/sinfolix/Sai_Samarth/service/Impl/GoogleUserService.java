package com.sinfolix.Sai_Samarth.service.Impl;

import com.sinfolix.Sai_Samarth.entities.GoogleUser;

import com.sinfolix.Sai_Samarth.repositories.GoogleUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class GoogleUserService {

    @Autowired
    private GoogleUserRepository userRepository;
    public void processOAuthPostLogin(OAuth2AuthenticationToken authenticationToken) {
        String email = authenticationToken.getPrincipal().getAttribute("email");
        String name = authenticationToken.getPrincipal().getAttribute("name");
        String picture = authenticationToken.getPrincipal().getAttribute("picture");

        GoogleUser existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            GoogleUser newUser = new GoogleUser();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPicture(picture);
            userRepository.save(newUser);
        }
    }
}
