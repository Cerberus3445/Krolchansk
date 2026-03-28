package ru.krolchansk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Profile("prod")
@Configuration
public class ProdSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(request ->
                request.requestMatchers("/", "/css/**", "/images/**",
                                "/js/**", "/order", "/order-created", "/actuator/health", "/api/v1/images/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetails() {
        UserDetails user = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$12$aolOaYy2pOBBYQIc0K4C2uJshhh5yx9.EO41z2vdFKGP.dkUOMk8.")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
