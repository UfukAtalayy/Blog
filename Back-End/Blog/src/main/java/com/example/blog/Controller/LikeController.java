package com.example.blog.Controller;

import com.example.blog.DTO.LikeDTO;
import com.example.blog.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<LikeDTO> createLike(@RequestBody LikeDTO likeDTO) {
        LikeDTO createdLike = likeService.createLike(likeDTO);
        return new ResponseEntity<>(createdLike, HttpStatus.CREATED);
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDTO>> getLikesByPost(@PathVariable Long postId) {
        List<LikeDTO> likes = likeService.getLikesByPostId(postId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeDTO>> getLikesByUser(@PathVariable Long userId) {
        List<LikeDTO> likes = likeService.getLikesByUserId(userId);
        return ResponseEntity.ok(likes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{likeId}")
    public ResponseEntity<LikeDTO> updateLike(
            @PathVariable Long likeId,
            @RequestBody LikeDTO likeDTO) {
        LikeDTO updatedLike = likeService.updateLike(likeId, likeDTO);
        return ResponseEntity.ok(updatedLike);
    }
}
