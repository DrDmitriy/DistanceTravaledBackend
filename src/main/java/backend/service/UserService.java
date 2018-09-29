package backend.service;

import backend.forms.UserFormData;
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

    public UserEntity saveUser(UserFormData userFormData) {
        if (userRepository.getUserByEmail(userFormData.getEmail()) == null) {
            UserEntity userEntity = UserEntity
                    .builder()
                        .email(userFormData.getEmail())
                        .password(passwordEncoder.encode(userFormData.getPassword()))
                        .name(userFormData.getName())
                        .surname(userFormData.getSurname())
                        .role(Role.USER)
                        .facebook(false)
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

    public UserEntity joinFacebook(String email, boolean isFacebook) {
        if (email == null) {
            return null;
        } else {
            UserEntity userEntity = userRepository.getUserByEmail(email);
            userEntity.setFacebook(isFacebook);
            userRepository.save(userEntity);
            return userEntity;
        }
    }
}
