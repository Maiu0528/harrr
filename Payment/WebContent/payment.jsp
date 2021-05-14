<%@page import="model.paf.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payment</h1>
				<form id="formPayment" name="formPayment">
				    Pay NO:
					<input id="pay_no" name="pay_no" type="text"
						class="form-control form-control-sm"> <br>	
					User ID:
					<input id="user_id" name="user_id" type="text"
						class="form-control form-control-sm"> <br>
						
					Method : 
					 <input id="method" name="method" type="text"
						class="form-control form-control-sm"> <br> 
						
					 Status : 
					 <input id="status" name="status" type="text"
						class="form-control form-control-sm"> <br> 
						
					Amount : 
					 <input id="amount" name="amount" type="text"
						class="form-control form-control-sm"> <br> 
						
						
					Date : 
					 <input id="date" name="date" type="text"
						class="form-control form-control-sm"> <br> 	
					
					
					Description : 
					 <input id="description" name="description" type="text"
						class="form-control form-control-sm"> <br> 
					
						
						
						<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidpayment_idSave" name="hidpayment_idSave" value="">
						
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divPaymentGrid">
					<%
						Payment paymentObj = new Payment();
					                  out.print(paymentObj.readPayments());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>