package az.code.auctionbackend.configurations.security;

import az.code.auctionbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().loginPage("/login").defaultSuccessUrl("/index" , true)
                .and()
                .logout().logoutSuccessUrl("/login");

        http.authorizeHttpRequests()
                .requestMatchers("/index").authenticated()
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated();
////                .requestMatchers(HttpMethod.GET, "/test/open", "/test/open/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/test/open", "/test/open/**").permitAll()
////                .requestMatchers(HttpMethod.GET, "/TestServlet", "/TestServlet/**").permitAll()
////                .requestMatchers(HttpMethod.GET, "/home", "/home/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/test/open", "/test/open/**").permitAll()
////                .requestMatchers(HttpMethod.GET, "/test/admin").hasRole(ADMIN)
////                .requestMatchers(HttpMethod.POST, "/test/admin").hasRole(ADMIN)
////                .requestMatchers(HttpMethod.GET, "/test/user").hasRole(USER)
////                .requestMatchers(HttpMethod.POST, "/test/user").hasRole(USER)
////                .requestMatchers(HttpMethod.GET, "/test/both").hasAnyRole(ADMIN, USER)
////                .requestMatchers(HttpMethod.POST, "/test/both").hasAnyRole(ADMIN, USER)
//                .anyRequest().authenticated();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//
        return http.build();
    }



    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            return userRepository.findByUsername(username).map(user -> User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().getName())
                    .roles("ADMIN")
                    .build()).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        };
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
