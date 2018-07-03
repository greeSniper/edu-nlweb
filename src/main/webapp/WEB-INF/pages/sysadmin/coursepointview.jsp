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
    <!-- 导入富文本编辑器kindeditor类库 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/kindeditor-4.1.10/themes/default/default.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/kindeditor.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/kindeditor-all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/lang/zh_CN.js"></script>

    <script type="text/javascript">

        //所选课程id
        var courseId = "${courseId}";

        //批量舍弃
        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要舍弃的指标点！","warning");
            }else{
                //选中了指标点,弹出确认框
                $.messager.confirm("舍弃确认","你确定要舍弃选中的指标点吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的指标点的id
                        for(var i=0;i<rows.length;i++){
                            var p = rows[i];//json对象
                            var id = p.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/course/deletePointBatch?ids="+ids+"&id="+courseId;
                    }
                });
            }
        }

        //点击返回按钮
        function doBack(){
            window.history.go(-1);
        }
        
        //工具栏
        var toolbar = [{
            id : 'button-back',
            text : '返回',
            iconCls : 'icon-undo',
            handler : doBack
        }, {
            id : 'button-delete',
            text : '舍弃',
            iconCls : 'icon-cancel',
            handler : doDelete
        }];
        
        // 定义列
        var columns = [ [ {
            field : 'id',
            checkbox : true,
        },{
            field : 'pointName',
            title : '指标点名称',
            width : 100,
            align : 'center'
        },{
            field : 'pointDesc',
            title : '指标点介绍',
            width : 400,
            align : 'center'
        },{
            field : 'majorName',
            title : '所属专业',
            width : 200,
            align : 'center'
        },{
            field : 'courseName',
            title : '课程',
            width : 200,
            align : 'center'
        }] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 指标点信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/course/pagePointQuery?id="+courseId,
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