package com.salvatorechiacchio.mygym.config;


import com.salvatorechiacchio.mygym.exception.JwtAccessDeniedHandler;
import com.salvatorechiacchio.mygym.exception.JwtAuthenticationEntryPoint;
import com.salvatorechiacchio.mygym.security.jwt.JWTConfigurer;
import com.salvatorechiacchio.mygym.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public WebSecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint authenticationErrorHandler,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    // Configure BCrypt password encoder =====================================================================

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure paths and requests that should be ignored by Spring Security ================================

    @Override
    public void configure(WebSecurity web) {

        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")

                // allow anonymous resource requests
                .antMatchers(
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/h2-console/**"
                );
    }


    // Configure security settings ===========================================================================

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                //  .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // create no session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/register/user").permitAll()
                .antMatchers("/api/register/admin").permitAll()
                .antMatchers("/category/*").permitAll()
                .antMatchers("/news/*").permitAll()
                .antMatchers("/news/*/*").permitAll()
                .antMatchers("/place/*").permitAll()
                .antMatchers("/referralPerson/*").permitAll()
                .antMatchers("/secretary/*").permitAll()
                .antMatchers("/structure/*").permitAll()
                .antMatchers("/structure/*/*").permitAll()
                .antMatchers("/settingsdesctiption/*").permitAll()
                .antMatchers("/location/*").permitAll()
                .antMatchers("/test/*").permitAll()


                .antMatchers("/api/category/*").hasAuthority("ROLE_SECRETARY")
                .antMatchers("/api/news/*").hasAuthority("ROLE_SECRETARY")
                .antMatchers("/api/referralPerson/*").hasAuthority("ROLE_SECRETARY")
                .antMatchers("/api/secretary/*").hasAuthority("ROLE_SECRETARY")
                .antMatchers("/api/structure/*").hasAuthority("ROLE_SECRETARY")

                .antMatchers("/api/testUser").hasAuthority("ROLE_USER")
                .antMatchers("/api/testSecretary").hasAuthority("ROLE_SECRETARY")
                .antMatchers("/api/testAdminSecretary").hasAuthority("ROLE_ADMIN_SECRETARY")
                .antMatchers("/api/testAdmin").hasAuthority("ROLE_ADMIN")
                .antMatchers("/**").permitAll()

                .anyRequest().authenticated()

                .and()
                .apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
