package me.whiteship.refactoring._18_middle_man._40_replace_subclass_with_delegate;

public class PremiumDelegate {
    private Booking host;
    private PremiumExtra extra;

    public PremiumDelegate(Booking host, PremiumExtra extra) {
        this.host = host;
        this.extra = extra;
    }

    public boolean hasTalkback() {
        return this.host.show.hasOwnProperty("talkback");
    }
}
