package projectSC.ShoppingCart;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
private Long id;
private int quantity;
@ManyToOne
@JoinColumn(name="order_id")
private Order order;

@ManyToOne
@JoinColumn(name="product_id")
private Product product;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public int getQuantity() {
	return quantity;
}

public void setQuantity(int quantity) {
	this.quantity = quantity;
}

public Order getOrder() {
	return order;
}

public void setOrder(Order order) {
	this.order = order;
}

public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}

public OrderItem(Long id, int quantity, Order order, Product product) {
	super();
	this.id = id;
	this.quantity = quantity;
	this.order = order;
	this.product = product;
}

@Override
public String toString() {
	return "OrderItem [id=" + id + ", quantity=" + quantity + ", order=" + order + ", product=" + product + "]";
}

public OrderItem() {
	super();
	// TODO Auto-generated constructor stub
}


}
