package br.edu.ifpr.api_delivery.seguranca;

import java.io.IOException;
import java.util.Optional;

import br.edu.ifpr.api_delivery.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.ifpr.api_delivery.model.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroToken extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/usuarios/registrar") || requestURI.contains("/usuarios/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        var authenticationHeader = request.getHeader("Authorization");

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            String token = authenticationHeader.substring(7);

            Optional<Usuario> usuario = usuarioRepositorio.findByChave(token);

            if (usuario.isPresent()) {
                var authentication = new UsernamePasswordAuthenticationToken(usuario.get(), null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
