<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/auth/login.css">
    <link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.7/dist/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator-0.4.5/dist/css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/toastr-2.1.4/build/toastr.min.css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator-0.4.5/dist/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/toastr-2.1.4/build/toastr.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/auth/login.js"></script>
    <title>${title}</title>
</head>
<body>
<div class="container" style="margin-top: 10%">

    <div class="row">
    <div class="col-sm-6 col-md-6"><img src="${pageContext.request.contextPath}/resources/icons/book_login.png" width="500px" height="500px"/>
    </div>
    <div class="col-sm-6 col-md-6" style="margin-top: 3%;padding-left: 14%">
        <jsp:include page="${contentPath}"/>
    </div>
    </div>
</div>
</body>
</html>
