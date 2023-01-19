package com.example.board.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Setter
@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 255)
    private String contents;

    @ManyToOne
    @JoinColumn(nullable = false, name="author_id", referencedColumnName="id")
    private Author author;

    @Column(length = 50)
    private String email;

    @Column
    private LocalDateTime createDate;



}
