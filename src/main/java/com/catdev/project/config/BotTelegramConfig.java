package com.catdev.project.config;

import com.pengrad.telegrambot.TelegramBot;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class BotTelegramConfig {
    @Value("${bot.token}")
    private String token;
    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }
    @Bean
    public TelegramBot telegramBot(){
        return new TelegramBot.Builder(token).okHttpClient(okHttpClient()).build();
    }
}
