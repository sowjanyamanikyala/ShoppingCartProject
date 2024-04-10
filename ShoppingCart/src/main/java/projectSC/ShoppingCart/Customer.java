package projectSC.ShoppingCart;

import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "customer")
public class Customer
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "first_name")
    private String firstName;
    private String address;
    private String email;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PurchaseOrder> purchaseorder;
    
   
    
    public Customer(List<PurchaseOrder> purchaseorder) {
		super();
		this.purchaseorder = purchaseorder;
	}
	public List<PurchaseOrder> getPurchaseorder() {
		return purchaseorder;
	}
	public void setPurchaseorder(List<PurchaseOrder> purchaseorder) {
		this.purchaseorder = purchaseorder;
	}
	public Customer(String firstName, String address, String email) {
        this.firstName = firstName;
        this.address = address;
        this.email = email;
    }
    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }
    public String getFirstName() 
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getaddress() 
    {
        return address;
    }
    public void setLaddress(String address) 
    {
        this.address = address;
    }
    @Override
	public String toString() 
    {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", address=" + address + ", email="
				+ email + "]";
	}
    public String getEmail() 
	{
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
   
    public Customer() 
    {
		super();
	}
}

