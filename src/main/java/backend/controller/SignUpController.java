package backend.controller;

import backend.forms.UserFormSignUp;
import backend.entity.UserEntity;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/signUp")
public class SignUpController {

    private final UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postSignUp(@RequestBody UserFormSignUp userFormSignUp) {
        UserEntity user = userService.saveUser(userFormSignUp);
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
