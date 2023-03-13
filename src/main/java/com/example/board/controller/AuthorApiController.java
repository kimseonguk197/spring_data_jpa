package com.example.board.controller;

import com.example.board.domain.Author;
import com.example.board.service.AuthorService;
import com.example.board.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorApiController {

    private final AuthorService authorService;
    private final PostService postService;

    public AuthorApiController(AuthorService authorService, PostService postService) {
        this.authorService = authorService;
        this.postService = postService;
    }


//    left join
    @GetMapping("authors/api/findByPostsById")
    public List<Author> findByPostsById(@RequestParam(value="id")Long id){
        List<Author> lstAuthor = authorService.findByPostsById(id);
        return lstAuthor;
    }



//    fetch조인후 AuthorResponse로 return
    @GetMapping("authors/api/findByPosts")
    public List<AuthorResponseDto> findByPosts(){
        List<Author> lstAuthor = authorService.findAllFetchJoin();
        return lstAuthor.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    //    Author객체로 return하게 되면 순환참조 에러가 발생할것.
    @GetMapping("alluser")
    public List<Author> alluser(){
        List<Author> lstAuthor = authorService.findAll();
        return lstAuthor;
    }


//    아래와 같이 convert 메서드를 만들거나 modelmapper 라이브러리를 사용해 convert하여도 된다.
//    https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
//    위 블로그 참고
    private AuthorResponseDto convertToDto(Author author) {
        AuthorResponseDto authorResponse = new AuthorResponseDto();
        authorResponse.setId(author.getId());
        authorResponse.setName(author.getName());
        authorResponse.setEmail(author.getEmail());
        authorResponse.setPassword(author.getPassword());
        authorResponse.setRole(author.getRole());
        authorResponse.setCreateDate(author.getCreateDate());
        return authorResponse;
    }


}
