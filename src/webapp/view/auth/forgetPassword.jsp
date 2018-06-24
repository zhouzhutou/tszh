<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/19 0019
  Time: 下午 7:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(document).ready(function () {
        $("#forgetPasswordForm").bootstrapValidator({
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
                }
            }
        });

        $("#forgetPasswordBtn").click(function () {
            var bootstrapValidator=$("#forgetPasswordForm").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()){
                var postData=JSON.stringify({email:$("#email").val().trim()});
                $.ajax({
                    url:"/tszh/doForgetPassword",
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
                            //window.location.replace("/tszh/home");
                            window.location.href=("/tszh/resetPassword?email="+res.data.email);
                        }
                    },
                    error:function (e) {
                        //console.log(e.responseText);
                        var error=eval("("+e.responseText+")");
                        toastr.error(error.message);
                    }
                });
            }
        });

    });
</script>
<div class="page-header"
     style="margin-bottom:15px;margin-left:58px;font-size: 32px;font-family: 微软雅黑">
    <h1>忘记密码</h1>
</div>
<form id="forgetPasswordForm" class="form-horizontal">
    <div class="form-group">
        <label for="email" class="col-xs-2 col-sm-2 col-md-2 control-label" id="emailLabel">注册邮箱</label>
        <div class="col-xs-3 col-sm-3 col-md-3">
            <input type="text" name="email" class="form-control" id="email"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-sm-offset-2 col-md-offset-2 col-xs-5 col-sm-5 col-md-5">
            <button type="button" class="btn btn-default" id="forgetPasswordBtn" style="width: 75px">提交</button>
        </div>
    </div>
</form>
