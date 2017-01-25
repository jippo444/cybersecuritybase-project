package sec.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // no real security at the moment
        http.csrf().disable();
//        http.headers().frameOptions().disable();
//        http.headers().xssProtection().disable();
        http.authorizeRequests().antMatchers("/form").authenticated();
        http.authorizeRequests().antMatchers("/admin").hasAuthority("ADMIN");
//        http.authorizeRequests().antMatchers("/signups/**").hasAuthority("ADMIN");
        http.authorizeRequests()
                .anyRequest().permitAll();
        http.sessionManagement().enableSessionUrlRewriting(true);
        http.formLogin().permitAll().and().logout();
        http.formLogin().permitAll();
        http.logout().invalidateHttpSession(false);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
