package com.demo.service.book;

import com.demo.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BookService {

    Book findBookById(Long id);

    Book createBook(String isbn, String author, String title, String summary, String tags, String rating, String genre, MultipartFile image, MultipartFile pdfFile);

    Page<Book> findBookByPage(Pageable pageable);

    Book updateBook(Book book);

    void deleteBook(Long id);

    List<Book> getRelatedBook(Book book);

}
