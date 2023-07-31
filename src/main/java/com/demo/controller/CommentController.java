package com.demo.controller;

import com.demo.dto.CommentDTO;
import com.demo.entity.comment.Comment;
import com.demo.repository.book.BookRepository;
import com.demo.repository.comment.CommentRepository;
import com.demo.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{bookId}")
    public ResponseEntity<Comment> createComment(@PathVariable("bookId") Long bookId,
                                                 @RequestBody Comment comment,
                                                 @RequestParam(name = "email") String email){
        Comment newComment = commentService.createComment(bookId, comment, email);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping("/book/list/{id}")
    public ResponseEntity<List<Comment>> getCommentByBookId(@PathVariable("id") Long id){
        List<Comment> getComment = commentService.getCommentById(id);
        return new ResponseEntity<>(getComment, HttpStatus.OK);
    }
}
