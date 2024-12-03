package com.sinfolix.Sai_Samarth.service.Impl;

import com.sinfolix.Sai_Samarth.DTO.UserDTO;

import com.sinfolix.Sai_Samarth.entities.User;
import com.sinfolix.Sai_Samarth.exceptions.ResourceNotFoundException;
import com.sinfolix.Sai_Samarth.repositories.OrderRepository;
import com.sinfolix.Sai_Samarth.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
    }

    public String saveAdmin(UserDTO userDTO){
        try {
            Optional<User> existingUserByUsername = userRepository.findByUsername(userDTO.getUsername());
            Optional<User> existingUserByEmail = userRepository.findByEmail(userDTO.getEmail());

            if (existingUserByUsername.isPresent() || existingUserByEmail.isPresent()) {
                return "Username or Email already exists.";
            }
            User user = new User();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRoles(List.of("ADMIN","USER"));

            userRepository.save(user);
            return "ADMIN Registered Successfully";
        }catch (Exception e){
            throw  new RuntimeException(" Error while sign up");
        }
    }

    public String saveNewUser(UserDTO userDTO) {
        try {
            Optional<User> existingUserByUsername = userRepository.findByUsername(userDTO.getUsername());
            Optional<User> existingUserByEmail = userRepository.findByEmail(userDTO.getEmail());

            if (existingUserByUsername.isPresent() || existingUserByEmail.isPresent()) {
                return "Username or Email already exists.";
            }
            User user = new User();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRoles(List.of("USER"));

            userRepository.save(user);
            return "User Registered Successfully";

        }catch (Exception e){
            throw  new RuntimeException(" Error while sign up");
        }
    }

    public void  deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public List<UserDTO> getAll() {
       List<User> user = this.userRepository.findAll();
        return user.stream().map(this::userToUserDTO).collect(Collectors.toList());
    }


    public UserDTO findByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found with username", username));
            return userToUserDTO(user);
        } catch(Exception e) {
            throw new UsernameNotFoundException("User not found with username " +e);
        }
    }
// Convert User to UserDTO
    public UserDTO userToUserDTO (User user){
        UserDTO userDTO= new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

// Convert UserDTO to USer
    public User userDtoToUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public void deleteAdmin(Long id) {
        userRepository.deleteById(id);
    }
}
