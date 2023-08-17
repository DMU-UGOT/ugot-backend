package com.dmuIt.global.config;

import com.dmuIt.global.auth.jwt.JwtTokenProvider;
import com.dmuIt.global.filter.JwtAuthenticationFilter;
import com.dmuIt.global.filter.JwtVerificationFilter;
import com.dmuIt.global.handler.MemberAccessDeniedHandler;
import com.dmuIt.global.handler.MemberAuthenticationEntryPoint;
import com.dmuIt.global.handler.MemberAuthenticationFailureHandler;
import com.dmuIt.global.handler.MemberAuthenticationSuccessHandler;
import com.dmuIt.global.utils.CustomAuthorityUtils;
import com.dmuIt.global.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfigure {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthorityUtils authorityUtils;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HttpSecurity builder) throws Exception {
        http.headers().frameOptions().sameOrigin()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .formLogin().disable()
                .rememberMe().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer());


        http.authorizeRequests(authorize -> authorize
//                .antMatchers("/members/signin", "/members/signup", "/members/reissue").permitAll()
//                .antMatchers(HttpMethod.PATCH, "/members/grantBadge/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/members/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/members/**").hasRole("USER")
//
//                .antMatchers(HttpMethod.POST, "/posts/**").hasRole("USER")
//                .antMatchers(HttpMethod.PATCH, "/posts/**/**").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/posts").permitAll()
//                .antMatchers(HttpMethod.GET, "/posts/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/posts/**").hasRole("USER")
//                .antMatchers(HttpMethod.PATCH, "/posts/**/vote/**").hasRole("USER")
//
//
//                .antMatchers(HttpMethod.POST, "/comments").hasRole("USER")
//                .antMatchers(HttpMethod.PATCH, "/comments/**").hasRole("USER")
//                .antMatchers(HttpMethod.PATCH, "/comments/*/vote/*").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/comments/**").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/comments/**").hasRole("USER")
                .anyRequest().permitAll());
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.addAllowedOrigin("http://localhost:3000");
//        configuration.addAllowedOrigin("https://whatsyourmbti.shop");
//        configuration.addAllowedOriginPattern("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider);
            jwtAuthenticationFilter.setFilterProcessesUrl("/members/signin");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtUtils(), authorityUtils);


            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(jwtTokenProvider);
    }

}
