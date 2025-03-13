package com.marcosporto.demo_product_api.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private com.marcosporto.demo_product_api.entity.User appUser;

    public JwtUserDetails(com.marcosporto.demo_product_api.entity.User user) {
        super(user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.appUser = user;
    }

    public Long getId() {
        return this.appUser.getId();
    }

    public String getRole() {
        return this.appUser.getRole().name();
    }
}
