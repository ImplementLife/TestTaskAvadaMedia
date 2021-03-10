package com.testTask.kinoCMS.controllers.auth;

import com.testTask.kinoCMS.entity.user.User;
import com.testTask.kinoCMS.services.UserService;
import com.testTask.kinoCMS.services.defaultRestData.AlreadyExistException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth")
public class Auth {

    @Autowired
    private UserService userService;

    @Operation(summary = "Registered new user")
    @PostMapping("/registration")
    public ResponseEntity<Object> registration(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String passwordConfirm
    ) {
        if (!password.equals(passwordConfirm)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords not equals");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirm(passwordConfirm);
        try {
            userService.create(user);
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this username already exist.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password) {}

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    public void logout() {}

    @Hidden
    @PostMapping("/login-success")
    public ResponseEntity<String> loginSuccess() {
        return ResponseEntity.ok().body("Login success");
    }

    @Hidden
    @PostMapping("/login-fail")
    public ResponseEntity<String> loginFail() {
        return ResponseEntity.ok().body("Username or password is wrong");
    }

    @Hidden
    @GetMapping("/logout-success")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok().body("Logout success");
    }

}
