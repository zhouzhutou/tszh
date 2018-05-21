<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/11 0011
  Time: 下午 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
    .label_div{
        margin-bottom: 3px;
    }
</style>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
</head>

<script type="text/javascript">

    $(document).ready(function () {

        //initialSelectPicker();
        $("#slpk").selectpicker({
            // noneSelectedText : '--请选择--'
        });

        $("#applicationDate").datetimepicker({
            language: 'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true,
            minView:"month",
            keyboardNavigation: true
        });

        function queryParams(params) {
            var parameters={};
            $("#searchForm input").each(function (index,element) {
                parameters[$(this).attr("name")]=$(this).val().trim();
            });
            parameters["status"]=$("#slpk option:selected").val().trim();
            parameters["pageSize"]=params.pageSize;
            parameters["pageNumber"]=params.pageNumber;
            console.log(parameters)
            return parameters;
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
            return [
                "<a class='like' href='javascript:void(0)' title='记录详情' onclick=\"getExBookItemById('"+row.id+"')\">",
                "<i class='glyphicon glyphicon-th'></i>",
                "</a>"
            ].join('');
        }
        $("#table").bootstrapTable({
            url: '/tszh/exchange/doExchangeBookItemSearch',
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
            height:441,
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
                    //visible:false,
                    title: '申请号'
                },
                {
                    field: 'bookName1',
                    align:'left',
                    valign:'middle',
                    title: '拥有图书名称'
                },
                {
                    field: 'owner1',
                    align:'left',
                    valign:'middle',
                    title: '申请者'
                },
               {
                    field: 'bookName2',
                    align:'left',
                    valign:'middle',
                    title: '申请图书名称'
                },
                {
                    field: 'owner2',
                    align:'left',
                    valign:'middle',
                    title: '申请图书拥有者'
                },
                {
                    field: 'applicationDate',
                    align:'left',
                    valign:'middle',
                    title: '申请提交日期'
                },
                {
                    field: 'passedDate',
                    align:'left',
                    valign:'middle',
                    title: '申请通过日期'
                },
                {
                    field: 'expiredDate',
                    align:'left',
                    valign:'middle',
                    title: '申请到期日期'
                },
                {
                    field: 'status',
                    align:'center',
                    valign:'middle',
                    title: '申请状态',
                    formatter:statusFormatter

                },
                {
                    title: '操作',
                    align:'center',
                    valign:'middle',
                    field: 'operate',
                    formatter: operateFormatter
                }
            ]
        });
        $("#search").click(function () {
            $('#table').bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        });

        $("#reset").click(function () {
            $("#searchForm").find($("input:text")).val("");
            select=$("#slpk");
            select.selectpicker('refresh');
            select.selectpicker('val','');
            $('#table').bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        })
    });
    function getExBookItemById(id) {
        alert("hello");
    }
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <ol class="breadcrumb" style="padding-left: 0">
        <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
        <li><a href="${pageContext.request.contextPath}/home/bookSearch" name="navbar2">置换记录</a></li>
    </ol>
    <div>
        <div style="font-family:宋体;color: #3c3c3c;font-size: 16px;margin-bottom: 10px;font-weight: 600">查询条件</div>
        <form class="form-inline" id="searchForm">
            <div class="row">
                    <div class="col-sm-2 col-md-2">
                        <div class="label_div"><label class="control-label">申请号：</label></div>
                        <input id="id" name="id" type="text" class="form-control"/>
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <div  class="label_div"><label class="control-label">拥有图书名称：</label></div>
                        <input id="bookName1" name="bookName1" type="text" class="form-control"/>
                    </div>
                    <div class="col-sm-2 col-sm-2">
                        <div  class="label_div"><label class="control-label">申请图书名称：</label></div>
                        <input id="bookName2" name="bookName2" type="text" class="form-control"/>
                    </div>
                    <div class="col-sm-2 col-sm-2">
                        <div  class="label_div"><label class="control-label">申请提交日期：</label></div>
                        <input  id="applicationDate" name="applicationDate" type="text" class="form-control" value="" readonly="readonly" />
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <div  class="label_div"><label class="control-label">申请状态：</label></div>
                        <select id="slpk" class="selectpicker">
                            <option value="">--请选择--</option>
                            <option value='0'>待审查</option>
                            <option value='1'>已通过</option>
                            <option value='2'>已过期</option>
                        </select>
                    </div>
            </div>

            <div class="row text-right" style="margin-top:20px;">
                <div class="col-sm-2 col-md-2 col-sm-offset-8 col-md-offset-8">
                    <input class="btn btn-primary" type="button" value="查询"
                           style="background-color: #3c3c3c;border-color:#3c3c3c" id="search">
                    <input class="btn btn-default" type="button" value="重置" id="reset">
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-sm-10 col-md-10"
             style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
        </div>
    </div>
    <div class="row">
        <div class="col-sm-10 col-md-10">
            <table id="table"></table>
        </div>
    </div>

