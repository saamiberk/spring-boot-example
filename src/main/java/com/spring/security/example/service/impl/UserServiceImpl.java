package com.spring.security.example.service.impl;

import com.spring.security.example.io.entity.UserEntity;
import com.spring.security.example.io.repo.UserRepository;
import com.spring.security.example.service.UserService;
import com.spring.security.example.shared.MyUtils;
import com.spring.security.example.shared.dto.UserDto;
import com.spring.security.example.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserServiceImpl implements UserService {

    private final int USER_ID_LENGTH = 30;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MyUtils myUtils;

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDto createUser(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()) != null)
            throw new RuntimeException("Record already exist..");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setUserId(myUtils.generateUserId(USER_ID_LENGTH));
        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;

    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null)
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null)
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getFirstName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null)
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
        userRepository.delete(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
