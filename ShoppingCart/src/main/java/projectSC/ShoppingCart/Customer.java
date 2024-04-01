package projectSC.ShoppingCart;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> order;

    // Constructors, getters, and setters

    public Customer() {
    }

    public Customer(String firstName, String address, String email) {
        this.firstName = firstName;
        this.address = address;
        this.email = email;
    }

    // Getters and Setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getaddress() {
        return address;
    }

    public void setLaddress(String address) {
        this.address = address;
    }

    @Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", address=" + address + ", email="
				+ email + ", order=" + order + "]";
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrders(List<Order> order) {
        this.order = order;
    }
}

