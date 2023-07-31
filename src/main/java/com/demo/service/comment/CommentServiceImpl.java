package com.demo.service.comment;

import com.demo.dto.CommentDTO;
import com.demo.entity.comment.Comment;
import com.demo.exception.EmailNotValidException;
import com.demo.mapper.CommentMapper;
import com.demo.repository.book.BookRepository;
import com.demo.repository.comment.CommentRepository;
import com.demo.validation.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(Long bookId, Comment comment, String email) {
        boolean isValid = EmailValidator.isEmailValid(email);
        Comment newComment;
        if(isValid){
            newComment = bookRepository.findById(bookId)
                    .map(book -> {
                        comment.setBook(book);
                        return commentRepository.save(comment);
                    }).orElseThrow(() -> new IllegalStateException("Not found"));
        }else{
            throw new EmailNotValidException(email);
        }
        return newComment;
    }

    @Override
    public List<Comment> getCommentById(Long id) {
        List<Comment> commentList = commentRepository.findCommentByBookId(id);
        return commentList;
    }
}
