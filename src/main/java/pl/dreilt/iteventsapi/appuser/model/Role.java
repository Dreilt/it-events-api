package pl.dreilt.iteventsapi.appuser.model;

public enum Role {
    ROLE_ADMIN("ADMIN", "Administrator"),
    ROLE_ORGANIZER("ORGANIZER", "Organizator"),
    ROLE_USER("USER", "Użytkownik");

    private final String role;
    private final String displayName;

    Role(String role, String displayName) {
        this.role = role;
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }

    public String getDisplayName() {
        return displayName;
    }
}
