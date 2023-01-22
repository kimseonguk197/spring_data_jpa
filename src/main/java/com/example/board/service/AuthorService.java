package com.example.board.service;

import com.example.board.domain.Author;
import com.example.board.domain.Role;
import com.example.board.repository.AuthorRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService  implements UserDetailsService {

//    외부 접근 불가능
    private final AuthorRepository repository;
    private final PasswordEncoder passwordEncoder;


//    생성자
    public AuthorService(AuthorRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public void create(Author author){
        repository.save(author);
        author.encodePassword(passwordEncoder);
    }

//    oneToMany관계에서 findAll을 실행하면, n+1 문제 발생
//    author1개를 조회한뒤 -> 관련한 post조회 -> 다시 author1개 조회뒤 관련한 post조회 => query가 연속해서 발생하는 것을 말함
//    fetch lazy 지연전략을 사용시 해당 문제 해결가능
    public List<Author> findAll(){
        List<Author> result = repository.findAll();
        return result;
    }

    public List<Author> apiAuthorList(Role role){
        List<Author> result = repository.findByRole(role);
        return result;
    }

//    SpringDataJpaRepository일경우
    public Optional<Author> findById(Long memberId){
        return repository.findById(memberId);
    }

    public Optional<Author> findByEmail(String email){
        return repository.findByEmail(email);
    }

    public List<Author> findByPostsById(Long id){
        List<Author> result = repository.findAllFetchJoinById(id);
        return result;
    }

    public List<Author> findAllFetchJoin(){
        List<Author> result = repository.findAllFetchJoin();
        return result;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Author author = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email 입니다."));
        return new User(author.getEmail(), author.getPassword(), Arrays.asList(new SimpleGrantedAuthority(author.getRole().toString())));
    }
}
