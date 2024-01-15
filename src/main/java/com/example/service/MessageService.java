package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;


    //CREATE NEW MESSAGE
    //POST localhost:8080/messages

    //GET ALL MESSAGES
    //GET localhost:8080/messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //GET ONE MESSAGE GIVEN MESSAGE ID
    //GET localhost:8080/messages/{message_id}
    public Message getMessageById(){
        return null;
    }

    //DELETE A MESSAGE GIVEN MESSAGE ID
    //DELETE localhost:8080/messages/{message_id}

    //UPDATE MESSAGE GIVE MESSAGE ID
    //PATCH localhost:8080/messages/{message_id}


    //GET ALL MESSAGES FROM USER GIVEN ACCOUNT_ID
    //GET localhost:8080/accounts/{account_id}/messages

}
