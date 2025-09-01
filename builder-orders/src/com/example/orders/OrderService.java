package com.example.orders;

import java.util.List;

public class OrderService {

    public Order createOrder(String id, String email, List<OrderLine> lines, Integer discount, boolean expedited, String notes) {
        Order.Builder builder = new Order.Builder(id, email);
        if (lines != null) {
            for (OrderLine l : lines) {
                builder.addLine(l);
            }
        }
        builder.discountPercent(discount);
        builder.expedited(expedited);
        builder.notes(notes);
        return builder.build();
    }
}
