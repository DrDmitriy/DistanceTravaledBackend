package backend.controller;

import backend.common.Constants;
import backend.common.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TestTokenExpiration {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response) {
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        List<String> data = JWTUtils.getAudience(token);
        response.addCookie(new Cookie("role", data.get(4)));
        response.addCookie(new Cookie("name", data.get(2)));
        response.addCookie(new Cookie("surname", data.get(3)));
        return "hello";
    }

    @RequestMapping(value = "/test-admin", method = RequestMethod.GET)
    public ResponseEntity<?> testAdmin(HttpServletRequest request, HttpServletResponse response) {
        final String token = request.getHeader(Constants.AUTH_HEADER).substring(7);
        List<String> data = JWTUtils.getAudience(token);
        if (data.get(4).equals("ADMIN")) {
            response.addCookie(new Cookie("role", data.get(4)));
            response.addCookie(new Cookie("name", data.get(2)));
            response.addCookie(new Cookie("surname", data.get(3)));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
