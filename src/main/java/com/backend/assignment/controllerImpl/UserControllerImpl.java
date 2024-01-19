package com.backend.assignment.controllerImpl;

import com.backend.assignment.constants.UserConstants;
import com.backend.assignment.controller.UserController;
import com.backend.assignment.service.UserService;
import com.backend.assignment.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> sayHello() {
        return UserUtil.getResponseEntity("Hello from GreenStitch",HttpStatus.OK);
    }
}
