package backend.service;

import backend.entity.UserEntity;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getUserByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        if (userEntity.isFacebook()) {
            return new User(userEntity.getEmail(), new BCryptPasswordEncoder().encode(" "), Collections.emptyList());
        }
        return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
    }
}
