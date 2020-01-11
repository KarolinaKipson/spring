package hr.kipson.karolina.ecommerce.controller;

import hr.kipson.karolina.ecommerce.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @RequestMapping(value="/emusicstore")
    public String home(){
        return "index";
    }

    // Login form
    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }


}
