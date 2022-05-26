package com.catdev.project.service.impl;

import com.catdev.project.entity.UserEntity;
import com.catdev.project.service.BotService;
import com.catdev.project.service.UserService;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

import static com.catdev.project.constant.BotConstant.*;

@Service
@Log4j2
public class BotServiceImpl implements BotService {
    private final TelegramBot telegramBot;
    private final UserService userService;
    public BotServiceImpl(TelegramBot telegramBot, UserService userService) {
        this.telegramBot = telegramBot;
        this.userService = userService;

        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                if(update.callbackQuery() == null){
                    takeRequestFromUserAndReply(update);
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    void sendMessageFromBot(Object chatID, String message){
        sendMessageFromBot(chatID,message, (Keyboard) null);
    }

    void sendMessageFromBot(Object chatID, String message, Keyboard... replyKeyboardMarkup){
        SendMessage sendMessage = new SendMessage(chatID,message);

        if(Objects.nonNull(replyKeyboardMarkup)){
            for (Keyboard keyboard : replyKeyboardMarkup){
                sendMessage.replyMarkup(keyboard);
            }
        }

        telegramBot.execute(sendMessage, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                log.info("onResponse -> request : {}, response : {}",request,response.message());
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {
                log.error("onFailure -> request : {}",request,e);
            }
        });
    }

    private void takeRequestFromUserAndReply(Update update){
        Message message = update.message();

        if(message == null){
            return;
        }

        User userTelegram = message.from();

        Chat chat = message.chat();

        if(chat == null){
            return;
        }

        log.info("getMessage {}, userTelegram {}",message,userTelegram);

        String messageText = message.text();

        Long userTelegramId = userTelegram.id();

        UserEntity userEntity = userService.findUserEntityByTelegramId(userTelegramId);

        switch (messageText)
        {
             case START_BOT ->
             {
                sendMessageFromBot(chat.id(),WELCOME_BOT_MESSAGE);

                if(userEntity == null)
                {
                    sendMessageFromBot(chat.id(),USER_NOT_FOUND);
                }
                else
                {

                }
            }
            default ->
            {
                 log.error("command invalid : {}",messageText);
                 sendMessageFromBot
                         (
                             chat.id(),
                             COMMAND_NOT_FOUND,
                                 new InlineKeyboardMarkup(
                                         new InlineKeyboardButton("url").url("www.google.com"),
                                         new InlineKeyboardButton("callback_data").callbackData("callback_data"),
                                         new InlineKeyboardButton("Switch!").switchInlineQuery("switch_inline_query")
                                 )
                         );
            }
        }
    }


}
