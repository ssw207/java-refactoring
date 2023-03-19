package me.whiteship.refactoring._11_primitive_obsession._32_replace_conditional_with_polymorphism.variation;

import java.util.List;

/**
 * Voyage zone 값에 따라 다른 동작을 함
 * Voyage zone 을 클래스로 분리해 관련로직을 이동한다.
 */
public class VoyageRating {

    protected Voyage voyage;

    protected List<VoyageHistory> history;

    public VoyageRating(Voyage voyage, List<VoyageHistory> history) {
        this.voyage = voyage;
        this.history = history;
    }

    public char value() {
        final int vpf = this.voyageProfitFactor();
        final int vr = this.voyageRisk();
        final int chr = this.captainHistoryRisk();
        return (vpf * 3 > (vr + chr * 2)) ? 'A' : 'B';
    }

    protected int captainHistoryRisk() {
        int result = 1;
        if (this.history.size() < 5) result += 4;
        result += this.history.stream().filter(v -> v.profit() < 0).count();
        return getPositive(result);
    }

    protected int getPositive(int result) {
        return Math.max(result, 0);
    }

    private int voyageRisk() {
        int result = 1;
        if (this.voyage.length() > 4) result += 2;
        if (this.voyage.length() > 8) result += this.voyage.length() - 8;
        if (List.of("china", "east-indies").contains(this.voyage.zone())) result += 4;
        return getPositive(result);
    }

    protected int voyageProfitFactor() {
        int result = 2;

        if (this.voyage.zone().equals("china")) result += 1;
        if (this.voyage.zone().equals("east-indies")) result +=1 ;
        result += voyageLengthFactor();
        result += historyLengthFactor();

        return result;
    }

    protected int voyageLengthFactor() {
        return (this.voyage.length() > 14) ? -1 : 0;
    }

    protected int historyLengthFactor() {
        return (this.history.size() > 8) ? 1 : 0 ;
    }
}
