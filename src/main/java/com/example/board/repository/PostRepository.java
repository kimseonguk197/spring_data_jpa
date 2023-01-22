package com.example.board.repository;
import com.example.board.domain.Author;
import com.example.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
    List<Post> findAllByAuthor_Id(Long author_id);
    @Query("select p from Post p left join fetch p.author")
    List<Post> findAllFetchJoin();


}
