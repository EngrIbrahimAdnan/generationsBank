package CODEDBTA.GenerationsBank.bo.admin;

import CODEDBTA.GenerationsBank.enums.Roles;

public class AssignRoleRequest {

    private Long userId;
    private Roles role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
