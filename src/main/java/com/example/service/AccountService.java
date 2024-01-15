package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.entity.Account;
import com.example.exception.*;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //REGISTER
    //POST localhost:8080/register
    @ResponseStatus(HttpStatus.OK)
    public Account registerUser(Account account){
        String username = account.getUsername();
        int passwordLength = account.getPassword().length();

        //check valid registration 
        if(username.length() == 0  || passwordLength < 4) throw new InvalidRegistrationException();

        //check if user already exist
        Account existingAccount = accountRepository.getAccountByUsername(username);
        if(existingAccount != null) throw new DuplicateUsernameException();

        return accountRepository.save(account);
    }

    //LOGIN
    //public Optional<Account> getAccountByUsername(String username){
        //return accountRepository.getAccountByUserName(username);
    //}
    //POST localhost:8080/login
}
