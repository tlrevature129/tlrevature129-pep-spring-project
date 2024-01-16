package com.example.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.MessageErrorException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AccountRepository accountRepository;


    //CREATE NEW MESSAGE
    //POST localhost:8080/messages
    public Message createNewMessage(Message message){

        Optional<Account> optAccount = accountRepository.findById(message.getPosted_by());

        //check if new message meets requirement
        int messageLength = message.getMessage_text().length();
        if(!optAccount.isPresent() || messageLength == 0 || messageLength > 255) throw new MessageErrorException();

        return messageRepository.save(message);
    }


    //GET ALL MESSAGES
    //GET localhost:8080/messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //GET ONE MESSAGE GIVEN MESSAGE ID
    //GET localhost:8080/messages/{message_id}
    public Message getMessageById(int message_id){
        Optional<Message> optMessage = messageRepository.findById(message_id);
        if(optMessage.isPresent())
            return optMessage.get();
        return null;
    }


    //DELETE A MESSAGE GIVEN MESSAGE ID
    //DELETE localhost:8080/messages/{message_id}
    public String deleteMessageById(int message_id){
        Optional<Message> optMessage = messageRepository.findById(message_id);
        if(optMessage.isPresent()){
            messageRepository.deleteById(message_id);
            return "1";
        } else {
            return "";
        }
    }

    //UPDATE MESSAGE GIVEN MESSAGE ID & NEW MESSAGE
    //PATCH localhost:8080/messages/{message_id}
    public String updateMessage(int message_id, String newMessage){
        Optional<Message> optMessage = messageRepository.findById(message_id);

        boolean isValidMessage = newMessage != null && !newMessage.isBlank() && newMessage.length() <= 255;
        
        if(optMessage.isPresent() && isValidMessage){
            Message message = optMessage.get();
            message.setMessage_text(newMessage);
            messageRepository.save(message);
            return "1";
        } else {
            throw new MessageErrorException();
        }
    }


    //GET ALL MESSAGES FROM USER GIVEN ACCOUNT_ID
    //GET localhost:8080/accounts/{account_id}/messages

}
