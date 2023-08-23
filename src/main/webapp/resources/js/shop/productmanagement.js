$(function() {
	// get the url for the product list of current shop
	var listUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
	// URL - take the product off the shelf
	var statusUrl = '/o2o/shopadmin/modifyproduct';
	getList();
	/**
	 * get the product list of the current shop
	 * 
	 * @returns
	 */
	function getList() {
		// get product list from backend
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';
				// iterate each shop info, and concatenate them to include
				// product name, priority, on/off shelf (with productID), edit button (with productId)
				// preview (with productId)
				productList.map(function(item, index) {
					var textOp = "Off shelf"; //invalid
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						// if the status is 0, it means the product is not valid, operation becomes valid when click button to put product on shelf
						textOp = "On shelf"; //valid
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					// concatenate for each shop
					tempHtml += '' + '<div class="row row-product">'
							+ '<div class="col-33">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">Edit</a>'
							+ '<a href="#" class="status" data-id="'
							+ item.productId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">Preview</a>'
							+ '</div>'
							+ '</div>';
				});
				// assign the concatenated result to htlm controller in the frontend
				$('.product-wrap').html(tempHtml);
			}
		});
	}
	// associate the label 'a' in the class product-wrap with the click event
	$('.product-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							// if there is class edit, then just click to enter into the page for editing with productId
							window.location.href = '/o2o/shopadmin/productoperation?productId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('status')) {
							// if there is class status, then call the function to take the product on/off the shelf with productId
							changeItemStatus(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							// if there class preview, then just go to the frontend to display the info of the product
							window.location.href = '/o2o/frontend/productdetail?productId='
									+ e.currentTarget.dataset.id;
						}
					});
	function changeItemStatus(id, enableStatus) {
		// define product Json object and add productId and status(on/off shlef)
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('Are you sure?', function() {
			// off/on the product
			$.ajax({
				url : statusUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('Successful operation！');
						getList();
					} else {
						$.toast('Unsuccessful operation！');
					}
				}
			});
		});
	}
});