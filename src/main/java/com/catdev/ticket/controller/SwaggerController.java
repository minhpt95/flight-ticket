package com.catdev.ticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class SwaggerController {
    @RequestMapping(method = RequestMethod.GET)
    public String swaggerUi() {
        return "redirect:/swagger-ui/";
    }
}
