package br.com.gabriel.mutualtls;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        UserDetails currentUser =
                (UserDetails) ((Authentication) principal).getPrincipal();
        model.addAttribute("username", currentUser.getUsername());

        return "user";
    }
}
