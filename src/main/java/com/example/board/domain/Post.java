package com.example.board.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name="post")
public class Post {

    @Id@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 255)
    private String contents;

    @Column(length = 50, nullable = false)
    private String email;

    @Column
    private LocalDateTime createDate;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(nullable = false, name="authorId", referencedColumnName="id")
    private Author author;

    @Transient
    private String authorName;

    @OneToMany(mappedBy = "post")
//    JsonIgnore는 likes값이 없을경우 join에서 제외시키는것
//    JsonBackReference && JsonManagedReference 은 likes가 없더라도 leftjoin
//    https://stackoverflow.com/questions/37392733/difference-between-jsonignore-and-jsonbackreference-jsonmanagedreference
    @JsonIgnore
    private List<PostLike> likes = new ArrayList<>();


}
