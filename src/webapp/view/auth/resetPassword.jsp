<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/19 0019
  Time: 下午 8:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(document).ready(function () {
        $("#resetPasswordForm").bootstrapValidator({
            message: '填入值不正确',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:{
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        identical: {
                            field: 'confirmPassword',
                            message: '密码与确认密码必须一致'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]{6,30}$/,
                            message: '密码必须是6-30位的字母、数字、下划线或点号的组合'
                        }
                    }
                },
                confirmPassword: {
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        identical: {
                            field: 'password',
                            message: '确认密码与密码必须一致'
                        }
                    }
                },
                verifyCode: {
                    validators: {
                        notEmpty: {
                            message: '邮箱验证码不能为空'
                        }
                    }
                }
            }
        });


        $("#resetPasswordBtn").click(function () {
            var bootstrapValidator=$("#resetPasswordForm").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid())
            {
                var data={};
                $("#resetPasswordForm").find($("input")).each(function (i,e) {
                    data[$(this).attr("name")]=$(this).val().trim();
                });
                var postData=JSON.stringify(data);
                $.ajax({
                    url:"/tszh/doResetPassword",
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
<div class="page-header" style="margin-bottom:15px;margin-left:40px;font-size: 32px;font-family: 微软雅黑">
    <h1>重置密码</h1>
</div>
<form id="resetPasswordForm" class="form-horizontal">
    <div class="form-group hidden">
        <label class="col-xs-2 col-sm-2 col-md-2 control-label">邮箱</label>
        <div class="col-xs-3 col-sm-3 col-md-3">
            <input type="text" class="form-control" name="email" id="email" value="${email}" />
        </div>
    </div>


    <div class="form-group">
        <label class="col-xs-2 col-sm-2 col-md-2 control-label">重置密码</label>
        <div class="col-xs-3 col-sm-3 col-md-3">
            <input type="password" class="form-control" name="password" id="password" placeholder="重置密码"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-2 col-sm-2 col-md-2 control-label">确认密码</label>
        <div class="col-xs-3 col-sm-3 col-md-3">
            <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="确认密码"/>
        </div>
    </div>

    <div class="form-group">
        <label for="forgetPasswordVerifyCode" class="col-xs-2 col-sm-2 col-md-2 control-label" id="verifyCodeLabel">邮箱验证码</label>
        <div class="col-xs-2 col-sm-2 col-md-2">
            <input type="text"  class="form-control" name="forgetPasswordVerifyCode" id="forgetPasswordVerifyCode" placeholder="邮箱验证码"/>
        </div>
    </div>

    <div class="form-group">
        <div class="col-xs-offset-2 col-sm-offset-2 col-md-offset-2 col-xs-5 col-sm-5 col-md-5">
            <button type="button" class="btn btn-success" id="resetPasswordBtn" style="width: 75px">重置</button>
        </div>
    </div>

</form>
