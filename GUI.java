/* Name: <Donald Kelly>
Course: CNT 4714 – Spring 2024
Assignment title: Project 1 – An Event-driven Enterprise Simulation
Date: Sunday January 30, 2024
*/

package project1;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



public class GUI {

	private JFrame frmNilecom;    //main-frame
	private JTextField textField; 
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	
	private JLabel lblNewLabel_5;
	
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JButton findItemButton;
	private JButton viewCartButton;
	private JButton emptyCartButton;
	private JButton addItemButton;
	private JButton checkOutButton;
	private JButton exitButton;
	
	
	// variable for current item number
	private int currentItemNumber = 1;
	
	private int currentShoppingNumber = 0;
	
	//total after discount
	 private double subtotal = 0.0;
	 
	
	 // Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmNilecom.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 //Create the application.
	
	public GUI() {
		initialize();
		
		
		
	}

//---------------------------------------------------------------------------------------------------------------------------------//
	
	
	//Button 1/6 Find item 
	
	private String searchItemInCSV(String itemId, String quantity) {
	    String csvFile = "C:\\Users\\donal\\Downloads\\inventory.csv";
	    String line = "";
	    String csvSplitBy = ",";

	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(csvSplitBy);
	            if (data.length == 5 && data[0].equals(itemId)) {
	                boolean isAvailable = Boolean.parseBoolean(data[2].trim());

	                if (isAvailable) {
	                    int availableQuantity = Integer.parseInt(data[3].trim());
	                    int requestedQuantity = Integer.parseInt(quantity.trim());

	                    if (requestedQuantity <= availableQuantity) {
	                        double price = Double.parseDouble(data[4]);
	                        double discount = calculateDiscount(requestedQuantity);
	                        double discountedPrice = price * (1 - discount);
	                        
	                        return data[0] + ", Description=" + data[1] +
	                                ", In Stock=" + availableQuantity + ", Quantity=" + quantity +
	                                ", Price=" + data[4] + ", Discount=" + (discount * 100) + "%" +
	                                ", Total Price=" + discountedPrice;
	                    } else {
	                        showInfoPopup("Requested quantity exceeds available quantity for item ID " + itemId);
	                        textField.setText("");  // Clear item ID field
		                    textField_1.setText(""); // Clear quantity field
	                        return "Item not found - Quantity exceeds available stock";
	                    }
	                } else {
	                    showInfoPopup("Item ID " + itemId + " is out of stock");
	                    textField.setText("");  // Clear item ID field
	                    textField_1.setText(""); // Clear quantity field
	                    return "Item not found - Item ID out of stock";
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    showInfoPopup("Item ID " + itemId + " is not in file");
	    return "Item not found - Item ID not in file";
	}
	
	
	
	//error popup
	private void showInfoPopup(String message) {
	    JOptionPane.showMessageDialog(frmNilecom, message, "Error", JOptionPane.INFORMATION_MESSAGE);
	    findItemButton.setEnabled(true);
	    
	}
	//view cart popup
		private void showShoppingPopup(String message) {
		    JOptionPane.showMessageDialog(frmNilecom, message, "Nile Dot Com - Current Shopping Cart Status", JOptionPane.INFORMATION_MESSAGE);
		    
		}
		
		private void showInvoicePopup(String message) {
		    JOptionPane.showMessageDialog(frmNilecom, message, "Final Invoice", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		
	//Discount 
	 private double calculateDiscount(int quantity) {
		    if (quantity >= 1 && quantity <= 4) {
		        return 0.0; // 0% discount
		    } else if (quantity >= 5 && quantity <= 9) {
		        return 0.1; // 10% discount
		    } else if (quantity >= 10 && quantity <= 14) {
		        return 0.15; // 15% discount
		    } else if (quantity >= 15) {
		        return 0.2; // 20% discount
		    } else {
		        return 0.0; // Default to no discount
		    }
		}
	 
	 
	 // Button 2/6 View Cart
	 private String getCartContents() {
		    StringBuilder cartContents = new StringBuilder("Your Shopping Cart:\n\n");

		    // Append each item's details to the cartContents StringBuilder
		    if (!textField_8.getText().isEmpty()) {
		        cartContents.append("Item 1: ").append(textField_8.getText()).append("\n");
		    }
		    if (!textField_4.getText().isEmpty()) {
		        cartContents.append("Item 2: ").append(textField_4.getText()).append("\n");
		    }
		    if (!textField_5.getText().isEmpty()) {
		        cartContents.append("Item 3: ").append(textField_5.getText()).append("\n");
		    }
		    if (!textField_6.getText().isEmpty()) {
		        cartContents.append("Item 4: ").append(textField_6.getText()).append("\n");
		    }
		    if (!textField_7.getText().isEmpty()) {
		        cartContents.append("Item 5: ").append(textField_7.getText()).append("\n");
		    }

		    // Append the subtotal to the cartContents
		    cartContents.append("\nSubtotal: $").append(String.format("%.2f", subtotal));

		    return cartContents.toString();
		}
	 
	 
	 
	 //Button 3/ 6 Empty Cart
	 private void clearCart() {
		 
		    // Clear all text fields in cart
		    textField_4.setText("");
		    textField_5.setText("");
		    textField_6.setText("");
		    textField_7.setText("");
		    textField_8.setText("");
		    textField_2.setText("");
		    textField_3.setText("");

		    // Reset the subtotal
		    subtotal = 0.0;
		    updateSubtotal();

		    showInfoPopup("Your shopping cart has been cleared.");
		}
	    
	
	
	// button 4/6 add item to cart
	 private void addToCart(String searchResult) {
		    if (!searchResult.equals("Item not found")) {
		        // Extract the price from searchResult
		        String[] details = searchResult.split(", ");
		        String priceString = details[details.length - 1].substring("Price=".length());

		        // Handle the price text case
		        priceString = priceString.replaceAll("[^\\d.]", "");

		        double price = Double.parseDouble(priceString);

		        // Calculate the total price for the current item
		        int quantity = Integer.parseInt(textField_1.getText());
		        double totalPrice = price * quantity;

		        // Update the subtotal
		        subtotal += totalPrice;
		        updateSubtotal();

		        textField.setText("");
		        textField_1.setText("");

		        // Determine the text field to update based on the current item number
		        switch (currentItemNumber) {
		            case 1:
		                textField_8.setText(searchResult + ", Total Price=$" + totalPrice);
		                break;
		            case 2:
		                textField_4.setText(searchResult + ", Total Price=$" + totalPrice);
		                break;
		            case 3:
		                textField_5.setText(searchResult + ", Total Price=$" + totalPrice);
		                break;
		            case 4:
		                textField_6.setText(searchResult + ", Total Price=$" + totalPrice);
		                break;
		            case 5:
		                textField_7.setText(searchResult + ", Total Price=$" + totalPrice);
		                break;
		            default:
		                showInfoPopup("Your Cart is full");
		                break;
		        }
		    }
		}
	 
	 
	 //Print Subtotal 
	 private void updateSubtotal() {
		    textField_3.setText(String.format("%.2f", subtotal));
		}

	 
	// Button 5/6 Checkout
	 private void checkout() {
	     // Extract information from the shopping cart text fields
	     String item1Details = textField_8.getText();
	     String item2Details = textField_4.getText();
	     String item3Details = textField_5.getText();
	     String item4Details = textField_6.getText();
	     String item5Details = textField_7.getText();

	     // Check if the cart is empty
	     if (item1Details.isEmpty() && item2Details.isEmpty() && item3Details.isEmpty()
	             && item4Details.isEmpty() && item5Details.isEmpty()) {
	         showInfoPopup("Your shopping cart is empty. Add items before checking out.");
	         return;
	     }

	     // Construct the transaction details
	     StringBuilder transactionDetails = new StringBuilder();
	     if (!item1Details.isEmpty()) {
	         transactionDetails.append(item1Details).append("\n");
	     }
	     if (!item2Details.isEmpty()) {
	         transactionDetails.append(item2Details).append("\n");
	     }
	     if (!item3Details.isEmpty()) {
	         transactionDetails.append(item3Details).append("\n");
	     }
	     if (!item4Details.isEmpty()) {
	         transactionDetails.append(item4Details).append("\n");
	     }
	     if (!item5Details.isEmpty()) {
	         transactionDetails.append(item5Details).append("\n");
	     }

	     // Calculate and display the final invoice
	     String timestamp = new SimpleDateFormat("MMddyyyyHHmmss").format(new Date());
	     int numberOfItems = currentShoppingNumber;

	     // Append the transaction details to the transactions file
	     appendTransactionToCSV(timestamp, transactionDetails.toString());

	     // Display the final invoice message
	     String invoiceMessage = generateInvoiceMessage(timestamp, numberOfItems, transactionDetails.toString());
	     showInvoicePopup(invoiceMessage);

	     // Reset the cart and shopping-related variables
	     resetCart();

	     System.out.println("Transaction completed. Invoice generated.");
	 }


	 private String generateInvoiceMessage(String timestamp, int numberOfItems, String transactionDetails) {
		    // Construct the final invoice message
		    StringBuilder invoiceMessage = new StringBuilder("Nile Dot Com - FINAL INVOICE\n\n");

		    // Format timestamp
		    invoiceMessage.append("Date: ").append(timestamp).append("\n\n");

		    // Append the number of line items
		    invoiceMessage.append("Number of line items: ").append(numberOfItems).append("\n\n");

		    // Append the details of the items in the cart
		    invoiceMessage.append("Item#/ID/Title / Price/Qty/ Disc % / Subtotal:\n");
		    invoiceMessage.append(transactionDetails).append("\n");

		    // Append order subtotal, tax rate, tax amount, and order total
		    double taxRate = 0.06; // 6% tax rate 
		    double orderSubtotal = subtotal;
		    double taxAmount = orderSubtotal * taxRate;
		    double orderTotal = orderSubtotal + taxAmount;

		    invoiceMessage.append("\nOrder subtotal: $").append(String.format("%.2f", orderSubtotal)).append("\n");
		    invoiceMessage.append("Tax rate: ").append(String.format("%.0f%%", taxRate * 100)).append("\n");
		    invoiceMessage.append("Tax amount: $").append(String.format("%.2f", taxAmount)).append("\n");
		    invoiceMessage.append("ORDER TOTAL: $").append(String.format("%.2f", orderTotal)).append("\n\n");

		    invoiceMessage.append("Thanks for shopping at Nile Dot Com!");

		    return invoiceMessage.toString();
		}
		
	 private void appendTransactionToCSV(String timestamp, String transactionDetails) {
		    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\donal\\Downloads\\transactions.csv", true))) {
		        // Append the transaction details to the "transactions.csv" file with timestamp
		        writer.write(timestamp + "," + transactionDetails);
		        writer.newLine();
		        writer.flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		 private void resetCart() {
		        // Clear all text fields related to the cart
		        textField_4.setText("");
		        textField_5.setText("");
		        textField_6.setText("");
		        textField_7.setText("");
		        textField_8.setText("");
		        textField_2.setText("");
		        textField_3.setText("");

		        // Reset the subtotal
		        subtotal = 0.0;
		        updateSubtotal();

		        // Reset shopping-related variables
		        currentShoppingNumber = 0;
		        currentItemNumber = 1;
		    }
	    
		
	    
	    //Main-Frame
	    private void initialize() {
	        frmNilecom = new JFrame();
	        frmNilecom.getContentPane().setForeground(new Color(0, 0, 0));
	        frmNilecom.setTitle("Nile.com - Spring 2024");
	        frmNilecom.setBounds(100, 100, 676, 491);
	        frmNilecom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frmNilecom.getContentPane().setLayout(null);
	        
//---------------------------------------------------------------------------------------------------------------------------------//
			     
	        
	        
	        
		//Labels!! 
	        
	     //Enter Item ID label  
		lblNewLabel = new JLabel("Enter item ID for item #" + currentItemNumber + ":");
		lblNewLabel.setFont(new Font("Sitka Small", Font.BOLD, 11));
		lblNewLabel.setForeground(new Color(255, 0, 128));
		lblNewLabel.setBounds(154, 23, 156, 20);
		frmNilecom.getContentPane().add(lblNewLabel);
		
		// Enter Quantity label
		lblNewLabel_1 = new JLabel("Enter quantity for Item #" + currentItemNumber + ":");
		lblNewLabel_1.setFont(new Font("Sitka Small", Font.BOLD, 11));
		lblNewLabel_1.setForeground(new Color(255, 0, 128));
		lblNewLabel_1.setBackground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(124, 54, 180, 14);
		frmNilecom.getContentPane().add(lblNewLabel_1);
		
		//Details for item Label
		lblNewLabel_2 = new JLabel("Details for item #" + currentItemNumber + ":");
		lblNewLabel_2.setFont(new Font("Sitka Small", Font.BOLD, 11));
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(154, 79, 126, 20);
		frmNilecom.getContentPane().add(lblNewLabel_2);
		
		//Subtotal label
		
		lblNewLabel_3 = new JLabel("Current Subtotal for " + currentShoppingNumber + " item(s):");
		lblNewLabel_3.setFont(new Font("Sitka Small", Font.BOLD, 11));
		lblNewLabel_3.setForeground(new Color(0, 0, 255));
		lblNewLabel_3.setBounds(124, 109, 182, 14);
		frmNilecom.getContentPane().add(lblNewLabel_3);
		
		//Shopping cart label
		JLabel lblNewLabel_4 = new JLabel("Your shopping cart is empty:");
		lblNewLabel_4.setFont(new Font("Sitka Small", Font.BOLD, 11));
		lblNewLabel_4.setForeground(new Color(255, 0, 0));
		lblNewLabel_4.setBounds(200, 132, 355, 14);
		frmNilecom.getContentPane().add(lblNewLabel_4);
		
		
		//User Control label (Non-Dynamic)
		lblNewLabel_5 = new JLabel("USER CONTROLS");
		lblNewLabel_5.setFont(new Font("Sitka Small", Font.BOLD, 16));
		lblNewLabel_5.setBounds(34, 312, 156, 20);
		frmNilecom.getContentPane().add(lblNewLabel_5);
		
		
	//-------------------------------------------------------------------------------------------------------------------------------//
		
		
		
		//Text Boxes
		
				textField = new JTextField();
				textField.setBounds(326, 21, 229, 20);
				frmNilecom.getContentPane().add(textField);
				textField.setColumns(10);
				
				textField_1 = new JTextField();
				textField_1.setBounds(326, 49, 229, 20);
				frmNilecom.getContentPane().add(textField_1);
				textField_1.setColumns(10);
				
				textField_2 = new JTextField();
				textField_2.setBounds(326, 77, 315, 20);
				frmNilecom.getContentPane().add(textField_2);
				textField_2.setColumns(10);
				
				textField_3 = new JTextField();
				textField_3.setBounds(326, 104, 229, 20);
				frmNilecom.getContentPane().add(textField_3);
				textField_3.setColumns(10);
				
				textField_4 = new JTextField();
				textField_4.setBounds(0, 188, 660, 20);
				frmNilecom.getContentPane().add(textField_4);
				textField_4.setColumns(10);
				
				textField_5 = new JTextField();
				textField_5.setBounds(0, 219, 660, 20);
				frmNilecom.getContentPane().add(textField_5);
				textField_5.setColumns(10);
				
				textField_6 = new JTextField();
				textField_6.setBounds(0, 250, 660, 20);
				frmNilecom.getContentPane().add(textField_6);
				textField_6.setColumns(10);
				
				textField_7 = new JTextField();
				textField_7.setBounds(0, 281, 660, 20);
				frmNilecom.getContentPane().add(textField_7);
				textField_7.setColumns(10);
				
				textField_8 = new JTextField();
				textField_8.setBounds(0, 157, 660, 20);
				frmNilecom.getContentPane().add(textField_8);
				textField_8.setColumns(10);
		
		
		
				
//---------------------------------------------------------------------------------------------------------------------------------//			
		
		
		//Buttons ! 
		
		
		
		// Find Item Button
		findItemButton = new JButton("Find Item #" + currentItemNumber);
		findItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String itemId = textField.getText(); // Get item ID from textField
			     String quantity = textField_1.getText(); // Get quantity from textField_1

			     // Call the method to search for the item in the CSV file
			      String searchResult = searchItemInCSV(itemId, quantity);
			        
			        // Update textField_8 with the search result
			        textField_2.setText(searchResult);
			       
			        findItemButton.setEnabled(false);
			        viewCartButton.setEnabled(true);
			        checkOutButton.setEnabled(true);
			        addItemButton.setEnabled(true);
			        
	
			}
		});

		findItemButton.setVerticalAlignment(SwingConstants.TOP);
		findItemButton.setFont(new Font("Sitka Small", Font.PLAIN, 11));
		findItemButton.setBounds(20, 337, 284, 20);
		frmNilecom.getContentPane().add(findItemButton);
		
		
		
		
		//Empty Cart Button
		emptyCartButton = new JButton("Empty Cart - Start a New Order");
		emptyCartButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Check if the cart is empty
		        if (currentShoppingNumber == 0) {
		            showInfoPopup("Your shopping cart is already empty.");
		        } else {
		            // Clear the cart and reset variables
		            clearCart();
		            currentShoppingNumber = 0;
		            currentItemNumber = 1;

		            // Update the labels with the new currentItemNumber
		            lblNewLabel.setText("Enter item ID for item #" + currentItemNumber + ":");
		            lblNewLabel_1.setText("Enter quantity for Item #" + currentItemNumber + ":");
		            lblNewLabel_2.setText("Details for item #" + currentItemNumber + ":");
		            lblNewLabel_3.setText("Current Subtotal for " + currentShoppingNumber + " item(s):");

		            findItemButton.setText("Find Item #" + currentItemNumber);
		            addItemButton.setText("Add item #" + currentItemNumber + " To Cart");

		            // Enable/disable buttons
		            findItemButton.setEnabled(true);
		            viewCartButton.setEnabled(false);
		            emptyCartButton.setEnabled(false);
		            checkOutButton.setEnabled(false);
		            addItemButton.setEnabled(false);

		            // Update the shopping cart label
		            lblNewLabel_4.setText("Your shopping cart is empty.");
		        }
		    }
		});
		
