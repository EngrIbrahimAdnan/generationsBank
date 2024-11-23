package CODEDBTA.GenerationsBank.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ADMIN, GUARDIAN, DEPENDENT;

    @Override
    public String getAuthority() { // Abdulrahman: Gets the authority level of the user
        return name();
    }
}
