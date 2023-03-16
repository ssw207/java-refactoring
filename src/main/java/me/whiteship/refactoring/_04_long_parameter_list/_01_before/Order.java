package me.whiteship.refactoring._04_long_parameter_list._01_before;

public class Order {

    private int quantity;

    private double itemPrice;

    public Order(int quantity, double itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public double finalPrice() {
        double basePrice = this.quantity * this.itemPrice;
        return this.discountedPrice(basePrice);
    }
    
    private int discountLevel() {
        return this.quantity > 100 ? 2 : 1;
    }

    // 파라미터로 전달하던 값을 메서드로 추출후 메서드 내에서 조회한다
    private double discountedPrice(double basePrice) {
        return discountLevel() == 2 ? basePrice * 0.9 : basePrice * 0.95;
    }
}
