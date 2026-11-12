import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final PW3TokenService tokenService;
    private final UsuarioRepository repository;

    public SecurityFilter(PW3TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            try {
                var tokenJWT = authorizationHeader.replace("Bearer ", "");
                var subject = tokenService.getSubject(tokenJWT);
                var usuario = repository.findByLogin(subject);
                if (usuario == null) {
                    throw new RuntimeException("Usuário do token não existe");
                }
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"erro\":\"Token JWT inválido ou expirado!\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
