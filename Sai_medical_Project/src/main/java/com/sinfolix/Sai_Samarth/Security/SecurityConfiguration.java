package com.sinfolix.Sai_Samarth.Security;

import com.sinfolix.Sai_Samarth.Config.CrossConfig;
import com.sinfolix.Sai_Samarth.Filter.JwtFilter;
import com.sinfolix.Sai_Samarth.service.Impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Autowired
    private CrossConfig crossConfig;
    @Autowired
    private JwtFilter jwtFilter;

    private final CustomUserDetailsServiceImpl userDetailsService;

    public SecurityConfiguration(CustomUserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(cors -> cors.configurationSource(crossConfig.corsConfigurationSource()))
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers("/public/**","/product/**","/oauth2/**","/swagger/**").permitAll();
                    authorize.requestMatchers("/api/v1/orders/**","/user/**","/product/**").authenticated();
                    authorize.requestMatchers("/admin/**").hasRole("ADMIN");
                    authorize.anyRequest().permitAll();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Default session management
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

//                .oauth2Login(oauth2login -> {
//                oauth2login.successHandler(( request,  response,  authentication) -> response.sendRedirect("/home"));
//                })
//                 .oauth2Login(oauth2login -> {
//                     oauth2login.defaultSuccessUrl("/public/loginSuccess",true);
//                 })

                .build();
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login")
//                        .successHandler((request, response, authentication) -> {
//                            // Redirect to the home page on success
//                            response.sendRedirect("/home");
//                        })
//                )


//                .exceptionHandling(exception->{
//                   exception.authenticationEntryPoint((request, response, authException) -> {
//                       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                       response.getWriter().write("Unauthorized: Authentication token required.");
//                   });
//                })


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}