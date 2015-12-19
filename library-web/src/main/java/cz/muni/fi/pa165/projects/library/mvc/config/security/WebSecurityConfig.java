package cz.muni.fi.pa165.projects.library.mvc.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static cz.muni.fi.pa165.projects.library.mvc.config.security.UserRoles.ADMIN;
import static cz.muni.fi.pa165.projects.library.mvc.config.security.UserRoles.LIBRARIAN;

/**
 * @author jkaspar
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
        .and()
            .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("both").password("123").authorities(ADMIN, LIBRARIAN).and()
            .withUser("admin").password("123").authorities(ADMIN).and()
            .withUser("librarian").password("123").authorities(LIBRARIAN);
    }
}
