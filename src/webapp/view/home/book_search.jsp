<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/11 0011
  Time: 下午 12:05
  To change this template use File | Settings | File Templates.
--%>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/home/book_search.css"/>
</head>
<style type="text/css">
    .label_div{
        margin-bottom: 3px;
    }
</style>

<script type="text/javascript">
    $(document).ready(function () {

        $("#slpk").selectpicker({
           // noneSelectedText : '--请选择--'
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
                   var select=$("#slpk");
                   for(var i=0;i<data.length;i++) {
                        select.append("<option value='"+data[i]. type+"'>"+data[i].type+"</option>")
                   }
                   select.selectpicker('refresh');
                   select.selectpicker('val','');
                }
            },
            error:function (e) {
                //console.log(e.responseText);
                var error=eval("("+e.responseText+")");
                toastr.error(error.message);
            }
        });

        function queryParams(params) {
            var bookName=$("#bookName").val().trim();
            var author=$("#author").val().trim();
            var press=$("#press").val().trim();
            var isbn=$("#isbn").val().trim();
            var booktype=$("#slpk option:selected").val().trim();
            //console.log(bookName+" "+author+" "+press+" "+ISBN+" "+booktype)
            var params={
                pageSize: params.pageSize,
                pageNumber:params.pageNumber,
                bookName:bookName,
                author:author,
                press:press,
                isbn:isbn,
                bookType:booktype
            }
            return params;
        }

        function canExchangeFormatter(value, row, index)
        {
            if(value===true)
                return "是";
            else
                return "否";
        }

        function operateFormatter(value, row, index) {
            return [
                "<a class='like' href='javascript:void(0)' title='图书详情' onclick=\"getExBookById('"+row.id+"')\">",
                "<i class='glyphicon glyphicon-th'></i>",
                "</a>"
            ].join('');
        }
        $("#table").bootstrapTable({
            url: '/tszh/exchangeBook/doSearchBook',
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
                    visible:false,
                    title: '图书id'
                },
                {
                    field: 'bookName',
                    align:'left',
                    valign:'middle',
                    title: '图书名称'
                },
                {
                    field: 'author',
                    align:'left',
                    valign:'middle',
                    title: '作者'
                },
               {
                    field: 'isbn',
                    align:'left',
                    valign:'middle',
                    title: 'ISBN'
                },
                {
                    field: 'press',
                    align:'left',
                    valign:'middle',
                    title: '出版社'
                },
                {
                    field: 'publicationDate',
                    align:'left',
                    valign:'middle',
                    title: '出版日期'
                },
                {
                    field: 'type',
                    align:'left',
                    valign:'middle',
                    title: '图书类型'
                },
                {
                    field: 'owner',
                    align:'left',
                    valign:'middle',
                    title: '拥有者'
                },
                {
                    field: 'releaseDate',
                    align:'left',
                    valign:'middle',
                    title: '发布日期'
                },
                {
                    field: 'canExchange',
                    align:'left',
                    valign:'middle',
                    width:25,
                    title: '可否交换',
                    formatter: canExchangeFormatter
                },
                {
                    title: '操作',
                    align:'center',
                    valign:'middle',
                    field: 'operate',
                    formatter: operateFormatter
                }
            ],
            onLoadError: function () {
                toastr.error("数据加载失败！");
            }
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
        });

    })

    function getExBookById(id) {
        console.log(id);
        $.ajax({
            url:"/tszh/exchangeBook/bookInfo/"+id ,
            method:"GET",
            dataType:"json",
            async:true,
            success:function (result) {
                //var data=JSON.parse(result)
                //console.log(data);
                var res=result;
                if(res.code==2000){
                    $("#ebInfoModal").modal({
                        show:true
                    });
                    $("#ebInfoModal input").each(function () {
                        var name=$(this).attr("name");
                        if(name=="canExchange"){
                            if(res.data[name]==true)
                                $(this).val("是");
                            else
                                $(this).val("否");
                        }else{
                            $(this).val(res.data[name]);
                        }
                    });
                    $("#ebInfoModal textarea").val(res.data['extra']);
                }
            },
            error:function (e) {
                //console.log(e.responseText);
                var error=eval("("+e.responseText+")");
                toastr.error(error.message);
            }
        });
    }
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <ol class="breadcrumb" style="padding-left: 0">
        <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
        <li><a href="${pageContext.request.contextPath}/home/bookSearch" name="navbar2">图书检索</a></li>
    </ol>
    <div>
        <div style="font-family:宋体;color: #3c3c3c;font-size: 16px;margin-bottom: 10px;font-weight: 600">查询条件</div>
        <form class="form-inline" id="searchForm">
            <div class="row">
                    <div class="col-sm-2 col-md-2">
                        <div class="label_div"><label class="control-label">图书名称：</label></div>
                        <input id="bookName" type="text" class="form-control"/>
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <div  class="label_div"><label class="control-label">图书作者：</label></div>
                        <input id="author" type="text" class="form-control"/>
                    </div>
                    <div class="col-sm-2 col-sm-2">
                        <div  class="label_div"><label class="control-label">出版社：</label></div>
                        <input id="press" type="text" class="form-control"/>
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <div  class="label_div"><label class="control-label">ISBN：</label></div>
                        <input id="isbn" type="text" class="form-control"/>
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <div  class="label_div"><label class="control-label">图书种类：</label></div>
                        <select id="slpk" class="selectpicker">
                            <option value="">--请选择--</option>
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

<div class="modal fade" id="ebInfoModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">图书详情</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                        <form id="exchange_book_info_form" class="form-horizontal text-center">
                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">图书名称</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="bookName" readonly/>
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">图书作者</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="author" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">ISBN</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="isbn" readonly/>
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">图书类型</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="type" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">出版社</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="press" readonly/>
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">出版日期</label>
                                <div class="col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" name="publicationDate" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">拥有者</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="owner" readonly/>
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">发布日期</label>
                                <div class="col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" name="releaseDate" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">可否交换</label>
                                <div class="col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" name="canExchange" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">备注</label>
                                <div class="col-sm-6 col-md-6">
                                    <textarea class="form-control txtarea" id="extra" name="extra" rows="6" data-bv-stringlength data-bv-stringlength-max="150"
                                      data-bv-stringlength-message="备注长度不超过150个字符" readonly></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        style="background-color: #3c3c3c;color: white">关闭</button>
            </div>
        </div>
    </div>
</div>

