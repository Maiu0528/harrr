$(document).ready(function() {
	
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidpayment_idSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});
});

function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidpayment_idSave").val("");
	$("#formHospital")[0].reset();
}
// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidpayment_idSave").val(
					$(this).closest("tr").find('#hidpayment_idUpdate').val());
			$("#pay_no").val($(this).closest("tr").find('td:eq(0)').text());
			$("#user_id").val($(this).closest("tr").find('td:eq(1)').text());
			$("#method").val($(this).closest("tr").find('td:eq(2)').text());
			$("#status").val($(this).closest("tr").find('td:eq(3)').text());
			$("#amount").val($(this).closest("tr").find('td:eq(4)').text());
			$("#date").val($(this).closest("tr").find('td:eq(5)').text());
			$("#description").val($(this).closest("tr").find('td:eq(6)').text());
		});

// REMOVE ====================================================

$(document).on("click", ".btnRemove", function(event) {
	
	$.ajax({
		url : "PaymentAPI",
		type : "DELETE",
		data : "payment_id=" + $(this).data("payment_id"),
		dataType : "text",
		complete : function(response, status) {
			onHospitalDeleteComplete(response.responseText, status);
		}
	});
});
function onHospitalDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validatePaymentForm() {
	// PNO
	if ($("#pay_no").val().trim() == "") {
		return "Insert Pay NO.";
	}
	// UID
	if ($("#user_id").val().trim() == "") {
		return "Insert User ID.";
	}
	//Method
	if ($("#method").val().trim() == "") {
		return "Insert Method.";
	}
	// Status-------------------------------
	if ($("#status").val().trim() == "") {
		return "Insert Status.";
	}
	
	// Amount-------------------------------
	if ($("#amount").val().trim() == "") {
		return "Insert Amount.";
	}
	// is numerical value
	var tmpCharge = $("#amount").val().trim();
	if (!$.isNumeric(tmpCharge)) {
		return "Insert a numerical value for Amount.";
	}
	// convert to decimal price
	$("#amount").val(parseFloat(tmpCharge).toFixed(2));
	// Date-------------------------------
	if ($("#date").val().trim() == "") {
		return "Insert Date.";
	}
	// Description-------------------------------
	if ($("#description").val().trim() == "") {
		return "Insert Description.";
	}
	
	
	return true;

}
