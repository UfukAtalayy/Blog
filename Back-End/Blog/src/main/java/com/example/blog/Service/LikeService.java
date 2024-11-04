package com.example.blog.Service;

import com.example.blog.DTO.LikeDTO;
import com.example.blog.Entity.Like;
import com.example.blog.Entity.Post;
import com.example.blog.Entity.User;
import com.example.blog.Mapper.LikeMapper;
import com.example.blog.Repository.LikeRepository;
import com.example.blog.Repository.PostRepository;
import com.example.blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeMapper likeMapper;

    @Autowired
    public LikeService(LikeRepository likeRepository,UserRepository userRepository,PostRepository postRepository,LikeMapper likeMapper){
        this.likeRepository=likeRepository;
        this.likeMapper=likeMapper;
        this.userRepository=userRepository;
        this.postRepository=postRepository;
    }

    // Create like
    public LikeDTO createLike(LikeDTO likeDTO) {
        // User ve Post'u veritabanından bul
        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Like entity'sini DTO'dan map'leyin
        Like like = likeMapper.toEntity(likeDTO, user, post);

        // Veritabanına kaydet
        Like savedLike = likeRepository.save(like);

        // Kaydedilen veriyi DTO'ya çevirip döndür
        return likeMapper.toDTO(savedLike);
    }
    // Get likes by post ID
    public List<LikeDTO> getLikesByPostId(Long postId) {
        List<Like> likes = likeRepository.findByPostId(postId);
        return likes.stream().map(likeMapper::toDTO).collect(Collectors.toList());
    }

    // Get likes by user ID
    public List<LikeDTO> getLikesByUserId(Long userId) {
        List<Like> likes = likeRepository.findByUserId(userId);
        return likes.stream().map(likeMapper::toDTO).collect(Collectors.toList());
    }

    // Delete like
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

    // **Update like**
    public LikeDTO updateLike(Long likeId, LikeDTO likeDTO) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        // Kullanıcıyı güncelleme
        if (likeDTO.getUserId() != null) {
            User user = userRepository.findById(likeDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            like.setUser(user);
        }

        // Postu güncelleme
        if (likeDTO.getPostId() != null) {
            Post post = postRepository.findById(likeDTO.getPostId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            like.setPost(post);
        }

        likeRepository.save(like);
        return likeMapper.toDTO(like);
    }

}
