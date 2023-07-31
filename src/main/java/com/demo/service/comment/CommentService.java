package com.demo.service.comment;

import com.demo.entity.comment.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    Comment createComment(Long id, Comment comment, String email);

    List<Comment> getCommentById(Long id);

}
