<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/21 0021
  Time: 下午 4:46
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
                "</a>",
                "<a class='like' href='javascript:void(0)' title='图书删除' style='margin-left: 5px' onclick=\"deleteExBookById('"+row.id+"')\">",
                "<i class='glyphicon glyphicon-erase'></i>",
                "</a>"
            ].join('');
        }
        $("#table").bootstrapTable({
            url: '/tszh/exchangeBook/doMySearchBook',
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
        /*清空搜索输入条件*/
        function clearSearchForm() {
            $("#searchForm").find($("input:text")).val("");
            select=$("#slpk");
            select.selectpicker('refresh');
            select.selectpicker('val','');
            $('#table').bootstrapTable('refresh',{pageNumber:1, queryParams: queryParams});
        }

        $("#reset").click(function () {
            clearSearchForm();
        });

        /*----------------------添加图书--------------------*/

        $("#exchange_book_release_form").bootstrapValidator({
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
                            max:128,
                            message: '出版日期长度不能超过64个字符'
                        }
                    }
                },
                extra:{
                    validators:{
                        stringLength:{
                            max:128,
                            message: '备注长度不能超过512个字符'
                        }
                    }
                }
            }
        });

        $("#addExBookSlpk1").selectpicker({
            noneSelectedText : '--请选择--'
        });
        $("#aebmPublicationDate").datetimepicker({
            language: 'zh-CN',
            format:"yyyy-mm-dd",
            autoclose:true,
            minView:"month",
            keyboardNavigation: true
        });
        $("#addExBookModal").on('hidden.bs.modal',function () {
            clearAddExBookModal();
            $('#exchange_book_release_form').data('bootstrapValidator').resetForm(true);
        });
        $("#exBookReleaseBtn").click(function () {
            $("#addExBookModal").modal();
            $.ajax({
                url:"/tszh/bookType/getBookTypes" ,
                method:"GET",
                dataType:"json",
                success:function (result) {
                    var res=result;
                    if(res.code==2000){
                        var data=res.data;
                        var select=$("#addExBookSlpk1");
                        select.empty();
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
        });
        function clearAddExBookModal() {
            $("#addExBookModal input").each(function () {
                $(this).val('');
            });
            $("#addExBookModal textarea[name='extra']").val('');
            var slpk1=$("#addExBookSlpk1");
            slpk1.selectpicker('refresh')
            slpk1.selectpicker('val','');
        }
        $("#aebm_submitBtn").click(function () {
            var bootstrapValidator=$("#exchange_book_release_form").data('bootstrapValidator');
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()){
                var operate=$(this).val();
                layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认"+operate+"图书？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
                    function () {
                        layer.closeAll();
                        var data={};
                        $("#exchange_book_release_form input").each(function () {
                            data[$(this).attr('name')]=$(this).val().trim();
                        });
                        data['extra']=$("#exchange_book_release_form textarea[name='extra']").val().trim();
                        var types=[];
                        $("#addExBookSlpk1 option:selected").each(function (i,e) {
                            types.push($(this).val().trim());
                        });
                        data['types']=types;
                        var slpk2Val=$("#addExBookSlpk2 option:selected").val().trim();
                        if(slpk2Val=='1')
                            data['canExchange']=true;
                        else
                            data['canExchange']=false;
                        //console.log(data);
                        var postData=JSON.stringify(data);
                        $.ajax({
                            url:"/tszh/exchangeBook/doReleaseBook" ,
                            method:"POST",
                            dataType:"json",
                            contentType: 'application/json',
                            data:postData,
                            async:true,
                            success:function (result) {
                                //var data=JSON.parse(result)
                                //console.log(data);
                                var res=result;
                                if(res.code==2000){
                                    toastr.options.onHidden=function () {
                                        $("#addExBookModal").modal('hide');
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
        $("#aebm_resetBtn").click(function () {
            clearAddExBookModal();
            $('#exchange_book_release_form').data('bootstrapValidator').resetForm(true);
        });

        /*----------------------修改图书--------------------*/
        var modifyFlag=false;
        $("#exchange_book_info_form").bootstrapValidator({
            message: '填入值不正确',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:{
                id:{
                    validators:{
                        notEmpty:{
                            message:'图书id不能为空'
                        }
                    }
                },
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
                            max:128,
                            message: '出版日期长度不能超过64个字符'
                        }
                    }
                },
                extra:{
                    validators:{
                        stringLength:{
                            max:128,
                            message: '备注长度不能超过512个字符'
                        }
                    }
                }
            }
        });
        $("#exBookInfoSlpk1").selectpicker({
            noneSelectedText : '--请选择--'
        });
        $("#exBookInfoSlpk2").selectpicker({
            noneSelectedText : '--请选择--'
        });
        $("#exBookInfoModal").on('hidden.bs.modal',function () {
            $('#exchange_book_info_form').data('bootstrapValidator').resetForm(true);
            clearExBookInfoModal();
            disableInput();
            modifyFlag=false;
        });
        /*清空ExBookInfoModal*/
        function clearExBookInfoModal() {
            $("#exBookInfoModal input").each(function () {
                $(this).val('');
            });
            $("#exBookInfoModal textarea[name='extra']").val('');
            $("#exBookInfoModal select").each(function () {
                $(this).selectpicker('refresh');
                $(this).selectpicker('val','');
            });
        }
        /*禁用控件*/
        function disableInput() {
            $("#exBookInfoModal input").each(function (i,e) {
                $(this).prop('readonly',true);
            });
            $("#exBookInfoModal textarea[name='extra']").prop('readonly',true);
            $("#exBookInfoModal select").each(function () {
                $(this).prop('disabled',true);
                $(this).selectpicker('refresh')
            });
            $("#ebimPublicationDate").datetimepicker('remove');
        }
        /*不禁用控件*/
        function enableInput() {
            $("#exBookInfoModal input[name!='releaseDate'][name!='publicationDate']").each(function (i,e) {
                $(this).prop('readonly',false);
            });
            $("#exBookInfoModal textarea[name='extra']").prop('readonly',false);
            $("#exBookInfoModal select").each(function () {
               $(this).prop('disabled',false);
               $(this).selectpicker('refresh')
            });
            $("#ebimPublicationDate").datetimepicker({
                language: 'zh-CN',
                format:"yyyy-mm-dd",
                autoclose:true,
                minView:"month",
                keyboardNavigation: true
            });
        }
        $("#ebim_enableInputBtn").click(function () {
            enableInput();
            modifyFlag=true;
        });
        $("#ebim_submitBtn").click(function () {
            if(modifyFlag){
                var bootstrapValidator=$("#exchange_book_info_form").data('bootstrapValidator');
                bootstrapValidator.validate();
                if(bootstrapValidator.isValid()){
                    var operate=$(this).val();
                    layer.confirm("<div style='text-align: center;margin-top: 50px;font-size: 16px'>确认"+operate+"图书？</div>", {type:1,btnAlign: 'c',area:['200px','150px'],title:false,btn:["确认","取消"]},
                        function () {
                            layer.closeAll();
                            var data={};
                            $("#exchange_book_info_form").find($("input:text")).each(function (i,e) {
                                data[$(this).attr('name')]=$(this).val().trim();
                            });
                            data['extra']=$("#exchange_book_info_form textarea[name='extra']").val().trim();
                            var value=[];
                            $("#exBookInfoSlpk1 option:selected").each(function (i,e) {
                                value.push($(this).val().trim());
                            });
                            data['types']=value;
                            var slpk2Val=$("#exBookInfoSlpk2 option:selected").val().trim();
                            if(slpk2Val=='1'){
                                data['canExchange']=true;
                            }else{
                                data['canExchange']=false;
                            }
                            var postData=JSON.stringify(data);
                            $.ajax({
                                url:"/tszh/exchangeBook/doModifyBook" ,
                                method:"POST",
                                dataType:"json",
                                contentType: 'application/json',
                                data:postData,
                                async:true,
                                success:function (result) {
                                    var res=result;
                                    if(res.code==2000){
                                        toastr.options.onHidden=function () {
                                            $("#exBookInfoModal").modal('hide');
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
            }
        });
    });

    /*根据id获取图书信息*/
    function getExBookById(id) {
        $.ajax({
            url:"/tszh/bookType/getBookTypes" ,
            method:"GET",
            dataType:"json",
            success:function (result) {
                var res=result;
                if(res.code==2000){
                    var data=res.data;
                    var select=$("#exBookInfoSlpk1");
                    select.empty();
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
        $.ajax({
            url:"/tszh/exchangeBook/myBookInfo/"+id ,
            method:"GET",
            dataType:"json",
            async:true,
            success:function (result) {
                var res=result;
                if(res.code==2000){
                    $("#exBookInfoModal").modal({
                        show:true
                    });
                    $("#exBookInfoModal input").each(function () {
                        var name=$(this).attr("name");
                        $(this).val(res.data[name]);
                    });
                    $("#exBookInfoModal textarea[name='extra']").val(res.data['extra']);
                    $("#exBookInfoSlpk1").selectpicker("val",res.data['types']);
                    if(res.data['canExchange']==true)
                        $("#exBookInfoSlpk2").selectpicker('val','1');
                    else
                        $("#exBookInfoSlpk2").selectpicker('val','0');
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
    <li><a href="${pageContext.request.contextPath}/home/bookSearch" name="navbar2">我的图书</a></li>
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
<%--       table      --%>
<div class="row">
    <div class="col-sm-10 col-md-10">
        <button class="btn btn-success" style="margin-top: 20px" id="exBookReleaseBtn">发布图书
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></button>

        <table id="table"></table>
    </div>
</div>
<%--modal addExBookModal--%>
<div class="modal fade aebm" id="addExBookModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">发布图书</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                        <form id="exchange_book_release_form" class="form-horizontal text-center">
                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">图书名称</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="bookName" />
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">图书作者</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="author" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">ISBN</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="isbn" />
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">图书类型</label>
                                <div class="col-sm-3 col-md-3">
                                    <select id="addExBookSlpk1" class="selectpicker form-control" multiple>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">出版社</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="press" />
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">出版日期</label>
                                <div class="col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" id="aebmPublicationDate" name="publicationDate" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">可否交换</label>
                                <div class="col-sm-3 col-md-3">
                                    <select id="addExBookSlpk2" class="selectpicker form-control" disabled="disabled">
                                        <%--<option value="">--请选择--</option>--%>
                                        <option value="1" selected="selected">是</option>
                                        <option value="0">否</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">备注</label>
                                <div class="col-sm-6 col-md-6">
                                    <textarea class="form-control txtarea" name="extra" rows="6" data-bv-stringlength data-bv-stringlength-max="150"
                                              data-bv-stringlength-message="备注长度不超过150个字符"></textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="aebm_submitBtn" value="发布">发布</button>
                <button type="button" class="btn btn-info" id="aebm_resetBtn">重置</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--modal ebInfoModal--%>
<div class="modal fade" id="exBookInfoModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
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
                            <div class="form-group hidden">
                                <%--图书id--%>
                                <input type="text" name="id" disabled="disabled"/>
                            </div>
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
                                    <select id="exBookInfoSlpk1" class="selectpicker form-control" disabled="disabled" multiple>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">出版社</label>
                                <div class="col-sm-3 col-md-3">
                                    <input type="text" class="form-control" name="press" readonly/>
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">出版日期</label>
                                <div class="col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" id="ebimPublicationDate" name="publicationDate" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-md-2 control-label">发布日期</label>
                                <div class="col-sm-3 col-md-3">
                                    <input  type="text" class="form-control" name="releaseDate" readonly/>
                                </div>
                                <label class="col-sm-2 col-md-2 control-label">可否交换</label>
                                <div class="col-sm-3 col-md-3">
                                    <select id="exBookInfoSlpk2" class="selectpicker form-control" disabled="disabled">
                                        <%--<option value="">--请选择--</option>--%>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select>
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
                <button type="button" class="btn btn-info" id="ebim_enableInputBtn" value="发布">修改</button>
                <button type="button" class="btn btn-primary" id="ebim_submitBtn" value="修改">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>


