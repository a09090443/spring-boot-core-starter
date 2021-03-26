//# sourceURL=data_table_util.js

/**
 * 資料表格 工具類別
 * 
 * @author adam.yeh
 */
var DataTableUtil = function () {
	
	/**
	 * 初始化data table
	 * 
	 * @author adam.yeh
	 */
	var create = function (tableId, customOptions) {
		let options = {
			paging: true,
			pagingType: 'full_numbers',
			lengthChange: true,
			pageLength: 10,
			searching: false,
			ordering: true,
			order: [],
			info: true,
			autoWidth: false,
		};
		
		options = $.extend(options, customOptions);
		$('table#' + tableId).DataTable(options);
	}
	
	return {
		create: create
	}
	
}();