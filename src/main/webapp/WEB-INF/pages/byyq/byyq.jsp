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

        function doAdd(){
            //alert("增加...");
            $('#addPointWindow').window("open");
        }

        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要删除的指标点！","warning");
            }else{
                //选中了指标点,弹出确认框
                $.messager.confirm("删除确认","你确定要删除选中的指标点吗？",function(r){
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
                        location.href = "/byyq/deleteBatch?ids="+ids;
                    }
                });
            }
        }

        //工具栏
        var toolbar = [{
            id : 'button-add',
            text : '增加',
            iconCls : 'icon-add',
            handler : doAdd
        }, {
            id : 'button-delete',
            text : '删除',
            iconCls : 'icon-cancel',
            handler : doDelete
        }];

        // 定义列
        var columns = [ [ {
            field : 'id',
            checkbox : true,
        }, {
            field : 'fname',
            title : '毕业要求名称',
            width : 120,
            align : 'center'
        }, {
            field : 'pname',
            title : '指标点名称',
            width : 120,
            align : 'center'
        }, {
            field : 'byyqDesc',
            title : '描述',
            width : 600,
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
                url : "/byyq/pageQuery",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            // 添加指标点窗口
            $('#addPointWindow').window({
                title: '添加指标点',
                width: 600,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable:false
            });

            // 修改指标点窗口
            $('#editPointWindow').window({
                title: '修改指标点',
                width: 600,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 400,
                resizable:false
            });

        });

        //数据表格绑定的双击行事件对应的函数
        function doDblClickRow(rowIndex, rowData){
            //alert("双击表格数据...");
            //打开修改指标点窗口
            $('#editPointWindow').window("open");
            //使用form表单对象的load方法回显数据
            $("#editPointForm").form("load", rowData);
        }

        $(function(){
            //为保存按钮绑定事件
            $("#save").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#addPointForm").form("validate");
                if(v){
                    $("#addPointForm").submit();
                }
            });

            //为修改按钮绑定事件
            $("#edit").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#editPointForm").form("validate");
                if(v){
                    $("#editPointForm").submit();
                }
            });

        });

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>
<div class="easyui-window" title="新增指标点" id="addPointWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addPointForm" action="/byyq/add" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">指标点信息</td>
                </tr>
                <tr>
                    <td>选择毕业要求</td>
                    <td>
                        <input class="easyui-combobox" name="fid" required="true"
                               data-options="valueField:'fid',textField:'fname',mode:'remote',url:'/byyq/fbyyqList'" />
                    </td>
                </tr>
                <tr>
                    <td>指标点名称</td>
                    <td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>指标点描述</td>
                    <td colspan="3">
                        <textarea rows="5" cols="50" type="text" class="easyui-validatebox" name="byyqDesc"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 修改指标点窗口 -->
<div class="easyui-window" title="修改指标点" id="editPointWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editPointForm" action="/byyq/edit" method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">指标点信息</td>
                </tr>
                <tr>
                    <td>指标点描述</td>
                    <td colspan="3">
                        <textarea rows="5" cols="50" type="text" class="easyui-validatebox" name="byyqDesc"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>