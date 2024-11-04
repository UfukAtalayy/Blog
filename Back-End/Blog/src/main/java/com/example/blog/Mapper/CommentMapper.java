package com.example.blog.Mapper;

import com.example.blog.DTO.CommentDTO;
import com.example.blog.Entity.Comment;
import com.example.blog.Entity.Post;
import com.example.blog.Entity.User;
import org.springframework.stereotype.Component;
@Component
public class CommentMapper {

    public CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAuthorId(comment.getAuthor().getId());  // author_id'yi alıyoruz
        dto.setUserId(comment.getUserId());            // user_id'yi alıyoruz
        dto.setPostId(comment.getPost().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }

    public Comment toEntity(CommentDTO commentDTO, User author, Post post) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setAuthor(author);                     // author'u set ediyoruz
        comment.setUserId(commentDTO.getUserId());     // user_id set ediliyor
        comment.setPost(post);
        return comment;
    }
}
