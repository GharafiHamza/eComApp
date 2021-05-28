package com.eCommerce.eComApp.security;

import com.eCommerce.eComApp.model.User;
import com.eCommerce.eComApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    UserRepository rep;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=rep.findByUserName(username).get();
        UserPrincipal userPrincipal= new UserPrincipal(user);
        return userPrincipal;
    }

}
