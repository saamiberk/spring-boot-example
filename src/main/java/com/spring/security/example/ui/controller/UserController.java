package com.spring.security.example.ui.controller;

import com.spring.security.example.exceptions.UserServiceException;
import com.spring.security.example.service.UserService;
import com.spring.security.example.shared.dto.UserDto;
import com.spring.security.example.ui.model.request.UserDetailsRequestModel;
import com.spring.security.example.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("users")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE,
                                MediaType.APPLICATION_JSON_VALUE })
    public UserRest getUser(@PathVariable String id){

        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }


    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) throws UserServiceException {

        if(userDetailsRequestModel.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages());

        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetailsRequestModel){

        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);

        UserDto updateUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updateUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public OperationStatusModel deleteUser(@PathVariable String id){

        OperationStatusModel returnValue = new OperationStatusModel(
                RequestOperationName.DELETE.name(), RequestOperationStatus.SUCCESS.name()
        );

        userService.deleteUser(id);

        return returnValue;

    }


}
