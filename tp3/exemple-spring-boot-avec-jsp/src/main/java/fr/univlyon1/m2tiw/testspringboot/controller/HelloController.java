package fr.univlyon1.m2tiw.testspringboot.controller;

import fr.univlyon1.m2tiw.testspringboot.beans.HelloBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class HelloController {
    private HelloBean helloBean;

    @Autowired
    public HelloController(HelloBean helloBean) {
        this.helloBean = helloBean;
    }

    @GetMapping
    public ModelAndView hello(@RequestParam(name="name") String name) {
        helloBean.setUser(name);
        Map<String, Object> map = new HashMap<>();
        map.put("message", helloBean.getGreetings());
        return new ModelAndView("hello", map);
    }
}
