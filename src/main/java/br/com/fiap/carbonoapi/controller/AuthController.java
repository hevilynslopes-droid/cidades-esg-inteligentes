package br.com.fiap.carbonoapi.controller;

import br.com.fiap.carbonoapi.dto.request.LoginRequest;
import br.com.fiap.carbonoapi.dto.response.LoginResponse;
import br.com.fiap.carbonoapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.autenticar(request));
    }
}