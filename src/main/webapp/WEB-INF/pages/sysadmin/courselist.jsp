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

        //点击返回按钮
        function doGoback(){
            window.history.go(-1);
        }

        //点击查询按钮
        function doSearch(){
            $('#searchWindow').window("open");
        }

        var classId = "${cslgClass.id}";
        var majorId = "${cslgClass.majorId}";
        var className = "${cslgClass.className}";

        //点击当前班级按钮
        function doAssociations(){
            $.messager.alert("提示信息","当前选课班级：【"+className+"】","warning");
            //$.messager.alert("提示信息",classId,"warning");
        }

        //点击选课按钮
        function doAdd(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要选择的课程！","warning");
            }else{
                //选中了课程,弹出确认框
                $.messager.confirm("选择确认","你确定要选择选中的课程吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的课程的id
                        for(var i=0;i<rows.length;i++){
                            var course = rows[i];//json对象
                            var id = course.courseIdAndTeacherId;
                            array.push(id);
                        }
                        //课程id_教师id,课程id_教师id,课程id_教师id
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/cslgClass/classSelectCourseBatch?ids="+ids+"&classId="+classId;
                        //$.post("/cslgClass/classSelectCourseBatch", {"ids":ids,"classId":classId});
                    }
                });
            }
        }

        //点击取消选课按钮
        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要取消的课程！","warning");
            }else{
                //选中了课程,弹出确认框
                $.messager.confirm("取消确认","你确定要取消选中的课程吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的课程的id
                        for(var i=0;i<rows.length;i++){
                            var course = rows[i];//json对象
                            var id = course.courseIdAndTeacherId;
                            array.push(id);
                        }
                        //课程id_教师id,课程id_教师id,课程id_教师id
                        var ids = array.join(",");
                        location.href = "/cslgClass/classUndoSelectCourseBatch?ids="+ids+"&classId="+classId;
                    }
                });
            }
        }

        //工具栏
        var toolbar = [{
            id : 'button-goback',
            text : '返回',
            iconCls : 'icon-undo',
            handler : doGoback
        }, {
            id : 'button-search',
            text : '查询',
            iconCls : 'icon-search',
            handler : doSearch
        }, {
            id : 'button-association',
            text : '当前班级',
            iconCls : 'icon-sum',
            handler : doAssociations
        }, {
            id : 'button-add',
            text : '选课',
            iconCls : 'icon-add',
            handler : doAdd
        }, {
            id : 'button-delete',
            text : '取消选课',
            iconCls : 'icon-cancel',
            handler : doDelete
        }];
        // 定义列
        var columns = [ [ {
            field : 'courseIdAndTeacherId',
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

            // 查询分区
            $('#searchWindow').window({
                title: '选课查询',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 200,
                resizable:false
            });

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
                url : "/course/findCourseByDepartId?majorId="+majorId+"&id="+classId,
                idField : 'courseIdAndTeacherId',
                columns : columns,
                onDblClickRow : doDblClickRow
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

            /**
             * 点击查询按钮
             */
            $("#btn").click(function(){
                //将指定的form表单中所有的输入项转为json数据{key:value,key:value}
                var p = $("#searchForm").serializeJson();
                //console.info(p);
                //调用数据表格的load方法，重新发送一次ajax请求，并且提交参数
                $("#grid").datagrid("load",p);
                //关闭查询窗口
                $("#searchWindow").window("close");
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

<!-- 查询分区 -->
<div class="easyui-window" title="选课查询窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="searchForm">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">查询条件</td>
                </tr>
                <tr>
                    <td>课程名称</td>
                    <td><input type="text" name="courseName" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>任课老师</td>
                    <td><input type="text" name="teacherName" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>