package com.example.blog.Service;

import com.example.blog.DTO.PostDTO;
import com.example.blog.Entity.Post;
import com.example.blog.Entity.User;
import com.example.blog.Repository.PostRepository;
import com.example.blog.Repository.UserRepository;
import com.example.blog.Mapper.PostMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    // Tüm postları getir
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(postMapper::toDTO).collect(Collectors.toList());
    }

    // Belirli bir postu getir
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        return postMapper.toDTO(post);
    }

    // Yeni post oluştur
    public PostDTO createPost(PostDTO postDTO) {
        // Author'ı bul
        User author = userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + postDTO.getAuthorId()));

        // Postu DTO'dan Entity'e çevir ve kaydet
        Post post = postMapper.toEntity(postDTO, author);
        Post savedPost = postRepository.save(post);

        return postMapper.toDTO(savedPost);
    }

    // Postu güncelle
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        // Güncellenecek postu bul
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        // Author'ı bul
        User author = userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + postDTO.getAuthorId()));

        // Postu güncelle
        existingPost.setTitle(postDTO.getTitle());
        existingPost.setContent(postDTO.getContent());
        existingPost.setAuthor(author);
        existingPost.setUpdatedAt(postDTO.getUpdatedAt());

        Post updatedPost = postRepository.save(existingPost);

        return postMapper.toDTO(updatedPost);
    }

    // Postu sil
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        postRepository.delete(post);
    }
}
