package com.example.board.controller;

import com.example.board.domain.Author;
import com.example.board.domain.Post;
import com.example.board.domain.Role;
import com.example.board.service.AuthorService;
import com.example.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthorController {

//    Autowired를 통해 스프링컨테이너에 등록된 service객체를 가져다 쓰게 된다. 다만
//    생성자가 하나만 정의되어 있고 스프링 빈이라면 @Autowired 어노테이션 생략 가능
//    그래서 아래와 같이 Service호출
    private final AuthorService authorService;
    private final PostService postService;

    public AuthorController(AuthorService authorService, PostService postService) {
        this.authorService = authorService;
        this.postService = postService;
    }

    @GetMapping("/authors/new")
    public String createForm(){
        return "authors/createAuthorForm";
    }
//    회원가입시 많은 데이터들이 넘어올때는 post방식을

    @PostMapping("/authors/new")
    public String create(AuthorPostForm authorPostForm){
        Author author = new Author();
        author.setName(authorPostForm.getName());
        author.setEmail(authorPostForm.getEmail());
        author.setPassword(authorPostForm.getPassword());
        author.setCreateDate(LocalDateTime.now());
        if(authorPostForm.getRole().equals("user")){
            author.setRole(Role.USER);
        }else{
            author.setRole(Role.WRITER);
        }
//        회원가입로직
        authorService.create(author);
        return "redirect:/";
    }


    //화면에다가 db에서 조회한 값을 넘겨주려면 어떻게?!
    @GetMapping("/authors")
    public String authorList(Model model){
//        key, value 값으로 넘겨줘야한다.
//        방법1. 전체 조회 후, id별로 post에서 조회해오는 방식
//        문제점 : author별로 post쿼리를 내보내게 된다.(N+1문제)
//        List<Author> lst = authorService.findAll();


//        방법2. join을 걸어서 posts가져온뒤 count값 계산하기 : 더 효율적일듯
        List<Author> lst = authorService.findAllFetchJoin();
        model.addAttribute("authors", lst);
        return "authors/authorList";
    }

    @GetMapping("authors/findById")
    public String findById(@RequestParam(value="id")Long id, Model model){
        model.addAttribute("author",  authorService.findById(id).orElse(null));

        return "authors/authorDetail";
    }


    @GetMapping("/authors/login")
    public String login(){
        return "authors/loginPage";
    }
    //화면에다가 db에서 조회한 값을 넘겨주려면 어떻게?!

    @GetMapping("/")
    public String home(){
        return "home";
    }


}
