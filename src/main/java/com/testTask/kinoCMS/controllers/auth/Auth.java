package com.testTask.kinoCMS.controllers.auth;

import com.testTask.kinoCMS.controllers.dto.RegistrationDTO;
import com.testTask.kinoCMS.entity.user.User;
import com.testTask.kinoCMS.services.UserService;
import com.testTask.kinoCMS.services.defaultRestData.AlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Auth {
    @Autowired
    private UserService userService;

    @PostMapping(name = "/registration")
    public ResponseEntity<Object> registration(@RequestBody RegistrationDTO data) {
        try {
            Map<String, Object> response = new HashMap<>();
            User user = userService.register(data);
            response.put("id", user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AlreadyExistException e) {
            return ResponseEntity.ok("User with this username already exist.");
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Object> login(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loadUserByUsername(username));
    }

    @PostMapping("/login-success")
    public ResponseEntity<String> loginSuccess() {
        return ResponseEntity.ok().body("Login success");
    }

    @PostMapping("/login-fail")
    public ResponseEntity<String> loginFail() {
        return ResponseEntity.ok().body("Username or password is wrong");
    }

    @GetMapping("/logout-success")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok().body("Logout success");
    }

}
