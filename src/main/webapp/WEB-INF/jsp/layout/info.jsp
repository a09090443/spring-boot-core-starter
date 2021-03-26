<%@ page contentType="text/html; charset=UTF-8"%>

<script language="javascript">
	<%--var loginUserInfo = ObjectUtil.parse('${sysUserVO}');--%>

	$(function() {
		$('button').click(function () {
			var logout = $(this).find('i').hasClass('icon-logout');
			if (logout == true) {
				parent.location.href = '${contextPath}/logout';
			}
		});

		$('.notice').click(function () {
			window.open(
					'ISGFR0199.doc', 'msgu', 'width=600,height=350,scrollbars=auto');
		});

		$('.notice').click(function() {
			window.open(
					'ISGFR0199.doc', 'msgu', 'width=600,height=350,scrollbars=auto');
		});
	})
</script>

<title><s:message code="system.name" text='電子發票作業平台' /></title>
<div class="frame_header">
	<header>
		<a href='${contextPath}/dashboard'><img src="${contextPath}/static/images/logo_main.png" class="logo" /></a>
		<button>
			<i class="icon-logout"></i> <s:message code="system.logout" text='登出' />
		</button>
		<section class="info">
			<span><s:message code="header.login.time" arguments="${sysUserVO.loginTime}" text='登入時間 : ' /></span> <span><s:message code="header.login.user" arguments="${sysUserVO.userId}" argumentSeparator=";" text='登入者 : ' /></span>
		</section>
	</header>
</div>
