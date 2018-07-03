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
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/datepicker/WdatePicker.js"></script>

    <script type="text/javascript">

        //点击筛选按钮
        function doSearch(){
            //为指标点下拉选发送请求
            $('#combo').combobox('reload', "/reachSearch/findAllPoint");
            $('#searchWindow').window("open");
        }

        //点击图像分析按钮
        function doImage(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择单个学生操作！","warning");
            }else {
                //选中了一个学生
                var id = rows[0].id;
                window.location.href = "/reach/toStuImageView?id="+id;
            }
        }

        //点击下载学生达成度统计表
        function doDownload(){
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择单个学生操作！","warning");
            }else {
                //选中了一个学生
                var id = rows[0].id;
                window.location.href = "/statistic/toDownloadStuStatistic?stuId="+id;
            }
        }

        //点击课程达成度统计
        function doStatistic(){
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择单个学生操作！","warning");
            }else {
                //选中了一个学生
                var id = rows[0].id + ",";
                window.location.href = "/statistic/toCourseStatisticView?mid="+id;
            }
        }

        //工具栏
        var toolbar = [{
            id : 'button-search',
            text : '筛选/下载',
            iconCls : 'icon-search',
            handler : doSearch
        }, {
            id : 'button-image',
            text : '图像分析',
            iconCls : 'icon-reload',
            handler : doImage
        }, {
            id : 'button-download',
            text : '下载学生达成度统计表',
            iconCls : 'icon-undo',
            handler : doDownload
        }, {
            id : 'button-statistic',
            text : '课程达成度统计',
            iconCls : 'icon-reload',
            handler : doStatistic
        }];
        
        // 定义列
        var columns = [ [ {
            field : 'id',
            checkbox : true,
        }, {
            field : 'stuId',
            title : '学号',
            width : 160,
            align : 'center'
        }, {
            field : 'stuName',
            title : '姓名',
            width : 160,
            align : 'center'
        }, {
            field : 'pointName',
            title : '指标点',
            width : 120,
            align : 'center'
        }, {
            field : 'reachFormat',
            title : '达成度',
            width : 120,
            align : 'center'
        }, {
            field : 'className',
            title : '班级',
            width : 160,
            align : 'center'
        }, {
            field : 'majorName',
            title : '专业',
            width : 200,
            align : 'center'
        }, {
            field : 'grade',
            title : '学年',
            width : 120,
            align : 'center'
        } ] ];

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
                url : "/reachSearch/findListByPage",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            // 筛选学生达成度
            $('#searchWindow').window({
                title: '筛选学生达成度',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 350,
                resizable:false
            });

            //定义一个工具方法，用于将指定的form表单中所有的输入项转为json数据{key:value,key:value}
            $.fn.serializeJson=function(){
                var serializeObj={};
                var array=this.serializeArray();
                $(array).each(function(){
                    if(serializeObj[this.name]){
                        if($.isArray(serializeObj[this.name])){
                            serializeObj[this.name].push(this.value);
                        }else{
                            serializeObj[this.name]=[serializeObj[this.name],this.value];
                        }
                    }else{
                        serializeObj[this.name]=this.value;
                    }
                });
                return serializeObj;
            };

            //点击筛选按钮
            $("#btn").click(function(){
                //将指定的form表单中所有的输入项转为json数据{key:value,key:value}
                var p = $("#searchForm").serializeJson();
                //调用数据表格的load方法，重新发送一次ajax请求，并且提交参数
                $("#grid").datagrid("load", p);
                //关闭查询窗口
                $("#searchWindow").window("close");
            });

            //点击下载按钮
            $("#download").click(function(){
                var grade = $("input[name=grade]").val();
                var classId = $("input[name=classId]").val();
                var courseId = $("input[name=courseId]").val();
                var pointId = $("input[name=pointId]").val();
                var reach = $("input[name=reach]").val();
                window.location.href = "/reachSearch/download?grade="+grade+"&classId="+classId+"&courseId="+courseId+"&pointId="+pointId+"&reach="+reach;
            });

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

<!-- 筛选学生达成度 -->
<div class="easyui-window" title="筛选/下载学生达成度" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="searchForm" action="/reach/downloadReach2" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">筛选/下载学生达成度</td>
                </tr>
                <tr>
                    <td>学年</td>
                    <td>
                        <input type="text" style="width:90px;" name="grade" onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy'});" />
                    </td>
                </tr>
                <tr>
                    <td>班级</td>
                    <td>
                        <input class="easyui-combobox" name="classId"
                               data-options="valueField:'id',textField:'className',mode:'remote',url:'/reachSearch/findAllClass'" />
                    </td>
                </tr>
                <tr>
                    <td>课程</td>
                    <td>
                        <input class="easyui-combobox" name="courseId"
                               data-options="valueField:'id',textField:'courseName',mode:'remote',url:'/reachSearch/findAllCourse'" />
                    </td>
                </tr>
                <tr>
                    <td>选择指标点</td>
                    <td>
                        <input id="combo" class="easyui-combobox" name="pointId"
                               data-options="valueField:'id',textField:'name',mode:'remote'" />
                    </td>
                </tr>
                <tr>
                    <td>达成度 小于 </td>
                    <td><input type="text" name="reach" class="easyui-numberbox" min="0" max="1" precision="2" required="true"/></td>
                </tr>
                <tr>
                    <td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">筛选</a></td>
                </tr>
                <tr>
                    <td colspan="2"><a id="download" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">下载</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>