		emptyCartButton.setVerticalAlignment(SwingConstants.TOP);
		emptyCartButton.setFont(new Font("Sitka Small", Font.PLAIN, 11));
		emptyCartButton.setBounds(20, 399, 284, 20);
		frmNilecom.getContentPane().add(emptyCartButton);
		
		
		
		
		//View Cart Button
		viewCartButton = new JButton("View Cart");
		viewCartButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (currentShoppingNumber == 0) {
		            showInfoPopup("Your shopping cart is empty.");
		        } else {
		            // Display the contents of the cart in a popup
		            String cartContents = getCartContents();
		            showShoppingPopup(cartContents);
		        }
		    }
		});
		viewCartButton.setVerticalAlignment(SwingConstants.TOP);
		viewCartButton.setFont(new Font("Sitka Small", Font.PLAIN, 11));
		viewCartButton.setBounds(20, 368, 284, 20);
		viewCartButton.setEnabled(false);
		frmNilecom.getContentPane().add(viewCartButton);

		
		
		
		//Add Item Button
		addItemButton = new JButton("Add item #" + currentItemNumber + " To Cart");
		addItemButton.setEnabled(false);
		addItemButton.setVerticalAlignment(SwingConstants.TOP);
		addItemButton.setFont(new Font("Sitka Small", Font.PLAIN, 11));
		addItemButton.setBounds(332, 337, 306, 20);
		frmNilecom.getContentPane().add(addItemButton);

		addItemButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String itemId = textField.getText();
		        String quantity = textField_1.getText();
		        String searchResult = searchItemInCSV(itemId, quantity);
        	
		            textField_2.setText(searchResult);
		            addToCart(searchResult); // Call addToCart method when the button is clicked
		            
		            findItemButton.setEnabled(true);
		            viewCartButton.setEnabled(true);
		            emptyCartButton.setEnabled(true);
		            checkOutButton.setEnabled(true);
		            addItemButton.setEnabled(false);

		            // Increment currentItemNum
		            currentItemNumber++;

		            // Check if the shopping cart is not full
		            if (currentShoppingNumber < currentItemNumber) {
		                currentShoppingNumber++;
		                lblNewLabel_4.setText("Your shopping cart has: " + currentShoppingNumber + " item(s)");
		            }
		        

		        // Update the labels with the new currentItemNumber
		        lblNewLabel.setText("Enter item ID for item #" + currentItemNumber + ":");
		        lblNewLabel_1.setText("Enter quantity for Item #" + currentItemNumber + ":");
		        lblNewLabel_3.setText("Current Subtotal for " + currentShoppingNumber + " item(s):");

		        findItemButton.setText("Find Item #" + currentItemNumber);
		        addItemButton.setText("Add item #" + currentItemNumber + " To Cart");
		    }
		});
		
		
		//Button 5/6 Checkout
		checkOutButton = new JButton("Check Out");
		checkOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        checkout();
		    }
		});
		checkOutButton.setVerticalAlignment(SwingConstants.TOP);
		checkOutButton.setFont(new Font("Sitka Small", Font.PLAIN, 11));
		checkOutButton.setBounds(332, 368, 306, 20);
		checkOutButton.setEnabled(false);
		frmNilecom.getContentPane().add(checkOutButton);
		
		
		
		
		//Button 6/6 EXIT
		exitButton = new JButton("Exit (Close App)");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.exit(0);
			}
		});
		exitButton.setVerticalAlignment(SwingConstants.TOP);
		exitButton.setFont(new Font("Sitka Small", Font.PLAIN, 11));
		exitButton.setBounds(332, 399, 306, 20);
		frmNilecom.getContentPane().add(exitButton);
	}
}


