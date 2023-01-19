package com.example.board.repository;

import com.example.board.domain.Author;
import com.example.board.domain.Post;
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

//    join방법. jpql
    @Query("select a from Author a join fetch a.posts p where a.id = :id")
    List<Author> findAllFetchJoinById(@Param("id")Long id);


    //    join방법. jpql
    @Query("select a from Author a left join fetch a.posts p")
    List<Author> findAllFetchJoin();

}
