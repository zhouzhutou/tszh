<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/29 0029
  Time: 下午 8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
    .my_account_info_block:not(:last-child){
        margin-bottom: 20px;
    }
    .my_account_info_label{
        display: inline-block;
        width: 60px;
        text-align: left;
    }
    .my_account_info_content{
        display: inline-block;
        width: 400px;
        text-align: left;
        margin-left: 20px;
    }
    .birth_style{
        padding-right: 5px;
        padding-left: 5px;
    }
   /* #birthday span:not(:first-child){
        padding-right: 4px;
        padding-left: 4px;
    }
    #birthday span:first-child{
        padding-right: 4px;
    }*/
</style>
<script type="text/javascript">
    $(document).ready(function () {
        $.ajax({
            url:"/tszh/user/doGetMyAccountInfo/"+$("#id").text().trim() ,
            method:"GET",
            dataType:"json",
            success:function (result) {
                var res=result;
                if(res.code==2000){
                    $(".well .my_account_info_content").each(function (i,e) {
                       var idVal=$(this).attr('id');
                       switch (idVal){
                           case 'sex':
                               if(res.data[idVal]==0)
                                   $(this).text('男');
                               else
                                   $(this).text('女');
                               break;
                           case 'birthday':
                               var birthday=res.data[idVal];
                               var birthdayArray=birthday.split('-');
                               $("#birthday_year").text(birthdayArray[0]);
                               $("#birthday_month").text(birthdayArray[1]);
                               $("#birthday_day").text(birthdayArray[2]);
                               break;
                           case 'address':
                               var str="";
                               var addressObj=res.data[idVal];
                               for(var key in addressObj){
                                   str+=addressObj[key];
                               }
                               $(this).text(str);
                               break;
                           default:
                               $(this).text(res.data[idVal]);
                       }
                    });
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
    <li><a href="${pageContext.request.contextPath}/home/accountInfo" name="navbar2">账户信息</a></li>
</ol>
<div class="row">
    <div class="col-sm-10 col-md-10"
         style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
    </div>
</div>
<div class="row">
    <div class="col-sm-10 col-md-10">

        <a class="btn btn-success" role="button" href="${pageContext.request.contextPath}/home/accountInfo/editAccountInfo"
           style="margin-top: 20px">修改个人信息<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>

        <div class="text-center well" style="margin-top: 20px">
            <div class="text-center my_account_info_block hidden">
                <div class="text-left my_account_info_content" id="id">
                    ${id}
                </div>
            </div>
            <div class="text-center my_account_info_block">
               <label class="my_account_info_label">邮箱</label>
               <div class="text-left my_account_info_content email" id="email">

               </div>
               <label class="my_account_info_label">用户名</label>
               <div class="text-left my_account_info_content username" id="username">

               </div>
            </div>
            <div class="text-center my_account_info_block">
                <label class="my_account_info_label">性别</label>
                <div class="text-left my_account_info_content sex" id="sex">

                </div>
                <label class="my_account_info_label">出生日期</label>
                <div class="text-left my_account_info_content birthday" id="birthday">
                    <span id="birthday_year"></span><span class="birth_style">年</span>
                    <span id="birthday_month"></span><span class="birth_style">月</span>
                    <span id="birthday_day"></span><span class="birth_style">日</span>
                </div>
            </div>
            <div class="text-center my_account_info_block">
                <label class="my_account_info_label">电话</label>
                <div class="text-left my_account_info_content phone" id="phone">

                </div>
                <label class="my_account_info_label">地址</label>
                <div class="text-left my_account_info_content address" id="address">

                </div>
            </div>
        </div>
    </div>
</div>