<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
            //alert("增加...");
            $('#addMajorWindow').window("open");
        }

        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要删除的专业！","warning");
            }else{
                //选中了专业,弹出确认框
                $.messager.confirm("删除确认","你确定要删除选中的专业吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的专业的id
                        for(var i=0;i<rows.length;i++){
                            var major = rows[i];//json对象
                            var id = major.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/major/deleteBatch?ids="+ids;
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
        },{
            field : 'major_name',
            title : '专业名称',
            width : 200,
            align : 'center'
        },{
            field : 'major_code',
            title : '专业编号',
            width : 200,
            align : 'center'
        }, {
            field : 'depart_name',
            title : '院系',
            width : 200,
            align : 'center'
        }] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 专业信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/major/pageQuery",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            // 添加专业窗口
            $('#addMajorWindow').window({
                title: '添加专业',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable:false
            });

            // 修改专业窗口
            $('#editMajorWindow').window({
                title: '修改专业',
                width: 400,
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
            //打开修改专业窗口
            $('#editMajorWindow').window("open");
            //使用form表单对象的load方法回显数据
            $("#editMajorForm").form("load", rowData);
        }

        $(function(){
            //为保存按钮绑定事件
            $("#save").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#addMajorForm").form("validate");
                if(v){
                    $("#addMajorForm").submit();
                }
            });

            //为修改按钮绑定事件
            $("#edit").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#editMajorForm").form("validate");
                if(v){
                    $("#editMajorForm").submit();
                }
            });

        });

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>
<div class="easyui-window" title="新增专业" id="addMajorWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addMajorForm" action="/major/add" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">专业信息</td>
                </tr>
                <tr>
                    <td>专业名称</td>
                    <td><input type="text" name="majorName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>专业编号</td>
                    <td><input type="text" name="majorCode" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>所属院系</td>
                    <td>
                        <input class="easyui-combobox" name="departId" required="true"
                               data-options="valueField:'id',textField:'departName',mode:'remote',url:'/major/departmentList'" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 修改专业窗口 -->
<div class="easyui-window" title="修改专业" id="editMajorWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editMajorForm" action="/major/edit" method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">专业信息</td>
                </tr>
                <tr>
                    <td>专业名称</td>
                    <td><input type="text" name="major_name" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>专业编号</td>
                    <td><input type="text" name="major_code" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>所属院系</td>
                    <td>
                        <input class="easyui-combobox" name="depart_id" required="true"
                               data-options="valueField:'id',textField:'departName',mode:'remote',url:'/major/departmentList'" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>