package com.sinfolix.Sai_Samarth.controller;


import com.sinfolix.Sai_Samarth.DTO.UserDTO;
import com.sinfolix.Sai_Samarth.Utils.JwtUtil;
import com.sinfolix.Sai_Samarth.entities.User;
import com.sinfolix.Sai_Samarth.service.Impl.CustomUserDetailsServiceImpl;
//import com.sinfolix.Sai_Samarth.service.Impl.GoogleUserService;
import com.sinfolix.Sai_Samarth.service.Impl.GoogleUserService;
import com.sinfolix.Sai_Samarth.service.Impl.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/public")
@Tag(name = "Public APIs")
public class PublicController {

    @Autowired
    private GoogleUserService googleUserService;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/loginSuccess")
    public ResponseEntity<String> loginSuccess(OAuth2AuthenticationToken authenticationToken){
        if(authenticationToken == null){
            return new ResponseEntity<>("Authentication token is missing", HttpStatus.UNAUTHORIZED);
        }
        String email = authenticationToken.getPrincipal().getAttribute("email");
        if(email == null){
            return new ResponseEntity<>("OAuth2 login failed. Email is missing.", HttpStatus.UNAUTHORIZED);
        }
        googleUserService.processOAuthPostLogin(authenticationToken);
        String jwtToken = jwtUtil.generateToken(email);
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        String result = userService.saveNewUser(userDTO);
        return new  ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
        System.out.println("called");
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/userinfo")
    public Map<String, Object> getFullUserInfo(@AuthenticationPrincipal OAuth2User oauthUser) {
        return oauthUser.getAttributes();
    }


}
