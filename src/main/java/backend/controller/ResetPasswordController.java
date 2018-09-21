package backend.controller;

import backend.common.Constants;
import backend.common.JWTUtils;
import backend.forms.UserFormData;
import backend.entity.UserEntity;
import backend.repository.UserRepository;
import backend.service.EmailService;
import backend.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ResetPasswordController {

    private final EmailService mailService;
    private final UserRepository userRepository;
    private final ResetPasswordService resetPasswordService;

    @Autowired
    public ResetPasswordController(EmailService mailService, UserRepository userRepository, ResetPasswordService resetPasswordService) {
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.resetPasswordService = resetPasswordService;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ResponseEntity<?> resetRequest(@RequestBody UserFormData userFormData) {
        if (userRepository.getUserByEmail(userFormData.getEmail()) == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            mailService.sendMail(userFormData.getEmail());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/do-reset-password", method = RequestMethod.PUT)
    public ResponseEntity<?> putNewPasswordRequest(@RequestBody UserFormData userFormData, HttpServletRequest request) {
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        final String email = JWTUtils.getAudience(token).get(1);
        UserEntity userEntity = resetPasswordService.updatePasswordByEmail(email, userFormData.getPassword());
        if (userEntity==null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}