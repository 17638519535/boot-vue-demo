package myf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vue")
public class IndexController {

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("name","涨三倍");
        return "index";
    }
}
