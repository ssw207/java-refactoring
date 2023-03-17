package me.whiteship.refactoring._07_divergent_change._25_move_function;

public class AccountType {
    private boolean premium;

    public AccountType(boolean premium) {
        this.premium = premium;
    }

    public boolean isPremium() {
        return this.premium;
    }

    // 다른클래스의 필드를 많이 참조하고 있다면 이동을 고려한다.
    // 순환참조가 발생하고 하나의 필드만 사용하기 때문에 객체를 주입받지 않는다.
    double overdraftCharge(int daysOverdrawn) {
        if (this.premium) {
            final int baseCharge = 10;
            if (daysOverdrawn <= 7) {
                return baseCharge;
            } else {
                return baseCharge + (daysOverdrawn - 7) * 0.85;
            }
        } else {
            return daysOverdrawn * 1.75;
        }
    }
}
