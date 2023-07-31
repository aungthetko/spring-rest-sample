package com.demo.repository.book;

import com.demo.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByIsbn(String isbn);

    List<Book> findBookById(Long id);

    @Modifying
    @Query(value = "select * from Book where author = :author " +
            "or genre = :genre or tags = :tags or rating = :rating order by id DESC LIMIT 7", nativeQuery = true)
    List<Book> getRelatedBook(@Param("author") String author,
                              @Param("genre") String genre,
                              @Param("tags") String tags,
                              @Param("rating") String rating);
}
