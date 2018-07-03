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

    <script type="text/javascript">

        function doAdd(){
            window.location.href = "/byyq/toAddByyqmb";
        }

        function doEdit(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一个毕业要求模板操作！","warning");
            }else{
                //获取毕业要求模板id
                var rows = $("#grid").datagrid("getSelections");
                var id = rows[0].id;
                //跳转到毕业要求模板编辑页面
                window.location.href = "/byyq/toEditByyqmb?id="+id;
            }
        }

        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要删除的毕业要求模板！","warning");
            }else{
                //选中了毕业要求模板,弹出确认框
                $.messager.confirm("删除确认","你确定要删除选中的毕业要求模板吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的毕业要求模板的id
                        for(var i=0;i<rows.length;i++){
                            var p = rows[i];//json对象
                            var id = p.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/byyq/deleteByyqmbBatch?ids="+ids;
                    }
                });
            }
        }

        //点击导出Excel按钮
        function doDownloadXls(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择单个矩阵操作！","warning");
            }else {
                //选中了一个矩阵
                var id = rows[0].id;
                window.location.href = "/byyq/exportXls?mid="+id;
            }
        }

        //点击课程达成度统计
        function doStatistic(){
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择单个矩阵操作！","warning");
            }else {
                //选中了一个矩阵
                var id = rows[0].id;
                window.location.href = "/statistic/toCourseStatisticView?mid="+id;
            }
        }

        //工具栏
        var toolbar = [{
            id : 'button-add',
            text : '增加',
            iconCls : 'icon-add',
            handler : doAdd
        }, {
            id : 'button-edit',
            text : '编辑',
            iconCls : 'icon-mini-edit',
            handler : doEdit
        }, {
            id : 'button-export',
            text : '导出Excel',
            iconCls : 'icon-undo',
            handler : doDownloadXls
        }, {
            id : 'button-delete',
            text : '删除',
            iconCls : 'icon-cancel',
            handler : doDelete
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
            field : 'name',
            title : '毕业要求模板名称',
            width : 400,
            align : 'center'
        }, {
            field : 'createTimeFormat',
            title : '创建时间',
            width : 120,
            align : 'center'
        }] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 毕业要求模板信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/byyq/pageByyqmbQuery",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
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

</body>
</html>