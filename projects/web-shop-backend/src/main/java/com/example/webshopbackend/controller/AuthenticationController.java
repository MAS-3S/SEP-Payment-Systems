package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.AuthenticatedUserDTO;
import com.example.webshopbackend.model.User;
import com.example.webshopbackend.security.TokenUtils;
import com.example.webshopbackend.security.UserTokenState;
import com.example.webshopbackend.security.auth.JwtAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response) {
        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User account = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(account.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();
        authenticatedUserDTO = new AuthenticatedUserDTO(account.getId(), account.getEmail(), account.getRole(), new UserTokenState(jwt, expiresIn));

        return new ResponseEntity<>(authenticatedUserDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String accessToken = this.tokenUtils.getToken(request);
        String email = this.tokenUtils.getUsernameFromToken(accessToken);

        User user = (User) this.userDetailsService.loadUserByUsername(email);

        String refreshToken = this.tokenUtils.refreshToken(accessToken);
        int expiresIn = this.tokenUtils.getExpiredIn();
        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO(user.getId(), user.getEmail(), user.getRole(), new UserTokenState(refreshToken, expiresIn));
        return new ResponseEntity<>(authenticatedUserDTO, HttpStatus.OK);

    }
}
