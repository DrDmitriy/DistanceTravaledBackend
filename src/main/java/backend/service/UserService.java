package backend.service;

import backend.forms.UserFormSignUp;
import backend.entity.Role;
import backend.entity.UserEntity;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity saveUser(UserFormSignUp userFormSignUp) {
        if (userRepository.getUserByEmail(userFormSignUp.getEmail()) == null) {
            UserEntity userEntity = UserEntity
                    .builder()
                        .email(userFormSignUp.getEmail())
                        .password(passwordEncoder.encode(userFormSignUp.getPassword()))
                        .name(userFormSignUp.getName())
                        .surname(userFormSignUp.getSurname())
                        .role(Role.USER)
                    .build();
            userRepository.save(userEntity);
            return userEntity;
        } else { return null; }
    }

    public UserEntity getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }


}
