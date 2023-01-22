package com.example.board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name="post_like")
public class PostLike {

    @Id@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String userEmail;

    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(nullable = false, name="postId", referencedColumnName="id")
    private Post post;

}
