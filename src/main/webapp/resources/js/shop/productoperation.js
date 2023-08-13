$(function() {
	// get productId from the URL
	var productId = getQueryString('productId');
	// get the URL of product info using productId
	var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
	// get the URL to the product category list of the current shop
	var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
	// update the URL to the product info
	var productPostUrl = '/o2o/shopadmin/modifyproduct';

	// Eidt and Add product use the same web page
	// use isEdit to decide Edit or Add
	var isEdit = false;
	if (productId) {
		// the operation is Edit if there is productId
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory();
		productPostUrl = '/o2o/shopadmin/addproduct';
	}

	// get the info of the product that will be edited, and assign the values
	function getInfo(id) {
		$
				.getJSON(
						infoUrl,
						function(data) {
							if (data.success) {
								// get product info from the return JSON, and assign values to the form
								var product = data.product;
								$('#product-name').val(product.productName);
								$('#product-desc').val(product.productDesc);
								$('#priority').val(product.priority);
								$('#normal-price').val(product.normalPrice);
								$('#promotion-price').val(product.promotionPrice);
								// get the category of original product and all product category list of the current shop
								var optionHtml = '';
								var optionArr = data.productCategoryList;
								var optionSelected = product.productCategory.productCategoryId;
								// generate the frontend product category list, and select all the product category before the edit
								optionArr
										.map(function(item, index) {
											var isSelect = optionSelected === item.productCategoryId ? 'selected'
													: '';
											optionHtml += '<option data-value="'
													+ item.productCategoryId
													+ '"'
													+ isSelect
													+ '>'
													+ item.productCategoryName
													+ '</option>';
										});
								$('#category').html(optionHtml);
							}
						});
	}

	// get all the product category list of the shop, and add new product 
	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var productCategoryList = data.data;
				var optionHtml = '';
				productCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}

	// for the widge to upload images, if the last image changes
	// and total uploaded images is less than 6, then generate a new widget for uploading use
	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	// click the submit
	$('#submit').click(
			function() {
				// create json object, and get the info from the dataform for all the attributes
				var product = {};
				product.productName = $('#product-name').val();
				product.productDesc = $('#product-desc').val();
				product.priority = $('#priority').val();
				product.normalPrice = $('#normal-price').val();
				product.promotionPrice = $('#promotion-price').val();
				// get the product category of the selected product
				product.productCategory = {
					productCategoryId : $('#category').find('option').not(
							function() {
								return !this.selected;
							}).data('value')
				};
				product.productId = productId;

				// get the file stream of the thumbnail image
				var thumbnail = $('#small-img')[0].files[0];
				// create formdata to accept data and pass to backend
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				// get the file stream of each detail image
				$('.detail-img').map(
						function(index, item) {
							// check if any file has been selected
							if ($('.detail-img')[index].files.length > 0) {
								// assign ith file stream to the "productImg" in the form
								formData.append('productImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});
				// convert product json object to string stream and save to "productStr" in the form
				formData.append('productStr', JSON.stringify(product));
				// get verification code from the form
				var verifyCodeActual = $('#j_captcha').val();
				if (!verifyCodeActual) {
					$.toast('Please input verfication code！');
					return;
				}
				formData.append("verifyCodeActual", verifyCodeActual);
				// send data to backend and process
				$.ajax({
					url : productPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('Successful submission！');
							$('#captcha_img').click();
						} else {
							$.toast('Unsuccessful submission！');
							$('#captcha_img').click();
						}
					}
				});
			});

});