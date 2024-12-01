package com.kamil.stockpredictor.controller;


import com.kamil.stockpredictor.Prediction;
import com.kamil.stockpredictor.repository.UserRepository;
import com.kamil.stockpredictor.serviceLayer.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.kamil.stockpredictor.User;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String showRegistrationForm(){
        return "register"; // looks for register.html
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email) {

        try {
            // Encode the password
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User(username, encodedPassword, email);

            // Save the user to the database
            userRepository.save(user);

            System.out.println("User saved successfully: " + username);

            // Authenticate the user after registration
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Set the authentication to the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect to home page after successful registration
            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
            // Return back to the registration page if there's an error
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login"; // return login page view
    }

//    // testing method
//    @GetMapping("/testsave")
//    @ResponseBody
//    public String testSave(){
//        User testUser = new User("Testuser", passwordEncoder.encode("testpassword"), "testuser@gmail.com");
//        userRepository.save(testUser);
//        return "User saved manually";
//    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model){
        User user = userRepository.findByUsername(username);

        if(user != null && passwordEncoder.matches(password, user.getPassword())) {
            // authenticating user manually
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // redirect to search page if login is approved
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // reload login.html with error message
        }
    }
//
//    @GetMapping("/search")
//    public String showSearchPage(){
//        return "search";
//    }

    @GetMapping("/home")
    public String showHomePage(){
        return "home";
    }

    @GetMapping("/search/prediction")
    public String getStockPrediction(@RequestParam String symbol, Model model){
        // fetch stock prediction from the database
        Prediction prediction = predictionService.getPredictionByStock(symbol);

        if(prediction != null){
            model.addAttribute("symbol", symbol);
            model.addAttribute("predictedPrice", prediction.getPredictedPrice());
            model.addAttribute("confidence", prediction.getConfidence());
        } else {
            model.addAttribute("error", "No prediction available for this stock");
        }

        return "search-result"; // html template to show the result

    }
}
