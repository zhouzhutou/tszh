<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/auth/login.css">
    <link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/themes/default/easyui.css"/>
    <link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/themes/icon.css"/>
    <link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/demo/demo.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/auth/login.js"></script>
    <title>登录</title>
</head>
<body>
<div>
    <div class="left" style="width: 60%"><img src="${pageContext.request.contextPath}/resources/icons/book_login.png" width="500px" height="500px"/>
    </div>
    <div class="right" style="width: 40%">
        <div style="margin-bottom: 5%;font-family: 楷体;font-size: 32px">
            图书置换系统
        </div>
        <form id="ff" class="easyui-form" method="post" data-options="novalidate:true" action="${pageContext.request.contextPath}/login">
            <div style="margin-bottom:20px">
                <label>邮箱</label>
                <input class="easyui-textbox" type="text" name="email" value="${email}" missingMessage="请输入邮箱" style="width:25%" data-options="required:true"/>
                       <%--data-options="required:true,validType:'email'">--%>
            </div>
            <div style="margin-bottom:20px">
                <label>密码</label>
                <input class="easyui-textbox" type="password" name="password" value="${password}" missingMessage="请输入密码" style="width:25%"
                       data-options="required:true"/>
            </div>
            <p style="font-family: 微软雅黑;color: red">${errorMsg}</p>
        </form>
        <div style="text-align:left;padding:5px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">登录</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px;display:inline-block;margin-left: 8px" >注册</a>
        </div>
    </div>
</div>
</body>
</html>
