package com.demo.repository.comment;

import com.demo.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c.*,b.* from comment c, book b where b.id = c.book_id and b.id= ?1", nativeQuery = true)
    List<Comment> findCommentByBookId(Long id);
}
