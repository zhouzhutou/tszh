<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/4 0004
  Time: 下午 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(document).ready(function () {
        $("#loginForm").bootstrapValidator({
            message: '填入值不正确',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:{
                email: {
                    validators: {
                        notEmpty: {
                            message: '邮箱不能为空'
                        }
                    }
                },
                password:{
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        }
                    }
                }
            }
        });

        $("#loginBtn").click(function () {
            var bootstrapValidator=$("#loginForm").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid())
            {
                var postData=JSON.stringify({email:$("#inputEmail").val(), password:$("#inputPassword").val()});
                //console.log(postData);
                $.ajax({
                    url:"/tszh/doLogin",
                    method:"POST",
                    dataType:"json",
                    contentType: 'application/json',
                    data:postData,
                    async:true,
                    success:function (result) {
                        //var data=JSON.parse(result)
                        //console.log(data);
                        var res=result;
                        if(res.code==2000){
                            if(res.data.roleName==="user") {
                                window.location.replace("/tszh/home");
                            } else if(res.data.roleName==="admin") {
                                window.location.replace("/tszh/admin");
                            }
                        }
                    },
                    error:function (e) {
                        //console.log(e.responseText);
                        var error=eval("("+e.responseText+")");
                        toastr.error(error.message);
                    }
                });
            }
        })
    })
</script>
<%--<div style="margin-bottom: 300px;">--%>
<div class="page-header"
     style="margin-bottom:15px;margin-left:10px;font-size: 32px;font-family: 微软雅黑">
    <h1>图书置换系统</h1>
</div>
<form id="loginForm" class="form-horizontal">
    <div class="form-group">
        <label for="inputEmail" class="col-xs-1 col-sm-1 col-md-1 control-label" id="emailLabel">邮箱</label>
        <div class="col-xs-3 col-sm-3 col-md-3">
            <input type="text" name="email" value="${email}" class="form-control" id="inputEmail" placeholder="邮箱"/>
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword" class="col-xs-1 col-sm-1 col-md-1 control-label"  id="passwordLabel">密码</label>
        <div class="col-xs-3 col-sm-3 col-md-3">
            <input type="password" name="password" value="${password}" class="form-control" id="inputPassword" placeholder="密码"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-1 col-sm-offset-1 col-md-offset-1 col-xs-3 col-sm-3 col-md-3">
            <div class="checkbox" style="border: none">
                <label>
                    <input type="checkbox"/> 记住我
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-1 col-sm-offset-1 col-md-offset-1 col-xs-5 col-sm-5 col-md-5">
            <button type="button" class="btn btn-default" style="margin-right: 8%" id="loginBtn">登录</button>
            <a href="${pageContext.request.contextPath}/register" style="margin-right: 8%">注册</a>
            <a href="${pageContext.request.contextPath}/forgetPassword">忘记密码</a>
        </div>
    </div>
</form>
<%--</div>--%>

