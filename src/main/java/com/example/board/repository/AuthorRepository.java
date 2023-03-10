package com.example.board.repository;

import com.example.board.domain.Author;
import com.example.board.domain.Post;
import com.example.board.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
//    JpaRepository에 기본적인 CRUD를 포함한 여러가지 기능이 사전 구현돼 있음
//    커스터마이징 하고 싶은 쿼리는 따로 추가하면됨.
//    findByA : 스프링에서 A로 조건을 걸어 조회하는 기능을 제공
//    findByAandB : 이러한 규칙을 가지고 다양한 조회 가능
    Optional<Author> findByEmail(String email);
    List<Author> findByRole(Role role);

//    jpql방식
    @Query("select distinct a from Author a join fetch a.posts where a.id = :id")
    List<Author> findAllFetchJoinById(@Param("id")Long id);

//    distinct를 하는 이유 : a는 1건인데, post는 여러건이라 join을 걸면 a도 다 건처럼 조회가 됨
//    join이 아니라 fetch join을 하는 이유
//    entity의 연관관계를 무시하고, select절에 명시된 내용만을 조회
//    그러므로, 그러므로, 지연로딩을 설정했다 하더라도, fetch join이 걸리면 join을 걸어 조회한다
//    그러나, join만을 하게 되면 one to many + lazy의 설정이 적용되어, 단건의 join으로 조회해오지 않고 이후 사용시에 N+1의 조회가 나가는 것.
    @Query("select distinct a from Author a left join a.posts")
    List<Author> findAllFetchJoin();

}
