<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/16 0016
  Time: 下午 8:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
</head>
<script type="text/javascript">
    $(document).ready(function () {

        $(".selectpicker").selectpicker({
            noneSelectedText : '--请选择--'
        });

        $("#publicationDate1").datetimepicker({
            language: 'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true,
            minView:"month",
            keyboardNavigation: true
        });

        $("#publicationDate2").datetimepicker({
            language: 'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true,
            minView:"month",
            keyboardNavigation: true
        });

        $.ajax({
            url:"/tszh/bookType/getBookTypes" ,
            method:"GET",
            dataType:"json",
            success:function (result) {
                //var data=JSON.parse(result)
                //console.log(data);
                var res=result;
                if(res.code==2000){
                    var data=res.data;
                    $(".selectpicker").each(function () {
                        var select=$(this);
                        for(var i=0;i<data.length;i++) {
                            select.append("<option value='" + data[i].type+"'>"+data[i].type+"</option>");
                        }
                        select.selectpicker('refresh');
                        select.selectpicker('val','');
                    });
                }
            },
            error:function (e) {
                //console.log(e.responseText);
                var error=eval("("+e.responseText+")");
                toastr.error(error.message);
            }
        });

        $("#exchange_book_apply_form").bootstrapValidator({
            message: '填入值不正确',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:{
                bookName1: {
                    validators: {
                        notEmpty: {
                            message: '图书名称不能为空'
                        }
                    }
                },
                author1:{
                    validators: {
                        notEmpty: {
                            message: '图书作者不能为空'
                        }
                    }
                },
                isbn1:{
                    validators:{
                        notEmpty:{
                            message:'ISBN号不能为空'
                        }
                    }
                },
                bookName2: {
                    validators: {
                        notEmpty: {
                            message: '图书名称不能为空'
                        }
                    }
                },
                author2:{
                    validators: {
                        notEmpty: {
                            message: '图书作者不能为空'
                        }
                    }
                },
                isbn2:{
                    validators:{
                        notEmpty:{
                            message:'ISBN号不能为空'
                        }
                    }
                },
                owner2:{
                    validators:{
                        notEmpty:{
                            message:'拥有者昵称不能为空'
                        }
                    }
                }
            }
        });

        $("#applyBtn").click(function () {
            var bootstrapValidator=$("#exchange_book_apply_form").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()) {
                var data = {};
                $("#exchange_book_apply_form").find($("input:text")).each(function (index, element) {
                    data[$(this).attr("name")] = $(this).val().trim();
                });
                $("#exchange_book_apply_form .selectpicker").each(function (idx, element) {
                    var attr = "type" + (idx + 1);
                    //var value = "";
                    var value=[];
                    $(this).find($("option:selected")).each(function (i, e) {
                        //value += $(this).val().trim() + ',';
                        value.push($(this).val().trim())
                    });
                    data[attr]=value;
                });
                data["extra1"]=$("#extra1").val().trim();
                data["extra2"]=$("#extra2").val().trim();
                //console.log(data);
                var postData = JSON.stringify(data);
                $.ajax({
                    url: "/tszh/exchange/doExchangeBookApply",
                    method: "POST",
                    dataType: "json",
                    contentType: 'application/json',
                    data: postData,
                    async: true,
                    success: function (result) {
                        //var data=JSON.parse(result)
                        //console.log(data);
                        var res = result;
                        if (res.code == 2000) {
                            toastr.success(res.message);
                        }
                    },
                    error: function (e) {
                        //console.log(e.responseText);
                        var error = eval("(" + e.responseText + ")");
                        toastr.error(error.message);
                    }
                });
            }
        });

    })
</script>
<ol class="breadcrumb" style="padding-left: 0">
    <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
    <li><a href="${pageContext.request.contextPath}/home/exchangeBookApply" name="navbar2">置换申请</a></li>
