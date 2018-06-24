<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/29 0029
  Time: 下午 8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/distpicker/dist/distpicker.data.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/distpicker/dist/distpicker.min.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/theme/default/layer.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
</head>
<script type="text/javascript">
    $(document).ready(function () {
        $("#edit_my_account_info_form").bootstrapValidator({
            message: '填入值不正确',
            excluded: [':disabled', ':hidden', ':not(:visible)'],
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
                        },
                        emailAddress: {
                            message: '邮箱地址不合法'
                        }
                    }
                },
                username:{
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            max: 32,
                            message: '昵称的长度不能超过32个字符'
                        }
                    }
                },
                phone:{
                    validators:{
                        regexp:{
                            regexp:/^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,
                            message:'电话号码格式错误'
                        }
                    }
                },
                birthday:{
                    validators:{
                        date :{
                            format:'yyyy-MM-dd',
                            message: '日期格式错误，应为yyyy-MM-dd'
                        }
                    }

                },
              /*  sex:{
                    //container:'#sex_icon_container',
                    validators:{
                        notEmpty:{
                            message: '请选择性别'
                        }
                    }
                },*/
                street:{
                    validators:{
                        stringLength:{
                            max:128,
                            message: '街道门牌号长度不能超过128个字符'
                        }
                    }
                }
            }
        });
        /*初始化生日日期选择器*/
        $("#emaifBirthday").datetimepicker({
            language: 'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true,
            minView:"month",
            keyboardNavigation: true
        });
        /*初始化地区选择器*/
     /*   $("#emaifDistpicker").distpicker({
            province: "---- 所在省 ----",
            city: "---- 所在市 ----",
            district: "---- 所在区 ----",
            autoSelect: false
        });*/

        $.ajax({
            url:"/tszh/user/doGetMyAccountInfo/"+$("#id").val().trim() ,
            method:"GET",
            dataType:"json",
            success:function (result) {
                var res=result;
                if(res.code==2000){
                    $("#edit_my_account_info_form").find($("input[name!='sex']")).each(function (i,e) {
                       var name=$(this).attr('name');
                       $(this).val(res.data[name]);
                    });
                    if(res.data['sex']==0)
                        $("#maleRadio").attr("checked","checked");
                    else
                        $("#femaleRadio").attr("checked","checked");
                    var addressObj=res.data['address'];
                    //console.log(addressObj);
                    $("#emaifDistpicker").distpicker({
                        province: typeof addressObj!='undefined'&&addressObj['province']? addressObj['province'] : "---选择省---",
                        city: typeof addressObj!='undefined'&&addressObj['city'] ? addressObj['city'] : "---选择市---",
                        district: typeof addressObj!='undefined'&&addressObj['county'] ? addressObj['county'] : "---选择区县---",
                        autoSelect: false
                    });
                    if(addressObj!==undefined) {
                        $("#street").val(addressObj['street']);
                    }else{
                        $("#street").val('');
                    }
                }
            },
            error:function (e) {
                //console.log(e.responseText);
                var error=eval("("+e.responseText+")");
                toastr.error(error.message);
            }
        });

        $("#emaifSubmitBtn").click(function () {
           var bootstrapValidator=$("#edit_my_account_info_form").data('bootstrapValidator');
           bootstrapValidator.validate();
           if(bootstrapValidator.isValid()){
               layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认修改个人信息？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
                   function () {
                       layer.closeAll();
                       var data={};
                       $("#edit_my_account_info_form").find($("input[name!='sex'][name!='street']")).each(function (i,e) {
                            data[$(this).attr('name')]=$(this).val().trim();
                       });
                       data['sex']=$("#edit_my_account_info_form").find($("input[name='sex']:checked")).val().trim();
                       var address={};
                       address['province']=$("#province option:selected").val().trim();
                       address['city']=$("#city option:selected").val().trim();
                       address['county']=$("#county option:selected").val().trim();
                       address['street']=$("#street").val().trim();
                       data['addressVO']=address;
                       var postData=JSON.stringify(data);
                       $.ajax({
                           url:"/tszh/user/doModifyUser" ,
                           method:"POST",
                           dataType:"json",
                           contentType: 'application/json',
                           data:postData,
                           async:true,
                           success:function (result) {
                               var res=result;
                               if(res.code==2000){
                                   toastr.options.onHidden=function () {
                                       window.location.href="/tszh/home/accountInfo";
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
                   },
                   function () {
                       layer.close();
                   });
           }
        });
    });
</script>
<ol class="breadcrumb" style="padding-left: 0">
    <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
    <li><a href="${pageContext.request.contextPath}/home/accountInfo" name="navbar2">账户信息</a></li>
    <li><a href="${pageContext.request.contextPath}/home/accountInfo/editAccountInfo" name="navbar2">修改个人信息</a></li>
</ol>
<div class="row">
    <div class="col-sm-10 col-md-10"
         style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
    </div>
</div>
<div class="row" style="margin-top: 50px">
    <div class="col-sm-10 col-md-10">
        <form id="edit_my_account_info_form" class="form-horizontal">

            <div class="form-group hidden">
                <label class="col-sm-1 col-md-1 col-sm-offset-1 col-md-offset-1 control-label"></label>
                <div class="col-sm-2 col-md-2">
                    <input type="text" class="form-control" id="id" name="id" value="${id}" readonly/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 col-sm-offset-1 col-md-offset-1 control-label">邮箱</label>
                <div class="col-sm-2 col-md-2">
                    <input type="text" class="form-control" name="email" readonly/>
                </div>
                <label class="col-sm-1 col-md-1 control-label">用户名</label>
                <div class="col-sm-2 col-md-2">
                    <input  type="text" class="form-control" name="username"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 col-sm-offset-1 col-md-offset-1 control-label">电话</label>
                <div class="col-sm-2 col-md-2">
                    <input type="text" class="form-control" name="phone" />
                </div>
                <label class="col-sm-1 col-md-1 control-label">出生日期</label>
                <div class="col-sm-2 col-md-2">
                    <input  type="text" class="form-control" id="emaifBirthday" name="birthday" readonly/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 control-label col-sm-offset-1 col-md-offset-1 control-label">性别</label>
                <div class="col-sm-2 col-md-2">
                    <label class="radio-inline">
                        <input type="radio" name="sex" value="0" id="maleRadio"/> 男
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="sex" value="1" id="femaleRadio"/> 女
                    </label>
                </div>
               <%-- <div class="col-sm-1 col-md-1" id="sex_icon_container">
                </div>--%>
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 col-sm-offset-1 col-md-offset-1 control-label">地址</label>
                <div id="emaifDistpicker">
                    <div class="col-sm-2 col-md-2">
                        <select type="text" class="form-control" id="province" name="province"></select>
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <select type="text" class="form-control" id="city" name="city"></select>
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <select type="text" class="form-control" id="county" name="county"></select>
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <input type="text" placeholder="街道门牌号" class="form-control" id="street" name="street"/>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-2 col-md-2 col-sm-offset-2 col-md-offset-2">
                    <button type="button" style="width: 100px" class="btn btn-primary text-center" id="emaifSubmitBtn" value="提交">提交</button>
                </div>
            </div>

        </form>
    </div>
</div>