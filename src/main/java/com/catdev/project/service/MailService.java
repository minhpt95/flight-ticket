package com.catdev.project.service;

import com.catdev.project.entity.UserEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

public interface MailService {
    void sendEmailAttach(String to, String subject, String body, String fileName, File file) throws Exception;

    void sendEmailAttach(String[] to, String subject, String body, String fileName, File file) throws Exception;

    void sendEmail(String to, String subject, String body) throws Exception;

    void sendEmail(String [] to, String subject, String body) throws Exception;

    void sendEmail(String [] to, String from, String personal, String subject, String body) throws Exception;

    @Async
    void sendActivationEmail(UserEntity to);
}
