package com.example.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Immutable Order class with Builder pattern
 */
public class Order {
    private final String id;
    private final String customerEmail;
    private final List<OrderLine> lines;
    private final Integer discountPercent;
    private final boolean expedited;
    private final String notes;

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    private Order(Builder builder) {
        this.id = builder.id;
        this.customerEmail = builder.customerEmail;
        this.lines = Collections.unmodifiableList(new ArrayList<>(builder.lines));
        this.discountPercent = builder.discountPercent;
        this.expedited = builder.expedited;
        this.notes = builder.notes;
    }

    public String getId() { return id; }
    public String getCustomerEmail() { return customerEmail; }
    public List<OrderLine> getLines() { return lines; }
    public Integer getDiscountPercent() { return discountPercent; }
    public boolean isExpedited() { return expedited; }
    public String getNotes() { return notes; }

    public int totalBeforeDiscount() {
        int sum = 0;
        for (OrderLine l : lines) sum += l.getQuantity() * l.getUnitPriceCents();
        return sum;
    }

    public int totalAfterDiscount() {
        int base = totalBeforeDiscount();
        if (discountPercent == null) return base;
        return base - (base * discountPercent / 100);
    }

    public static class Builder {
        private String id;
        private String customerEmail;
        private List<OrderLine> lines = new ArrayList<>();
        private Integer discountPercent;
        private boolean expedited = false;
        private String notes;

        public Builder(String id, String customerEmail) {
            this.id = id;
            this.customerEmail = customerEmail;
        }

        public Builder addLine(OrderLine line) {
            this.lines.add(new OrderLine(line.getSku(), line.getQuantity(), line.getUnitPriceCents()));
            return this;
        }

        public Builder discountPercent(Integer discountPercent) {
            this.discountPercent = discountPercent;
            return this;
        }

        public Builder expedited(boolean expedited) {
            this.expedited = expedited;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Order build() {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Order ID is required and cannot be empty");
            }
            
            if (customerEmail == null || customerEmail.trim().isEmpty()) {
                throw new IllegalArgumentException("Customer email is required and cannot be empty");
            }
            
            if (!EMAIL_PATTERN.matcher(customerEmail).matches()) {
                throw new IllegalArgumentException("Customer email format is invalid");
            }
            
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("Order must have at least one line item");
            }
            
            if (discountPercent != null && (discountPercent < 0 || discountPercent > 100)) {
                throw new IllegalArgumentException("Discount percent must be between 0 and 100");
            }

            return new Order(this);
        }
    }
}
