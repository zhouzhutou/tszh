<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/20 0020
  Time: 上午 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/theme/default/layer.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
</head>
<style type="text/css">
    .label_div{
        margin-bottom: 3px;
    }
    #slpk{
        width: 196px;
    }
</style>
<script type="text/javascript">
    $(document).ready(function () {

        $("#slpk").selectpicker({});

        function getBookTypes(selector){
            $.ajax({
                url:"/tszh/bookType/getBookTypes" ,
                method:"GET",
                dataType:"json",
                success:function (result) {
                    var res=result;
                    if(res.code==2000){
                        var data=res.data;
                        for(var i=0;i<data.length;i++) {
                            selector.append("<option value='"+data[i]. type+"'>"+data[i].type+"</option>")
                        }
                        selector.selectpicker('refresh');
                        selector.selectpicker('val','');
                    }
                },
                error:function (e) {
                    //console.log(e.responseText);
                    var error=eval("("+e.responseText+")");
                    toastr.error(error.message);
                }
            })
        }

        getBookTypes($("#slpk"));

        function queryParams(params){
            var ps={};
            $("#searchForm").find($("input:text")).each(function (i,e) {
               ps[$(this).attr("name")]=$(this).val().trim();
            });
            var bookType=$("#slpk option:selected").val().trim();
            ps["bookType"]=bookType;
            ps["pageSize"]=params.pageSize;
            ps["pageNumber"]=params.pageNumber;
            return ps;
        }

        function maxLengthFormatter(value,row,index) {
            if(value.length>20){
                var str=value.substr(0,20);
                str+="...";
                return str;
            }
            return value;
        }

        function typeFormatter(value,row,index)
        {
            var str='';
            for(var i=0;i<value.length;i++){
                if(i>0)
                    str+=','+value[i];
                else
                    str+=value[i];
            }
            if(str.length>20)
                return str.substr(0,20)+"...";
            return str;
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
                "</a>",
                "<a class='like' href='javascript:void(0)' title='图书删除' style='margin-left: 5px' onclick=\"deleteExBookById('"+row.id+"')\">",
                "<i class='glyphicon glyphicon-erase'></i>",
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
                 checkbox: true
                },
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
                    title: '图书id',
                    visible:false
                },
                {
                    field: 'bookName',
                    align:'left',
                    valign:'middle',
                    title: '图书名称',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'author',
                    align:'left',
                    valign:'middle',
                    title: '作者',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'isbn',
                    align:'left',
                    valign:'middle',
                    title: 'ISBN',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'press',
                    align:'left',
                    valign:'middle',
                    title: '出版社',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'publicationDate',
                    align:'left',
                    valign:'middle',
                    title: '出版日期',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'types',
                    align:'left',
                    valign:'middle',
                    title: '图书类型',
                    formatter:typeFormatter
                },
                {
                    field: 'owner',
                    align:'left',
                    valign:'middle',
                    title: '拥有者',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'releaseDate',
                    align:'left',
                    valign:'middle',
                    title: '发布日期',
                    formatter:maxLengthFormatter
                },
                {
                    field: 'canExchange',
                    align:'left',
                    valign:'middle',
                    width:20,
                    title: '可否交换',
                    formatter:canExchangeFormatter
                },
                {
                    title: '图书详情',
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

        $("#searchBtn").click(function () {
            $("#table").bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        });

        function clearSearchForm(){
            $("#searchForm").find($("input:text")).val("");
            select=$("#slpk");
            select.selectpicker('refresh');
            select.selectpicker('val','');
            $('#table').bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        }

        $("#resetBtn").click(function () {
            clearSearchForm();
        });
    });

    /*根据id获取图书信息*/
    function getExBookById(id) {
        $.ajax({
            url:"/tszh/exchangeBook/bookInfo/"+id ,
            method:"GET",
            dataType:"json",
            async:true,
            success:function (result) {
                var res=result;
                if(res.code==2000){
                    $("#ebInfoModal").modal({
                        show:true
                    });
                    $("#exchange_book_info_form").find($("input:text[name!='canExchange']")).each(function (i,e) {
                        var name=$(this).attr("name");
                        if(name=="types"){
                            var types=res.data[name];
                            var str=""
                            for(var i=0;i<types.length;i++){
                                if(i>0)
                                    str+=","+types[i];
                                else
                                    str+=types[i];
                            }
                            if(str.length>20)
                                $(this).val(str.substr(0,20));
                            else
                                $(this).val(str);
                        }else{
                            $(this).val(res.data[name]);
                        }
                    });
                    if(res.data['canExchange']) {
                        $("#exchange_book_info_form").find($("input[name='canExchange']")).val('是');
                    }else {
                        $("#exchange_book_info_form").find($("input[name='canExchange']")).val('否');
                    }
                    $("#ebInfoModal textarea[name='extra']").val(res.data['extra']);
                }
            },
            error:function (e) {
                //console.log(e.responseText);
                var error=eval("("+e.responseText+")");
                toastr.error(error.message);
            }
        });
    }

    /*根据id删除图书*/
    function deleteExBookById(id){
        var operate="删除";
        layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认"+operate+"图书？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
            function () {
                layer.closeAll();
                $.ajax({
                    url:"/tszh/exchangeBook/doDeleteBook/"+id ,
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
        )
    }

    function deleteExBooksBtIds(){
        var rows=$("#table").bootstrapTable("getAllSelections");
        if(rows.length==0) {
            toastr.warning("请选择要删除的图书");
            return;
        }
        var exBookIds=[];
        for(var i=0;i<rows.length;i++)
            exBookIds.push(rows[i]["id"]);
        var postData=JSON.stringify({bookIds:exBookIds});
        var operate="删除"+exBookIds.length+"本";
        layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认"+operate+"图书？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
            function () {
                layer.closeAll();
                $.ajax({
                    url:"/tszh/exchangeBook/doDeleteBooks" ,
                    method:"POST",
                    dataType:"json",
                    contentType: 'application/json',
                    data:postData,
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
                        //console.log(e.responseText);
                        var error=eval("("+e.responseText+")");
                        toastr.error(error.message);
                    }
                });
            },
            function () {
                layer.close();
            }
        )
    }
