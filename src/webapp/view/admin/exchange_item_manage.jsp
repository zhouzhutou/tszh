<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/21 0021
  Time: 下午 3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/theme/default/layer.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
</head>

<script type="text/javascript">

    $(document).ready(function () {

        //initialSelectPicker();
        $("#slpk").selectpicker({
            // noneSelectedText : '--请选择--'
        });

        $("#startDate").datetimepicker({
            language: 'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true,
            minView:"month",
            keyboardNavigation: true
        });

        $("#endDate").datetimepicker({
            language:'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true,
            minView:"month",
            keyboardNavigation: true
        })

        function queryParams(params) {
            var parameters={};
            $("#searchForm input").each(function (index,element) {
                parameters[$(this).attr("name")]=$(this).val().trim();
            });
            parameters["status"]=$("#slpk option:selected").val().trim();
            parameters["pageSize"]=params.pageSize;
            parameters["pageNumber"]=params.pageNumber;
            return parameters;
        }

        function maxLengthFormatter(value,row,index) {
            if(value.length>20){
                var str=value.substr(0,20);
                str+="...";
                return str;
            }
            return value;
        }

        function statusFormatter(value,row,index) {
            if(value==0)
                return "待审查";
            if(value==1)
                return "已通过";
            if(value==2)
                return "已过期";
        }
        function operateFormatter(value, row, index) {
            str="";
            str+="<button class='btn btn-primary' type='button' onclick=\"deleteExBookItemById('"+row.id+"')\"";
            if(row.status!=0)
                str+="disabled='disabled'";
            str+=">撤销</button>";

            str+="<button class='btn btn-success' type='button' style='margin-left:5px' onclick=\"approveExBookItemById('"+row.id+"')\"";
            if(row.status!=0)
                str+="disabled='disabled'";
            str+=">通过</button>";

            str+="<button class='btn btn-danger' type='button' style='margin-left:5px' onclick=\"expireExBookItemById('"+row.id+"')\"";
            if(row.status!=1)
                str+="disabled='disabled'";
            str+=">过期</button>";
            return str;
         }
        $("#table").bootstrapTable({
            url: '/tszh/exchange/doSearchExchangeBookItem',
            queryParamsType: '',              //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            queryParams: queryParams,
            method: "post",
            dataType:"json",
            contentType:"application/json",
            pagination: true,
            pageNumber: 1,
            pageSize: 8,
            pageList: [10, 20, 50, 100],
            sidePagination: "server",         //分页方式：client客户端分页，server服务端分页（*）
            striped: true,                    //是否显示行间隔色
            cache: false,
            uniqueId: "id",//每一行的唯一标识，一般为主键列
            height:602,
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            showRefresh:true,
            showFooter:false,
            //showToggle:true,
            //cardView: true,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns:[
                {
                    title:"序号",
                    align:'left',
                    formatter:
                        function (value, row, index)
                        {
                            return index + 1;
                        }

                },
                {
                    field: 'id',
                    align:'left',
                    valign:'middle',
                    title: '申请号'
                },
                {
                    field: 'bookName1',
                    align:'left',
                    valign:'middle',
                    title: '置换图书的名称',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'owner1',
                    align:'left',
                    valign:'middle',
                    title: '图书置换的申请者',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'bookName2',
                    align:'left',
                    valign:'middle',
                    title: '申请图书的名称',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'owner2',
                    align:'left',
                    valign:'middle',
                    title: '申请图书的拥有者',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'applicationDate',
                    align:'left',
                    valign:'middle',
                    title: '申请提交日期',
                    width:'80px'
                },
                {
                    field: 'passedDate',
                    align:'left',
                    valign:'middle',
                    title: '申请通过日期',
                    width:'80px'
                },
                {
                    field: 'expiredDate',
                    align:'left',
                    valign:'middle',
                    title: '申请到期日期',
                    width:'80px'
                },
                {
                    field: 'status',
                    align:'left',
                    valign:'middle',
                    title: '申请状态',
                    formatter:statusFormatter

                }
                , {
                 title: '操作',
                 align:'center',
                 valign:'middle',
                 field: 'operate',
                 formatter: operateFormatter
                 }
            ]
        });
        $("#searchBtn").click(function () {
            $('#table').bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        });

        $("#resetBtn").click(function () {
            $("#searchForm").find($("input:text")).val("");
            select=$("#slpk");
            select.selectpicker('refresh');
            select.selectpicker('val','');
            $('#table').bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        })
    });

    function deleteExBookItemById(id)
    {
        var operate="撤销";
        layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认"+operate+"这条置换记录？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
            function () {
                layer.closeAll();
                $.ajax({
                    url:"/tszh/exchange/doDeleteExchangeBookItem/"+id ,
                    method:"GET",
                    dataType:"json",
                    async:true,
                    success:function (result) {
                        var res=result;
                        if(res.code==2000){
                            toastr.success(res.message);
                            $("#searchForm").find($("input:text")).val("");
                            select=$("#slpk");
                            select.selectpicker('refresh');
                            select.selectpicker('val','');
                            $('#table').bootstrapTable('refresh',{pageNumber:1});
                        }
                    },
                    error:function (e) {
                        var error=eval("("+e.responseText+")");
                        toastr.error(error.message);
                    }
                });
            },
            function () {
                layer.close();
            }
        );
    }

    function approveExBookItemById(id)
    {
        var operate="通过";
        layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认"+operate+"这条置换记录？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
            function () {
                layer.closeAll();
                $.ajax({
                    url:"/tszh/exchange/doApproveExchangeBookItem/"+id ,
                    method:"GET",
                    dataType:"json",
                    async:true,
                    success:function (result) {
                        var res=result;
                        if(res.code==2000){
                            toastr.success(res.message);
                            $("#searchForm").find($("input:text")).val("");
                            select=$("#slpk");
                            select.selectpicker('refresh');
                            select.selectpicker('val','');
                            $('#table').bootstrapTable('refresh',{pageNumber:1});
                        }
                    },
                    error:function (e) {
                        var error=eval("("+e.responseText+")");
                        toastr.error(error.message);
                    }
                });
            },
            function () {
                layer.close();
            }
        );
    }

    function expireExBookItemById(id){
        var operate="过期";
        layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认使这条置换记录"+operate+"？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
            function () {
                layer.closeAll();
                $.ajax({
                    url:"/tszh/exchange/doExpireExchangeBookItem/"+id ,
                    method:"GET",
                    dataType:"json",
                    async:true,
                    success:function (result) {
                        var res=result;
                        if(res.code==2000){
                            toastr.success(res.message);
                            $("#searchForm").find($("input:text")).val("");
                            select=$("#slpk");
                            select.selectpicker('refresh');
                            select.selectpicker('val','');
                            $('#table').bootstrapTable('refresh',{pageNumber:1});
                        }
                    },
                    error:function (e) {
                        var error=eval("("+e.responseText+")");
                        toastr.error(error.message);
                    }
                });
            },
            function () {
                layer.close();
            }
        );
    }
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ol class="breadcrumb" style="padding-left: 0">
    <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/exchangeItemManage" name="navbar2">置换管理</a></li>
