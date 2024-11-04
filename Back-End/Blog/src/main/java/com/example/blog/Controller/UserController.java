package com.example.blog.Controller;


import com.example.blog.DTO.UserDTO;
import com.example.blog.Entity.User;
import com.example.blog.Mapper.UserMapper;
import com.example.blog.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // Tüm kullanıcıları al
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    // ID ile kullanıcı al
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = Optional.ofNullable(userService.getUserById(id));
        return user.map(value -> ResponseEntity.ok(userMapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Yeni kullanıcı oluştur
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(userMapper.toDto(savedUser));
    }

    // Kullanıcıyı güncelle
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> updatedUser = userService.updateUser(id, userMapper.toEntity(userDTO));
        return updatedUser.map(user -> ResponseEntity.ok(userMapper.toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Kullanıcıyı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
