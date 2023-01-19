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
            author.setRole(Role.ADMIN);
        }
//        회원가입로직
        authorService.create(author);
        return "redirect:/";
    }


    //화면에다가 db에서 조회한 값을 넘겨주려면 어떻게?!
    @GetMapping("/authors")
    public String authorList(Model model){
//        key, value 값으로 넘겨줘야한다.
        model.addAttribute("authors", authorService.findAll());
        return "authors/authorList";
    }

    @GetMapping("authors/findById")
    public String findById(@RequestParam(value="id")Long id, Model model){
        model.addAttribute("author",  authorService.findById(id).orElse(null));

        return "authors/authorDetail";
    }
    @GetMapping("authors/api/list")
    @ResponseBody
    public List<Author>  apiAuthorList(){
        List<Author> lstAuthor = authorService.findAll();
        return lstAuthor;
    }


//    left join
    @GetMapping("authors/api/findByPostsById")
    @ResponseBody
    public Map<Integer, Post> findByPostsById(@RequestParam(value="id")Long id){
        List<Author> lstAuthor = authorService.findByPostsById(id);
        Map<Integer, Post> hm = new HashMap<>();
        int i = 0;
        for(Author a : lstAuthor){
            hm.put(i, a.getPosts().get(i));
            i++;
        }
        return hm;
    }


//    전체 join 데이터 구하기
    @GetMapping("authors/api/findByPosts")
    @ResponseBody
    public Map<Integer, Post> findByPosts(){
        List<Author> lstAuthor = authorService.findAllFetchJoin();
        Map<Integer, Post> hm = new HashMap<>();
        int i = 0;
        for(Author a : lstAuthor){
//            left조인을 걸면 아래 로직은 out of bounds 날것
            hm.put(i, a.getPosts().get(i));
            i++;
        }
        return hm;
    }

    @GetMapping("authors/api/counts")
    @ResponseBody
    public List<Author> authorListCounts(){
        List<Author> lst = authorService.findAll();
        List<Author> new_lst = new ArrayList<>();
        for(Author a : lst){
            List<Post> posts = postService.findAllByAuthor_Id(a.getId());
            a.setCounts(posts.size());
            new_lst.add(a);
        }
        return new_lst;
    }

}
