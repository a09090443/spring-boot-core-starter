//# sourceURL=FileUtil.js

/**
 * 檔案處理工具
 * 
 * @author adam.yeh
 */
var FileUtil = function () {
	
	var file;
	
	/**
	 * 清空已讀取的檔案
	 */
	var reset = function () {
		file = {};
	}
	
	/**
	 * 將網頁檔案轉換成FormData型態檔案
	 * 
	 * k formData的key
	 * v 檔案
	 */
	var readAsFile = function (k, v) {
		file = new FormData();
		file.append(k, v);
	}
	
	/**
	 * 取得FormData型態的檔案資料
	 */
	var getFormFile = function () {
		return file;
	}
	
	/**
	 * 上傳
	 * 
	 * formFile JS FormData物件
	 */
	var upload = function (uri, formFile, callback) {
		file = {};
		var options = {
			data : formFile,
			cache : false,
			contentType : false,
			processData : false
		};
		SendUtil.post(uri, '', callback, options, true);
	}
	
	return {
		reset : reset,
		getFormFile : getFormFile,
		upload : upload,
		readAsFile : readAsFile
	}
}();