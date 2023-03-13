package com.example.LaptopKG.models.enums;



public enum Role {
    ROLE_USER("User"), ROLE_ADMIN("Admin");
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getAuthority() {
        return name();
    }
}
