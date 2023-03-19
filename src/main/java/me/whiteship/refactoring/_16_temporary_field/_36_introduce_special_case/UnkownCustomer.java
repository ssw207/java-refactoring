package me.whiteship.refactoring._16_temporary_field._36_introduce_special_case;

public class UnkownCustomer extends Customer {

    public UnkownCustomer() {
        super("unknown", new BasicBillingPlan(), new NullPaymentHistory());
    }

    @Override
    public boolean isUnknown() {
        return true;
    }

    @Override
    public String getName() {
        return "occupant";
    }


}
