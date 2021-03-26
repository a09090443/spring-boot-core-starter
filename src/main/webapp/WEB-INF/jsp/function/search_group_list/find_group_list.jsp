<!doctype html>
<html>
<head>
<title>電子發票管理系統</title>
<%@ include file="/WEB-INF/jsp/common/global.jsp"%>

<script>//# sourceURL=find_group_list.js
$(function () {
	SystemViewUtil.initSearchPanel();

	let options = {
		data: fetchFakeData(),
		columnDefs: [
			{orderable: false, targets: []},
			{targets: [0], title: '群組代碼', data: 'groupId', className: '', width: "10%"},
			{targets: [1], title: '群組名稱', data: 'groupName', className: '', width: "80%"},
			{
				targets: [2],
				data: null,
				title: "功能",
				className: '',
				width: "10%",
				defaultContent: '<button><i class="icon-edit"></i>編輯</button>'
			},
		]
	};
	DataTableUtil.create('dataTable', options);
});

function fetchFakeData () {
	let data = [];
	let i, sign;

	for (i = 0; i < 100; i++) {
		sign = i + 1;
		let obj = {};
		obj.groupId = sign;
		obj.groupName = '群組名稱' + sign;
		data.push(obj);
	}

	return data;
}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>
	<%@ include file="/WEB-INF/jsp/layout/menu.jsp"%>

	<main>
		<%@ include file="/WEB-INF/jsp/layout/breadcrumb.jsp"%>

		<h1>查詢群組權限</h1>

		<fieldset class="search">
			<legend>查詢條件</legend>

			<button id='toggleSearchPanel' class="small fieldControl">
				<i class="icon-collapse"></i>
			</button>

			<table class="grid_query">
				<tr>
					<th>&nbsp;&nbsp;&nbsp;&nbsp;群組名稱</th>
					<td>
						<input type="text" style="width: 20rem;" />
						&nbsp;&nbsp;
						<button><i class="icon-search"></i>查詢</button>
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>

		<fieldset>
			<legend>查詢結果</legend>
			<table id="dataTable" class="display collapse">
				<thead></thead>
				<tbody></tbody>
			</table>
		</fieldset>
	</main>
</body>
</html>
