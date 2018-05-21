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
        })

        $("#loginBtn").click(function () {
            var bootstrapValidator=$("#loginForm").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid())
            {
                var postData=JSON.stringify({email:$("#inputEmail").val(), password:$("#inputPassword").val()});
                //console.log(postData);
                $.ajax({
                    url:"/tszh/doLogin" ,
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
                            window.location.replace("/tszh/home");
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

<div class="page-header" style="margin-bottom:4%; padding-left:9%; font-family:楷体; font-size: 32px">
    <h1>图书置换系统</h1>
</div>
<form id="loginForm" class="form-horizontal">
    <div class="form-group">
        <label for="inputEmail" class="col-sm-3 col-md-3 control-label" id="emailLabel">邮箱</label>
        <div class="col-sm-6 col-md-6">
            <input type="text" name="email" value="${email}" class="form-control" id="inputEmail" placeholder="邮箱">
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword" class="col-sm-3 col-md-3 control-label"  id="passwordLabel">密码</label>
        <div class="col-sm-6 col-md-6">
            <input type="password" name="password" value="${password}" class="form-control" id="inputPassword" placeholder="密码">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-3 col-md-offset-3 col-sm-6 col-md-6">
            <div class="checkbox" style="border: none">
                <label>
                    <input type="checkbox"> 记住我
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-md-offset-2 col-sm-6 col-md-6">
            <button type="button" class="btn btn-default" style="margin-right: 5%" id="loginBtn">登录</button>
            <a href="${pageContext.request.contextPath}/register" style="margin-right: 5%">注册</a>
            <a href="#">忘记密码</a>
        </div>
    </div>
</form>

