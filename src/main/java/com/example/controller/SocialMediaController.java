package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.entity.*;
import com.example.exception.*;
import com.example.service.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    //REGISTER
    //POST localhost:8080/register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody Account account){
        try{
            accountService.registerUser(account);
        } catch (DuplicateUsernameException | InvalidRegistrationException e) {
            throw e;
        }
    }

    //LOGIN
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Account login(@RequestBody Account account){
        try{
            return accountService.login(account);
        } catch (UnauthorizedException e){
            throw e;
        }
    }

    //MESSAGES
    //Create New Message
    //POST localhost:8080/messages
    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public Message createNewMessage(@RequestBody Message message){
        try{
            return messageService.createNewMessage(message);
        } catch (MessageErrorException e){
            throw e;
        }
    }

    //Get All Messages
    //GET localhost:8080/messages
    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }    

    //Get One Message Given Message Id
    //GET localhost:8080/messages/{message_id}
    @GetMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public Message getMessageById(@PathVariable int message_id){
        return messageService.getMessageById(message_id);
    }

    //Delete a Message Given Message Id
    //DELETE localhost:8080/messages/{message_id}
    @DeleteMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteMessageById(@PathVariable int message_id){
        return messageService.deleteMessageById(message_id);
    }

    //Update Message Given Message Id
    //PATCH localhost:8080/messages/{message_id}
    @PatchMapping("/messages/{message_id}")
    public String updateMessage(@PathVariable int message_id, @RequestBody String newMessage){
        try{
            if(newMessage == null || newMessage.isBlank() || newMessage.length() > 255)
                throw new MessageErrorException();
            return messageService.updateMessage(message_id, newMessage);
        } catch (MessageErrorException e){
            throw e;
        }
    }


    //GET localhost:8080/accounts/{account_id}/messages

}
