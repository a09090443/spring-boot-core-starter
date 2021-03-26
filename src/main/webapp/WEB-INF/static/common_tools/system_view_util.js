//# sourceURL=system_view_util.js

/**
 * 系統前端畫面的控制事件綁定 工具類別
 * 
 * @author adam.yeh
 */
var SystemViewUtil = function () {
	
	/**
	 * 綁定搜尋面板的顯示/隱藏事件
	 * 
	 * @author adam.yeh
	 */
	var initSearchPanel = function () {
		$("button#toggleSearchPanel").click(function () {
			let $header = $(this);
			$header.next().slideToggle(500, function () {
				let $icon = $header.find('i');
				$icon.toggleClass(function () {
					if ($icon.hasClass('icon-collapse')) {
						return 'icon-expand';
					}
				});
			});
		});
	}
	
	return {
		initSearchPanel: initSearchPanel
	}
	
}();