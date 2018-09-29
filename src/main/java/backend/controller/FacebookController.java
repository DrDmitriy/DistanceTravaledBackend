package backend.controller;

import backend.entity.Role;
import backend.forms.UserFormData;
import backend.service.UserService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FacebookController {

    private final UserService userService;

    public FacebookController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @CrossOrigin("*")
    public String getNotification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map data = (HashMap) ((OAuth2Authentication) request.getUserPrincipal()).getUserAuthentication().getDetails();
        String email = data.get("email").toString();
        String names = data.get("name").toString();
        String name = names.substring(0, names.indexOf(" "));
        String surname = names.substring(names.indexOf(" ") + 1, names.length());
        userService.saveUser(UserFormData.builder().email(email).password(" ").name(name).surname(surname).build());
        if (userService.getByEmail(email).getRole().equals(Role.USER)) {
            userService.joinFacebook(email, true);
            response.sendRedirect("http://localhost:4200/home?email=" + email);
            return "hi";
        } else {
            return "FORBIDDEN";
        }
    }
}