package projectSC.ShoppingCart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class CartItem 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name="Product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseorder;

 

public CartItem(PurchaseOrder purchaseorder) {
		super();
		this.purchaseorder = purchaseorder;
	}
@Override
	public String toString() {
		return "CartItem [id=" + id + ", quantity=" + quantity + ", product=" + product + ", purchaseorder="
				+ purchaseorder + "]";
	}
public PurchaseOrder getPurchaseorder() {
		return purchaseorder;
	}
	public void setPurchaseorder(PurchaseOrder purchaseorder) {
		this.purchaseorder = purchaseorder;
	}
public Product getProduct() 
{
	return product;
}
public void setProduct(Product product) 
{
	this.product = product;
}
public CartItem(Product product) 
{
	super();
	this.product = product;

}
public Long getId()
{
	return id;
}
public void setId(Long id) 
{
	this.id = id;
}
public int getQuantity() 
{
	return quantity;
}
public void setQuantity(int quantity)
{
	this.quantity = quantity;
}
public CartItem(Long id, int quantity) 
{
	super();
	this.id = id;
	this.quantity = quantity;
}
public CartItem() 
{
	super();
	// TODO Auto-generated constructor stub
}
}