</ol>
<div>
    <div style="font-family:宋体;color: #3c3c3c;font-size: 16px;margin-bottom: 10px;font-weight: 600">查询条件</div>
    <form id="searchForm" class="form-horizontal">
        <div class="form-group" style="margin-left: -56px">
            <label for="id" class="control-label col-xs-1 col-sm-1 col-md-1">申请号</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="id" name="id"/>
            </div>
            <label for="bookName1" class="control-label col-xs-1 col-sm-1 col-md-1">置换图书名称</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="bookName1" name="bookName1"/>
            </div>
            <label for="bookName2" class="control-label col-xs-1 col-sm-1 col-md-1">申请图书名称</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="bookName2" name="bookName2"/>
            </div>
        </div>

        <div class="form-group" style="margin-left: -56px">
            <label for="slpk" class="control-label col-xs-1 col-sm-1 col-md-1">申请状态</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <select id="slpk" class="selectpicker form-control">
                    <option value="" selected>--请选择--</option>
                    <option value='0'>待审查</option>
                    <option value='1'>已通过</option>
                    <option value='2'>已过期</option>
                </select>
            </div>
            <label class="control-label col-xs-1 col-sm-1 col-md-1">申请提交日期</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="startDate" name="startDate" readonly="readonly"/>
            </div>
            <div class="control-label col-xs-1 col-sm-1 col-md-1" style="width: 10px;padding-left: 0px;">至</div>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="endDate" name="endDate" readonly="readonly"/>
            </div>
        </div>

        <div class="form-group text-right" style="margin-top: 20px; margin-right: 0px">
            <div class="col-xs-3 col-sm-3 col-md-3 col-xs-offset-6 col-sm-offset-6 col-md-offset-6">
                <input class="btn btn-primary" type="button" value="查询"
                       style="background-color: #3c3c3c;border-color:#3c3c3c" id="searchBtn"/>
                <input class="btn btn-default" type="button" value="重置" id="resetBtn"/>
            </div>
        </div>
    </form>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12"
             style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-12">
            <table id="table"></table>
        </div>
    </div>
</div>


