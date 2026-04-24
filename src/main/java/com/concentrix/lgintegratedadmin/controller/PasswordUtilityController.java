package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.PasswordUtilityApi;
import com.concentrix.lgintegratedadmin.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PasswordUtilityController implements PasswordUtilityApi {

    private final PasswordGenerator passwordGenerator;

    @Override
    public String encode(String password) {
        return passwordGenerator.encodePassword(password);
    }

    @Override
    public boolean match(String raw, String encoded) {
        return passwordGenerator.matches(raw, encoded);
    }
}
