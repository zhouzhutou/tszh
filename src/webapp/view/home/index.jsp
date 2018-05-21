<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/home/index.css"/>
    <link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.7/dist/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-select-1.12.4/dist/css/bootstrap-select.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator-0.4.5/dist/css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/plugin/toastr-2.1.4/build/toastr.min.css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/jquery-easyui-1.5.4.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/bootstrap-table.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-select-1.12.4/dist/js/bootstrap-select.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrap-select-1.12.4/dist/js/i18n/defaults-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator-0.4.5/dist/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugin/toastr-2.1.4/build/toastr.min.js"></script>
    <script type="text/javascript">
        $(function () {
            toastr.options = {
                closeButton: false,
                debug: false,
                //progressBar: true,
                positionClass: "toast-top-center",
                onclick: null,
                //showDuration: "2000",
                hideDuration: "1000",
                timeOut: "2000",
                extendedTimeOut: "1000",
                showEasing: "swing",
                hideEasing: "linear",
                showMethod: "fadeIn",
                hideMethod: "fadeOut"
            };
        });
    </script>
    <title>${title}</title>
</head>

<style type="text/css">
     .container-fluid{
        width: 1920px;
        max-width: none !important;
    }
</style>
<
<script type="text/javascript">
    $(document).ready(function () {
        $("#accordion .panel").each(function (index,element) {
            $(this).find($(".panel-body:first-child")).find($("li")).each(function (i,e) {
                if(i>0)
                    $(this).css({"border-top":"1px solid lightgray"});
            })
        });

        function initialCollapse() {
            var title=$("head title:first").text();
            //console.log(title);
            var t;
            if(title=="首页") {
                t=$("#collapseOne");
                //t.collapse({'toggle':true});
                //t.parent().siblings().children($(".collapse")).collapse({'toggle':false});
                t.addClass("in");
                t.parent().siblings().children($(".collapse")).removeClass("in");
            }
            t=$(".collapse:contains("+title+")");
            //t.collapse({'toggle':true});
            //t.parent().siblings().children($(".collapse")).collapse({'toggle':false});
            t.addClass("in");
            t.parent().siblings().children($(".collapse")).removeClass("in");
        }
        initialCollapse();
    })
</script>>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">TSZH</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">注销</a></li>
                </ul>
                <p class="navbar-text navbar-right" style="color: grey"><shiro:principal/></p>
            </div>
        </div>
    </nav>

<div class="container-fluid"style="padding-top: 50px;">
    <div class="row">
        <div class="col-sm-2 col-md-2">
            <div class="panel-group" id="accordion">

                <div class="panel panel-default" id="panel_1">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                图书库
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul >
                                <li><a href="${pageContext.request.contextPath}/home/bookSearch">图书检索</a></li>
                                <li><a href="${pageContext.request.contextPath}/home/myBookSearch">我的图书</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default" id="panel_2">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                我的阅读
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul>
                                <li><a>已阅图书</a></li>
                                <li><a>待阅图书</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default" id="panel_3">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                我的置换
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul>
                                <li><a href="${pageContext.request.contextPath}/home/exchangeBookApply">置换申请</a></li>
                                <li><a href="${pageContext.request.contextPath}/home/exchangeBookItemSearch">置换记录</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default" id="panel_4">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                我的账户
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul>
                                <li><a>账户管理</a></li>
                                <li><a>保证金管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-sm-10 col-md-10">
            <jsp:include page="${contentPath}"/>
        </div>
    </div>
</div>

</body>
</html>