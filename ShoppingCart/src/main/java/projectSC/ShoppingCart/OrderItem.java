package projectSC.ShoppingCart;

import javax.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id") // Specify the foreign key column name
    private PurchaseOrder purchaseorder;

    // Constructors, getters, and setters

   

	public OrderItem() {
        // Default constructor
    }

    public OrderItem(PurchaseOrder purchaseorder) {
		super();
		this.purchaseorder = purchaseorder;
	}

	public PurchaseOrder getPurchaseorder() {
		return purchaseorder;
	}

	public void setPurchaseorder(PurchaseOrder purchaseorder) {
		this.purchaseorder = purchaseorder;
	}

	public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
