package com.library.Smart_Library.controller;

import com.library.Smart_Library.model.Transaction;
import com.library.Smart_Library.model.User;
import com.library.Smart_Library.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

/**
 * Owner: Prajwal Kittali
 * SRN: PES2UG23CS419
 * Purpose: Security module controller for login and dashboard navigation.
 * GRASP: Controller as the first layer for UI events.
 * Pattern: Singleton collaborator injection through Spring IoC.
 */
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Redirects application root to login page.
     * GRASP: Controller by routing the initial UI entry request.
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    /**
     * Prepares login form model.
     * GRASP: Controller for view-model assembly at presentation boundary.
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "authRequired", required = false) String authRequired,
            Model model,
            HttpSession session) {
        if (logout != null) {
            session.invalidate();
            model.addAttribute("success", "You have been logged out successfully.");
        }
        if (authRequired != null) {
            model.addAttribute("authRequired",
                    "Please login first. Access to dashboard and module pages is restricted.");
        }
        model.addAttribute("user", new User());
        return "login";
    }

    /**
     * Processes credentials and starts user session on successful authentication.
     * GRASP: Controller delegates business rules to AuthService.
     */
    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") User loginRequest, Model model, HttpSession session) {
        AuthService.LoginResult result = authService.authenticate(loginRequest.getUsername(),
                loginRequest.getPassword());

        if (result.status() == AuthService.LoginStatus.SUCCESS) {
            session.setAttribute("loggedInUser", result.username().orElse("unknown"));
            return "redirect:/dashboard";
        }

        if (result.status() == AuthService.LoginStatus.LOCKED) {
            model.addAttribute("locked", result.message());
        } else {
            model.addAttribute("error", result.message());
            if (result.attemptsRemaining() != null) {
                model.addAttribute("attemptsRemaining", result.attemptsRemaining());
            }
        }
        return "login";
    }

    /**
     * Loads dashboard model after login.
     * GRASP: Controller builds page model and keeps UI flow coordination.
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "dashboard";
    }
}