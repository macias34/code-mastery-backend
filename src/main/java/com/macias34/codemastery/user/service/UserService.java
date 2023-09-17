package com.macias34.codemastery.user.service;

import com.macias34.codemastery.user.dto.UserDto;
import com.macias34.codemastery.user.entity.UserEntity;
import com.macias34.codemastery.user.mapper.UserMapper;
import com.macias34.codemastery.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public List<UserDto> getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();

        return userEntities.stream().map(userMapper::fromEntityToDto).toList();
    }
}
