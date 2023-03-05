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




//    name = "member_id" 을 통해 사용하고자 하는 컬럼명 지정
//    JoinColumn은 생략한다 하더라도, ManyToOne에서 참조하는 테이블의 "테이블_id"가 자동으로 id로 설정된다.

//    join없이 notNull만 세팅후 DB정합성을 맞추려면 사용자 정보가 정확히 있어야 가능할것.
//    (fetch = FetchType.LAZY) 를 넣어주지 않으면, author를 사용하는 상황이 아니어도 author를 조회해온다.
//    @JsonBackReference 를 통해 한쪽방향으로만 참조를 걸수도 있으나, 그러면 양쪽방향 조회가 안된다.
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false, name="authorId", referencedColumnName="id")
//    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Author author;

//    Transient는 entity에서 제외
    @Transient
    private String authorName;

    @OneToMany(mappedBy = "post")
//    JsonIgnore는 likes값이 없을경우 join에서 제외시키는것
//    JsonBackReference && JsonManagedReference 은 likes가 없더라도 leftjoin
//    https://stackoverflow.com/questions/37392733/difference-between-jsonignore-and-jsonbackreference-jsonmanagedreference
    @JsonIgnore
    private List<PostLike> likes = new ArrayList<>();


}
