package model.paf;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment_front", "root", "");
			
			//For testing
			System.out.print("Successfully connected");
			
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String pay_no,String user_id, String method , String status , String amount , String date , String description) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) 
			{
				return "Error while connecting to the database for inserting.";
			}
			
			// create a prepared statement
			String query = "insert into payment(`pay_no`,`payment_id`,`user_id`,`method`,`status`,`amount`,`date`,`description`)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, (pay_no));
			preparedStmt.setInt(2, 0);
			preparedStmt.setString(3, (user_id));
			preparedStmt.setString(4, method);
			preparedStmt.setString(5, status);
			preparedStmt.setDouble(6, Double.parseDouble(amount));
			preparedStmt.setDate(7, java.sql.Date.valueOf(date));
			preparedStmt.setString(8, description);
			
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayments() {

		String output = "";
		try 
		{
			Connection con = connect();
			if (con == null) 
			{
				return "Error while connecting to the database for reading.";
		    }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Pay NO</th> <th>User ID</th> <th>Method</th> <th>Status</th> <th>Amount</th> "
					+ "<th>Date</th> <th>Description</th>  <th>Update</th><th>Remove</th></tr>";
			
			
			
			
			
			
			
			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String pay_no = Integer.toString(rs.getInt("pay_no"));
				String payment_id = Integer.toString(rs.getInt("payment_id"));
				String user_id = Integer.toString(rs.getInt("user_id"));
				String method = rs.getString("method");
				String status = rs.getString("status");
				String amount = Double.toString(rs.getDouble("amount"));
			     Date date = rs.getDate("date");
				String description = rs.getString("description");
				
				
				
				// Add into the html table
				
				output += "<tr><td><input id= 'hidpayment_idUpdate' name = 'hidpayment_idUpdate' type='hidden' value= '" + pay_no
						+ "'>"+ payment_id + "</td>";
				output += "<td>" + user_id + "</td>";
				output += "<td>" + method + "</td>";
				output += "<td>" + status + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + date + "</td>";
				//String description;
				output += "<td>" + description + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-payment_id='"
						+ payment_id + "'>" + "</td></tr>";
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		} 
		catch (Exception e)
		{
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String pay_no,String payment_id ,String user_id , String method , String status , String amount , String date , String description) {
		String output = "";
		
		try 
		{
			Connection con = connect();
			if (con == null) 
			{
				return "Error while connecting to the database for updating.";
			}
			
			// create a prepared statement
			String query = "UPDATE payment SET pay_no=?,user_id=?,method=?,status=?,amount=?,date=?,description=? WHERE payment_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, (pay_no));
			preparedStmt.setString(2, (user_id));
			preparedStmt.setString(3,method);
			preparedStmt.setString(4, status);
			preparedStmt.setDouble(5, Double.parseDouble(amount));
			preparedStmt.setDate(6, java.sql.Date.valueOf(date));
			preparedStmt.setString(7, description);
			preparedStmt.setInt(8, Integer.parseInt(payment_id));
			
			
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayments();
			
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePayment(String payment_id) {
		String output = "";
	
		try 
		{
			Connection con = connect();
			if (con == null) 
			{
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from payment where payment_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(payment_id));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayments();
			
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
		} 
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
