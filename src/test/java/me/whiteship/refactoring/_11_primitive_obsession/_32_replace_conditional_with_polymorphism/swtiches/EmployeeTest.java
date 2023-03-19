package me.whiteship.refactoring._11_primitive_obsession._32_replace_conditional_with_polymorphism.swtiches;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void fulltime() {
        EmpEnum employee = EmpEnum.of("full-time");
        assertEquals(120, employee.vacationHours());
        assertTrue(employee.canAccessTo("new project"));
        assertTrue(employee.canAccessTo("spring"));
    }

    @Test
    void partime() {
        EmpEnum employee = EmpEnum.of("part-time");
        assertEquals(80, employee.vacationHours());
        assertFalse(employee.canAccessTo("new project"));
        assertTrue(employee.canAccessTo("spring"));
    }

    @Test
    void temporal() {
        EmpEnum employee = EmpEnum.of("temporal");
        assertEquals(32, employee.vacationHours());
        assertFalse(employee.canAccessTo("new project"));
        assertFalse(employee.canAccessTo("spring"));
        assertTrue(employee.canAccessTo("jpa"));
    }

}