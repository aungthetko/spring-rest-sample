package com.demo.entity.book;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String isbn;
    @NotNull(message = "Author can not be null or empty")
    private String author;
    @NotNull(message = "Title can not be null or empty")
    private String title;
    @NotNull(message = "Summary can not be null or empty")
    @Lob
    private String summary;
    @NotNull(message = "Tags can not be null or empty")
    private String tags;
    private String rating;
    @NotNull(message = "Genre can not be null or empty")
    private String genre;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] coverImage;
    @Lob
    private byte[] pdfFile;

}
