package pl.thinkdata.b2bbase.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Profile("prod")
@Configuration
public class SecurityConfig {

    private String secret;

    public SecurityConfig(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationManager authenticationManager,
                                           UserDetailsService userDetailsService) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/catalog/**").permitAll()
                .requestMatchers("/blog/**").permitAll()
                .requestMatchers("/category/**").permitAll()
                .requestMatchers("/search/**").permitAll()
                .requestMatchers("/remember-password/**").permitAll()
                .requestMatchers("/send-password/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/verify/**").permitAll()
                .requestMatchers("/branch/**").hasRole("USER")
                .requestMatchers("/company/**").hasRole("USER")
                .requestMatchers("/social/**").hasRole("USER")
                .requestMatchers("/img/get/**").permitAll()
                .requestMatchers("/img/upload/**").hasRole("USER")
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/page/**").permitAll()
                .requestMatchers("/htmlpage/**").permitAll()
                .requestMatchers("/sitemap.xml").permitAll()
                .requestMatchers("/sendEmail/**").hasRole("USER")
                .requestMatchers("/subscription/**").hasRole("USER")
                .requestMatchers("/pricelist/**").permitAll()
                .anyRequest().denyAll()
        );
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailsService, secret));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }
}