</script>
<ol class="breadcrumb" style="padding-left: 0">
    <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/bookManage" name="navbar2">图书管理</a></li>
</ol>
<div>

    <div style="font-family:宋体;color: #3c3c3c;font-size: 16px;margin-bottom: 10px;font-weight: 600">查询条件</div>

    <form id="searchForm" class="form-horizontal">
        <div class="form-group" style="margin-left: -56px">
            <label for="bookName" class="control-label col-xs-1 col-sm-1 col-md-1">图书名称</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="bookName" name="bookName"/>
            </div>
            <label for="author" class="control-label col-xs-1 col-sm-1 col-md-1">图书作者</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="author" name="author"/>
            </div>
            <label for="press" class="control-label col-xs-1 col-sm-1 col-md-1">出版社</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="press" name="press"/>
            </div>
        </div>

        <div class="form-group" style="margin-left: -56px">
            <label for="isbn" class="control-label col-xs-1 col-sm-1 col-md-1">ISBN</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="isbn" name="isbn"/>
            </div>
            <label for="bookOwner" class="control-label col-xs-1 col-sm-1 col-md-1">图书发布者</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <input type="text" class="form-control" id="bookOwner" name="bookOwner"/>
            </div>
            <label for="slpk" class="control-label col-xs-1 col-sm-1 col-md-1">图书种类</label>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <select id="slpk" class="selectpicker form-control">
                    <option value="">--请选择--</option>
                </select>
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
        <div class="col-sm-10 col-md-10"
             style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-10 col-md-10">
            <button class="btn btn-danger" style="margin-top: 20px" id="exBookBatchDeleteBtn" onclick="deleteExBooksBtIds()">批量删除
                <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span></button>
            <table id="table"></table>
        </div>
    </div>

</div>

<%--ebInfoModal--%>
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
                                    <input type="text" class="form-control" name="types" readonly/>
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
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
