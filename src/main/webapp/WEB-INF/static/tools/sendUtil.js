//# sourceURL=SendUtil.js

/**
 * 連線請求管理 工具
 * 
 * @author adam.yeh
 */
var SendUtil = function () {
	
	/**
	 * POST
	 * 
	 * action 	URI
	 * data 	Form data object
	 */
	var post = function (action, data, callback, options, hasLoading) {
		if (hasLoading) {
			DialogUtil.showLoading();
		}
		
		PurifyUtil.obj(data);
		
		var o = {
			method: "post",
			url: contextPath + action,
			contentType: "application/json;charset=utf-8;",
			data: ObjectUtil.stringify(data),
			async: true,
			success: function (resData) {
				resData.isLogicError = false;
				
				if (resData.validateFieldErrors) {
					showFieldErrors(resData.validateFieldErrors);
					resData.isLogicError = true;
				} else if (resData.validateLogicError) {
					alert(resData.validateLogicError);
					resData.isLogicError = true;
				} else if (resData.toString().indexOf('loginForm') > 0) {
					href('/login');
					return;
				}
				
				callback(resData);
			},
			error: function (xhr) {
				if (xhr.responseText) {
					xhr = ObjectUtil.parse(xhr.responseText);
					alert(xhr.message);
					href('/dashboard');
				}
			},
			complete: function () {
				DialogUtil.hideLoading();
			}
		};
		
		$.ajax($.extend(o, options));
	}
	
	/**
	 * Form Post
	 */
	var formPost = function (uri, data) {
		var postForm = 'form#postForm';
		
		$('div#breadcrumb').append("<form id='postForm' class='hidden' action='' method='post'></form>");
		$(postForm)
			.attr('action', contextPath + uri)
			.append("<input name='formPostData' value='" + ObjectUtil.stringify(data) + "' />")
			.submit();
	}
	
	/**
	 * GET
	 * 
	 * action	URI
	 * data		String or Array
	 */
	var get = function (action, data, callback, options, hasLoading) {
		if (hasLoading) {
			DialogUtil.showLoading();
		}
		
		var path = contextPath + action + renderRestfulData(data);
		var o = {
			method: "get",
			url: PurifyUtil.uri(path),
			contentType: "application/json;charset=utf-8;",
			async: true,
			success: function (resData) {
				resData.isLogicError = false;
				
				if (resData.validateFieldErrors) {
					showFieldErrors(resData.validateFieldErrors);
					resData.isLogicError = true;
				} else if (resData.validateLogicError) {
					alert(resData.validateLogicError);
					resData.isLogicError = true;
				} else if (resData.toString().indexOf('loginForm') > 0) {
					href('/login');
					return;
				}
				
				callback(resData);
			},
			error: function (xhr) {
				if (xhr.responseText) {
					xhr = ObjectUtil.parse(xhr.responseText);
					alert(xhr.message);
					href('/dashboard');
				}
			},
			complete: function () {
				DialogUtil.hideLoading();
			}
		};
		
		$.ajax($.extend(o, options));
	}
	
	/**
	 * location.href
	 * 
	 * action	URI
	 * data		String/Number or Array
	 */
	var href = function (action, data, removeLoading) {
		if (!removeLoading) {
			DialogUtil.showLoading();
			data = renderRestfulData(data);
		}	

		location.href = PurifyUtil.uri(contextPath + action + data);
	}
	
	/**
	 * location.reload
	 * 
	 * action	URI
	 * data		String or Array
	 */
	var reload = function () {
		DialogUtil.close();
		DialogUtil.showLoading();
		location.reload();
	}
	
	function renderRestfulData (data) {
		var mark = '/';

		if (typeof data === 'string') {
			data = mark + data;
		} else if (isNumberOrBoolean(data)) {
			data = mark + data.toString();
		} else if (data instanceof Array) {
			data = arrayToRestfulPath(data, mark);
		} else {
			data = '';
		}
		
		return PurifyUtil.dom(data);
	}
	
	function showFieldErrors (errors) {
		var displayMessages = "";
		
		errors.forEach(function (item, index, array) {
			displayMessages += "欄位名稱:" + item.fieldName;
			displayMessages += ", 錯誤訊息:" + item.errorMessage + "\n";
		});
		
		alert(displayMessages);
	}

	function emptyObject (obj) {
		var copy = $.extend(true, {}, obj);// 深度複製 ( 防止異動原資料 )
		
		if (copy) {
			$.each(ObjectUtil.keys(copy), function (i, key) {
				copy[key] = '';
			});
		}
		
		return copy;
	}
	
	function arrayToRestfulPath (data, mark) {
		var temp = '';
		
		$.each(data, function (i, value) {
			if (!isNull(value)) {
				temp += mark + value;
			}
		});
		
		return temp;
	}
	
	function isNull (data) {
		return (typeof data == 'undefined' || data == null);
	}

	function isNumberOrBoolean (data) {
		return (typeof data === 'number') || (typeof data === 'boolean')
	}
	
	return {
		get : get,
		href : href,
		post : post,
		reload : reload,
		formPost : formPost
	}
}();