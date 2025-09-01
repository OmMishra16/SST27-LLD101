import com.example.orders.*;
import java.util.List;

public class TryIt {
    public static void main(String[] args) {
        OrderLine l1 = new OrderLine("A", 1, 200);
        OrderLine l2 = new OrderLine("B", 3, 100);
        
        Order o = new Order.Builder("o2", "a@b.com")
            .addLine(l1)
            .addLine(l2)
            .discountPercent(10)
            .build();
            
        System.out.println("Before: " + o.totalAfterDiscount());
        
        // OrderLine is now immutable, so this line would cause compilation error:
        // l1.setQuantity(999);
        
        // Instead demonstrate that the order is immutable - totals remain stable
        System.out.println("After:  " + o.totalAfterDiscount());
        System.out.println("=> Order and OrderLine are now immutable - totals remain stable.");
        
        // Demonstrate validation
        try {
            new Order.Builder("", "invalid-email")
                .addLine(l1)
                .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }
        
        try {
            new Order.Builder("o3", "valid@email.com")
                .discountPercent(150)
                .addLine(l1)
                .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Discount validation works: " + e.getMessage());
        }
    }
}
