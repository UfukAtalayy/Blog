package com.example.blog.Mapper;

import com.example.blog.DTO.LikeDTO;
import com.example.blog.Entity.Like;
import com.example.blog.Entity.Post;
import com.example.blog.Entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LikeMapper {

    public LikeDTO toDTO(Like like){
        LikeDTO dto = new LikeDTO();
        dto.setId(like.getId());
        dto.setPostId(like.getPost().getId());
        dto.setUserId(like.getUser().getId());
        dto.setCreatedAt(like.getCreatedAt());
        dto.setUpdatedAt(like.getUpdatedAt());
        return dto;
    }

    public Like toEntity(LikeDTO likeDTO, User user, Post post){
        Like like = new Like();
        like.setUser(user); // User'ı set ediyoruz
        like.setPost(post); // Post'u set ediyoruz
        like.setCreatedAt(likeDTO.getCreatedAt() != null ? likeDTO.getCreatedAt() : LocalDateTime.now()); // Eğer DTO'da oluşturulma tarihi varsa onu kullan, yoksa şimdiki zamanı kullan
        return like;
    }
}
