package com.lutzarDemos.shoppingdemo.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to confirm a request has been authenticated
 *
 *  * @author      Lutzar
 *  * @version     1.2, 2024/09/17
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    /**
     * Checks to see if the request is authenticated or not
     *
     * @param request               parameters of the request
     * @param response              web server response to client
     * @param authException         String message, Exception
     * @throws IOException          base class for exceptions
     * @throws ServletException     String message, Throwable rootCause
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", "Please log in to your account and try again!");
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
