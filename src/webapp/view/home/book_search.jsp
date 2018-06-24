<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/11 0011
  Time: 下午 12:05
  To change this template use File | Settings | File Templates.
--%>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/home/book_search.css"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/layer/theme/default/layer.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/layer/layer.js"></script>
</head>

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
            var ps={
                pageSize: params.pageSize,
                pageNumber:params.pageNumber,
                bookName:bookName,
                author:author,
                press:press,
                isbn:isbn,
                bookType:booktype
            }
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

            ].join('');
        }

        function applyFormatter(value, row, index) {
            return [
                "<a class='btn btn-success' role='button' onclick=\"applyExBookById('"+row.id+"')\">",
                "申请置换",
                "</a>"
            ].join('')
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
            height:553,
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
                },
                {
                    title:'申请置换',
                    align:'center',
                    valign:'middle',
                    field:'application',
                    formatter:applyFormatter
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
            clearSearchForm();
        });

        function clearSearchForm(){
            $("#searchForm").find($("input:text")).val("");
            select=$("#slpk");
            select.selectpicker('refresh');
            select.selectpicker('val','');
            $('#table').bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        }

        /*----------------------申请图书置换-----------------------*/

        $("#my_own_book_form").bootstrapValidator({
            message: '填入值不正确',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:{
                bookName: {
                    validators: {
                        notEmpty: {
                            message: '图书名称不能为空'
                        },
                        stringLength:{
                            max:128,
                            message: '图书名称不能超过128个字符'
                        }
                    }
                },
                author:{
                    validators: {
                        notEmpty: {
                            message: '图书作者不能为空'
                        },
                        stringLength:{
                            max:128,
                            message: '图书作者姓名不能超过128个字符'
                        }
                    }
                },
                isbn:{
                    validators:{
                        notEmpty:{
                            message:'ISBN号不能为空'
                        },
                        stringLength:{
                            max:128,
                            message: 'ISBN号长度不能超过128个字符'
                        }
                    }
                },
                press:{
                    validators:{
                        stringLength:{
                            max:128,
                            message: '出版社名称长度不能超过128个字符'
                        }
                    }

                },
                publicationDate:{
                    validators:{
                        stringLength:{
                            max:64,
                            message: '出版日期长度不能超过64个字符'
                        }
                    }
                },
                extra:{
                    validators:{
                        stringLength:{
                            max:512,
                            message: '备注长度不能超过512个字符'
                        }
                    }
                }
            }
        });

        /*初始化类型选择*/
        $("#myBookInfoSlpk1").selectpicker({
            noneSelectedText : '--请选择--'
        });

        $("#myOwnBooksSearchSlpk").selectpicker({});

        /*初始化日期选择*/
        $("#mbimPublicationDate").datetimepicker({
            language: 'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true,
            minView:"month",
            keyboardNavigation: true
        });
        /*modal监听隐藏事件*/
        $("#myBookInfoModal").on('hidden.bs.modal',function () {
            $('#my_own_book_form').data('bootstrapValidator').resetForm(true);
            clearMyBookInfoModal();
            $("#myBookInfoSlpk1").empty();
        });
        /*清空myBookInfoModal*/
        function clearMyBookInfoModal(){
            $("#my_own_book_form").find($("input:text")).each(function () {
                $(this).val('');
            })
            $("#my_own_book_form").find($("textarea[name='extra']")).val('');
            var mbiSlpk1=$("#myBookInfoSlpk1");
            var mbiSlpk2=$("#myBookInfoSlpk2");
            mbiSlpk1.selectpicker('refresh');
            mbiSlpk1.selectpicker('val','');
            mbiSlpk2.selectpicker('refresh');
            mbiSlpk2.selectpicker('val','1');
        }
        /*提交置换申请*/
        $("#mbim_submitBtn").click(function () {
            var bootstrapValidator=$("#my_own_book_form").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()){
                layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认提交置换图书申请？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
                    function () {
                        layer.closeAll();
                        var data={};
                        var myOwnBookForm=$("#my_own_book_form");
                        data['myOwnBookId']=myOwnBookForm.find($("input[name='id']")).val();
                        data['wishBookId']=myOwnBookForm.find($("input[name='wish_book_id']")).val();
                        myOwnBookForm.find($("input[name!='id'][name!='wish_book_id']")).each(function () {
                           data[$(this).attr('name')]=$(this).val().trim();
                        });
                        var types=[];
                        $("#myBookInfoSlpk1 option:selected").each(function () {
                            types.push($(this).val().trim());
                        })
                        data['types']=types;
                        var mbiSlpk2=$("#myBookInfoSlpk2 option:selected").val().trim();
                        if(mbiSlpk2=='1')
                            data['canExchange']=true;
                        else
                            data['canExchange']=false;
                        data['extra']=myOwnBookForm.find($("textarea[name='extra']")).val().trim();
                        var postData=JSON.stringify(data);

                        $.ajax({
                            url:"/tszh/exchange/doExchangeBookApply" ,
                            method:"POST",
                            dataType:"json",
                            contentType: 'application/json',
                            data:postData,
                            async:true,
                            success:function (result) {
                                var res=result;
                                if(res.code==2000){
                                    toastr.options.onHidden=function () {
                                        $("#myBookInfoModal").modal('hide');
                                        clearSearchForm();
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

        /*重置按钮*/
        $("#mbim_resetBtn").click(function () {
            $('#my_own_book_form').data('bootstrapValidator').resetForm(true);
            clearMyBookInfoModal();
        });

        /*获取查询参数*/
        function myBooksQueryParams(params) {
            var ps={};
            $("#my_own_book_search_form").find($("input:text")).each(function () {
               ps[$(this).attr('name')]=$(this).val().trim();
            });
            ps['bookType']=$("#myOwnBooksSearchSlpk").selectpicker('val').trim();
            ps['pageSize']=params.pageSize;
            ps['pageNumber']=params.pageNumber;
            return ps;
        }
        function chooseBookFormatter(value, row, index){
            return [
                "<a class='like' href='javascript:void(0)' title='选择图书' onclick=\"fillMyBookInfo('"+row.id+"')\">",
                "<i class='glyphicon glyphicon-plus'></i>",
                "</a>"
            ].join('');
        }

        function getMyOwnBooksTable(){
            $("#myOwnBooksTable").bootstrapTable({
                url: '/tszh/exchangeBook/doSearchMyCanExchangeBook',
                queryParamsType: '',              //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
                queryParams: myBooksQueryParams,
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
                //height:339,
                paginationPreText: "上一页",
                paginationNextText: "下一页",
                //showRefresh:true,
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
                        visible:false,
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
                        visible:false
                    },
                    {
                        field: 'types',
                        align:'left',
                        valign:'middle',
                        title: '图书类型',
                        formatter:typeFormatter
                    },
                    {
                        field: 'releaseDate',
                        align:'left',
                        valign:'middle',
                        title: '发布日期',
                        visible:false
                    },
                    {
                        field: 'canExchange',
                        align:'left',
                        valign:'middle',
                        width:20,
                        title: '可否交换',
                        formatter: canExchangeFormatter
                    },
                    {
                        title: '操作',
                        align:'center',
                        valign:'middle',
                        field: 'operate',
                        formatter: chooseBookFormatter
                    }
                ],
                onLoadError: function () {
                    toastr.error("数据加载失败！");
                }
            });
        }

        var loadFlag=false;
        /*添加图书链接*/
        $("#myOwnBooksLink").click(function () {
            getBookTypes($("#myOwnBooksSearchSlpk"));//获取图书类型
            getMyOwnBooksTable();
            $("#myOwnBooksModal").modal();//显示myOwnBooksModal
        });
        $("#myOwnBooksModal").on('hidden.bs.modal',function () {
            clearMyOwnBookSearchForm();
            $('#myOwnBooksTable').bootstrapTable('refresh',{pageNumber:1, queryParams: myBooksQueryParams});//刷新列表
            $("#myOwnBooksSearchSlpk").find($("option")).each(function (i,e) {
                if(i!=0)
                    $(this).remove();
            });
        });

        $("#myOwnBooksSearchBtn").click(function () {
            $('#myOwnBooksTable').bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        });

        /*重置my_own_book_search_form的按钮*/
        $("#myOwnBooksSearchResetBtn").click(function () {
            clearMyOwnBookSearchForm();
            $('#myOwnBooksTable').bootstrapTable('refresh',{pageNumber:1, queryParams: myBooksQueryParams});

        });
        /*清空my_own_book_search_form*/
        function clearMyOwnBookSearchForm() {
            $("#my_own_book_search_form").find($("input:text")).each(function (i,e) {
               $(this).val('');
            });
            var mobsSlpk=$("#myOwnBooksSearchSlpk");
            mobsSlpk.selectpicker('refresh');
            mobsSlpk.selectpicker('val','');
        }
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

    /*获取图书种类*/
    function getBookTypes(select) {
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
    }

    /*申请图书置换*/
    function applyExBookById(id){
        $.ajax({
            url:"/tszh/exchangeBook/getCanExchangeBook/"+id ,
            method:"GET",
            dataType:"json",
            async:true,
            success:function (result) {
                //var data=JSON.parse(result)
                //console.log(data);
                var res=result;
                if(res.code==2000){
                    //toastr.success(res.message);
                    getBookTypes($("#myBookInfoSlpk1"));
                    $("#my_own_book_form").find("input[name='wish_book_id']").val(id);
                    $("#myBookInfoModal").modal();
                }
            },
            error:function (e) {
                //console.log(e.responseText);
                var error=eval("("+e.responseText+")");
                toastr.error(error.message);
            }
        });
    }

    function fillMyBookInfo(id) {
        $.ajax({
            url:"/tszh/exchangeBook/myBookInfo/"+id ,
            method:"GET",
            dataType:"json",
            async:true,
            success:function (result) {
                var res=result;
                if(res.code==2000){
                    var data=res.data;
                    $("#my_own_book_form").find($("input[name!='wish_book_id']")).each(function (i,e) {
                        $(this).val(data[$(this).attr('name')]);
                    });
                    $("#mbimPublicationDate").val(data['publicationDate']);
                    $("#myBookInfoSlpk1").selectpicker('val',data['types']);
                    if(data['canExchange'])
                        $("#myBookInfoSlpk2").selectpicker('val','1');
                    else
                        $("#myBookInfoSlpk2").selectpicker('val','0');
                    $("#my_own_book_form").find($("textarea[name='extra']")).val(data['extra']);
                    $("#myOwnBooksModal").modal('hide');
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
                           style="background-color: #3c3c3c;border-color:#3c3c3c" id="search"/>
                    <input class="btn btn-default" type="button" value="重置" id="reset"/>
                </div>
            </div>
        </form>

        <%--<form class="form-inline" id="searchForm">
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
                        <select id="slpk" class="form-control selectpicker">
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
        </form>--%>
        <div class="row">
            <div class="col-xs-10 col-sm-10 col-md-10"
                 style="height: 20px;border-bottom-color: #b94a48;border-bottom-style: solid;border-bottom-width: 2px">
            </div>
        </div>
        <div class="row">
            <div class="col-xs-10 col-sm-10 col-md-10">
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
                    <div class="col-xs-12 col-md-12 col-sm-12">
                        <form id="exchange_book_info_form" class="form-horizontal text-center">
                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书名称</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="bookName" readonly/>
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书作者</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="author" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">ISBN</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="isbn" readonly/>
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书类型</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="types" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">出版社</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="press" readonly/>
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">出版日期</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" name="publicationDate" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">拥有者</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="owner" readonly/>
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">发布日期</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" name="releaseDate" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">可否交换</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" name="canExchange" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">备注</label>
                                <div class="col-xs-6 col-sm-6 col-md-6">
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

<%--myBookInfoModal--%>
<div class="modal fade" id="myBookInfoModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">用于置换的图书</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12 col-md-12 col-sm-12">
                        <form id="my_own_book_form" class="form-horizontal text-center">
                            <%--图书id--%>
                            <div class="form-group hidden">
                                <input type="text" name="wish_book_id" disabled="disabled"/>
                                <input type="text" name="id" disabled="disabled"/>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书名称</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="bookName" />
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书作者</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="author" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">ISBN</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="isbn" />
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书类型</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <select id="myBookInfoSlpk1" class="selectpicker form-control" multiple>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">出版社</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="press" />
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">出版日期</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" id="mbimPublicationDate" name="publicationDate" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">可否交换</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <select id="myBookInfoSlpk2" class="selectpicker form-control" disabled="disabled">
                                        <%--<option value="">--请选择--</option>--%>
                                        <option value="1" selected="selected">是</option>
                                        <option value="0">否</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">备注</label>
                                <div class="col-xs-6 col-sm-6 col-md-6">
                                    <textarea class="form-control txtarea" name="extra" rows="6" data-bv-stringlength data-bv-stringlength-max="150"
                                              data-bv-stringlength-message="备注长度不超过150个字符"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label"></label>
                                <div class="col-xs-6 col-sm-6 col-md-6 text-left">
                                    <a class='like' href='javascript:void(0)'id="myOwnBooksLink">
                                        从已有图书中添加<i class="glyphicon glyphicon-plus-sign" aria-hidden="true"></i></a>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="mbim_submitBtn" value="提交申请">提交申请</button>
                <button type="button" class="btn btn-info" id="mbim_resetBtn">重置</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--myOwnBooksModal--%>
<div class="modal fade" id="myOwnBooksModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">我的图书</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12 col-md-12 col-sm-12">
                        <form id="my_own_book_search_form" class="form-horizontal text-center">
                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书名称</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="bookName" />
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书作者</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="author" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">ISBN</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="isbn" />
                                </div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">出版社</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="press" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label">图书类型</label>
                                <div class="col-xs-3 col-sm-3 col-md-3">
                                    <select id="myOwnBooksSearchSlpk" class="selectpicker form-control">
                                        <option value="">--请选择--</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label"></label>
                                <div class="col-xs-3 col-sm-3 col-md-3 text-right"></div>
                                <label class="col-xs-2 col-sm-2 col-md-2 control-label"></label>
                                <div class="col-xs-3 col-sm-3 col-md-3 text-right">
                                    <input class="btn btn-primary" type="button" value="查询"
                                           style="background-color: #3c3c3c;border-color:#3c3c3c" id="myOwnBooksSearchBtn"/>
                                    <input class="btn btn-default" type="button" value="重置" id="myOwnBooksSearchResetBtn"/>
                                </div>
                            </div>

                        </form>
                    </div>
                    <div class="col-xs-12 col-md-12 col-sm-12">
                        <table id="myOwnBooksTable"></table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>