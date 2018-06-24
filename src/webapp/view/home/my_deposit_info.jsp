<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/29 0029
  Time: 下午 8:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/my_account/myAccount.js"></script>
</head>
<style type="text/css">
    .my_account_info_block{
        padding-top: 60px;
    }
    .my_account_info_label{
        display: inline-block;
        width: 100px;
        text-align: left;
        font-size: 24px;
    }
    .my_account_info_content{
        display: inline-block;
        width: 100px;
        text-align: left;
        margin-left: 20px;
        font-size: 16px;
        font-family: 微软雅黑;
    }
    .my_account_info_content .deposit_unit{
        margin-left: 5px;
    }
</style>
<script type="text/javascript">
    /*function getDepositFloat(deposit) {
        var money=Math.round(parseFloat(deposit)*100)/100;
        var arr=money.toString().split('.');
        if(arr.length==1)
            return money.toString()+".00";
        if(arr.length>1)
            if(arr[1].length==1)
                return money.toString()+"0";
        return money;
    }*/
    $(document).ready(function () {
        $.ajax({
            url:"/tszh/user/doGetMyAccountInfo/"+$("#id").text().trim() ,
            method:"GET",
            dataType:"json",
            success:function (result) {
                var res=result;
                if(res.code==2000){
                    $("#deposit_val").text(getDepositFloat(res.data.deposit));
                }
            },
            error:function (e) {
                //console.log(e.responseText);
                var error=eval("("+e.responseText+")");
                toastr.error(error.message);
            }
        });
    });
</script>
<ol class="breadcrumb" style="padding-left: 0">
    <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
    <li><a href="${pageContext.request.contextPath}/home/depositInfo" name="navbar2">保证金信息</a></li>
</ol>

<div class="row">
    <div class="col-sm-10 col-md-10"
         style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">

        <a class="btn btn-success" role="button" href="${pageContext.request.contextPath}/home/accountInfo/rechargeAccount"
           style="margin-top: 20px">充值账户<span class="glyphicon glyphicon-jpy" aria-hidden="true"></span></a>

        <div class="text-center well" style="height: 160px;padding-top: 0;padding-bottom: 0;margin-bottom: 0;margin-top: 20px">
                <div class="text-center my_account_info_block hidden">
                    <label class="my_account_info_label"></label>
                    <div class="text-left my_account_info_content deposit" id="id">
                        ${id}
                    </div>
                </div>
                <div class="text-center my_account_info_block">
                    <label class="my_account_info_label">账户余额</label>
                    <div class="text-left my_account_info_content deposit" id="deposit">
                        <span id="deposit_val"></span><span class="deposit_unit">元</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>