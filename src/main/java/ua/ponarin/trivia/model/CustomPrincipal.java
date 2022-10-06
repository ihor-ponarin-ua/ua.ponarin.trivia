package ua.ponarin.trivia.model;

import lombok.Data;

import java.security.Principal;

@Data
public class CustomPrincipal implements Principal {
    private final String name;

    @Override
    public String getName() {
        return name;
    }
}
