package projectSC.ShoppingCart;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "billing")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String billingAddress;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "total_amount")
    private double totalAmount;
    @Column(name = "billing_date")
    private LocalDate billingDate;
    @Column(name = "discount_amount")
    private double discountAmount;
    @OneToOne
    @JoinColumn(name="billing")
    private PurchaseOrder purchaseorder;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
   public Long getId() 
   {
		return id;
	}

	public Billing(PurchaseOrder purchaseorder) {
	super();
	this.purchaseorder = purchaseorder;
}

	public PurchaseOrder getPurchaseorder() {
	return purchaseorder;
}

public void setPurchaseorder(PurchaseOrder purchaseorder) {
	this.purchaseorder = purchaseorder;
}

	public void setId(Long id) 
	{
		this.id = id;
	}
    public String getBillingAddress() 
    {
		return billingAddress;
	}
    public void setBillingAddress(String billingAddress)
    {
		this.billingAddress = billingAddress;
	}
    public String getCardNumber() 
    {
		return cardNumber;
	}
    public void setCardNumber(String cardNumber)
    {
		this.cardNumber = cardNumber;
	}
    public double getTotalAmount()
    {
		return totalAmount;
	}
    public void setTotalAmount(double totalAmount) 
    {
		this.totalAmount = totalAmount;
	}
    public LocalDate getBillingDate() 
    {
		return billingDate;
	}
    public void setBillingDate(LocalDate billingDate)
    {
		this.billingDate = billingDate;
	}
    public double getDiscountAmount()
    {
		return discountAmount;
	}
    public void setDiscountAmount(double discountAmount) 
    {
		this.discountAmount = discountAmount;
	}
   
    public Product getProduct() 
    {
		return product;
	}
    public void setProduct(Product product)
    {
		this.product = product;
	}
    public Billing(Long id, Long orderId, String billingAddress,  String cardNumber,
			Date expiryDate, double totalAmount, LocalDate billingDate, double taxAmount, double discountAmount, PurchaseOrder purchaseorder,
			Product product) 
    {
		super();
		this.id = id;
		this.billingAddress = billingAddress;
		this.cardNumber = cardNumber;
		this.totalAmount = totalAmount;
		this.billingDate = billingDate;
		this.discountAmount = discountAmount;
		this.purchaseorder= purchaseorder;
		this.product = product;
	}
    public Billing() 
    {
		super();
	}

}
