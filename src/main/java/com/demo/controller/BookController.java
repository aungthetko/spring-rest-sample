package com.demo.controller;

import com.demo.entity.book.Book;
import com.demo.response.HttpResponse;
import com.demo.service.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestParam String isbn,
                                           @RequestParam String author,
                                           @RequestParam String title,
                                           @RequestParam String summary,
                                           @RequestParam String tags,
                                           @RequestParam String rating,
                                           @RequestParam String genre,
                                           @RequestPart MultipartFile image,
                                           @RequestPart MultipartFile pdfFile){
        Book createdBook = bookService.createBook(isbn, author, title, summary, tags, rating, genre, image, pdfFile);
        return new ResponseEntity<>(createdBook, CREATED);
    }

    @GetMapping("/download/{bookId}")
    public ResponseEntity<byte[]> downloadBook(@PathVariable Long bookId){
         Book book = bookService.findBookById(bookId);
         HttpHeaders httpHeaders = new HttpHeaders();
         httpHeaders.setContentType(MediaType.APPLICATION_PDF);
         httpHeaders.setContentDispositionFormData("attachment", book.getFileName());

         return ResponseEntity.ok()
                 .headers(httpHeaders).body(book.getPdfFile());
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllBook(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "3") int size){
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookList = bookService.findBookByPage(pageable);
        response.put("books", bookList.getContent());
        response.put("currentPage", bookList.getNumber());
        response.put("totalItems", bookList.getTotalElements());
        response.put("totalPages", bookList.getTotalPages());
        return new ResponseEntity<>(response, OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HttpResponse> editBook(@PathVariable("id") Long id, @RequestBody Book book, WebRequest request){
        book.setId(id);
        bookService.updateBook(book);
        return new ResponseEntity<>(new HttpResponse(
                LocalDateTime.now().toString(),
                "Successfully Update", request.getDescription(false), OK.name()), OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id){

        bookService.deleteBook(id);
        return new ResponseEntity<>("Book was successfully delete!", OK);
    }

    @GetMapping("/related/{bookId}")
    public ResponseEntity<List<Book>> getRelatedBook(@PathVariable("bookId") Long bookId){
        Book book = bookService.findBookById(bookId);
        List<Book> relatedBookList = bookService.getRelatedBook(book);
        return new ResponseEntity<>(relatedBookList, OK);
    }
}
