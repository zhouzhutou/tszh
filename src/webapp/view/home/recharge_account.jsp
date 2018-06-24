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

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/my_account/myAccount.js"></script>
</head>
<style type="text/css">
    .money_val:not(:last-child){
        margin-right: 10px;
    }

    #recharge_account_form .form-group:not(:last-child){
        margin-bottom: 20px;
    }
</style>
<script type="text/javascript">
    $(document).ready(function () {
        $("#recharge_account_form").bootstrapValidator({
            message: '填入值不正确',
            excluded: [':disabled', ':hidden', ':not(:visible)'],
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:{
                rechargeDeposit: {
                    validators: {
                        regexp: {
                            regexp: /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/,
                            message: '充值金额格式错误'
                        }
                    }
                }
            }
        });
        $(".money_val").click(function () {
            var inputTxt=$(this).val().trim();
            $("#rechargeDeposit").val(getDepositFloat(parseFloat(inputTxt)));
        });
        $("#rcfBtn").click(function () {
            //var bootstrapValidator=$("#recharge_account_form").data('bootstrapValidator');
            //bootstrapValidator.validate();
            var data={};
            $("#recharge_account_form").find("input[type='text']").each(function (i,e) {
               data[$(this).attr('name')]=$(this).val().trim();
            });
            layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认充值"+data['rechargeDeposit']+"元金额？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
                function () {
                    layer.closeAll();
                    var postData=JSON.stringify(data);
                    $.ajax({
                        url:"/tszh/user/doRechargeAccount" ,
                        method:"POST",
                        dataType:"json",
                        contentType: 'application/json',
                        data:postData,
                        async:true,
                        success:function (result) {
                            var res=result;
                            if(res.code==2000){
                                toastr.options.onHidden=function () {
                                    window.location.href="/tszh/home/depositInfo";
                                }
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
        });
    });
</script>
<ol class="breadcrumb" style="padding-left: 0">
    <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
    <li><a href="${pageContext.request.contextPath}/home/depositInfo" name="navbar2">保证金信息</a></li>
    <li><a href="${pageContext.request.contextPath}/home/accountInfo/rechargeAccount" name="navbar2">充值账户</a></li>
</ol>
<div class="row">
    <div class="col-sm-10 col-md-10"
         style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
    </div>
</div>
<div class="row" style="margin-top: 50px">
    <div class="col-sm-10 col-md-10">
        <form id="recharge_account_form" class="form-horizontal">

            <div class="form-group hidden">
                <label class="col-sm-1 col-md-1 col-sm-offset-4 col-md-offset-4 control-label"></label>
                <div class="col-sm-1 col-md-1">
                    <input type="text" class="form-control" id="id" name="id" value="${id}" readonly/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 col-sm-offset-4 col-md-offset-4 control-label">充值金额</label>
                <div class="col-sm-1 col-md-1" style="padding: 0">
                    <input type="text" class="form-control" name="rechargeDeposit" id="rechargeDeposit" readonly/>
                </div>
                <label class="control-label" style="margin-left: 4px">元</label>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-5 col-md-offset-5">
                    <input  class="btn btn-default money_val" type="button" value="1元"/>
                    <input  class="btn btn-default money_val" type="button" value="10元"/>
                    <input  class="btn btn-default money_val" type="button" value="50元"/>
                    <input  class="btn btn-default money_val" type="button" value="100元"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-5 col-md-offset-5 money_choice">
                    <button type="button" class="btn btn-primary" id="rcfBtn" value="充值" style="width: 60px">充值</button>
                </div>
            </div>

        </form>
    </div>
</div>