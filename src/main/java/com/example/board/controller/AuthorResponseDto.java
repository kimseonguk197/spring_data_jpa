package com.example.board.controller;

import com.example.board.domain.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuthorResponseDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createDate;
}
