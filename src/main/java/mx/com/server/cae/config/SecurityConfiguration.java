package mx.com.server.cae.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected static final String[] AUTH_WHITELIST = {
        "/", "/csrf", "/swagger/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/configuration/**", "/v3/api-docs/**"
    };

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http
                .cors()
                .and()
                .csrf().disable();

        http
                .formLogin().disable()
                .httpBasic().disable();

        // Set permissions on endpoints
        http
                .authorizeRequests()
                // Our public endpoints
                .antMatchers("/**").permitAll()
                // Our private endpoints
                .anyRequest().authenticated();

    }

    @Override
    public void configure(WebSecurity web) {
        // Tell Spring to ignore securing the handshake endpoint. This allows the handshake to take place unauthenticated
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

}
