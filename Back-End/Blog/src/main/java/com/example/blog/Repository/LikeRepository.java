package com.example.blog.Repository;

import com.example.blog.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long postId);
    List<Like> findByUserId(Long userId);
}
