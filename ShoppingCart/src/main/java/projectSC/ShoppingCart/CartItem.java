package projectSC.ShoppingCart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    
  private Long id;
  private int quantity;
  
  @ManyToOne
  @JoinColumn(name="Product_id")
  private Product product;
  
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  

public CartItem(Order order) {
	super();
	this.order = order;
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

public CartItem(Product product) {
	super();
	this.product = product;
}
@Override
public String toString() {
	return "CartItem [order=" + order + "]";
}
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

public CartItem(Long id, int quantity) {
	super();
	this.id = id;
	this.quantity = quantity;
}
public CartItem() {
	super();
	// TODO Auto-generated constructor stub
}
  
}
