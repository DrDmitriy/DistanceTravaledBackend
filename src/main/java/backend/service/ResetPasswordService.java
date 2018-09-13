package backend.service;

import backend.entity.UserEntity;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ResetPasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntity updatePasswordByEmail(String email, String password) {
        if (email == null || password == null) {
            return null;
        } else {
            UserEntity userEntity = userRepository.getUserByEmail(email);
            userEntity.setPassword(passwordEncoder.encode(password));
            userRepository.save(userEntity);
            return userEntity;
        }
    }
}
