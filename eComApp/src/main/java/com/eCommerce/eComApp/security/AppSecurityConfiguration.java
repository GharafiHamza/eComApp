package com.eCommerce.eComApp.security;

import com.eCommerce.eComApp.exceptions.NotFoundException;
import com.eCommerce.eComApp.model.Admin;
import com.eCommerce.eComApp.service.AdminService;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Autowired
    AdminService adminService;

    UserPrincipalDetailsService service;

    @Autowired
    public AppSecurityConfiguration(UserPrincipalDetailsService service) {

        this.service = service;
    }

    @PostConstruct
    public void init() {
        List<Admin> currentAdminList= new ArrayList<Admin>();
        try {
            currentAdminList = adminService.getAllAdmins();
        } catch (NotFoundException e) {
            Admin    admin    = new Admin();
            admin.setUserName("admin");
            admin.setPassword("admin");
            admin.setEmail("ensa.backend@gmail.com");
            admin.setRole("Admin");
            adminService.createAdmin(admin);

        }


    }

    @Bean
    public DaoAuthenticationProvider autProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        return provider;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(ImmutableList.of("accept",
                "accept-encoding",
                "authorization",
                "content-type",
                "dnt",
                "origin",
                "user-agent",
                "x-csrftoken",
                "x-requested-with"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .authorizeRequests()
                //ADMIN
                .antMatchers(HttpMethod.GET,"/admins").hasRole("Admin")		//afficher les admins
                .antMatchers(HttpMethod.GET,"/admin/{id}").hasRole("Admin")		//admin par username
                .antMatchers(HttpMethod.POST,"/admins").hasRole("Admin")		//creer les admins
                .antMatchers(HttpMethod.PUT,"/admin/{id}").hasRole("Admin")	//modifier un admin
                .antMatchers(HttpMethod.DELETE,"/admin/{id}").hasRole("Admin")	//supprimer un admin

                //MARCHAND
                .antMatchers(HttpMethod.POST,"/marchands").hasRole("Admin")		//creer marchand
                .antMatchers(HttpMethod.PUT,"/marchand/{id}").hasRole("Admin")	//modifier marchand
                .antMatchers(HttpMethod.DELETE,"/marchand/{id}").hasRole("Marchand")	//supprimer marchand
                .antMatchers(HttpMethod.GET,"/marchand/{id}").hasRole("Admin")		//marchand par id
                .antMatchers(HttpMethod.GET,"/marchands").hasRole("Admin")		//afficher les marchand

                //CLIENT
                .antMatchers(HttpMethod.POST,"/clients").hasRole("Marchand")		//creer client
                .antMatchers(HttpMethod.GET,"/clients").hasRole("Admin")		//afficher client
                .antMatchers(HttpMethod.GET,"/client/{id}").hasRole("Client")		//client par id
                .antMatchers(HttpMethod.PUT,"/client/{id}").hasRole("Client")	//modifier client
                .antMatchers(HttpMethod.DELETE,"/client/{id}").hasRole("Marchand")	//supprimer client

                //ITEM
                .antMatchers(HttpMethod.GET,"/items").hasAnyRole("Marchand","Client")	//afficher items
                .antMatchers(HttpMethod.GET,"/items").hasAnyRole("Marchand","Client")	//afficher item
                .antMatchers(HttpMethod.POST,"/items").hasRole("Marchand")	//creer item
                .antMatchers(HttpMethod.PUT,"/item/{id}").hasRole("Marchand")	//modifier item
                .antMatchers(HttpMethod.DELETE,"/item/{id}").hasRole("Marchand")	//supprimer item
                .antMatchers(HttpMethod.GET,"/item/{categorie}").hasRole("Client")	//afficher item par categorie

                .and()
                .httpBasic()
                .and()
                .csrf().disable()
        ;



        super.configure(http);
    }
}
