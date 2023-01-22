package com.example.board.controller;

import com.example.board.domain.Author;
import com.example.board.domain.Post;
import com.example.board.service.AuthorService;
import com.example.board.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final AuthorService authorService;

    public PostController(PostService postService, AuthorService authorService) {
        this.postService = postService;
        this.authorService = authorService;
    }

    @GetMapping("/posts/new")
    public String createForm(){
        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String create(PostForm postForm, HttpServletRequest request){
        HttpSession session = request.getSession();
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setContents(postForm.getContents());
        post.setEmail(session.getAttribute("email").toString());
        post.setCreateDate(LocalDateTime.now());
        Author a1 = authorService.findByEmail(post.getEmail()).orElse(null);
        post.setAuthor(a1);
        postService.create(post);
        return "redirect:/";
    }

    @GetMapping("/posts")
    public String postList(Model model){
//        key, value 값으로 넘겨줘야한다.
        model.addAttribute("posts", postService.findAll());
        return "posts/postList";
    }

    @GetMapping("posts/findById")
    public String findById(@RequestParam(value="id")Long id, Model model){
        model.addAttribute("post",  postService.findById(id).orElse(null));

        return "posts/postDetail";
    }

    @GetMapping("posts/api/findAllFetchJoin")
    @ResponseBody
    public List<Post> findAllFetchJoin(){
        List<Post> result = postService.findAllFetchJoin();
        for(Post a : result){
            System.out.println(a);
            System.out.println(a.getAuthor().getName());
        }
        return result;
    }


    @GetMapping("/posts/api/findAll")
    @ResponseBody
    public List<Post> postApiList(Model model){
//        key, value 값으로 넘겨줘야한다.
        return postService.findAll();
    }

}
