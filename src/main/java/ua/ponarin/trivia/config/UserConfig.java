package ua.ponarin.trivia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {
    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails userA = users
                .username("userA")
                .password("123")
                .roles("USER")
                .build();
        UserDetails userB = users
                .username("userB")
                .password("123")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(userA, userB, admin);
    }
}
