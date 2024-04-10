package projectSC.ShoppingCart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product 
{
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String name;
 private String category;
 private double Price;
 
 
public Long getId() 
{
	return id;
}
public void setId(Long id)
{
	this.id = id;
}
public String getName() 
{
	return name;
}
public void setName(String name) 
{
	this.name = name;
}
public String getCategory() 
{
	return category;
}
public void setCategory(String category) 
{
	this.category = category;
}
public double getPrice() 
{
	return Price;
}
public void setPrice(double price) 
{
	Price = price;
}
public Product(Long id, String name, String category, double price) 
{
	super();
	this.id = id;
	this.name = name;
	this.category = category;
	this.Price = price;
}
public Product() 
{
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() 
{
	return "Product [id=" + id + ", name=" + name + ", category=" + category + ", Price=" + Price + "]";
}
 

 
}
