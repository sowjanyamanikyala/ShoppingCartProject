package projectSC.ShoppingCart;


import java.time.LocalDate; 
import java.util.*;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ShoppingCart 
{
    private static Scanner scanner = new Scanner(System.in);
    private static SessionFactory sessionFactory;
    private static Session session;

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        sessionFactory = config.buildSessionFactory();
        session = sessionFactory.openSession();

        System.out.println("===========================");
        System.out.println("SHOPPING CART MANAGEMENT");
        System.out.println("===========================");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Admin Username: ");
        String userName1 = scanner.nextLine();
        System.out.println("Enter Admin Password: ");
        int password1 = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (userName1.equals(Admin.getUserName()) && password1 == Admin.getPassword()) {
            int choice;
            do {
                try {
                    System.out.println("\n======================================");
                    System.out.println("WELCOME TO SHOPPING CART MANAGEMENT SYSTEM");
                    System.out.println("======================================");
                    System.out.println("Select an option:");
                    System.out.println("--------------------");
                    System.out.println("1.Create");
                    System.out.println("2. Read Shopping Cart Details");
                    System.out.println("3. Update Shopping Cart Details");
                    System.out.println("4. Delete Shopping Cart Details");
                    System.out.println("5. Billing");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");

                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            create();
                            break;
                        case 2:
                            read();
                            break;
                        case 3:
                            updateShoppingCart();
                            break;
                        case 4:
                            deleteshoppingcart();
                            break;
                        case 5:
                            Billing();
                            break;
                        case 6:
                            System.out.println("Exiting program...");
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Clear the invalid input
                    choice = 0; // Reset choice to allow the loop to continue
                } catch (NoSuchElementException e) {
                    // If no more input is available, break out of the loop
                    break;
                }
            } while (true); // Infinite loop
        } else {
            System.out.println("Incorrect username or password.");
        }

        // Close the scanner after its usage
        scanner.close();
    }


    public static void create() 
    {
        Scanner scanner = new Scanner(System.in);
        int choice = 0; // Initialize choice outside the loop

        while (choice != 6)
        {
            System.out.println("Select What You Want to Create:");
            System.out.println("1. Customer");
            System.out.println("2. Create Product");
            System.out.println("3. Create Order Details");
            System.out.println("4. Create OrderItem Details");
            System.out.println("5. Create CartItem Details");
            System.out.println("6. Exit Menu");
            System.out.print("Enter Your Choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) 
            {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                	addOrder(scanner);
                    break;
                case 4:
                    addOrderItem(scanner);
                    break;
                case 5:
                    addcartItem();
                    break;
                case 6:
                    System.out.println("Exiting Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    break;
            }
        }

        // Close the scanner
        scanner.close();
    }


    private static void createCustomer()
    {
        System.out.println("\nCreating a New Customer:");

        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.println("Enter customer email: ");
        String email = scanner.nextLine().trim();

        System.out.println("Enter customer address: ");
        String address = scanner.nextLine().trim();

        // Validate that required fields are not empty
        if (firstName.isEmpty() || email.isEmpty() || address.isEmpty())
        {
            System.out.println("Please provide valid details. Name, email, and address are required.");
            return;
        }

        // Create a new Customer object
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setEmail(email);
        customer.setLaddress(address);

        // Save the customer to the database using Hibernate
        Transaction transaction = null;
        try 
        {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(customer);

            transaction.commit();
            System.out.println("Customer created successfully.");
        } catch (HibernateException e) 
        {
            if (transaction != null) 
            {
                transaction.rollback();
            }
            System.out.println("Failed to create customer. Error: " + e.getMessage());
        }
        finally 
        {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    

   
/*=============================================================================================*/
    private static void addProduct() 
    {
        System.out.println("Adding a new product:");
        System.out.println("----------------------");

        System.out.println("Enter product name: ");
        String productName = scanner.next();

        System.out.println("Enter product category: ");
        String Category = scanner.next();
       
        System.out.println("Enter product Price: ");
        double Price = scanner.nextDouble();

         // Create a new Product object
        Product product = new Product();
        product.setName(productName);
        product.setCategory(Category);
        product.setPrice(Price);
        
        // Save the product to the database using Hibernate
        Transaction transaction = null;
        try 
        {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(product);

            transaction.commit();
            System.out.println("Product added successfully.");
        }
        catch (Exception e) 
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            System.out.println("Failed to add product. Error: " + e.getMessage());
        }
        finally 
        {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public static void addOrder(Scanner scanner) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Get order details
            System.out.print("Enter Total Amount: ");
            float totalAmount = scanner.nextFloat();
            scanner.nextLine();

            System.out.print("Enter Order Date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            LocalDate orderDate = LocalDate.parse(dateString);

            // Create new Order object
            PurchaseOrder purchaseorder = new PurchaseOrder();
            purchaseorder.setTotalAmount(totalAmount);
            purchaseorder.setOrderDate(orderDate);

            // Get customer ID
            System.out.print("Enter Customer ID: ");
            long customerId = scanner.nextLong();
            scanner.nextLine();

            // Retrieve the customer by ID
            Customer customer = session.get(Customer.class, customerId);
            if (customer == null) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " not found.");
            }

            // Set the customer for the order
            purchaseorder.setCustomer(customer);

            // Save the order to the database
            session.save(purchaseorder);

            // Commit transaction
            transaction.commit();
            System.out.println("Order added successfully.");
        } catch (Exception e) {
            // Rollback transaction in case of failure
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Failed to add order. Error: " + e.getMessage());
        } finally {
            // Close Hibernate session
            if (session != null) {
                session.close();
            }
        }
    }
   
    public static void addOrderItem(Scanner scanner) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Get product ID from user
            System.out.print("Enter Product ID: ");
            long productId = scanner.nextLong();
            scanner.nextLine(); // Consume newline

            // Retrieve the product by ID
            Product product = session.get(Product.class, productId);
            if (product == null) {
                throw new IllegalArgumentException("Product with ID " + productId + " not found.");
            }

            // Get quantity from user
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Create a new OrderItem
            OrderItem orderItem = new OrderItem(product, quantity);

            // Save the OrderItem
            session.save(orderItem);

            // Commit transaction
            transaction.commit();
            System.out.println("Order item added successfully.");
        } catch (Exception e) {
            // Rollback transaction in case of failure
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Failed to add order item. Error: " + e.getMessage());
        } finally {
            // Close Hibernate session
            if (session != null) {
                session.close();
            }
        }
    }


	public static void addcartItem() {
		Product product=new Product();
		 System.out.println("Enter Product ID: ");
		 long id=scanner.nextLong();
		     scanner.nextLine();
		     System.out.println("Enter ProductName: ");    
		 String ProductName=scanner.next();
		     scanner.nextLine();
		     System.out.println("Enter Category: ");
		     String category=scanner.next();
		     scanner.nextLine();
		     System.out.println("Enter Price: ");
		     double price=scanner.nextDouble();
		     scanner.nextLine();
		     
	    System.out.print("Enter CartItem ID: ");
	    long Id = scanner.nextLong();
	    scanner.nextLine();

	    System.out.print("Enter Quantity: ");
	    int quantity = scanner.nextInt();
	    scanner.nextLine();

	    // Create a new CartItem object
	    CartItem cartItem = new CartItem();
	    cartItem.setId(Id);
	    cartItem.setQuantity(quantity);
	    cartItem.setProduct(product);
	    product.setName(ProductName);
	    product.setCategory(category);
	    product.setPrice(price);
	    

	    // Save the cart item to the database
	    Session session = sessionFactory.openSession();
	    Transaction transaction = null;
	    try {
	        transaction = session.beginTransaction();
	        session.save(product);
	        session.save(cartItem);
	        transaction.commit();
	        System.out.println("Cart item added successfully.");
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        System.out.println("Failed to add cart item. Error: " + e.getMessage());
	    } finally {
	        session.close();
	    }
	}
