package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.entity.*;
import com.example.exception.*;
import com.example.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


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
    public void registerUser(@RequestBody Account account){
        try{
            accountService.registerUser(account);
        } catch (DuplicateUsernameException | InvalidRegistrationException e) {
            throw e;
        }
    }

    //LOGIN
    @PostMapping("/login")
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
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }    

    //Get One Message Given Message Id
    //GET localhost:8080/messages/{message_id}
    @GetMapping("/messages/{message_id}")
    public Message getMessageById(@PathVariable int message_id){
        return messageService.getMessageById(message_id);
    }

    //Delete a Message Given Message Id
    //DELETE localhost:8080/messages/{message_id}
    @DeleteMapping("/messages/{message_id}")
    public String deleteMessageById(@PathVariable int message_id){
        return messageService.deleteMessageById(message_id);
    }

    //Update Message Given Message Id
    //PATCH localhost:8080/messages/{message_id}
    @PatchMapping("/messages/{message_id}")
    public String updateMessage(@PathVariable int message_id, @RequestBody String newMessage) throws Exception{

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jNode = objectMapper.readTree(newMessage);            
            newMessage = jNode.get("message_text").asText();

            return messageService.updateMessage(message_id, newMessage);
        } catch (MessageErrorException | JsonProcessingException e){
            throw e;
        }
    }


    //GET localhost:8080/accounts/{account_id}/messages
    @GetMapping("/accounts/{account_id}/messages")
    public @ResponseBody List<Message> getAllMessageById(@PathVariable int account_id){
            return messageService.getAllMessagesById(account_id);
    }
}
