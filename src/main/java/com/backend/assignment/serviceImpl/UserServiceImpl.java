package com.backend.assignment.serviceImpl;

import com.backend.assignment.constants.UserConstants;
import com.backend.assignment.jwt.CustomerUserDetailsService;
import com.backend.assignment.jwt.JwtUtil;
import com.backend.assignment.model.User;
import com.backend.assignment.repository.UserRepository;
import com.backend.assignment.service.UserService;
import com.backend.assignment.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside SignUp {}", requestMap);
        try {
            if(validateSignUpMap(requestMap)){
                User user = userRepository.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userRepository.save(getUserFromMap(requestMap));
                    return UserUtil.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return UserUtil.getResponseEntity("Email already Exist",HttpStatus.BAD_REQUEST);
                }
            } else {
                return UserUtil.getResponseEntity(UserConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String,String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        } else {
            return false;
        }
    }
    private User getUserFromMap(Map<String ,String > requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside Login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+ jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                            customerUserDetailsService.getUserDetail().getStatus())+"\"}",HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\""+"wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials.."+"\"}",HttpStatus.BAD_REQUEST);
    }
}