/*==================================================================================================================================*/	
	public static void Billing() {
		int choice;
        do {
            // Display menu options
            System.out.println("Shopping Cart Billing Menu");
            System.out.println("--------------------------");
            System.out.println("1. Generate Bill");
            System.out.println("2. View Particular Billing");
            System.out.println("3. View All Billing Records");
            System.out.println("4. Update Billing Record");
            System.out.println("5. Back");
            System.out.print("Enter Your Choice: ");

            // Read user choice
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Process user choice
            switch (choice) {
                case 1:
                    generateBill();
                    break;
                case 2:
                	viewBillById();
                    break;
                case 3:
                	viewAllBills();
                    break;
                case 4:
                	updateBillAmount();
                    break;
                case 5:
                    System.out.println("Returning to previous menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        } while (choice != 5);
        
        // Close the scanner after its usage
        scanner.close();
    }

	public static void generateBill() {
	    Scanner scanner = new Scanner(System.in);
	    Session session = null;

	    try {
	        // Initialize Hibernate configuration
	        Configuration configuration = new Configuration();
	        configuration.configure(); // Load Hibernate configuration from hibernate.cfg.xml

	        // Build session factory
	        SessionFactory sessionFactory = configuration.buildSessionFactory();

	        // Open session
	        session = sessionFactory.openSession();
   PurchaseOrder purchaseorder=new PurchaseOrder();
   Product product = new Product();
   
	        // Create a new Billing object
	        Billing billing = new Billing();

	        // Prompt user for billing information
	        System.out.print("Enter Billing Address: ");
	        String billingAddress = scanner.nextLine();
	        billing.setBillingAddress(billingAddress);

	        System.out.print("Enter Billing Date (YYYY-MM-DD): ");
	        String billingDateString = scanner.nextLine();
	        LocalDate billingDate = LocalDate.parse(billingDateString);
	        billing.setBillingDate(billingDate);

	        System.out.print("Enter Card Number: ");
	        String cardNumber = scanner.nextLine();
	        billing.setCardNumber(cardNumber);
	        
	        System.out.print("Enter Total Amount: ");
	        double totalamount = scanner.nextDouble();
	        billing.setTotalAmount(totalamount);

	        System.out.print("Enter Discount Amount: ");
	        double discountAmount = scanner.nextDouble();
	        billing.setDiscountAmount(discountAmount);

	        // Clear scanner buffer
	        scanner.nextLine();

	        System.out.print("Enter Order ID: ");
	        long orderId = scanner.nextLong();

	        System.out.print("Enter Product ID: ");
	        long productId = scanner.nextLong();

	        // Save the billing record
	        billing.setPurchaseorder(purchaseorder);;
	        billing.setProduct(product);

	        session.beginTransaction();
	        session.save(purchaseorder);
	        session.save(product);
	        session.save(billing);
	        session.getTransaction().commit();

	        System.out.println("Billing record created successfully.");
	    } catch (Exception e) {
	        if (session != null && session.getTransaction() != null) {
	            session.getTransaction().rollback();
	        }
	        System.out.println("Failed to create billing record. Error: " + e.getMessage());
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	        scanner.close();
	    }
	}
	public static void viewBillById() {
		 try {
		        System.out.print("Enter Billing ID: ");
		        long id = scanner.nextLong();
		        scanner.nextLine(); // Consume newline character

		        // Fetch billing details by ID
		        Query query = session.createQuery("SELECT b.billingAddress, b.cardNumber, b.totalAmount, b.billingDate, b.discountAmount, b.purchaseorder.id, b.product.id " +
		                                           "FROM Billing b " +
		                                           "WHERE b.id = :id")
		                             .setParameter("id", id);
		        Object[] result = (Object[]) query.getSingleResult();

		        // Print billing details in table format
		        System.out.println("\nBilling Details:");
		        System.out.println("==============================================================");
		        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
		                          "Billing Address", "Card Number", "Total Amount", "Billing Date", "Discount Amount", "Order ID", "Product ID");
		        System.out.println("--------------------------------------------------------------");

		        String billingAddress = (String) result[0];
		        String cardNumber = (String) result[1];
		        double totalAmount = (double) result[2];
		        LocalDate billingDate = (LocalDate) result[3];
		        double discountAmount = (double) result[4];
		        long orderId = (long) result[5];
		        long productId = (long) result[6];

		        System.out.printf("%-20s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
		                          billingAddress, cardNumber, totalAmount, billingDate, discountAmount, orderId, productId);

		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		    }
	}

	public static void viewAllBills() {
		String hqlQuery = "SELECT b.id, b.billingAddress, b.billingDate, b.cardNumber, b.discountAmount, b.totalAmount, b.purchaseorder.id, b.product.id " +
                "FROM Billing b";
List<Object[]> data = session.createQuery(hqlQuery, Object[].class).list();

if (data.isEmpty()) {
  System.out.println("There are no records found.");
} else {
  System.out.println("Billing Details:");
  System.out.println("=================================================================================================================================================");

  System.out.println("+------------+------------------+----------------------+-----------------+------------------+------------------+------------------+---------------");
  System.out.println("| Billing_Id | Billing_Address  | Billing_Date         | Card_Number     | Discount_Amount  | Total Amount    | Order_Id         |   Product_Id     |");
  System.out.println("+------------+------------------+----------------------+-----------------+------------------+------------------+------------------+----------------");
  System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
  for (Object[] row : data) {
      System.out.printf("| %-10s | %-16s | %-20s | %-15s | %-16s | %-16s |%-16s| %-16s |\n", row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]);
  }

  System.out.println("+------------+------------------+----------------------+-----------------+------------------+------------------+------------------+");
}
	}

	private static void updateBillAmount() { 
		 scanner = new Scanner(System.in);

	        try {
	            System.out.print("Enter Billing Id to Update: ");
	            long id = scanner.nextLong();
	            scanner.nextLine();

	            Billing billing = session.get(Billing.class, id);

	            if (billing == null) {
	                System.out.println("No billing record found for the provided Id.");
	                return;
	            }

	            Transaction transaction = null;
	            try {
	                transaction = session.beginTransaction();

	                System.out.println("Enter Updated Billing Details:");

	                System.out.print("Billing Address: ");
	                String billingAddress = scanner.nextLine();
	                billing.setBillingAddress(billingAddress);

	                System.out.print("Billing Date (YYYY-MM-DD): ");
	                String dateString = scanner.nextLine();
	                LocalDate billingDate = LocalDate.parse(dateString);
	                billing.setBillingDate(billingDate);

	                System.out.print("Card Number: ");
	                String cardNumber = scanner.nextLine();
	                billing.setCardNumber(cardNumber);

	                System.out.print("Discount Amount: ");
	                double discountAmount = scanner.nextDouble();
	                billing.setDiscountAmount(discountAmount);

	                System.out.print("Total Amount: ");
	                double totalAmount = scanner.nextDouble();
	                billing.setTotalAmount(totalAmount);

	                // Here you should fetch and set the PurchaseOrder and Product objects by their respective IDs
	                // For simplicity, assuming you have methods to fetch them

	                System.out.print("Order Id: ");
	                long orderId = scanner.nextLong();
	                PurchaseOrder purchaseOrder = session.get(PurchaseOrder.class, orderId);
	                billing.setPurchaseorder(purchaseOrder);

	                System.out.print("Product Id: ");
	                long productId = scanner.nextLong();
	                Product product = session.get(Product.class, productId);
	                billing.setProduct(product);

	                session.update(billing);
	                transaction.commit();
	                System.out.println("Billing details updated successfully.");
	            } catch (Exception e) {
	                if (transaction != null) {
	                    transaction.rollback();
	                }
	                System.out.println("Failed to update billing details. Error: " + e.getMessage());
	            }
	        } catch (Exception e) {
	            System.out.println("Invalid input. Please enter a valid ID.");
	        } finally {
	            scanner.nextLine(); // Consume newline character
	        }
	    }



    
/*-------------------------------------------------------------------------------------------------------------------------------*/
    public static void read() {
    	  boolean exit = false; // Flag to control returning to the main menu
    	    while (!exit){
            System.out.println("\nSelect an option:");
            System.out.println("1. View Product by ID");
            System.out.println("2. View All Products");
            System.out.println("3. View OrderId details");
            System.out.println("4. View All Order details");
            System.out.println("5. View OrderItem Details");
            System.out.println("6. View CartItem Details");
            System.out.println("7. View CustomerID Details");
            System.out.println("8. View All Customer Details");
            
            System.out.println("9. Go Back");
            System.out.print("\nEnter your choice: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewProductById();
                        break;
                    case 2:
                        viewAllProducts();
                        break;
                    case 3:
                    	vieworder();
                         break;
                    case 4:
                    	viewAllOrderDetails();
                    	break;
                    case 5:
                    	viewOrderItem();
                    	break;
                    case 6:
                    	viewCartItem();
                    	break;
                    case 7:
                    	viewcustomerid();
                    	break;
                    case 8:
                    	viewcustomerdetails();
                    	break;
                    case 9:
                    	 exit = true;
                         break;
                    default:
                        System.out.println("Invalid choice! Please enter a valid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    public static void viewProductById() {
        System.out.print("\nEnter Product Id: ");
        Long id = scanner.nextLong();

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Product product = session.get(Product.class, id);

            if (product == null) {
                System.out.println("No product found for this ID.");
            } else {
                System.out.println("\nProduct Details:");
                System.out.println("==================");

                System.out.println("+----+------------------+-------------+");
                System.out.println("| id |   Product Name   |   Category  |");
                System.out.println("+----+------------------+-------------+");
                System.out.printf("| %-2d | %-16s | %-11s |\n", product.getId(), product.getName(), product.getCategory());
                System.out.println("+----+------------------+-------------+");
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    public static void viewAllProducts() {
        Session session = null;
        try {
            // Open session
            session = sessionFactory.openSession();

            // Execute HQL query to fetch all products
            String hqlQuery = "from Product";
            List<Product> productList = session.createQuery(hqlQuery, Product.class).list();

            // Check if productList is empty
            if (productList.isEmpty()) {
                System.out.println("No products found.");
            } else {
                System.out.println("\nProduct Details:");
                System.out.println("================");

                System.out.println("+----+------------------+-------------+");
                System.out.println("| id |   Product Name   |   Category  |");
                System.out.println("+----+------------------+-------------+");

                // Print details of each product
                for (Product product : productList) {
                    System.out.printf("| %-2d | %-16s | %-11s |\n", product.getId(), product.getName(), product.getCategory());
                }

                System.out.println("+----+------------------+-------------+");
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch products. Error: " + e.getMessage());
        } finally {
            // Close session in finally block to ensure it is closed even if an exception occurs
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static void vieworder() {
        scanner = new Scanner(System.in);

        System.out.print("Enter Order ID: ");
        long orderId = scanner.nextLong();
        scanner.nextLine();

        PurchaseOrder purchaseorder = session.get(PurchaseOrder.class, orderId);

        if (purchaseorder == null) {
            System.out.println("Order with ID " + orderId + " does not exist.");
        } else {
            System.out.println("\nOrder Details:");
            System.out.println("===============");
            System.out.println("Order ID: " + purchaseorder.getId());
            System.out.println("Total Amount: " + purchaseorder.getTotalAmount());

            // View order items
            List<OrderItem> orderItems = purchaseorder.getOrderItems();
            if (!orderItems.isEmpty()) {
                System.out.println("\nOrder Items:");
                System.out.println("=============");
                for (OrderItem orderItem : orderItems) {
                    System.out.println("Product ID: " + orderItem.getProduct().getId());
                    System.out.println("Quantity: " + orderItem.getQuantity());
                    System.out.println("Price per unit: " + orderItem.getProduct().getPrice());
                    // Calculate and display total price for the order item
                    double totalPrice = orderItem.getQuantity() * orderItem.getProduct().getPrice();
                    System.out.println("Total Price: " + totalPrice);
                }
            } else {
                System.out.println("No items in this order.");
            }
        }
    }
    public static void viewAllOrderDetails() {
        Session session = null;
        try {
            // Open session
            session = sessionFactory.openSession();

            // Execute HQL query to fetch all orders with associated customer
            String hqlQuery = "SELECT o.id, o.orderDate, o.totalAmount, o.customer.id FROM PurchaseOrder o";
            List<Object[]> orderDetailsList = session.createQuery(hqlQuery, Object[].class).list();

            // Check if orderDetailsList is empty
            if (orderDetailsList.isEmpty()) {
                System.out.println("No orders found.");
            } else {
                System.out.println("\nOrder Details:");
                System.out.println("===========================================================");

                // Print details of each order
                for (Object[] orderDetails : orderDetailsList) {
                    long orderId = (long) orderDetails[0];
                    LocalDate orderDate = (LocalDate) orderDetails[1];
                    float totalAmount = (float) orderDetails[2];
                    Long customerId = (Long) orderDetails[3]; // Use Long to handle null value

                    System.out.println("Order ID: " + orderId);
                    System.out.println("Order Date: " + orderDate);
                    System.out.println("Total Amount: " + totalAmount);
                    System.out.println("Customer ID: " + (customerId != null ? customerId : "Customer ID Not Available"));

                    System.out.println("-----------------------------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch order details. Error: " + e.getMessage());
        } finally {
            // Close session in finally block to ensure it is closed even if an exception occurs
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

        
    

        
        

    public static void viewOrderItem() {
        Session session = null;
        try {
            // Open session
            session = sessionFactory.openSession();

            // Execute HQL query to fetch all order items with associated purchase orders and products
            String hqlQuery = "SELECT oi FROM OrderItem oi JOIN FETCH oi.purchaseorder JOIN FETCH oi.product";
            List<OrderItem> orderItemsList = session.createQuery(hqlQuery, OrderItem.class).list();

            // Check if orderItemsList is empty
            if (orderItemsList.isEmpty()) {
                System.out.println("No order items found.");
            } else {
                System.out.println("\nOrder Items:");
                System.out.println("+------------------------------------------------------------+");
                System.out.printf("| %-12s | %-12s | %-12s | %-12s |\n", "Order Item ID", "Quantity", "Product ID", "Order ID");
                System.out.println("+--------------+--------------+--------------+--------------+");

                // Print details of each order item
                for (OrderItem orderItem : orderItemsList) {
                    long orderItemId = orderItem.getId();
                    int quantity = orderItem.getQuantity();
                    long productId = orderItem.getProduct().getId();
                    long orderId = orderItem.getPurchaseorder().getId();

                    System.out.printf("| %-12d | %-12d | %-12d | %-12d |\n", orderItemId, quantity, productId, orderId);
                    System.out.println("+--------------+--------------+--------------+--------------+");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch order items. Error: " + e.getMessage());
        } finally {
            // Close session in finally block to ensure it is closed even if an exception occurs
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    	
    
    public static void viewCartItem() {
        // Open session
        Session session = sessionFactory.openSession();

        try {
            // Fetch all cart items from the database
            List<CartItem> cartItems = session.createQuery("FROM CartItem", CartItem.class).list();

            // Check if there are any cart items
            if (cartItems.isEmpty()) {
                System.out.println("No cart items found.");
            } else {
                System.out.println("Cart Items:");
                System.out.println("-------------------------------------------------");
                for (CartItem cartItem : cartItems) {
                    System.out.println("Product ID: " + cartItem.getProduct());
                    System.out.println("Quantity: " + cartItem.getQuantity());
                    System.out.println("---------------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch cart items. Error: " + e.getMessage());
        } finally {
            // Close session
            session.close();
        }
    }  

    private static void viewcustomerid(){
    	scanner = new Scanner(System.in);

    System.out.print("Enter Customer ID: ");
    long customerId = scanner.nextLong();
    scanner.nextLine();

    Customer customer = session.get(Customer.class, customerId);

    if (customer == null) {
        System.out.println("Customer with ID " + customerId + " does not exist.");
    } else {
        System.out.println("\nCustomer Details:");
        System.out.println("=================");
        
        System.out.println("Name: " + customer.getFirstName());
        System.out.println("Email: " + customer.getEmail());
        
        System.out.println("Address: " + customer.getaddress());

        // View orders of the customer
        List<PurchaseOrder> orders = customer.getPurchaseorder();
        if (!orders.isEmpty()) {
            System.out.println("\nCustomer Orders:");
            System.out.println("=================");
            for (PurchaseOrder order : orders) {
                System.out.println("Order ID: " + order.getId());
                System.out.println("Total Amount: " + order.getTotalAmount());
                // Additional order details can be displayed here
            }
        } else {
            System.out.println("No orders for this customer.");
        }
    }}
    
    private static void viewcustomerdetails() {
        Session session = null;
        try {
            // Open session
            session = sessionFactory.openSession();

            // Execute HQL query to fetch all customers
            String hqlQuery = "SELECT c.customerId, c.firstName, c.email, c.address FROM Customer c";
            List<Object[]> customerDetailsList = session.createQuery(hqlQuery, Object[].class).list();

            // Check if customerDetailsList is empty
            if (customerDetailsList.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                System.out.println("\nCustomer Details:");
                System.out.println("===========================================================");

                // Print details of each customer
                for (Object[] customerDetails : customerDetailsList) {
                    long customerId = (long) customerDetails[0];
                    String firstname = (String) customerDetails[1];
                    String email = (String) customerDetails[2];
                    String address = (String) customerDetails[3];

                    System.out.println("Customer ID: " + customerId);
                    System.out.println("Name: " + firstname);
                    System.out.println("Email: " + email);
                    System.out.println("Address: " + address);

                    System.out.println("-----------------------------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch customer details. Error: " + e.getMessage());
        } finally {
            // Close session in finally block to ensure it is closed even if an exception occurs
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


/*------------------------------------------------------------------------------------------*/    
   public static void updateProduct() {
	    scanner = new Scanner(System.in);
	    
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    
	    SessionFactory sessionFactory = configuration.buildSessionFactory();
	    Session session = sessionFactory.openSession();
	    
	    System.out.print("Enter Product Id to Update: ");
	    Long id = scanner.nextLong();
	    scanner.nextLine();
	    
	    Product product = session.get(Product.class, id);
	    
	    if (product == null) {
	        System.out.println("There is no Product Found for This Id.");
	    } else {
	        Transaction transaction = session.beginTransaction();
	        
	        System.out.print("Enter the New Name for Product Id " + id + ": ");
	        String newName = scanner.nextLine();
	        product.setName(newName);
	        
	        System.out.print("Enter the New Category for Product Id " + id + ": ");
	        String newCategory = scanner.nextLine();
	        product.setCategory(newCategory);
	        
	        System.out.print("Enter the New Price for Product Id " + id + ": ");
	        double newPrice = scanner.nextDouble();
	        product.setPrice(newPrice);
	        
	        session.update(product);
	        System.out.println("Product Updated Successfully.");
	        
	        transaction.commit();
	        session.close();
	    }
	}
   public static void updateOrder()  {
	    Scanner scanner = new Scanner(System.in);

	    // Assuming you already have a valid Hibernate session
	    Session session = sessionFactory.openSession(); // Your Hibernate session initialization here

	    System.out.println("Enter Order Id to Update: ");
	    long id = scanner.nextLong();
	    scanner.nextLine();

	    PurchaseOrder purchaseorder = session.get(PurchaseOrder.class, id);
	    if (purchaseorder == null) {
	        System.out.println("No order found for the provided Id.");
	    } else {
	        Transaction transaction = session.beginTransaction();
	        try {
	            System.out.print("Enter New Order Date (YYYY-MM-DD): ");
	            String dateString = scanner.nextLine();
	            LocalDate orderDate = LocalDate.parse(dateString);

	            System.out.print("Enter New Total Amount: ");
	            float totalAmount = scanner.nextFloat();
	            scanner.nextLine(); // Consume newline character

	           purchaseorder.setOrderDate(orderDate);
	            purchaseorder.setTotalAmount(totalAmount);

	            // Optionally, update cart items associated with the order
	            // For example, you can access the cart items using order.getCartItems()
	            // and update them accordingly

	            session.update(purchaseorder);
	            transaction.commit();
	            System.out.println("Order details updated successfully.");
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            System.out.println("Failed to update order details. Error: " + e.getMessage());
	        } finally {
	            session.close();
	            scanner.close();
	        }
	    }
	}


   
   public static void updateShoppingCart() {
	    Scanner scanner = new Scanner(System.in);

	    while(true) {
	        System.out.println("\nSelect What you Want to Update in Shopping Cart.");
	        System.out.println("----------------------------------------------------");
	        System.out.println("1. Update Product.");
	        System.out.println("2. Update Order.");
	        System.out.println("3. Update Order Item.");
	        System.out.println("4. Update Cart Item.");
	        System.out.println("5. Update Customer.");
	        System.out.println("6. Back.");
	        System.out.print("Enter your Choice: ");
	        
	        try {
	            int choice = scanner.nextInt();
	            
	            switch(choice) {
	                case 1:
	                    updateProduct();
	                    System.out.println();
	                    break;
	                    
	                case 2:
	                    updateOrder();
	                    System.out.println();
	                    break;
	                    
	                case 3:
	                    updateOrderItem();
	                    System.out.println();
	                    break;
	                    
	                case 4:
	                    updateCartItem();
	                    System.out.println();
	                    break;
	                    
	                case 5:
	                    updateCustomer();
	                    System.out.println();
	                    break;
	                    
	                case 6:
	                    return;
	                    
	                default:
	                    System.out.println("Invalid Choice! Please enter a valid choice.");
	                    System.out.println();
	                    break;            
	            }
	        } catch(InputMismatchException e) {
	            System.out.println("Please Enter an Integer Value.");
	            System.out.println();
	            scanner.nextLine();
	        }
	    }
	}

	
	public static void updateOrderItem() {
	    // Other initialization code...

	    System.out.print("Enter Order Item Id to Update Quantity: ");
	    long id = scanner.nextLong();
	    scanner.nextLine();

	    OrderItem orderItem = session.get(OrderItem.class, id);
	    if (orderItem == null) {
	        System.out.println("No order item found for the provided Id.");
	    } else {
	        Transaction transaction = session.beginTransaction();

	        System.out.print("Enter the New Quantity for Order Item Id " + id + ": ");
	        int quantity = scanner.nextInt();
	        orderItem.setQuantity(quantity);
	        
	        System.out.print("Enter the New Total Amount for Order Item Id " + id + ": ");
	        float totalamount = scanner.nextFloat();
	        

	        // Check if the associated order is not null before updating its total amount
	        PurchaseOrder order = orderItem.getPurchaseorder();
	        if (order != null) {
	            order.setTotalAmount(totalamount);
	        }

	        session.update(orderItem);
	        transaction.commit();
	        System.out.println("Order item quantity updated successfully.");

	        session.close();
	    }
	}

	

		 
		    

	public static void updateCartItem() {
	    try {
	        // Other initialization code...

	        System.out.print("Enter Cart Item Id to Update Quantity: ");
	        long id = scanner.nextLong();
	        scanner.nextLine();

	        CartItem cartItem = session.get(CartItem.class, id);
	        if (cartItem == null) {
	            System.out.println("No cart item found for the provided Id.");
	        } else {
	            Transaction transaction = session.beginTransaction();

	            System.out.print("Enter the New Quantity for Cart Item Id " + id + ": ");
	            int quantity = scanner.nextInt();
	            cartItem.setQuantity(quantity);

	            // Check if the associated product is not null before updating its total amount
	            Product product = cartItem.getProduct();
	            if (product != null) {
	                cartItem.setProduct(product);
	            }

	            session.update(cartItem);
	            transaction.commit();
	            System.out.println("Cart item quantity updated successfully.");
	        }
	    } catch (Exception e) {
	        System.out.println("Failed to update cart item quantity. Error: " + e.getMessage());
	    }
	}

	    

	public static void updateCustomer()  {
	    // Other initialization code...

	    System.out.print("Enter Customer Id to Update Details: ");
	    long id = scanner.nextLong();
	    scanner.nextLine();

	    Customer customer = session.get(Customer.class, id);
	    if (customer == null) {
	        System.out.println("No customer found for the provided Id.");
	    } else {
	        Transaction transaction = session.beginTransaction();

	        System.out.print("Enter the New Email for Customer Id " + id + ": ");
	        String email = scanner.nextLine();
	        customer.setEmail(email);

	        System.out.print("Enter the New First Name for Customer Id " + id + ": ");
	        String firstName = scanner.nextLine();
	        customer.setFirstName(firstName);

	        System.out.print("Enter the New Address for Customer Id " + id + ": ");
	        String address = scanner.nextLine();
	        customer.setLaddress(address);

	        // Assuming you have a method to fetch the Order object by its Id
	        System.out.print("Enter the Order Id for Customer Id " + id + ": ");
	        long orderId = scanner.nextLong();
	        PurchaseOrder purchaseorder = session.get(PurchaseOrder.class, orderId);
	        

	        session.update(customer);
	        transaction.commit();
	        System.out.println("Customer details updated successfully.");

	        session.close();
	    }
	}

	    
	
	/*-------------------------------------------------------------------------------------------------------------------------------------*/
	public static void deleteProductById() {
	    scanner = new Scanner(System.in);

	    System.out.print("Enter Product Id to Delete: ");
	    Long id = scanner.nextLong();
	    scanner.nextLine();

	    Product product = session.get(Product.class, id);

	    if (product == null) {
	        System.out.println("Product with ID " + id + " does not exist.");
	        return;
	    } else {
	        System.out.println(". . . . . . . . . . . . . . . . . . .  A  L  E  R  T  . . . . . . . . . . . . . . . . . . .");
	        System.out.println("You Are About to Permanently Delete a Particular Product.");

	        System.out.print("Do You Wish to Continue? (Yes/No) : ");
	        String confirmation = scanner.nextLine();

	        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
	            // Check if the product has associated order items
	            List<OrderItem> orderItems = session.createQuery("FROM OrderItem WHERE product = :product", OrderItem.class)
	                    .setParameter("product", product)
	                    .getResultList();

	            if (!orderItems.isEmpty()) {
	                System.out.println("This product is associated with order items.");
	                System.out.print("Do you want to delete the associated order items as well? (Yes/No): ");
	                String deleteOrderItemsConfirmation = scanner.nextLine();
	                if (deleteOrderItemsConfirmation.equalsIgnoreCase("yes") || deleteOrderItemsConfirmation.equalsIgnoreCase("y")) {
	                    // Delete associated order items
	                    for (OrderItem orderItem : orderItems) {
	                        session.delete(orderItem);
	                    }
	                } else {
	                    System.out.println("Deletion of associated order items cancelled.");
	                    return;
	                }
	            }

	            // Proceed with deleting the product
	            Transaction transaction = session.beginTransaction();
	            session.delete(product);
	            transaction.commit();
	            System.out.println("Product with ID " + id + " Deleted Successfully.");
	        } else {
	            System.out.println("Deletion Cancelled!");
	        }
	    }
	}


	public static void deleteAllProducts() {
	    scanner = new Scanner(System.in);

	    String hqlQuery = "select count(*) from Product";
	    Long count = session.createQuery(hqlQuery, Long.class).getSingleResult();

	    if (count == 0) {
	        System.out.println("There are no products to delete.");
	        return;
	    }

	    System.out.println(". . . . . . . . . . . . . . . .  A   L   E   R   T . . . . . . . . . . . . . . . .");
	    System.out.println("You are about to permanently delete all product records.");

	    System.out.print("Do You Wish to Continue? (Yes/No) : ");
	    String confirmation = scanner.nextLine();

	    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
	        Transaction transaction = session.beginTransaction();
	        Query deleteQuery = session.createQuery("delete from Product");
	        deleteQuery.executeUpdate();
	        transaction.commit();
	        System.out.println("All products deleted successfully.");
	    } else {
	        System.out.println("Deletion cancelled.");
	    }
	}

	public static void deleteshoppingcart() {
	    while (true) {
	        System.out.println("\nSelect How You Want to Delete Product Record:");
	        System.out.println("----------------------------------------------");
	        System.out.println("1. Delete Particular Product.");
	        System.out.println("2. Delete All Products.");
	        System.out.println("3. Delete Particular orderID .");
	        System.out.println("4. Delete All Order Details .");
	        System.out.println("5. Delete  Particular OrderItemID.");
	        System.out.println("6. Delete All Order Item Details  .");
	        System.out.println("7. Delete Particular Cart Item ID.");
	        System.out.println("8. Delete All Cart Items Details.");
	        System.out.println("9. Delete Particular CustomerID.");
	        System.out.println("10. Delete All Customer Details.");
	        System.out.println("11. Back.");
	        System.out.print("\nEnter Your Choice: ");

	        try 
	        {
	            int choice = scanner.nextInt();

	            switch (choice) 
	            {
	                case 1:
	                    deleteProductById();
	                    System.out.println();
	                    break;
	                case 2:
	                    deleteAllProducts();
	                    System.out.println();
	                    break;
	                case 3:
	                	deleteOrderById();
	                System.out.println();
	                break;
	                case 4:
	                	deleteAllOrderDetails();
	                	break;
	                case 5:
	                	deleteOrderItemById();
	                	break;
	                case 6:
	                	deleteAllOrderItems();
	                	break;
	                case 7:
	                	deleteCartItembyId();
	                	break;
	                case 8:
	                	deleteAllCartItems();
	                	break;
	                case 9:
	                	deletecustomerId();
	                	break;
	                case 10:
	                	deleteAllCustomers();
	                	break;
	                case 11:
	                    return;
	                default:
	                    System.out.println("Please Enter a Valid Choice.");
	                    System.out.println();
	                    break;
	            }
	        }
	        catch (InputMismatchException e)
	        {
	            System.out.println("Please Enter an Integer Value.");
	            System.out.println();
	            scanner.nextLine();
	        }
	    }
	}

	public static void deleteOrderById() {
	    scanner = new Scanner(System.in);

	    System.out.print("Enter Order Id to Delete: ");
	    Long orderId = scanner.nextLong();
	    scanner.nextLine();

	    PurchaseOrder order = session.get(PurchaseOrder.class, orderId);

	    if (order == null) {
	        System.out.println("Order with ID " + orderId + " does not exist.");
	        return;
	    }

	    System.out.println(". . . . . . . . . . . . . . . . . . .  A  L  E  R  T  . . . . . . . . . . . . . . . . . . .");
	    System.out.println("You Are About to Permanently Delete a Particular Order.");

	    System.out.print("Do You Wish to Continue? (Yes/No) : ");
	    String confirmation = scanner.nextLine();

	    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
	        Transaction transaction = session.beginTransaction();

	        // Delete associated billing records
	        session.createQuery("DELETE FROM Billing b WHERE b.purchaseorder.id = :orderId")
	                .setParameter("orderId", orderId)
	                .executeUpdate();

	        // Delete the order
	        session.delete(order);

	        transaction.commit();
	        System.out.println("Order with ID " + orderId + " Deleted Successfully.");
	    } else {
	        System.out.println("Deletion Cancelled!");
	    }
	}




private static void deleteAllOrderDetails() {
	
	    Scanner scanner = new Scanner(System.in);
	    
	    // Count the total number of OrderItems
	    String hqlQuery = "select count(*) from OrderItem";
	    Long count = session.createQuery(hqlQuery, Long.class).getSingleResult();
	    
	    if(count == 0) {
	        System.out.println("There are no Order Items to delete.");
	        
	    }
	    
	    System.out.println(". . . . . . . . . . . . . . . .  A   L   E   R   T . . . . . . . . . . . . . . . .");
	    System.out.println("You are about to permanently delete all Order Items.");
	    System.out.print("Do You Wish to Continue? (Yes/No) : ");

	    String confirmation = scanner.nextLine();

	    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
	        Transaction tx = session.beginTransaction();

	        // Delete all OrderItems
	        Query deleteOrderItemsQuery = session.createQuery("delete from OrderItem");
	        int deletedOrderItemsCount = deleteOrderItemsQuery.executeUpdate();

	        tx.commit();

	        System.out.println(deletedOrderItemsCount + " Order Items removed.");
	    } else {
	        System.out.println("Deletion cancelled.");
	    }
	    
	    scanner.close();
	}


private static void deleteOrderItemById(){
	Scanner scanner = new Scanner(System.in);
Session session = sessionFactory.openSession();
Transaction transaction = null;

try {
    transaction = session.beginTransaction();
    System.out.println("Delete OrderItem ID :");
    Long orderItemId=scanner.nextLong();
    

    OrderItem orderItem = session.get(OrderItem.class, orderItemId);

    if (orderItem == null) {
        System.out.println("Order Item with ID " + orderItemId + " does not exist.");
        return;
    }

    System.out.println("You Are About to Permanently Delete a Particular Order Item.");

    System.out.print("Do You Wish to Continue? (Yes/No) : ");
    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
        // Get the associated order for potential updates
        PurchaseOrder purchaseorder = orderItem.getPurchaseorder();

        // Remove the order item from the associated order (if exists)
        if (purchaseorder != null) {
            purchaseorder.removeOrderItem(orderItem);
            session.saveOrUpdate(purchaseorder);
        }

        // Finally, delete the order item
        session.delete(orderItem);
        transaction.commit();

        System.out.println("Order Item with ID " + orderItemId + " Deleted Successfully.");
    } else {
        System.out.println("Deletion Cancelled!");
    }
} catch (Exception e) {
    if (transaction != null) {
        transaction.rollback();
    }
    System.out.println("Failed to delete Order Item. Error: " + e.getMessage());
} finally {
    if (session != null) {
        session.close();
    }
    scanner.close();
}}

private static void deleteAllOrderItems()  {
    Scanner scanner = new Scanner(System.in);

    // Count the total number of OrderItems
    String hqlQuery = "select count(*) from OrderItem";
    Long count = session.createQuery(hqlQuery, Long.class).getSingleResult();

    if (count == 0) {
        System.out.println("There are no Order Items to delete.");
        return;
    }

    System.out.println(". . . . . . . . . . . . . . . .  A   L   E   R   T . . . . . . . . . . . . . . . .");
    System.out.println("You are about to permanently delete all Order Items.");
    System.out.print("Do You Wish to Continue? (Yes/No) : ");

    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
        Transaction tx = session.beginTransaction();

        // Get all OrderItems
        Query query = session.createQuery("from OrderItem");
        List<OrderItem> orderItems = query.getResultList();


        for (OrderItem orderItem : orderItems) {
            // Get the associated order and product
            PurchaseOrder purchaseorder = orderItem.getPurchaseorder();
            Product product = orderItem.getProduct();

            

            // Finally, delete the order item
            session.delete(orderItem);
        }

        tx.commit();

        System.out.println(count + " Order Items removed.");
    } else {
        System.out.println("Deletion cancelled.");
    }

    scanner.close();
}

private static void deleteCartItembyId()  {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter Cart Item Id to Delete: ");
    long id = scanner.nextLong();
    scanner.nextLine();

    CartItem cartItem = session.get(CartItem.class, id);

    if (cartItem == null) {
        System.out.println("Cart Item with ID " + id + " does not exist.");
        return;
    } else {
        System.out.println(". . . . . . . . . . . . . . . . . . .  A  L  E  R  T  . . . . . . . . . . . . . . . . . . .");
        System.out.println("You Are About to Permanently Delete a Particular Cart Item.");

        System.out.print("Do You Wish to Continue? (Yes/No) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            Transaction transaction = session.beginTransaction();
            
            // Get the associated product
            Product product = cartItem.getProduct();
            
            // Remove the cart item from the product
            if (product != null) {
                // Update the product's quantity or perform any necessary actions
                session.update(product);
            }
            
            // Finally, delete the cart item
            session.delete(cartItem);
            transaction.commit();
            System.out.println("Cart Item with ID " + id + " Deleted Successfully.");
        } else {
            System.out.println("Deletion Cancelled!");
        }
    }
}

private static void deleteAllCartItems()
{
    Scanner scanner = new Scanner(System.in);

    System.out.println(". . . . . . . . . . . . . . . .  A   L   E   R   T . . . . . . . . . . . . . . . .");
    System.out.println("You are about to permanently delete all Cart Items.");
    System.out.print("Do You Wish to Continue? (Yes/No) : ");
    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
        Transaction transaction = session.beginTransaction();

        // Query to delete all cart items
        Query deleteQuery = session.createQuery("delete from CartItem");
        int deletedCount = deleteQuery.executeUpdate();

        transaction.commit();

        System.out.println(deletedCount + " Cart Items removed.");
    } else {
        System.out.println("Deletion cancelled.");
    }

    scanner.close();
}

public static void deletecustomerId() {
	
	    Scanner scanner = new Scanner(System.in);
	    Session session = null;
	    Transaction transaction = null;
	    
	    try {
	        session = sessionFactory.openSession();
	        transaction = session.beginTransaction();

	        System.out.print("Enter the Customer ID to delete: ");
	        long id = scanner.nextLong();
	        scanner.nextLine(); // Consume the newline character
	        
	        Customer customer = session.get(Customer.class, id);
	        if (customer == null) {
	            System.out.println("Customer with ID " + id + " not found.");
	        } else {
	            session.delete(customer);
	            transaction.commit();
	            System.out.println("Customer with ID " + id + " deleted successfully.");
	        }
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	       
	    }
	}
private static void deleteAllCustomers() {
    Scanner scanner = new Scanner(System.in);

    System.out.println(". . . . . . . . . . . . . . . .  A   L   E   R   T . . . . . . . . . . . . . . . .");
    System.out.println("You are about to permanently delete all Customers.");
    System.out.print("Do You Wish to Continue? (Yes/No) : ");
    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
        Transaction transaction = session.beginTransaction();

        // Query to delete all customers
        Query deleteQuery = session.createQuery("delete from Customer");
        int deletedCount = deleteQuery.executeUpdate();

        transaction.commit();

        System.out.println(deletedCount + " Customers removed.");
    } else {
        System.out.println("Deletion cancelled.");
    }

    scanner.close();
}

}
