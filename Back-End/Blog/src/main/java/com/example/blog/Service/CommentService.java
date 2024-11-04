package com.example.blog.Service;

import com.example.blog.DTO.CommentDTO;
import com.example.blog.Entity.Comment;
import com.example.blog.Entity.Post;
import com.example.blog.Entity.User;
import com.example.blog.Mapper.CommentMapper;
import com.example.blog.Repository.CommentRepository;
import com.example.blog.Repository.PostRepository;
import com.example.blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    // Belirli bir post'a ait tüm yorumları getirir
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Yorum id'sine göre tek bir yorumu getirir
    public Optional<CommentDTO> getCommentById(Long id) {
        return commentRepository.findById(id)  // Burada findById kullanıyoruz
                .map(commentMapper::toDTO);    // Yorum bulunursa DTO'ya dönüştürüyoruz
    }

    // Yeni bir yorum oluşturur
    public CommentDTO createComment(CommentDTO commentDTO) {
        User author = userRepository.findById(commentDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = commentMapper.toEntity(commentDTO, author, post);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDTO(savedComment);
    }

    // Yorum güncellemesi yapar
    public Optional<CommentDTO> updateComment(Long id, CommentDTO commentDTO) {
        return commentRepository.findById(id).map(comment -> {
            comment.setContent(commentDTO.getContent());
            comment.setUpdatedAt(LocalDateTime.now());
            Comment updatedComment = commentRepository.save(comment);
            return commentMapper.toDTO(updatedComment);
        });
    }

    // Yorum siler
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
