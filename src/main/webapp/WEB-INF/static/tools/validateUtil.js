//# sourceURL=ValidateUtil.js

/**
 * 資料驗證共用工具
 * 
 * @author AndrewLee
 */
var ValidateUtil = function () {
	
	/**
	 * 前端畫面欄位/按鈕權限控制
	 */
	var authView = function (info) {
		$('form#headForm').find('select').attr('disabled', true);
		SendUtil.post('/commonForm/getEditableCols', info, function (response) {
			switch (info.formStatus) {
				case 'PROPOSING':
					$('li#tabLog').show();
					$('li#tabProgram').show();
					$('li#tabFileList').show();
					
					break;
					
				default:
					if (info.formStatus) {
						$('li#tabLog').show();
						$('li#tabProgram').show();
						$('li#tabCheckLog').show();
						$('li#tabFileList').show();
						$('li#tabLinkList').show();
					}
					
					break;
			}

			HtmlUtil.enableFunctionButtons(null);
			HtmlUtil.enableFunctionButtons(response.buttons, true);
			HtmlUtil.disableElements(response.disabledColumns, true);
			HtmlUtil.disableElements(response.enabledColumns, false);

			// 檢查當前頁簽的按鈕欄上沒有按鈕的話就把整欄隱藏
			$('div[id$="Buttons"]').toggle(
					$('div[id$="Buttons"] button').is(":visible"));
		}, ajaxSetting);
	}
	
	/**
	 * 傳入Type,檢查登入者是否為該角色
	 *
	 * 傳入參數:
	 * 	1.經辦, 2.副科, 3.科長, 4.檢查是否為自己處理的單子, 5.副理</br>
	 *  6.協理, 7.檢查是否為自己開的單
	 *
	 */
	var checkLoginRole = function () {
		
		var userId = loginUserInfo.userId;
		var groupId = loginUserInfo.groupId;
		var level = loginUserInfo.authorLevel;
		
		/**
		 *  經辦
		 */
		var isPIC = function () {
			return level <= 1;
		}

		/**
		 *  科長
		 */
		var isChief = function () {
			return (groupId.indexOf('VSC') == -1 && groupId.indexOf('SC') != -1);
		}

		/**
		 *  副科長
		 */
		var isVice = function () {
			return groupId.indexOf('VSC') != -1;
		}
		
		return {
			isPIC : isPIC,
			isVice : isVice,
			isChief : isChief
		}
	};
	

	/**
	 * 傳入URL以及FormVo物件,驗證欄位資料
	 * 
	 */
	var formFields = function(url,info) {
		let verifyResult = false;
		
		SendUtil.post(url, info, function (resData) {
			if(!resData.isSuccess) {
				alert(resData.errorMsg);
			}
			
			verifyResult = resData.isSuccess;
		},{async: false});

		return verifyResult;
	}
	
	return {
		authView : authView,
		formFields : formFields,
		checkLoginRole : checkLoginRole
	}
}();