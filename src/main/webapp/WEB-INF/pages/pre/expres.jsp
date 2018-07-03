<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <!-- 导入jquery核心类库 -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
    <!-- 导入easyui类库 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"></script>

    <script type="text/javascript">

        //点击下载模板按钮
        function doDownloadTemplet(){
            $('#searchWindow').window("open");
        }

        //点击上传考试成绩按钮
        function doImport(){
            $('#searchExpWindow').window("open");
        }

        //工具栏
        var toolbar = [{
            id : 'button-export',
            text : '下载模板',
            iconCls : 'icon-undo',
            handler : doDownloadTemplet
        }, {
            id : 'button-import',
            text : '上传实验成绩',
            iconCls : 'icon-redo',
            handler : doImport
        }];
        
        // 定义列
        var columns = [ [ ] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            // 查询班级
            $('#searchWindow').window({
                title: '下载哪个班级的模板？',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 150,
                resizable:false
            });

            // 查询课程
            $('#searchExpWindow').window({
                title: '上传哪门课程的实验成绩？',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 150,
                resizable:false
            });

            //为下载按钮绑定事件
            $("#download").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#searchForm").form("validate");
                if(v){
                    $("#searchForm").submit();
                    $('#searchWindow').window("close");
                }
            });

            //为上传按钮绑定事件
            $("#import").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#ImportForm").form("validate");
                if(v){
                    //获取所选课程id
                    var courseId = $("input[name=courseId]").val();

                    //校验完成后调用OCUpload插件的方法
                    $("#import").upload({
                        action:'/reach/importExpRes?courseId='+courseId,
                        name:'expResFile',
                        onComplete: function(data){
                            if(data == "success") {
                                //$.messager.alert("提示信息","上传成功！","warning");
                                alert("上传成功！");
                                sleep(500);
                                window.location.reload();
                            } else if(data == "fail") {
                                //$.messager.alert("提示信息","上传失败，请重新上传！","warning");
                                alert("上传失败，请重新上传！");
                                sleep(500);
                                window.location.reload();
                            }
                        }
                    });
                }
            });

            //自定义休眠方法
            function sleep(numberMillis) {
                var now = new Date();
                var exitTime = now.getTime() + numberMillis;
                while (true) {
                    now = new Date();
                    if (now.getTime() > exitTime)
                        return;
                }
            }

        });

        //数据表格绑定的双击行事件对应的函数
        function doDblClickRow(rowIndex, rowData){

        }

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>

<!-- 查询班级 -->
<div class="easyui-window" title="下载哪个班级的模板？" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="searchForm" action="/reach/downloaExpTemplet" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">请选择班级</td>
                </tr>
                <tr>
                    <td>班级</td>
                    <td>
                        <input class="easyui-combobox" name="classId" required="true"
                               data-options="valueField:'id',textField:'className',mode:'remote',url:'/reach/findClassByTeacherId'" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><a id="download" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">下载</a> </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 查询课程-->
<div class="easyui-window" title="上传哪门课程的实验成绩？" id="searchExpWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="ImportForm">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">请选择课程</td>
                </tr>
                <tr>
                    <td>课程</td>
                    <td>
                        <input class="easyui-combobox" name="courseId" required="true"
                               data-options="valueField:'id',textField:'courseName',mode:'remote',url:'/reach/findCourseListByTeacherId'" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><a id="import" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">上传</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>