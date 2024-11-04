package com.example.blog.Mapper;


import com.example.blog.DTO.UserDTO;
import com.example.blog.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Entity to DTO
    public UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    // DTO to Entity
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}

