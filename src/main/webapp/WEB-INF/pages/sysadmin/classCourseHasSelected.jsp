<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <!-- 导入jquery核心类库 -->
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
    <!-- 导入easyui类库 -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/css/default.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/datepicker/skin/WdatePicker.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
    <script
            src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/datepicker/WdatePicker.js"></script>

    <script type="text/javascript">

        var classId = "${cc.id}";
        var className = "${cc.className}";

        //点击返回按钮
        function doGoback(){
            window.history.go(-1);
        }

        //点击当前班级按钮
        function doAssociations(){
            $.messager.alert("提示信息","当前选课班级：【"+className+"】","warning");
        }

        //工具栏
        var toolbar = [{
            id : 'button-goback',
            text : '返回',
            iconCls : 'icon-undo',
            handler : doGoback
        }, {
            id : 'button-association',
            text : '当前班级',
            iconCls : 'icon-sum',
            handler : doAssociations
        }];

        // 定义列
        var columns = [ [ {
            field : 'id',
            checkbox : true,
        },{
            field : 'courseName',
            title : '课程名称',
            width : 200,
            align : 'center'
        },{
            field : 'courseCode',
            title : '课程编号',
            width : 120,
            align : 'center'
        },{
            field : 'teacherName',
            title : '任课老师',
            width : 200,
            align : 'center'
        },{
            field : 'departName',
            title : '院系',
            width : 200,
            align : 'center'
        },{
            field : 'isSelect',
            title : '选课情况',
            width : 120,
            align : 'center',
            formatter : function(data,row, index){
                if(data==0){
                    return "<font style='color:red;'>未选</font>";
                }else{
                    return "<font style='color:dodgerblue;'>已选</font>";
                }
            }
        }] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 课程信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/cslgClass/findCourseHasSelectedByClassId?id="+classId,
                idField : 'courseIdAndTeacherId',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

        });

        //数据表格绑定的双击行事件对应的函数
        function doDblClickRow(rowIndex, rowData){
            //alert("双击表格数据...");
        }

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>

</body>
</html>