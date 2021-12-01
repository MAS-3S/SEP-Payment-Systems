package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.AuthenticatedUserDTO;
import com.example.webshopbackend.model.User;
import com.example.webshopbackend.repository.UserRepository;
import com.example.webshopbackend.security.TokenUtils;
import com.example.webshopbackend.security.UserTokenState;
import com.example.webshopbackend.security.auth.JwtAuthenticationRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    protected final Log log = LogFactory.getLog(getClass());

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
        log.info("Signing up user with email: " + authenticationRequest.getEmail());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User account = (User) authentication.getPrincipal();
        if (!account.isRegistered()) {
            return new ResponseEntity<>("Verify profile before using application", HttpStatus.UNAUTHORIZED);
        }
        String jwt = tokenUtils.generateToken(account.getEmail());
        String refresh = tokenUtils.refreshToken(jwt, account.getId());
        int expiresIn = tokenUtils.getExpiredIn();
        authenticatedUserDTO = new AuthenticatedUserDTO(account.getId(), account.getEmail(), account.getRole().toString(), new UserTokenState(jwt, expiresIn, refresh));

        return new ResponseEntity<>(authenticatedUserDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {

        log.info("Getting new access token");
        String authHeader = request.getHeader(AUTHORIZATION);
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = this.tokenUtils.getToken(request);
                String id = this.tokenUtils.getUsernameFromToken(refreshToken);
                User user = userRepository.findById(id);
                if (user == null) {
                    log.error("Using access token instead of refresh token");
                    return new ResponseEntity<>("Use refresh token for creating new access token!", HttpStatus.UNAUTHORIZED);
                }
                String accessToken = tokenUtils.generateToken(user.getEmail());
                int expiresIn = tokenUtils.getExpiredIn();

                AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO(user.getId(), user.getEmail(), user.getRole().toString(), new UserTokenState(accessToken, expiresIn, refreshToken));
                return new ResponseEntity<>(authenticatedUserDTO, HttpStatus.OK);

            } catch (Exception exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
            }

        } else {
            log.error("Missing refresh token");
            return new ResponseEntity<>("Missing refresh token", HttpStatus.BAD_REQUEST);
        }

    }
}
