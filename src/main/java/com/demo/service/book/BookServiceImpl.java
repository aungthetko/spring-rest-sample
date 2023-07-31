package com.demo.service.book;

import com.demo.entity.book.Book;
import com.demo.exception.BookNotFoundException;
import com.demo.exception.ISBNAlreadyTakenException;
import com.demo.repository.book.BookRepository;
import com.demo.utility.ImageUtility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book findBookById(Long id) {
        Book book = bookRepository.findBookById(id).stream().findFirst()
                .orElseThrow(() -> new BookNotFoundException(String.valueOf(id)));
        return book;
    }

    @Override
    public Book createBook(String isbn, String author, String title, String summary, String tags, String rating, String genre, MultipartFile image, MultipartFile pdfFile) {
        Book createBook = null;
        Optional<Book> getBook = bookRepository.findBookByIsbn(isbn);
        if (getBook.isPresent()){
            throw new ISBNAlreadyTakenException(isbn);
        }else if(rating == null && rating.equals("")){
            rating = "0";
        }
        try{
             createBook = bookRepository.save(Book.builder()
                    .isbn(isbn)
                    .author(author)
                    .title(title)
                    .summary(summary)
                    .tags(tags)
                    .rating(rating)
                    .genre(genre)
                    .fileName(pdfFile.getOriginalFilename())
                    .fileType(image.getContentType())
                    .coverImage(ImageUtility.compressImage(image.getBytes()))
                     .pdfFile(pdfFile.getBytes())
                    .build());
        }catch (IOException e){
            e.printStackTrace();
        }
        return createBook;
    }

    @Override
    public Page<Book> findBookByPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book updateBook(Book newBook) {
        Book book = bookRepository.findById(newBook.getId()).stream().findFirst()
                .orElseThrow(() -> new BookNotFoundException(String.valueOf(newBook.getId())));
        book.setIsbn(newBook.getIsbn());
        book.setAuthor(newBook.getAuthor());
        book.setTitle(newBook.getTitle());
        book.setGenre(newBook.getGenre());
        book.setSummary(newBook.getSummary());
        book.setTags(newBook.getTags());
        book.setRating(newBook.getRating());
        bookRepository.save(book);
        return book;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getRelatedBook(Book book) {
        List<Book> relatedBookList = bookRepository.getRelatedBook(book.getAuthor(),
                book.getGenre(), book.getTags(),
                book.getRating());
        return relatedBookList;
    }
}
