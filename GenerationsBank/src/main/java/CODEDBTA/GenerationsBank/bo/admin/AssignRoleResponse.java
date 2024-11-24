package CODEDBTA.GenerationsBank.bo.admin;

import CODEDBTA.GenerationsBank.entity.UserEntity;

public class AssignRoleResponse {

    private String message;
    private UserEntity user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
