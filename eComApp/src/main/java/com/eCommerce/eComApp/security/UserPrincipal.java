package com.eCommerce.eComApp.security;

import com.eCommerce.eComApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    User user;

    @Autowired
    public UserPrincipal(User user) {
        super();
        this.user=user;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        GrantedAuthority authority= new SimpleGrantedAuthority("ROLE_"+this.user.getRole());
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {

        return this.user.getPassword();
    }

    @Override
    public String getUsername() {

        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
