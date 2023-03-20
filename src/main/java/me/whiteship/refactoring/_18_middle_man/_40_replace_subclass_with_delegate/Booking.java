package me.whiteship.refactoring._18_middle_man._40_replace_subclass_with_delegate;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Booking {

    protected Show show;

    protected LocalDateTime time;

    protected PremiumDelegate premiumDelegate;

    public Booking(Show show, LocalDateTime time) {
        this.show = show;
        this.time = time;
    }

    public static Booking createBooking(Show show, LocalDateTime time) {
        return new Booking(show, time);
    }

    public static PremiumBooking createPremiumBooking(Show show, LocalDateTime time, PremiumExtra extra) {
        PremiumBooking premiumBooking = new PremiumBooking(show, time, extra);
        premiumBooking.premiumDelegate = new PremiumDelegate(premiumBooking, extra);
        return premiumBooking;
    }

    public boolean hasTalkback() {
        return this.show.hasOwnProperty("talkback") && !this.isPeakDay();
    }

    protected boolean isPeakDay() {
        DayOfWeek dayOfWeek = this.time.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public double basePrice() {
        double result = this.show.getPrice();
        if (this.isPeakDay()) result += Math.round(result * 0.15);
        return result;
    }
}
