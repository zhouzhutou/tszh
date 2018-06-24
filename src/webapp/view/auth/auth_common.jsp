<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
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
    <script type="text/javascript">
        $(function () {
            toastr.options = {
                closeButton: false,
                debug: false,
                //progressBar: true,
                positionClass: "toast-top-center",
                onclick: null,
                //showDuration: "2000",
                hideDuration: "1000",
                timeOut: "2000",
                extendedTimeOut: "1000",
                showEasing: "swing",
                hideEasing: "linear",
                showMethod: "fadeIn",
                hideMethod: "fadeOut"
            };
        });
    </script>
    <title>${title}</title>
</head>
<style type="text/css">
    .container{
        width: 1800px !important;
        max-width: none !important;
    }
</style>
<body>
<div class="container" style="margin-top: 9%">

    <div class="row">
    <div class="col-xs-6 col-sm-6 col-md-6 text-center"><img src="${pageContext.request.contextPath}/resources/icons/book_login.png" width="500px" height="500px"/>
    </div>
    <div class="col-xs-6 col-sm-6 col-md-6" style="margin-top: 2.2%">
        <jsp:include page="${contentPath}"/>
    </div>
    </div>
</div>
</body>
</html>
