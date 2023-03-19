package me.whiteship.refactoring._11_primitive_obsession._32_replace_conditional_with_polymorphism.variation;

import java.util.List;

public class ChinaExpericedVoyageRating extends VoyageRating {
    public ChinaExpericedVoyageRating(Voyage voyage, List<VoyageHistory> history) {
        super(voyage, history);
    }

    @Override
    protected int captainHistoryRisk() {
        return getPositive(super.captainHistoryRisk() - 2); // 상위 클래스의 계산결과에서 추가로 -2 를 빼주면된다.
    }

    @Override
    protected int voyageProfitFactor() {
        return super.voyageProfitFactor() + 3;
    }

    @Override
    protected int voyageLengthFactor() {
        int result = 0;

        if (this.voyage.length() > 12) result += 1;
        if (this.voyage.length() > 18) result -= 1;

        return result;
    }

    @Override
    protected int historyLengthFactor() {
        return (this.history.size() > 10) ? 1 : 0;
    }
}
