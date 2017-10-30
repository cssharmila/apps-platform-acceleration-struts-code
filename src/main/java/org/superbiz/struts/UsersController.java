package org.superbiz.struts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UsersController {

    private final UserRepository userService;

    public UsersController(UserRepository userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/addUser")
    public String addUser(){
        return "addUserForm";
    }

    @PostMapping("/addUser")
    public String addUser(User user, Model model){
        try{
            userService.save(user);
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
            return "addUserForm";
        }
        return "addedUser";
    }

    @GetMapping("/findUser")
    public String findUser(){
        return "findUserForm";
    }

    @PostMapping("/findUser")
    public String findUser(@RequestParam long id, Model model){
        User user = null;
        try{
            user = userService.findOne(id);
        }catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
        }
        if(user == null){
            model.addAttribute("errorMessage", "User not found");
            return "findUserForm";
        }

        model.addAttribute("user", user);
        return "displayUser";
    }

    @GetMapping("/list")
    public String listUsers(Model model){
        model.addAttribute("users",userService.findAll());
        return "displayUsers";
    }
}
