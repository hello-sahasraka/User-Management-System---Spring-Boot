package com.user.management.controller;

import com.user.management.dto.UserDTO;
import com.user.management.repo.UserRepo;
import com.user.management.service.UserService;
import com.user.management.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getusers")
    public List<UserDTO> getUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/getuserbyid/{userId}")
    public UserDTO getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @PostMapping(
            value = "/createuser",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public UserDTO createUser(@RequestPart("user") UserDTO userDTO, @RequestPart(value = "image", required = false) MultipartFile image) {

        String password = PasswordGenerator.generateSecurePassword(12);
        userDTO.setPassword(password);

        return userService.createUser(userDTO);
    }

    @PutMapping("/updateuser")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) { return userService.updateUser(userDTO); }

    @DeleteMapping("/deleteuser/{userId}")
    public String deleteUser(@PathVariable int userId) { return userService.deleteUser(userId); }
}
