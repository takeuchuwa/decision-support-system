package ua.nure.decisionsupportsystem.entity.common.enums;

public enum UserType {
    HR("HR"), EMPLOYEE("Employee");

    private final String displayValue;

    UserType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