</ol>
<div class="row">
    <div class="col-md-10 col-sm-10">
        <form id="exchange_book_apply_form" method="post" class="form-horizontal">

            <%--1--%>
            <div style="font-family:宋体;color: #3c3c3c;font-size: 16px;font-weight: 600;
                margin-bottom: 15px;border-bottom: 2px solid #b94a48">
                拥有图书信息
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 control-label">图书名称</label>
                <div class="col-sm-3 col-md-3">
                    <input type="text" class="form-control" name="bookName1"/>
                </div>
                <label class="col-sm-1 col-md-1 control-label">图书作者</label>
                <div class="col-sm-3 col-md-3">
                    <input type="text" class="form-control" name="author1" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 control-label">ISBN</label>
                <div class="col-sm-3 col-md-3">
                    <input type="text" class="form-control" name="isbn1"/>
                </div>
                <label class="col-sm-1 col-md-1 control-label">图书类型</label>
                <div class="col-sm-3 col-md-3">
                    <select id="slpk1" class="selectpicker form-control" multiple>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 control-label">出版社</label>
                <div class="col-sm-3 col-md-3">
                    <input type="text" class="form-control" name="press1"/>
                </div>
                <label class="col-sm-1 col-md-1 control-label">出版日期</label>
                <div class="col-sm-3 col-md-3">
                    <input  type="text" class="form-control" value="" id="publicationDate1" name="publicationDate1" readonly="readonly" />
                   <%-- <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>--%>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-1 col-md-1 control-label">备注</label>
                <div class="col-sm-5 col-md-5">
                    <textarea class="form-control txtarea" id="extra1" name="extra1" rows="6" data-bv-stringlength data-bv-stringlength-max="150"
                              data-bv-stringlength-message="备注长度不超过150个字符"></textarea>
                </div>
            </div>

            <%--2--%>

                <div style="font-family:宋体;color: #3c3c3c;font-size: 16px;font-weight: 600;
                margin-bottom: 15px;border-bottom: 2px solid #b94a48">
                    申请图书信息
                </div>

                <div class="form-group">
                    <label class="col-sm-1 col-md-1 control-label">图书名称</label>
                    <div class="col-sm-3 col-md-3">
                        <input type="text" class="form-control" name="bookName2"/>
                    </div>
                    <label class="col-sm-1 col-md-1 control-label">图书作者</label>
                    <div class="col-sm-3 col-md-3">
                        <input type="text" class="form-control" name="author2" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-1 col-md-1 control-label">ISBN</label>
                    <div class="col-sm-3 col-md-3">
                        <input type="text" class="form-control" name="isbn2"/>
                    </div>
                    <label class="col-sm-1 col-md-1 control-label">图书类型</label>
                    <div class="col-sm-3 col-md-3">
                        <select id="slpk2" class="selectpicker form-control" multiple>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-1 col-md-1 control-label">出版社</label>
                    <div class="col-sm-3 col-md-3">
                        <input type="text" class="form-control" name="press2"/>
                    </div>
                    <label class="col-sm-1 col-md-1 control-label">出版日期</label>
                    <div class="col-sm-3 col-md-3 input-append date form_datetime">
                        <%-- <div class="">--%>
                        <input  type="text" class="form-control" value="" id="publicationDate2" name="publicationDate2" readonly="readonly" />
                      <%--  <span class="glyphicon glyphicon-calendar" style="display: inline" aria-hidden="true"></span>--%>
                        <%--     </div>--%>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-1 col-md-1 control-label">拥有者昵称</label>
                    <div class="col-sm-3 col-md-3">
                        <input type="text" class="form-control" name="owner2"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-1 col-md-1 control-label">备注</label>
                    <div class="col-sm-5 col-md-5">
                    <textarea class="form-control txtarea" id="extra2" name="extra2" rows="6" data-bv-stringlength data-bv-stringlength-max="150"
                              data-bv-stringlength-message="备注长度不超过150个字符"></textarea>
                    </div>
                </div>

                <div class="form-group" style="margin-top: 30px">
                    <div class="col-sm-8 col-md-8 text-center">
                        <button type="button" class="btn btn-primary" style="margin-right: 5%" name="applyBtn" id="applyBtn" value="提交申请">提交申请</button>
                        <button type="button" class="btn btn-info" name="resetBtn" id="resetBtn" value="重置表单">重置表单</button>
                    </div>
                </div>
        </form>
    </div>
</div>

