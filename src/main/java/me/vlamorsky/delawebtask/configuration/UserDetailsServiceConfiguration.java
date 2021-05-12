package me.vlamorsky.delawebtask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsServiceConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        org.springframework.security.core.userdetails.UserDetails firstUser =
                User.builder()
                        .username("first")
                        .password("123qweASD")
                        .authorities("USER")
                        .passwordEncoder(passwordEncoder::encode)
                        .build();

        org.springframework.security.core.userdetails.UserDetails secondUser =
                User.builder()
                        .username("second")
                        .password("123qweASD")
                        .authorities("USER")
                        .passwordEncoder(passwordEncoder::encode)
                        .build();

        return new InMemoryUserDetailsManager(firstUser, secondUser);
    }
}
