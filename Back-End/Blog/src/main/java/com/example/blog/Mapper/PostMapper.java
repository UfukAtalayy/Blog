package com.example.blog.Mapper;

import com.example.blog.DTO.PostDTO;
import com.example.blog.Entity.Post;
import com.example.blog.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthorId(post.getAuthor().getId());
        dto.setAuthorName(post.getAuthor().getUsername());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }

    public Post toEntity(PostDTO postDTO, User author) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthor(author);
        post.setCreatedAt(postDTO.getCreatedAt());
        post.setUpdatedAt(postDTO.getUpdatedAt());
        return post;
    }
}
