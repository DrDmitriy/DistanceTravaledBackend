package backend.security;


import backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfigureAuthentication extends WebSecurityConfigurerAdapter {


    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public ConfigureAuthentication(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(corsFilter(), ChannelProcessingFilter.class);
        http
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/signUp", "/reset-password").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilter(authenticationFilter())
                    .addFilterBefore(authorizationFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/join", "POST"));
        return authenticationFilter;
    }
}
