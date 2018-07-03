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
            $('#addTeacherWindow').window("open");
        }

        //设置管理员
        function doSave(){
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要设为管理员的教师！","warning");
            }else{
                //选中了教师,弹出确认框
                $.messager.confirm("设置确认","你确定要设置选中的教师吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的教师的id
                        for(var i=0;i<rows.length;i++){
                            var t = rows[i];//json对象
                            var id = t.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/teacher/saveBatch?ids="+ids;
                    }
                });
            }
        }

        //还原为普通用户
        function doRestore() {
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要还原的教师！","warning");
            }else{
                //选中了教师,弹出确认框
                $.messager.confirm("设置确认","你确定要还原选中的教师吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的教师的id
                        for(var i=0;i<rows.length;i++){
                            var t = rows[i];//json对象
                            var id = t.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/teacher/restoreBatch?ids="+ids;
                    }
                });
            }
        }

        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要删除的教师！","warning");
            }else{
                //选中了教师,弹出确认框
                $.messager.confirm("删除确认","你确定要删除选中的教师吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的教师的id
                        for(var i=0;i<rows.length;i++){
                            var t = rows[i];//json对象
                            var id = t.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/teacher/deleteBatch?ids="+ids;
                    }
                });
            }
        }

        //点击教师列表关联课程按钮
        function doAssociations(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一个教师操作！","warning");
            }else{
                //选中了一个教师
                $('#courseWindow').window('open');
                //清理下拉框
                $("#noassociationSelect").empty();
                $("#associationSelect").empty();
                //发送ajax请求，请求教师Action，在教师Action中获取课程数据
                var url_1 = "/teacher/findCourseToAssociation";
                var teacherId = rows[0].id;
                $.post(url_1, {"id":teacherId}, function(data){
                    //遍历json数组
                    for(var i=0;i<data.length;i++){
                        var id = data[i].id;
                        var name = data[i].courseName;
                        $("#noassociationSelect").append("<option value='"+id+"'>"+name+"</option>");
                    }
                }, 'json');

                //发送ajax请求，请求教师Action，在教师Action中获取课程数据
                var url_2 = "/teacher/findCourseByTeacherId";
                $.post(url_2, {"id":teacherId}, function(data){
                    //遍历json数组
                    for(var i=0;i<data.length;i++){
                        var id = data[i].id;
                        var name = data[i].courseName;
                        $("#associationSelect").append("<option value='"+id+"'>"+name+"</option>");
                    }
                }, 'json');
            }
        }

        //工具栏
        var toolbar = [{
            id : 'button-add',
            text : '增加',
            iconCls : 'icon-add',
            handler : doAdd
        }, {
            id : 'button-save',
            text : '设为管理员',
            iconCls : 'icon-save',
            handler : doSave
        }, {
            id : 'button-restore',
            text : '还原为普通用户',
            iconCls : 'icon-cancel',
            handler : doRestore
        }, {
            id : 'button-delete',
            text : '删除',
            iconCls : 'icon-cancel',
            handler : doDelete
        }, {
            id : 'button-association',
            text : '关联课程',
            iconCls : 'icon-sum',
            handler : doAssociations
        }];
        // 定义列
        var columns = [ [ {
            field : 'id',
            checkbox : true,
        },{
            field : 'username',
            title : '工号',
            width : 120,
            align : 'center'
        },{
            field : 'teacherName',
            title : '姓名',
            width : 120,
            align : 'center'
        }, {
            field : 'gender',
            title : '性别',
            width : 120,
            align : 'center'
        }, {
            field : 'type',
            title : '是否为管理员',
            width : 120,
            align : 'center',
            formatter : function(data,row, index){
                if(data==0){
                    return "是";
                }else{
                    return "否";
                }
            }
        }, {
            field : 'telephone',
            title : '电话',
            width : 120,
            align : 'center'
        }, {
            field : 'email',
            title : '邮箱',
            width : 200,
            align : 'center'
        }, {
            field : 'departName',
            title : '院系',
            width : 200,
            align : 'center'
        }] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 教师信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/teacher/pageQuery",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            // 添加教师窗口
            $('#addTeacherWindow').window({
                title: '添加教师',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 450,
                resizable:false
            });

            // 修改教师窗口
            $('#editTeacherWindow').window({
                title: '修改教师',
                width: 400,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 450,
                resizable:false
            });

        });

        //数据表格绑定的双击行事件对应的函数
        function doDblClickRow(rowIndex, rowData){
            //alert("双击表格数据...");
            //打开修改教师窗口
            $('#editTeacherWindow').window("open");
            //使用form表单对象的load方法回显数据
            $("#editTeacherForm").form("load", rowData);
        }

        $(function(){
            //为保存按钮绑定事件
            $("#save").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#addTeacherForm").form("validate");
                if(v){
                    $("#addTeacherForm").submit();
                }
            });

            //为修改按钮绑定事件
            $("#edit").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#editTeacherForm").form("validate");
                if(v){
                    $("#editTeacherForm").submit();
                }
            });

            var reg = /^1[3|4|5|7|8][0-9]{9}$/;
            //扩展手机号校验规则
            $.extend($.fn.validatebox.defaults.rules, {
                telephone: {
                    validator: function(value, param){
                        return reg.test(value);
                    },
                    message: '手机号输入有误！'
                }
            });

            //为左右移动按钮绑定事件
            $("#toRight").click(function(){
                $("#associationSelect").append($("#noassociationSelect option:selected"));
            });
            $("#toLeft").click(function(){
                $("#noassociationSelect").append($("#associationSelect option:selected"));
            });

            //为关联客户按钮绑定事件
            $("#associationBtn").click(function(){
                var rows = $("#grid").datagrid("getSelections");
                var id = rows[0].id;
                //为隐藏域（存放定区id）赋值
                $("input[name=id]").val(id);
                //提交表单之前，需要将右侧下拉框中所有的选项选中,为option添加一个selected属性
                $("#associationSelect option").attr("selected","selected");
                $("#courseForm").submit();
            });

        });

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>
<div class="easyui-window" title="新增教师" id="addTeacherWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addTeacherForm" action="/teacher/add" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">教师信息</td>
                </tr>
                <tr>
                    <td>所属院系</td>
                    <td>
                        <input class="easyui-combobox" name="departId" required="true"
                               data-options="valueField:'id',textField:'departName',mode:'remote',url:'/teacher/departmentList'" />
                    </td>
                </tr>
                <tr>
                    <td>工号</td>
                    <td><input type="text" name="username" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>姓名</td>
                    <td><input type="text" name="teacherName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>性别</td>
                    <td>
                        <input type="radio" name="gender" class="easyui-validatebox" value="男"/>男
                        <input type="radio" name="gender" class="easyui-validatebox" value="女"/>女
                    </td>
                </tr>
                <tr>
                    <td>职称</td>
                    <td><input type="text" name="positional" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td><input type="text" name="telephone" data-options="validType:'telephone'" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>邮箱</td>
                    <td><input type="text" name="email" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>QQ</td>
                    <td><input type="text" name="qq" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>常住地址</td>
                    <td><input type="text" name="address" class="easyui-validatebox"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 修改教师窗口 -->
