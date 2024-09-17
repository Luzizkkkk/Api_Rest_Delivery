package br.edu.ifpr.api_delivery.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

   @Autowired
    private FiltroToken filtroToken;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf((csfr) -> csfr.disable()).sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .authorizeHttpRequests(authorize ->
                       authorize.requestMatchers(HttpMethod.POST, "/usuarios/login", "/usuarios/registrar")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                                .addFilterBefore(filtroToken, UsernamePasswordAuthenticationFilter.class)
                                .build();
    };

}
