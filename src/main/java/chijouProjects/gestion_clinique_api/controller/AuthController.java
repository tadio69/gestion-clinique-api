package chijouProjects.gestion_clinique_api.controller;

import chijouProjects.gestion_clinique_api.dto.AuthResponseDTO;
import chijouProjects.gestion_clinique_api.dto.LoginRequest;
import chijouProjects.gestion_clinique_api.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private AuthenticationManager authManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String token = jwtUtils.generateToken(loginRequest.username(), role);
        return ResponseEntity.ok(new AuthResponseDTO(token, loginRequest.username(), role));
    }
}
