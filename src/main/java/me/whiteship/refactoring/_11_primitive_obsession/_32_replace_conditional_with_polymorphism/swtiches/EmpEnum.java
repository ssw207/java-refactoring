package me.whiteship.refactoring._11_primitive_obsession._32_replace_conditional_with_polymorphism.swtiches;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public enum EmpEnum {
    FULL("full-time", 120, p -> true),
    PART("part-time", 80, p -> List.of("spring", "jpa").contains(p)),
    TMP("temporal", 32, p -> List.of("jpa").contains(p))
    ;

    private final String code;
    private final int vacationHours;
    private final Predicate<String> canAccessTo;

    EmpEnum(String code, int vacationHours, Predicate<String> isAccess) {
        this.code = code;
        this.vacationHours = vacationHours;
        this.canAccessTo = isAccess;
    }

    public static EmpEnum of (String code) {
        return Arrays.stream(values())
                .filter(a -> a.code.equals(code))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean canAccessTo(String projectName) {
        return canAccessTo.test(projectName);
    }

    public String getCode() {
        return code;
    }

    public int vacationHours() {
        return vacationHours;
    }
}
