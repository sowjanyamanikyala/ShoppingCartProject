package projectSC.ShoppingCart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;
 private static String Username="admin";
 public Admin() {
	super();
	// TODO Auto-generated constructor stub
}
private static String password="admin012";
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public static String getUsername() {
	return Username;
}
public static void setUsername(String username) {
	Username = username;
}
public static String getPassword() {
	return password;
}
public static void setPassword(String password) {
	Admin.password = password;
}
public Admin(int id) {
	super();
	this.id = id;
}
@Override
public String toString() {
	return "Admin [id=" + id + "]";
}
 
 
}
