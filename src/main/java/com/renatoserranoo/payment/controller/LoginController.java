package com.renatoserranoo.payment.controller;

import com.renatoserranoo.payment.dto.AuthenticationRequest;
import com.renatoserranoo.payment.dto.AuthenticationResponse;
import com.renatoserranoo.payment.entity.User;
import com.renatoserranoo.payment.service.AuthenticationService;
import com.renatoserranoo.payment.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest authenticationRequest,
                                HttpServletResponse response){

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();
        var name = user.getName();
        var role = user.getRole();
        var token = tokenService.generateToken((User) auth.getPrincipal());

        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
        
        return ResponseEntity.ok(new AuthenticationResponse(name, role));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<String> oauth2LoginSuccess(HttpServletResponse response) {
        try {
            User user = authenticationService.getCurrentAuthenticatedOAuth2User();
            String token = tokenService.generateToken(user);

            String name = user.getName();
            String role = user.getRole().toString();

            Cookie cookie = new Cookie("JWT_TOKEN", token);
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setDomain("localhost");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);

            String htmlResponse = """
                <!DOCTYPE html>
                <html>
                <body>
                    <script>
                        window.opener.postMessage(
                            { 
                                success: true,
                                message: 'Authentication successful',
                                name: '%s',
                                role: '%s'
                            },
                            'http://localhost:5173'
                        );                                                                       
                        window.close();
                    </script>
                </body>
                </html>
            """.formatted(name, role);

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(htmlResponse);

        } catch (Exception e) {
            String errorHtml = """
                <!DOCTYPE html>
                <html>
                <body>
                    <script>
                        window.opener.postMessage(
                            { 
                                success: false,
                                message: 'Authentication failed: %s'
                            }, 
                        );
                        window.close();
                    </script>
                </body>
                </html>
            """.formatted(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_HTML)
                    .body(errorHtml);
        }
    }
}