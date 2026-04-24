package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.MessageApi;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class MessageController implements MessageApi {

    private final MessageSource messageSource;

    public MessageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getWelcome(Locale locale) {
        System.out.println("Locale: " + locale + " :: " + LocaleContextHolder.getLocale());
        return messageSource.getMessage("welcome.message", null, locale);
    }

    @Override
    public String getUser(String name) {
        return messageSource.getMessage("user.name", new Object[]{name}, LocaleContextHolder.getLocale());
    }
}
