package br.com.fiap.carbonoapi.config;

import br.com.fiap.carbonoapi.service.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UsuarioDetailsService usuarioDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Endpoints públicos
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // Endpoints de leitura — autenticados
                        .requestMatchers(HttpMethod.GET, "/emissoes-carbono").authenticated()
                        .requestMatchers(HttpMethod.GET, "/historico-emissoes/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/empresas-sustentaveis").authenticated()
                        .requestMatchers(HttpMethod.GET, "/empresas-sustentaveis/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/indicadores-sustentabilidade").authenticated()
                        .requestMatchers(HttpMethod.GET, "/impacto-mitigacao/**").authenticated()

                        // Endpoints de escrita — apenas ADMIN e EMPRESA
                        .requestMatchers(HttpMethod.POST, "/relatorio-emissao")
                        .hasAnyRole("ADMIN", "EMPRESA")
                        .requestMatchers(HttpMethod.POST, "/agendamento-reducao-carbono")
                        .hasAnyRole("ADMIN", "EMPRESA")
                        .requestMatchers(HttpMethod.PUT, "/agendamento-reducao-carbono/**")
                        .hasAnyRole("ADMIN", "EMPRESA")
                        .requestMatchers(HttpMethod.DELETE, "/agendamento-reducao-carbono/**")
                        .hasAnyRole("ADMIN", "EMPRESA")
                        .requestMatchers(HttpMethod.POST, "/compensacao-carbono")
                        .hasAnyRole("ADMIN", "EMPRESA")

                        // Endpoints exclusivos do ADMIN
                        .requestMatchers(HttpMethod.POST, "/empresas-sustentaveis")
                        .hasRole("ADMIN")

                        // Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}