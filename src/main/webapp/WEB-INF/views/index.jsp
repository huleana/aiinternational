<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">

  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" />
  <spring:url value="/resources/css/app.min.css" var="appCss" />
  <link href="${appCss}" rel="stylesheet" />

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <title>User Reviews Analytic Tool</title>
  <script type='text/javascript'>//<![CDATA[
    var productList;
    $(document).ready(function() {
		$('#callApi').click(function() {
			callApi("http://localhost:8088");
        });
    });

	function showModal(obj) {
		var product = getProductById(obj.id.slice(8));
		$('#modal-productName').text(product.productName);
		$('#modal-price').text(product.price);
		$('#modal-rating').text(product.rating);
		$('#modal-description').text(product.description);
		$('#modal-review').text(product.review);
		$('#myModal').modal('show');
	}

	function getProductById(productId){
		for(var i=0; i<productList.length; i++) {
			if (productList[i].productId == productId) {
				return productList[i];
			}
		}
		return null;
	}

	function callApi(apiUrl){
		var userInput = $('#userInput').val();
		if (isEmpty(userInput)) {
			alert("User Input is required.");
			$('#userInput').focus();
			return false;
		}

		userInput = '{"condition":"' + userInput + '"}';
		var method = "POST";
		var requestMapping = "analyze";
		makeApiCall(method, requestMapping, userInput);
	}

	function isEmpty(value){
	  return (value == null || value.length === 0);
	}

	function makeApiCall(method, apiUrl, requestJsonData) {
		$.ajax({
			headers : {
				'Content-Type' : 'application/json;charset=utf-8',
			},
			type: method,
            url: apiUrl,
            dataType: 'json',
			data : requestJsonData,
			success : function(response) {
				if (response.status != 'OK') {
					$("#errMsg").html('No data found');
					$("#errMsg").show();
				} else {
					$("#errMsg").html('');
					$("#errMsg").hide();
					$("#productList").html('');
					var resultSize = response.productList.length;
					productList = response.productList;
					//resultSize = 3;
					for(var i=0; i<resultSize; i++) {
						var productId = response.productList[i].productId;
						var productName = response.productList[i].productName;
						var price = response.productList[i].price;
						var description = response.productList[i].description;
						$("#productList").append($("#productTemplate").clone().html()
										.replace('{{productId}}', productId)
										.replace('{{productName}}', productName)
										.replace('{{price}}', price)
										.replace('{{description}}', description));
					}
				}
			},
			error: function (jqXHR, exception) {
				var msg = '';
				if (jqXHR.status === 0) {
					msg = 'Status Code: ' + jqXHR.status + '\nError Message:Not connect.\n Verify Network.';
				}else {
					try {
						msg = 'Status Code: ' + jqXHR.status + '\nError Message:\n' + JSON.stringify(JSON.parse(jqXHR.responseText), null, 4);
					} catch (e) {
						msg = 'Status Code: ' + jqXHR.status + '\nError Message:\n' + jqXHR.responseText;
					}
				}
				msg = "Status: Error. \n" + msg + "\n";

                $("#errMsg").html(msg);
				$("#errMsg").show();
			}
		});
	}
//]]>
  </script>
</head>
<body>
  <div id="main-container">
	<div style="margin-left: 20px;">
      <h2>AI-User Reviews Analytic Tool</h2>
    </div>
	<div class="padding-md">
		<div class="panel panel-default">
			<div class="panel-heading"><strong>Search form</strong></div>
			<div class="panel-body">
				<form class="form-inline no-margin">
					<div class="row">
						<div class="col-md-5 input-group" style="margin-left: 20px;">
							<input type="text" class="form-control input-sm" id="userInput">
							<div class="input-group-btn">
								<button type="button" class="btn btn-sm btn-success" tabindex="-1" id="callApi">Search</button>
							</div> <!-- /input-group-btn -->
						</div> <!-- /input-group -->
					</div><!-- /.row -->
				</form>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading"><strong>Analyze Result</strong></div>
			<div class="panel-body">
				<div class="row" id="errMsg" style="display: none;">
					<div class="alert alert-danger"></div>
				</div>
				<template id="productTemplate">
					<div class="col-md-3 col-sm-6">
						<div class="pricing-widget active">
							<div class="ribbon-wrapper">
								<div class="ribbon-inner bg-success shadow-pulse">
									Hot
								</div>
							</div>
							<div class="pricing-head">
								Product
							</div>
							<div class="pricing-body">
								<div class="pricing-cost">
									<strong class="block">{{productName}}</strong>
									<small>{{price}}</small>
								</div>
								<ul class="pricing-list text-center">
									<li>Description <strong>{{description}}</strong></li>
									<li class="text-center"><a href="#" data-toggle="modal" onClick="showModal(this)" class="btn btn-default btn-success" id="details_{{productId}}">Details</a></li>
								</ul>
							</div>
						</div><!-- /pricing-widget -->
					</div>
				</template>
				<div class="row" id="productList">
				</div><!-- /.row -->

				<!-- Modal -->
				<div class="modal fade" id="myModal" role="dialog">
					<div class="modal-dialog">
					  <!-- Modal content-->
					  <div class="modal-content">
						<div class="modal-header">
						  <button type="button" class="close" data-dismiss="modal">&times;</button>
						  <h4 class="modal-title">Product Details</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal" style="margin-left: 100px;">
								<div class="form-group">
									<label for="inputEmail1" class="col-lg-2 control-label" style="margin-top: -7px;">Name: </label>
									<div class="col-lg-10">
										<span id="modal-productName"></span>
									</div><!-- /.col -->
								  </div>
								  <div class="form-group">
									<label for="inputEmail1" class="col-lg-2 control-label" style="margin-top: -7px;">Price: </label>
									<div class="col-lg-10">
										<span id="modal-price"></span>
									</div><!-- /.col -->
								  </div>
								  <div class="form-group">
									<label for="inputEmail1" class="col-lg-2 control-label" style="margin-top: -7px;">Rating: </label>
									<div class="col-lg-10">
										<span id="modal-rating"></span>
									</div><!-- /.col -->
								  </div>
								  <div class="form-group">
									<label for="inputEmail1" class="col-lg-2 control-label" style="margin-top: -7px;">Description: </label>
									<div class="col-lg-10">
										<span id="modal-description"></span>
									</div><!-- /.col -->
								  </div>
								  <div class="form-group">
									<label for="inputEmail1" class="col-lg-2 control-label" style="margin-top: -7px;">Review: </label>
									<div class="col-lg-10">
										<span id="modal-review"></span>
									</div><!-- /.col -->
								  </div>
							</form>
						</div>
						<div class="modal-footer">
						  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						</div>
					  </div>
					</div>
				</div>
			</div>
		</div>
  </div>
</body>
</html>