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
        $("#matchRegisterVerifyCodeForm").bootstrapValidator({
            message: '填入值不正确',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:{
                verifyCode: {
                    validators: {
                        notEmpty: {
                            message: '邮箱验证码不能为空'
                        }
                    }
                },
                email:{
                    validators:{
                        notEmpty:{
                            message: '邮箱不能为空'
                        },
                        emailAddress: {
                            message: '邮箱地址不合法'
                        }
                    }
                }
            }
        });


        $("#matchRegisterVerifyCodeBtn").click(function () {
            var bootstrapValidator=$("#matchRegisterVerifyCodeForm").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid())
            {
                var data={};
                $("#matchRegisterVerifyCodeForm").find($("input")).each(function (i,e) {
                    data[$(this).attr("name")]=$(this).val().trim();
                });
                var postData=JSON.stringify(data);
                $.ajax({
                    url:"/tszh/doMatchRegisterVerifyCode",
                    method:"POST",
                    dataType:"json",
                    contentType: 'application/json',
                    data:postData,
                    async:true,
                    success:function (result) {
                        var res=result;
                        if(res.code==2000){
                            toastr.options.onHidden=function (){
                                postData=JSON.stringify({email:res.data.email,password:res.data.password});
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
                            };
                            toastr.success(res.message);
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
    });
</script>
<%--<div style="margin-bottom: 300px;">--%>
<div class="page-header"
     style="margin-bottom:15px;margin-left:40px;font-size: 32px;font-family: 微软雅黑">
    <h1>注册</h1>
</div>
<form id="matchRegisterVerifyCodeForm" class="form-horizontal">
    <div class="form-group hidden">
        <label for="email" class="col-xs-2 col-sm-2 col-md-2 control-label" id="emailLabel"></label>
        <div class="col-xs-2 col-sm-2 col-md-2">
            <input type="text" name="email" class="form-control" id="email" value="${email}"/>
        </div>
    </div>
    <div class="form-group">
        <label for="registerVerifyCode" class="col-xs-2 col-sm-2 col-md-2 control-label" id="verifyCodeLabel">邮箱验证码</label>
        <div class="col-xs-2 col-sm-2 col-md-2">
            <input type="text" name="registerVerifyCode" class="form-control" id="registerVerifyCode" placeholder="邮箱验证码"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-sm-offset-2 col-md-offset-2 col-xs-5 col-sm-5 col-md-5">
            <button type="button" class="btn btn-success" id="matchRegisterVerifyCodeBtn" style="width: 75px">提交</button>
        </div>
    </div>
</form>
<%--</div>--%>

