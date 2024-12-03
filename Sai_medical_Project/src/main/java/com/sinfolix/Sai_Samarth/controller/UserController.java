package com.sinfolix.Sai_Samarth.controller;

import com.sinfolix.Sai_Samarth.DTO.UserDTO;
import com.sinfolix.Sai_Samarth.entities.User;
import com.sinfolix.Sai_Samarth.repositories.UserRepository;
import com.sinfolix.Sai_Samarth.service.Impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000/")
@Tag(name = "User APIs",description = "Read & Delete User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
   private UserRepository userRepository;


    @GetMapping("/hello")
    public String greet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return "Welcome " + username +" to Sai Samartha Pharmacy";
    }

// Delete user
    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteUser(username);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }

// Get user info while login through username and password
    @GetMapping("/info")
    @Operation(summary = "Give info about logged in User")
    public UserDTO userInfo(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            try {
                User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                UserDTO userDTO = userService.userToUserDTO(user);
                return userDTO;
            }
            catch (Exception e){
                 return null;
            }
        }
}
