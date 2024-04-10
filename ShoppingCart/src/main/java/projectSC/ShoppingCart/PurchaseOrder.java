package projectSC.ShoppingCart;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.List;

@Entity
@Table(name = "purchase_order") // Use a different table name to avoid conflicts
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float totalAmount;

    private LocalDate orderDate;

    @OneToMany
     @JoinColumn(name="Orderitem")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Constructors, getters, and setters

    public PurchaseOrder() {
        // Default constructor
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Helper method to add OrderItem to PurchaseOrder
    public void addOrderItem(OrderItem orderItem) {
        orderItem.setPurchaseorder(this); // Set the purchase order reference in OrderItem
        orderItems.add(orderItem); // Add OrderItem to the list
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem); // Remove OrderItem from the list
        orderItem.setPurchaseorder(null); // Clear the purchase order reference in OrderItem
    }
}
