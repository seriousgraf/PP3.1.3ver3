package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.models.Role;

public class RoleGrantAuthorityImpl implements GrantedAuthority {

    private final Role role;

    public RoleGrantAuthorityImpl(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
