package backend.security;

import backend.common.Constants;
import backend.common.JWTUtils;
import backend.entity.UserEntity;
import backend.repository.UserRepository;
import backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String email;
    @Autowired
    private UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserEntity userEntity = objectMapper
                    .readValue(request.getInputStream(), UserEntity.class);
            email = userEntity.getEmail();
            final UsernamePasswordAuthenticationToken token;
            UserEntity forFacebook = userRepository.getUserByEmail(email);
            if (forFacebook!=null && forFacebook.isFacebook()) {
                token = new UsernamePasswordAuthenticationToken(
                        userEntity.getEmail(), " "
                );
            } else {
                if (userEntity.getPassword()!=null && userEntity.getPassword().equals(" ")) {
                    token = new UsernamePasswordAuthenticationToken(
                            userEntity.getEmail(), ""
                    );
                } else {
                    token = new UsernamePasswordAuthenticationToken(
                            userEntity.getEmail(), userEntity.getPassword()
                    );
                }
            }
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new InternalAuthenticationServiceException("Error while sign in", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        UserEntity userEntity = userRepository.getUserByEmail(email);
        if (userEntity.isFacebook()) {
            userService.joinFacebook(email, false);
        }
        String token = JWTUtils.generateToken(userEntity.getUserID(), userEntity.getEmail(), userEntity.getName(), userEntity.getSurname(), userEntity.getRole());
        response.addHeader(Constants.AUTH_HEADER, Constants.HEADER_PREFIX + token);
        response.addCookie(new Cookie("id", userEntity.getUserID().toString()));
        response.addCookie(new Cookie("role", userEntity.getRole().toString()));
        response.addCookie(new Cookie("name", userEntity.getName()));
        response.addCookie(new Cookie("surname", userEntity.getSurname()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        if (failed instanceof BadCredentialsException || failed.getCause() instanceof BadCredentialsException ||
                failed instanceof UsernameNotFoundException) {
            UserEntity userEntity = userRepository.getUserByEmail(email);
            if (userEntity != null && userEntity.isFacebook()) {
                userService.joinFacebook(email, false);
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
}
