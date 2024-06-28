package br.com.gabriel.mutualtls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class X509AuthenticationServer {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest()
                        .authenticated())
                .x509(x509 -> x509.subjectPrincipalRegex("CN=(.*?)(?:,|$)"))
                .userDetailsService(userDetailsService());

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            if (username.equals("Gabriel")) {
                return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
            }

            throw new UsernameNotFoundException("User not found");
        };
    }
}
