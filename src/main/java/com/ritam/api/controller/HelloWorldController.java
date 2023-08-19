package com.ritam.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    private MessageSource messageSource;

    @Autowired
    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-i18n")
    public String helloWorldI18N(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("wish.message", null, "Default Message", locale);
    }
}
