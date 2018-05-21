<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/4 0004
  Time: 下午 2:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">
    $(document).ready(function () {
        $('#registerForm').bootstrapValidator({
//        live: 'disabled',
            message: '填入值不正确',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                email: {
                    validators: {
                        emailAddress: {
                            message: '邮箱地址不合法'
                        }
                    }
                },
                username: {
                    message: '昵称不合法',
                    validators: {
                        notEmpty: {
                            message: '昵称不能为空'
                        },
                        stringLength: {
                            max: 30,
                            message: '昵称的长度不能超过30个字符'
                        },
                      /*  regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: 'The username can only consist of alphabetical, number, dot and underscore'
                        },*/
                     /*   remote: {
                            url: 'remote.php',
                            message: 'The username is not available'
                        }*/
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        identical: {
                            field: 'confirmPassword',
                            message: '密码与确认密码必须一致'
                        },
                       /* different: {
                            field: 'email',
                            message: '密码不能和邮箱一致'
                        },*/
                        /*stringLength: {
                            min:6,
                            max: 30,
                            message: '密码的长度应该介于6到30个字符之间'
                        },*/
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
                        }/*,
                        different: {
                            field: 'email',
                            message: '密码不能和邮箱一致'
                        }*/
                    }
                },
                gender: {
                    validators: {
                        notEmpty: {
                            message: '请选择性别'
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
        });

        $("#signupBtn").click(function () {
            var bootstrapValidator=$("#registerForm").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid())
            {
                var email=$("#inputEmail").val();
                var username=$("#inputUsername").val();
                var password=$("#inputPassword").val();
                //var confirmPassword=$("#inputConfirmPassword").val();
                var gender=$('input[name="gender"]:checked').val();
                var postData=JSON.stringify({
                    email:email,
                    username:username,
                    password:password,
                    gender:gender
                });
                console.log(postData);
                $.ajax({
                    url:"/tszh/doRegister",
                    method:"POST",
                    dataType:"json",
                    contentType: 'application/json',
                    data:postData,
                    async:true,
                    success:function (result) {
                        var res=result;
                        console.log(res);
                        if(res.code==2000){
                            postData=JSON.stringify({email:res.data.email,password:res.data.password})
                            toastr.options.onHidden=function () {
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
                                })
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
        });

        $("#resetBtn").click(function () {
            $('#registerForm').data('bootstrapValidator').resetForm(true);
        });
    });
</script>

<div class="page-header" style="margin-bottom:5%; padding-left:9%; font-family:楷体; font-size: 32px">
    <h1>注册</h1>
</div>
<form id="registerForm" class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-3 col-md-3 control-label">邮箱</label>
        <div class="col-sm-6 col-md-6">
            <input type="text" class="form-control" name="email" id="inputEmail" placeholder="邮箱" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 col-md-3 control-label">昵称</label>
        <div class="col-sm-6 col-md-6">
            <input type="text" class="form-control" name="username" id="inputUsername" placeholder="昵称"/>
        </div>
    </div>


    <div class="form-group">
        <label class="col-sm-3 col-md-3 control-label">密码</label>
        <div class="col-sm-6 col-md-6">
            <input type="password" class="form-control" name="password" id="inputPassword" placeholder="密码"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 col-md-3 control-label">确认密码</label>
        <div class="col-sm-6 col-md-6">
            <input type="password" class="form-control" name="confirmPassword" id="inputConfirmPassword" placeholder="确认密码"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 col-md-3 control-label">性别</label>
        <div class="col-sm-6 col-md-6">
            <div class="radio">
                <label>
                    <input type="radio" name="gender" value="0" /> 男
                </label>
            </div>
            <div class="radio">
                <label>
                    <input type="radio" name="gender" value="1" /> 女
                </label>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-6 col-md-6 col-sm-offset-3 col-md-offset-3">
            <button type="button" class="btn btn-primary" name="signup" id="signupBtn" style="margin-right: 5%">注册</button>
            <button type="button" class="btn btn-info" id="resetBtn">重置</button>
        </div>
    </div>
</form>
