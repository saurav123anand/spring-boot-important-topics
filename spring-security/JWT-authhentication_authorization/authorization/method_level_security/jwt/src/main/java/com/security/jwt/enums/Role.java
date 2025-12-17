package com.security.jwt.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Role {
    ADMIN(Set.of(Permission.WEATHER_WRITE,Permission.WEATHER_READ,Permission.WEATHER_DELETE)),
    USER(Set.of(Permission.WEATHER_READ));

    Set<Permission> permissions=new HashSet<>();

    Role(Set<Permission> permissions){
        this.permissions=permissions;
    }
}
