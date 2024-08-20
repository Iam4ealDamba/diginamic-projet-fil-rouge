package fr.projet.diginamic.backend.middlewares;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.projet.diginamic.backend.services.JwtService;
import fr.projet.diginamic.backend.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Filter for JWT authentication */
@Configuration
public class JWTAuthMiddleware extends OncePerRequestFilter {

    /** JWT service */
    @Autowired
    private JwtService jwtService;

    /** User service */
    @Autowired
    UserService userService;

    /**
     * Filter method for JWT authentication before each request
     * 
     * @param request     - the request
     * @param response    - the response
     * @param filterChain - the filter chain
     * 
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);
        String username = jwtService.extractUsername(jwtToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (userDetails != null && jwtService.isTokenExpirationValid(jwtToken)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                        userDetails.getPassword(), userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}

