<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/28 0028
  Time: 下午 8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(document).ready(function () {
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

        function queryParams(params) {
            var ps={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                sortName:params.sortName,
                sortOrder:params.sortOrder
            };
            return ps;
        }
        $("#book_have_read_table").bootstrapTable({
            url: '/tszh/exchangeBook/doSearchHaveReadBooks',
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
            sortName:"applicationDate",
            sortOrder:"desc",
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
                    field: 'applicationDate',
                    align:'left',
                    valign:'middle',
                    title: '申请日期',
                    sortable:true,
                    order:'desc',
                    formatter:maxLengthFormatter
                }
            ],
            onLoadError: function () {
                toastr.error("数据加载失败！");
            }
        });
    });
</script>
<ol class="breadcrumb" style="padding-left: 0">
    <li><a href="#" style="color: black;font-size: 18px;">tszh</a></li>
    <li><a href="${pageContext.request.contextPath}/home/haveReadBooks" name="navbar2">已阅图书</a></li>
</ol>
<div class="row">
    <div class="col-sm-10 col-md-10"
         style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
    </div>
</div>
<div class="row">
    <div class="col-md-10 col-sm-10">
        <table id="book_have_read_table"></table>
    </div>
</div>