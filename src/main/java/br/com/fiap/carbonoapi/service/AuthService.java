package br.com.fiap.carbonoapi.service;

import br.com.fiap.carbonoapi.dto.request.LoginRequest;
import br.com.fiap.carbonoapi.dto.response.LoginResponse;
import br.com.fiap.carbonoapi.entity.Usuario;
import br.com.fiap.carbonoapi.exception.ResourceNotFoundException;
import br.com.fiap.carbonoapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse autenticar(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario.getEmail(), usuario.getRole().name());

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .email(usuario.getEmail())
                .role(usuario.getRole().name())
                .build();
    }
}