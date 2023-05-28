package pl.thinkdata.b2bbase.security.model;

public enum UserRole {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
