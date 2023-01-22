package com.example.board.controller;

import com.example.board.domain.Author;
import com.example.board.domain.Post;
import com.example.board.domain.Role;
import com.example.board.service.AuthorService;
import com.example.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthorApiController {

    private final AuthorService authorService;
    private final PostService postService;

    public AuthorApiController(AuthorService authorService, PostService postService) {
        this.authorService = authorService;
        this.postService = postService;
    }

    @GetMapping("authors/api/list")
    @ResponseBody
    public List<Author>  apiAuthorList(){
        List<Author> lstAuthor = authorService.apiAuthorList(Role.WRITER);
        return lstAuthor;
    }

    @GetMapping("alluser")
    @ResponseBody
    public List<Author> alluser(){
        List<Author> lstAuthor = authorService.findAll();
        return lstAuthor;
    }

//    left join
    @GetMapping("authors/api/findByPostsById")
    @ResponseBody
    public List<Author> findByPostsById(@RequestParam(value="id")Long id){
        List<Author> lstAuthor = authorService.findByPostsById(id);
        return lstAuthor;
    }


//    전체 join 데이터 구하기
    @GetMapping("authors/api/findByPosts")
    @ResponseBody
    public List<Author> findByPosts(){
        List<Author> lstAuthor = authorService.findAllFetchJoin();
        return lstAuthor;
    }


}
