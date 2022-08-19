package com.catdev.project.controller;

import com.catdev.project.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/refreshToken/")
public class RefreshTokenController {

    @GetMapping("/getResponse")
    public ResponseDto<?> getResponse(){
        return new ResponseDto<>();
    }
}
