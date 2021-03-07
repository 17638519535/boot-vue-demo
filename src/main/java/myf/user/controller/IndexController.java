package myf.user.controller;

import myf.user.Srevise.UserSevise;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vue")
public class IndexController {
      @Resource
      private UserSevise userSevise;

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("name","涨三倍");
        userSevise.test();
        return "/index";
    }


    @RequestMapping("/thread-test")
    public void ThreadTest(Model model){
    }


    @RequestMapping("/user")
    public String user(Model model){
//        model.addAttribute("name","涨三倍");
        return "/menu/user";
    }
    @RequestMapping("/dept")
    public String dept(Model model){
        model.addAttribute("name","涨三倍");
        return "/menu/dept";
    }
    @RequestMapping("/findMenu")
    public ResponseEntity findMenu(Model model){
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (int i=0;i<2;i++){
            Map<String,Object> map1 = new HashMap<>();
            if(i==0){
                map1.put("system","dept");
                map1.put("dept","dept"+i);
                map1.put("url","/vue/dept");
            }else{
                map1.put("system","user");
                map1.put("dept","user"+i);
                map1.put("url","/vue/user");
            }

            mapList.add(map1);
        }
        model.addAttribute("list",mapList);
      return   ResponseEntity.ok(mapList);
    }
}
