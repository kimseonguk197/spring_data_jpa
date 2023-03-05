package com.example.board.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Setter
@Getter
@Entity
@Table(name="author")
public class Author {

//    Entity로 선언을 할때는, pk가 어떤건지를 정확하게 지정해줘야 한다.
//    Author_basic까지 수행하고 나면 Lombok, Auto-ddl 등으로 변경후 advanced 진행

    //Entity로 선언을 할때는, pk가 어떤건지를 정확하게 지정해줘야 한다.
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Role role;

    @Column
    private LocalDateTime createDate;

//    mappedBy를 통해 연관관계의 주인이 누구인지를 결정한다. 즉 fk를 누구에게 설정하느냐는 말이다.
//    mappedBy의 반대편대상에 fk가 설정된다.
//    OneToMany는 LAZY가 Default, ManyToOne은 EAGER가 Default
//    사실 LAZY, Default여부를 떠나서 fetch를 통해 조회를 해오면 2번 조회하러 가지 않는다. 그래서 아래 posts의 경우 조회1번으로 끝난다.
    @OneToMany(mappedBy = "author")
//    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    @Transient
    private int postcounts;

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}
