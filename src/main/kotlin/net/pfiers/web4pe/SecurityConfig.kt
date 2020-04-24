package net.pfiers.web4pe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
class SecurityConfig() : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .headers().frameOptions().sameOrigin()
            .and()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/css/**").permitAll()
            .antMatchers("/images/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/blog").permitAll()
            .antMatchers("/ws").permitAll()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/", true) //.failureUrl("/login.html?error=true")
//                .failureHandler(authenticationFailureHandler())
            .and()
            .logout()
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/")
//                .logoutSuccessHandler(logoutSuccessHandler())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}