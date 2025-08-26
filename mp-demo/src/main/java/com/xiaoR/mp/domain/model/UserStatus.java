package com.xiaoR.mp.domain.model;

public enum UserStatus {
    NORMAL(1), FROZEN(2);

    private int status;

    private UserStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "status=" + status +
                '}';
    }


}
