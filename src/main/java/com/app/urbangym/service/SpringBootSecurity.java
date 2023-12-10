package com.app.urbangym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity // Intercetor entre el usuario y la aplicación
public class SpringBootSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    // Valida usuario
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getEncoder());
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Restringe vistast al usuario
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/urbangym/admin/coach/**").hasRole("ADMIN")
                .antMatchers("/urbangym/admin/plan/**").hasRole("ADMIN")
                .antMatchers("/urbangym/admin/**").hasRole("ADMIN")
                .antMatchers("/urbangym/admin/user/**").hasRole("ADMIN")
                .antMatchers("/urbangym/coach/**").hasAnyRole("COACH")
                .antMatchers("/urbangym/login/**").permitAll()
                .antMatchers("/urbangym/register/**").permitAll()
                .antMatchers("/urbangym/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/urbangym/login")
                .defaultSuccessUrl("/urbangym/login/acceder")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/urbangym/login/logout")
                .logoutSuccessUrl("/urbangym?logout_success")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");
    }

}
