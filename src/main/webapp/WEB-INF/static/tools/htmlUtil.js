//# sourceURL=HtmlUtil.js

/**
 * 處理需要依照登入者資訊而變動的前端元件內容值
 * 
 * @author adam.yeh
 */
var HtmlUtil = function () {

	var oriWidth, newWidth, breadcrumbs, latest, isPop;
	
	/**
	 * 依據傳入陣列啟用/取消元素編輯狀態
	 */
	var disableElements = function (ary, isDisabled) {
		$.each(ary, function (i, name) {
			$(name).prop('disabled', isDisabled);
			if ($(name).next('button').length > 0) {
				$($(name).next('button')).prop('disabled', isDisabled);
			}
		});
	}
	
	/**
	 * 依據傳入陣列啟用/取消功能按鈕編輯狀態
	 * 如果不設定陣列參數的話預設會隱藏所有按鈕ID以Button結尾的元件
	 */
	var enableFunctionButtons = function (ary, isEnabled) {
		if (!ary || ary.length == 0) {
			$('button[id$="Button"]').toggle(false);
			return;
		}
		
		$.each(ary, function (i, name) {
			$(name).toggle(isEnabled);
		});
	}
	
	/**
	 * 存入麵包屑
	 */
	var putBreadcrumb = function (href, name) {
		breadcrumbs = SessionUtil.get('breadcrumb') ? 
				ObjectUtil.parse(SessionUtil.get('breadcrumb')) : [];
		
		var breadcrumb = {
			href: href,
			name: name
		};
		
		breadcrumbs.push(breadcrumb);
		SessionUtil.set('breadcrumb', breadcrumbs);
	}
	
	/**
	 * 取出最後一員麵包屑
	 */
	var popBreadcrumb = function () {
		breadcrumbs = SessionUtil.get('breadcrumb') ? 
				ObjectUtil.parse(SessionUtil.get('breadcrumb')) : [];
		latest = breadcrumbs[breadcrumbs.length-1];
		isPop = isPopBreadcrumb(latest);
		
		if (isPop) {
			latst = breadcrumbs.pop();
			SessionUtil.set('breadcrumb', breadcrumbs);
		}
		
		return latest;
	}
	
	/**
	 * 沖刷出所有麵包屑
	 */
	var flushBreadcrumbs = function () {
		breadcrumbs = SessionUtil.get('breadcrumb') ? 
				ObjectUtil.parse(SessionUtil.get('breadcrumb')) : [];
		SessionUtil.set('breadcrumb', []);
		
		return breadcrumbs;
	}
	
	/**
	 * 取得所有麵包屑清單
	 */
	var getBreadcrumbs = function () {
		breadcrumbs = SessionUtil.get('breadcrumb') ? 
				ObjectUtil.parse(SessionUtil.get('breadcrumb')) : [];
		
		return breadcrumbs;
	}
	
	/**
	 * 動態加載下拉選單內容
	 * 
	 * selector JQuery selector statement
	 * contents Data List
	 */
	var initSelect = function (selector, contents) {
		$.each($(selector).find('option'), function () {
			if ($(this).val()) {
				$(this).remove();
			}
		});
		
		$.each(contents, function () {
			var option = '<option value=' + this.value + '>' + this.wording + '</options>';
			$(selector).append(option);
		});
	}

	/**
	 * 鎖定Enter 空格以及ESC鍵
	 */
	var lockSubmitKey = function(lock) {
		if(lock) {
			$(document).keydown(function(e) {
				if (e.keyCode == 32 || e.keyCode == 13 || e.keyCode == 27) return false;
			});
		} else {
			$(document).unbind("keydown");
		}
	}
	
	/**
	 * 初始化tab頁簽
	 */
	var initTabs = function () {
		$('.nav-tabs > li > a').unbind('click').click(function (event) {
			//stop browser to take action for clicked anchor
			event.preventDefault();
						
			//get displaying tab content jQuery selector
			var activeTabSelector = $('.nav-tabs > li.active > a').attr('href');					
						
			//find actived navigation and remove 'active' css
			var activedNav = $('.nav-tabs > li.active');
			activedNav.removeClass('active');
						
			//add 'active' css into clicked navigation
			$(this).parents('li').addClass('active');
						
			//hide displaying tab content
			$(activeTabSelector).removeClass('active');
			$(activeTabSelector).addClass('hide');
						
			//show target tab content
			var targetTabSelector = $(this).attr('href');
			$(targetTabSelector).removeClass('hide');
			$(targetTabSelector).addClass('active');
		});
	}

	var saveOriWidth = function (width) {
		if (!$('main').hasClass('wide')) {
			oriWidth = width;
		}
	}
	
	var saveNewWidth = function (width) {
		if (!newWidth && $('main').hasClass('wide')) {
			newWidth = width;
		}
	}
	
	var adjustTableWidth = function () {
		var width = !$('main').hasClass('wide') ? oriWidth : newWidth;
		$('.dataTable').attr('style', width);
		$('.dataTables_wrapper').attr('style', width);
	}
	
	var resetSelector = function(id) {
		$(id).empty().append("<option value=''>請選擇</option>");
	}
	
	function isFuntionButtons (element) {
		return $('div#formInfoButtons').has(element).length > 0;
	}
	
	function isPopBreadcrumb (breadcrumb) {
		var isPop = true;
		
		// variable functions is define at menu.jsp
		$.each(functions, function () {
			if (breadcrumb.name == $(this).text()) {
				isPop = false;
				return false;// break
			}
		});
		
		return isPop;
	}
	
	return {
		initSelect : initSelect,
		initTabs : initTabs,
		adjustTableWidth : adjustTableWidth,
		adjustTableWidth : adjustTableWidth,
		saveOriWidth : saveOriWidth,
		saveNewWidth : saveNewWidth,
		resetSelector : resetSelector,
		putBreadcrumb : putBreadcrumb,
		popBreadcrumb : popBreadcrumb,
		getBreadcrumbs : getBreadcrumbs,
		flushBreadcrumbs : flushBreadcrumbs,
		lockSubmitKey : lockSubmitKey,
		disableElements : disableElements,
		enableFunctionButtons : enableFunctionButtons
	}
}();