<div class="easyui-window" title="修改教师" id="editTeacherWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editTeacherForm" action="/teacher/edit" method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">教师信息</td>
                </tr>
                <tr>
                    <td>所属院系</td>
                    <td>
                        <input class="easyui-combobox" name="departId" required="true"
                               data-options="valueField:'id',textField:'departName',mode:'remote',url:'/teacher/departmentList'" />
                    </td>
                </tr>
                <tr>
                    <td>工号</td>
                    <td><input type="text" name="username" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>姓名</td>
                    <td><input type="text" name="teacherName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>性别</td>
                    <td>
                        <input type="radio" name="gender" class="easyui-validatebox" value="男"/>男
                        <input type="radio" name="gender" class="easyui-validatebox" value="女"/>女
                    </td>
                </tr>
                <tr>
                    <td>职称</td>
                    <td><input type="text" name="positional" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td><input type="text" name="telephone" data-options="validType:'telephone'" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>邮箱</td>
                    <td><input type="text" name="email" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>QQ</td>
                    <td><input type="text" name="qq" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>常住地址</td>
                    <td><input type="text" name="address" class="easyui-validatebox"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 关联课程窗口 -->
<div modal=true class="easyui-window" title="关联课程窗口" id="courseWindow" collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 400px;height: 300px;">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="courseForm" action="${pageContext.request.contextPath }/teacher/assignCourseToTeacher" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="3">关联课程</td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" name="id" id="teacherId" />
                        <select id="noassociationSelect" multiple="multiple" size="10"></select>
                    </td>
                    <td>
                        <input type="button" value="》》" id="toRight"><br/>
                        <input type="button" value="《《" id="toLeft">
                    </td>
                    <td>
                        <select id="associationSelect" name="courseIds" multiple="multiple" size="10"></select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><a id="associationBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联课程</a> </td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>