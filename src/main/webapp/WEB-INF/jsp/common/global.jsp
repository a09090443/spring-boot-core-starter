<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.Date"%>

<c:set var='now' value='<%=new Date()%>' />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="/WEB-INF/jsp/common/library.jsp"%>

<script>
    var contextPath = "${pageContext.request.contextPath}";

    function back () {
        var breadcrumb = HtmlUtil.popBreadcrumb();

        if (breadcrumb.href) {
            SendUtil.href('/' + breadcrumb.href);
        }
    }

    function gotoFunction (uri) {
        DialogUtil.showLoading();
        SessionUtil.clear();

        var postForm = 'form#postForm';
        $('div#breadcrumb').append("<form id='postForm' class='hidden' action='' method='post'></form>");
        $(postForm)
            .attr('action', contextPath + uri)
            .submit();
    }
</script>
