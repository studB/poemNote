package com.studb.poemNote.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.user.User;
import com.studb.poemNote.domain.user.UserDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.user.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final LogService logService;

    @Transactional(readOnly = true)
    public List<UserDto> findAll(){
        
        try{
            log.info("UserService.findAll");
            return userRepository.findAll().stream()
                    .map(UserDto::new)
                    .collect(Collectors.toList());    

        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }  
        
    }

    public UserDto check(String key){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        try{
            Optional<User> result = userRepository.check(key);
            if(result.isPresent()){
                UserDto user = result.map(UserDto::new).get();
                logService.insertLog("Pre-user", "Login", String.format("[Key Checked] %s", user.getRole()), timestamp);
                log.info("UserService.check : cheked [" + timestamp + "]");
                return user;
            }else{
                logService.insertLog("Pre-user", "Login", "[Key Failed] Wrong Key", timestamp);
                log.warn("UserService.check : wrong key [" + timestamp + "]");
                return null;
            }
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

}