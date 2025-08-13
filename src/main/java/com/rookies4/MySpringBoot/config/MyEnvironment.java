package com.rookies4.MySpringBoot.config;

public class MyEnvironment {
    private String mode;

    public MyEnvironment(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "MyEnvironment{"
                + "mode='" + mode + "'"
                + "}";
    }
}
