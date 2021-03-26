<!doctype html>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/global.jsp" %>

    <title><s:message code="system.name" text='電子發票作業平台' /></title>
    <script>
        var dataTable;
        var ajaxOption = {async : false};

        $(function () {
            initElements();
            initTable();
            search();
            initDatePicker();
        });

        function initTable() {
            DateUtil.datePicker(options);
            var options = {
                data: [],
                columnDefs: [
                    {orderable: false, targets: []},
                    {"width": "8%", targets: [0], title: '<s:message code="invoice.detail.result.type" text='發票類型' />', data: 'type', className: 'dt-center',
                        render: function (data) {
                            return data;
                        }},
                    {"width": "10%", targets: [1], title: '<s:message code="invoice.detail.result.number" text='發票號碼' />', data: 'number', className: 'dt-center',
                        render: function (data) {
                            return data;
                        }},
                    {"width": "10%", targets: [2], title: '<s:message code="invoice.detail.result.name" text='系統名稱' />', data: 'systemName', className: 'dt-center',
                        render: function (data) {
                            return data;
                        }},
                    {"width": "9%", targets: [3], title: '<s:message code="invoice.detail.verify.file.status" text='檔案檢核結果' />', data: 'verifyFileStatus', className: 'dt-left',
                        render: function (data) {
                            return data;
                        }},
                    {"width": "9%", targets: [4], title: '<s:message code="invoice.detail.upload.verification.status" text='上傳檢核結果' />', data: 'uploadVerificationStatus', className: 'dt-left',
                        render: function (data) {
                            return data;
                        }},
                    {"width": "9%", targets: [5], title: '<s:message code="invoice.detail.upload.status" text='是否已上傳' />', data: 'isUploaded', className: 'dt-left',
                        render: function (data) {
                            return data;
                        }},
                    {"width": "15%", targets: [6], title: '<s:message code="invoice.detail.verify.file.message" text='檔案檢核訊息' />', data: 'verifyFileMessage', className: 'dt-left',
                        render: function (data) {
                            return data;
                        }},
                    {"width": "15%", targets: [7], title: '<s:message code="invoice.detail.upload.verification.message" text='檔案上傳檢核訊息' />', data: 'uploadVerificationMessage', className: 'dt-left',
                        render: function (data) {
                            return data;
                        }},
                    {"width": "15%", targets: [8], title: '<s:message code="invoice.detail.result.executeTime" text='執行時間' />', data: 'executeTime', className: 'dt-left',
                        render: function (data) {
                            return data;
                        }}
                ]
            };
            dataTable = TableUtil.init('#dataTable', options);
        }
        function initElements(){
            SendUtil.get('/invoice/initInvoiceDetailPageElements', '', function (data) {

                $.each(data.invoiceTypeMap, function(key, value) {
                    $(new Option(key, value)).appendTo('#invoiceType');
                });

                $.each(data.verifyFileStatusMap, function(key, value) {
                    $(new Option(value, key)).appendTo('#verifyFileStatus');
                });

                $.each(data.uploadVerificationStatusMap, function(key, value) {
                    $(new Option(value, key)).appendTo('#uploadVerificationStatus');
                });

                $.each(data.uploadStatusMap, function(key, value) {
                    $(new Option(value, key)).appendTo('#isUploaded');
                });

                $.each(data.systemNameMap, function(key, value) {
                    $(new Option(value, value)).appendTo('#systemName');
                });
            },ajaxOption,true);
        }

        function initDatePicker () {
            var dateOption = {
                minDate: '',
                maxDate: new Date()
            };
            DateUtil.datePickerById('#startDate' ,dateOption);
            DateUtil.datePickerById('#endDate' ,dateOption);
        }

        function search () {
            if($("#startDate").val()!='' && $("#endDate").val()!=''){
                if(new Date($("#startDate").val()) > new Date($("#endDate").val())){
                    alert('<s:message code="global.date.condition.error.msg" text="開始時間不可大於結束時間!" />');
                    return false;
                }
            }

            SendUtil.post('/invoice/findInvoices', form2object('search'), function (data) {
                TableUtil.reDraw(dataTable, data);
            },ajaxOption,true);
        }
    </script>
</head>
<%@ include file="/WEB-INF/jsp/layout/header.jsp" %>
<h1><s:message code="invoice.detail.page.title.name" text='電子發票上傳結果'/></h1>
<fieldset class="search">
    <legend><s:message code="schedule.find.legend.name" text='排程名稱'/></legend>

    <button class="small fieldControl searchPanel">
        <i class="icon-collapse"></i>
    </button>

    <form id='search'>
        <table class="grid_query">
            <tr>
                <th>
                    <s:message code="invoice.detail.result.number" text='發票號碼'/>
                </th>
                <td>
                    <input type="text" name='invoiceNumber' style="width: 20rem;"/>
                </td>
                <th>
                    <s:message code="invoice.detail.result.type" text='發票類型'/>
                </th>
                <td>
                    <select name="invoiceType" id="invoiceType">
                        <option value=""><s:message code="global.select.please.choose" text='請選擇'/></option>
                    </select>
                </td>
                <th>
                    <s:message code="invoice.detail.verify.file.status" text='檔案檢核結果'/>
                </th>
                <td>
                    <select name="verifyFileStatus" id="verifyFileStatus">
                        <option value=""><s:message code="global.select.please.choose" text='請選擇'/></option>
                    </select>
                </td>
                <th>
                    <s:message code="invoice.detail.upload.verification.status" text='上傳檢核結果'/>
                </th>
                <td>
                    <select name="uploadVerificationStatus" id="uploadVerificationStatus">
                        <option value=""><s:message code="global.select.please.choose" text='請選擇'/></option>
                    </select>
                </td>
                <th>
                    <s:message code="invoice.detail.upload.status" text='是否已上傳'/>
                </th>
                <td>
                    <select name="isUploaded" id="isUploaded">
                        <option value=""><s:message code="global.select.please.choose" text='請選擇'/></option>
                    </select>
                </td>
                <th>
                    <s:message code="invoice.detail.result.name" text='系統名稱'/>
                </th>
                <td>
                    <select name="systemName" id="systemName">
                        <option value=""><s:message code="global.select.please.choose" text='請選擇'/></option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    <s:message code="invoice.detail.result.range.date" text='日期區間'/>
                </th>
                <td colspan="4">
                    <input type="text" id='startDate' name='startDate' style="width: 20rem;"/>
                    <input type="text" id='endDate' name='endDate' style="width: 20rem;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <button type='button' onclick='search()'>
                        <i class="icon-search"></i>
                        <s:message code="button.search" text='查詢'/>
                    </button>
                </td>
            </tr>
        </table>
    </form>
</fieldset>

<fieldset>
    <legend><s:message code="schedule.find.legend.result" text='查詢結果'/></legend>
    <table id="dataTable" class="display collapse cell-border">
        <thead></thead>
        <tbody></tbody>
    </table>
</fieldset>
<%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>
