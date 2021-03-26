<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%--    <%@ include file="/WEB-INF/jsp/common/global.jsp"%>--%>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/thirdParty/bootstrap/3.3.5/css/bootstrap.css?v=${now}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/thirdParty/bootstrap/3.3.5/css/bootstrap-theme.css?v=${now}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/thirdParty/fontawesome/4.1.0/font-awesome.min.css?v=${now}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/thirdParty/icomoon/style.css?v=${now}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/login.css?v=${now}" />
    <meta name=”robots” content=”NOINDEX, NOFOLLOW”>
    <title>電子發票管理系統</title>
    <link rel="shortcut icon" href="${contextPath}/static/images/title.ico"/>
</head>
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>

<body bgcolor="#FFFFFF" text="#000000" oncontextmenu="return false" ondragstart="return false" onselectstart="return true">


<form name='login' action="${contextPath}/login" method='POST'>
    <div class="content">
        <c:if test="${not empty error}"><div style="color:red; font-weight: bold; margin: 30px 0px;">${error}</div></c:if>

        <div class="login_box">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="r_goin">
                        <div class="form-group">
                            <label class="control-label"><s:message code="login.account.field" text="帳  號" /><input type="hidden" name="act"></label>
                            <input type="text" class="form-control" placeholder="請輸入您的登入帳號" name="username" size="20" maxlength="20" value="">
                        </div>
                        <div class="form-group">
                            <label class="control-label"><s:message code="login.password.field" text="密  碼" /></label>
                            <input type="password" class="form-control" placeholder="請輸入您的密碼" name="password" size="20" maxlength="20" value="">
                        </div>
                        <button type="submit" name="login" class="btn btn-success but01"><s:message code="login.enter.button" text="登入" /></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
