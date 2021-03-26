//# sourceURL=DialogUtil.js

/**
 * 對話框管理工具
 * 
 * @author adam.yeh
 */
var DialogUtil = function () {
	
	var dialog;
	var customLoad;
	var ajaxSetting = {async:false};
	var dialogTitleBar = '.ui-dialog-titlebar';
	var dialogTitleColor = '#007A55';
	
	var clickRow = function (table, callback) {
		if (!callback) {
			return;
		}
		
		$(table + ' tbody').unbind('click').on('click', 'tr', function () {
			callback(TableUtil.getRow($(table).DataTable(), this));
			close();
		});
	}
	
	var show = function (d, o) {
		dialog = d;
		
		var option = {
			width: '1000px',
			maxWidth: '1000px',
			resizable: false
		};
		
		open($.extend(option, o));
	}
	
	/**
	 * 開啟「選擇使用者」對話框
	 */
	var userSelect = function (element, request, callback) {
		dialog = 'div#userDialog';
		
		var table = dialog + ' table#table';
		SendUtil.post('/html/getGroupUserList', request, function (resData) {
			var options = {
				title: '選擇人員',
				name: '選擇人員',
				width: '100%',
				data: resData.userList,
				columnDefs: [
					{ 
			            targets: 0,
			            searchable: false,
			            orderable: false,
			            className: 'dt-center',
			            render: function(data, type, full, meta){
			                  data = '<input type="radio" name="id" value="' + full.userId + '">';
			                  data += '<input type="hidden" name="name" value="' + (full.name ? full.name : full.userName) + '">';
			               return data;
			            }
			        },
					{targets: [1], title: '姓名', data: '', className: 'dt-center',autoWidth: true, 
			        	render: function (data, type, full, meta) {
			        		return full.name ? full.name : full.userName;
			        	}}, 
					{targets: [2], title: 'Email', data: 'email', className: 'dt-center'},
				]
			};
			$("#groupName").text(resData.groupName);
			
			if ($.fn.dataTable.isDataTable(table)) {
				TableUtil.reDraw(
						$(table).DataTable(), options.data);
			} else {
				TableUtil.init(table, options);
			}
		}, null, true);
		
		clickRowToCheckUpRadioEvent('div#userDialog table#table tbody');

		var o = {
			width:'900px',
			maxHeight: '500px',
			maxWidth: '100px',
			resizable: true,
			close: function ()　{
				TableUtil.deleteTable(table);
				close(dialog);
			},
			buttons: {
				"確定": function (e) {
					HtmlUtil.lockSubmitKey(true);
					if (callback) {
						var radio = $(this).find("input[name=id]:checked");
						var checkValue = radio.val();
						var checkValueDisPlay = radio.siblings("input[name=name]").val();
						var selected = {
							"value":checkValue,
							"display":checkValueDisPlay
						}
						callback($(element).closest('tr'), selected);
						TableUtil.deleteTable(table);
						close(dialog);
					}
				}
			}
		};
		
		open(o);
	}
	
	/**
	 * 開啟送簽關卡對話視窗
	 */
	var signing = function (request, confirmButton, cancelButton) {
		if ('CLOSED' == request.formStatus) {
			alert('表單已結案。');
			return;
		}
		
		dialog = 'div#signingDialog';
		
		var levels, options;
		var table = dialog + ' table#signingList';
		var radio = '<input type="radio" name="detailId" value="{0}" {1} />';
		
		showLoading();

		SendUtil.post('/html/getSigningList', request, function (resData) {
			levels = resData;
		}, ajaxSetting);
		
		options = {
			data: levels,
			columnDefs: [
				{orderable: false, targets: []}, 
				{
					targets: [0], 
					data: 'detailId', 
					title: "功能", 
					className: 'dt-center', 
					width: '5%',
					render: function (data, type, row, meta) {
						return StringUtil.format(radio, data, meta.row == 0 ? 'checked' : '');
					}
				},
				{targets: [1], title: '順序', data: 'processOrder', className: 'dt-center', width: '5%'}, 
				{targets: [2], title: '流程關卡', data: 'groupName', className: 'dt-center', width: '20%'}, 
				{targets: [3], title: '', data: 'groupId', className: 'dt-center hidden'}
			]
		};

		if (isLastLevel(levels)) {
			if (!$.fn.DataTable.isDataTable('#signingList')) {
				TableUtil.init('#signingList', options);
			}
			
			var item = $(table).DataTable().row(':last').data();
			if (!item) {
				item = {};
				item.processOrder = 0;
			}
			
			confirmButton(item);
			
			return;
		}
		
		var o = {
			width: '700px',
			maxWidth: '1000px',
			resizable: false,
			open: function (event, ui) {
				if ($.fn.DataTable.isDataTable('#signingList')) {
					TableUtil.reDraw($(table).DataTable(), levels);
					return;
				}
				
				TableUtil.init('#signingList', options);
				hideLoading();
			},
			close: function ()　{
				TableUtil.deleteTable(table);
			},
			buttons: {
				"確定": function () {
					HtmlUtil.lockSubmitKey(true);
					if (confirmButton) {
						var checked = findRadioChecked(table);
						confirmButton(TableUtil.getRow($(table).DataTable(), checked));
					}
				},
				"取消": function () {
					if (cancelButton) {
						TableUtil.deleteTable(table);
						close();
						cancelButton();	
					}
				}
			}
		};
		
		open(o);
		hideLoading();
		clickRowToCheckUpRadioEvent('div#signingDialog table#signingList tbody');
	}
	
	/**
	 * 系統名稱清單對話框
	 * 
	 */
	var system = function () {
		dialog = 'div#systemDialog';
		
		var table = dialog + ' table#systemList';
		var o = {
			width: '1000px',
			maxWidth: '1000px',
			resizable: false,
			open: function (event, ui) {
				var formInfo = form2object('headForm');
				loginUserInfo.mboName = formInfo.formClass;
				
				SendUtil.post('/html/getSystemInfoList', loginUserInfo, function (resData) {
					if ($.fn.DataTable.isDataTable('#systemList')) {
						TableUtil.reDraw($(table).DataTable(), resData);
						return;
					}

					var options = {
						data: resData,
						columnDefs: [
							{orderable: false, targets: '_all'}, 
							{targets: [0], title: '部門代號', data: 'department', className: 'dt-center'}, 
							{targets: [1], title: '系統編號', data: 'systemId', className: 'dt-center'}, 
							{targets: [2], title: '系統描述', data: 'description', className: ''}
						]
					};
					TableUtil.init('#systemList', options);
				}, null, true);
			},
			close: function ()　{
				TableUtil.deleteTable(table);
			}
		};
		
		open(o);
		clickRow(table, function (row) {
			$('td#systemModel input#systemBrand').val(row.systemBrand);
			$('td#systemModel input#systemId').val(row.systemId);
			$('td#systemModel textarea#system').val(row.systemName);
			$('textarea#assetGroup').val(row.mark);
		});
	}
	
	/**
     * 新增工作要項對話框
     */
    var workingItem = function (dialogSelector, tableSelector, callbackFunc) {
        if (!callbackFunc) { return false; }
        
        dialog = dialogSelector;
        
        let table = tableSelector;
        let o = {
            width: '1000px',
            maxWidth: '1000px',
            resizable: false,
            open: function (event, ui) {
                let formInfo = form2object('headForm');
                loginUserInfo.mboName = formInfo.formClass;
                
                SendUtil.post('/html/getWorkingItemList', loginUserInfo, function (resData) {
                    if ($.fn.DataTable.isDataTable(table)) {
                        TableUtil.reDraw($(table).DataTable(), resData);
                        
                        return;
                    }

                    let options = {
                        data: resData,
                        columnDefs: [
                            {orderable: false, targets: '_all'}, 
                            {targets: [0], title: '工作要項名稱', data: 'workingItemName', className: 'dt-center'}, 
                            {targets: [1], title: '系統科組別', data: 'spGroup', className: 'dt-center'}, 
                            {targets: [2], title: '變更衝擊分析', data: 'isImpact', className: 'dt-center'}, 
                            {targets: [3], title: '變更覆核', data: 'isReview', className: 'dt-center'},
                            {targets: [4], title: '', data: 'workingItemId', className: 'hidden'}
                        ]
                    };
                    TableUtil.init(table, options);
                }, null, true);
            },
            close: function ()　{
				TableUtil.deleteTable(table);
            }
        };
        
        open(o);
        clickRow('div#workingItemDialog table#workingItemResultList', function (row) {
            let result = callbackFunc(row);
            if (result.result) {
				TableUtil.deleteTable(table);
                close();
            } else {
                alert(result.message);
            }
        });
    }
    
    /**
	 * 取得非擬案、非結案、非作廢之事件單
	 */
	var incFormSelect = function (callback) {
		dialog = 'div#incFormDialog';
		var table = dialog + ' table#incTable';
		var options = {
			width: '100%',
			data: null,
			columnDefs: [
				{ 
		            targets: 0,
		            searchable: false,
		            orderable: false,
		            className: 'dt-center',
		            render: function(data, type, full, meta){
		                  data = '<input type="radio" name="incFormId" value="'+full.formId+'">';
		               return data;
		            }
		        },
				{targets: [1], title: '表單編號', data: 'formId', className: 'dt-center'}, 
				{targets: [2], title: '摘要', data: 'summary', className: 'dt-center'},
				{targets: [3], title: '事件發生時間', data: 'createTime', className: 'dt-center', 
					render: function (data) {
						return DateUtil.toDateTime(data);
					}},
				{targets: [4], title: '開單人員', data: 'userCreated', className: 'dt-center'},
				{targets: [5], title: '表單狀態', data: 'formStatus', className: 'dt-center'},
				{targets: [6], title: '', data: 'userId', className: 'dt-center hidden'}
			]
		};
		clickRowToCheckUpRadioEvent('div#incFormDialog table#incTable tbody');

		var o = {
			width:'900px',
			maxHeight: '500px',
			maxWidth: '100px',
			resizable: true,
			open: function (event, ui) {
				// 防止datatable的header跑掉,開完dailog再初始化datatable
				if ($.fn.dataTable.isDataTable(table)) {
					TableUtil.reDraw($(table).DataTable(), options);
				}else{
					TableUtil.init(table, options);
				}
				hideLoading();
			},
			close: function ()　{
				TableUtil.deleteTable(table);
				close(dialog);
			},
			buttons: {
				"確定": function (e) {
					HtmlUtil.lockSubmitKey(true);
					if (callback) {
						var radio = $(this).find("input[name=incFormId]:checked");
						var checkValue = radio.val();
						var result = {
								"checkValue":checkValue
						}
						
						callback(result);
						TableUtil.deleteTable(table);
						close(dialog);
					}
				}
			}
		};
		
		open(o);
	}
	
	var showLoading = function (options) {
		if (!customLoad) {
			var o = {
				zIndex: 10,
				overlay: $('#custom-overlay')
	        }
			
			customLoad = $('body').loading($.extend(o, options));
		}

		customLoad.loading('start');
	}
	
	var hideLoading = function () {
		if (customLoad) {
			customLoad.loading('stop');
		}
	}
	
	var close = function () {
		if (dialog) {
			$(dialog).dialog().dialog('destroy');
		}
		HtmlUtil.lockSubmitKey(false);
	}
	
	// 在指定的表格內找到已選取的radio button
	var findRadioChecked = function (table) {
		var checked;
		var radioList = $(table).find('input[type="radio"]');
		
		$.each(radioList, function () {
			if ($(this).is(':checked')) {
				checked = this;
				return false;
			}
		});
		
		return checked;
	}
	
	// 針對指定表格的每行註冊點擊事件並將radio勾起來
	var clickRowToCheckUpRadioEvent = function (clickBody) {
		var isChecked;
		
		$(clickBody).unbind('click').on('click', 'tr', function () {
			isChecked = $(this).find('input[type="radio"]').is(':checked');
			
			if (isChecked) {
				return;
			}
			
			$(this).find('input[type="radio"]').prop('checked', !isChecked);
		});
	}
	
	function open (options) {
		var def = {
			modal : true,
		    position: {
		    	my: 'center' + ' ' + 'center',
		    	at: 'center' + ' ' + 'center'
		    }
		};
		$(dialog)
			.dialog($.extend(def, options))
			.prev(dialogTitleBar)
			.css('background-color', dialogTitleColor);// 客製化對話框標題的背景顏色
		$('.ui-dialog').css('z-index', 3);
		$('.ui-widget-overlay').css('z-index', 2);
	}
	
	function isLastLevel (levels) {
		return (levels && levels.length == 0);
	}
	
	return {
		show : show,
		close : close,
		signing : signing,
		system : system,
		clickRow : clickRow,
		showLoading : showLoading,
		hideLoading : hideLoading,
		userSelect : userSelect,
		workingItem : workingItem,
		incFormSelect : incFormSelect,
		findRadioChecked : findRadioChecked,
		clickRowToCheckUpRadioEvent : clickRowToCheckUpRadioEvent
	}
}();