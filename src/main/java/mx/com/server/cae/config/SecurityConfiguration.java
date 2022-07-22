package mx.com.server.cae.config;

import mx.com.server.cae.security.auth.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

    @Value("${authentication.url}")
    private String authenticationUrl;

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new MyAuthenticationProvider();
    }

    //Used to handle user authentication
    //Injected into the endpoints method in the class
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider());
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
                .antMatchers(authenticationUrl).permitAll()
                // Our private endpoints
                .anyRequest().authenticated();

    }

    @Override
    public void configure(WebSecurity web) {
        // Tell Spring to ignore securing the handshake endpoint. This allows the handshake to take place unauthenticated
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

}
