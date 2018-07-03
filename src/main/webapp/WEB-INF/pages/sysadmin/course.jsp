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
            $('#addcourseWindow').window("open");
        }

        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要删除的课程！","warning");
            }else{
                //选中了课程,弹出确认框
                $.messager.confirm("删除确认","你确定要删除选中的课程吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的课程的id
                        for(var i=0;i<rows.length;i++){
                            var c = rows[i];//json对象
                            var id = c.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/course/deleteBatch?ids="+ids;
                    }
                });
            }
        }

        //点击关联指标点按钮
        function doAssociations(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一个课程操作！","warning");
            }else{
                //选中了一个课程
                $('#associationPointWindow').window('open');
            }
        }

        //点击查询指标点按钮
        function doSearth(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一个课程操作！","warning");
            }else{
                //选中了一个课程
                var id = rows[0].id;
                window.location.href = "/course/toCoursePointView?id="+id;
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
            field : 'courseCode',
            title : '课程编号',
            width : 120,
            align : 'center'
        },{
            field : 'courseName',
            title : '课程名称',
            width : 200,
            align : 'center'
        },{
            field : 'courseEnglishname',
            title : '课程英文名',
            width : 200,
            align : 'center'
        },{
            field : 'courseDesc',
            title : '课程描述',
            width : 400,
            align : 'center',
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
                url : "/course/pageQuery",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            // 添加课程窗口
            $('#addcourseWindow').window({
                title: '添加课程',
                width: 600,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable:false
            });

            // 修改课程窗口
            $('#editcourseWindow').window({
                title: '修改课程',
                width: 600,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 400,
                resizable:false
            });

            // 关联指标点窗口
            $('#associationPointWindow').window({
                title: '课程关联指标点',
                width: 500,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 160,
                resizable:false
            });

            // 关联指标点next窗口
            $('#associationPointNextWindow').window({
                title: '课程关联指标点',
                width: 500,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 160,
                resizable:false
            });

        });

        //数据表格绑定的双击行事件对应的函数
        function doDblClickRow(rowIndex, rowData){
            //alert("双击表格数据...");
            //打开修改课程窗口
            $('#editcourseWindow').window("open");
            //使用form表单对象的load方法回显数据
            $("#editcourseForm").form("load", rowData);
        }

        $(function(){
            //为保存按钮绑定事件
            $("#save").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#addcourseForm").form("validate");
                if(v){
                    $("#addcourseForm").submit();
                }
            });

            //为修改按钮绑定事件
            $("#edit").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#editcourseForm").form("validate");
                if(v){
                    $("#editcourseForm").submit();
                }
            });

            //课程关联指标点点击下一步
            $("#associate").click(function(){
                //表单校验
                var v = $("#associationForm").form("validate");
                if(v){
                    //清空checkbox
                    $("#point_checkbox").empty();

                    var rows = $("#grid").datagrid("getSelections");
                    $("input[name=courseId]").val(rows[0].id);
                    var courseId = rows[0].id;

                    //发送ajax请求查询所选专业的指标点
                    var majorId = $("input[name=majorId]").val();
                    $.post("/point/findPointByMajorId", {"majorId":majorId,"courseId":courseId}, function(data){
                        //遍历json数组
                        for(var i=0;i<data.length;i++){
                            var pid = data[i].id;
                            var pname = data[i].pointName;
                            $("#point_checkbox").append("<input type='checkbox' name='pointIds' value='"+pid+"' />"+pname);
                        }
                    }, 'json');

                    //设置专业id和课程id
                    $("input[name=majorId]").val(majorId);

                    //关闭上一步窗口
                    $('#associationPointWindow').window('close');
                    //打开下一步窗口
                    $('#associationPointNextWindow').window('open');
                }
            });

            //点击关联按钮
            $("#associateNext").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#associationPointForm").form("validate");
                if(v){
                    $("#associationPointForm").submit();
                }
            });

        });

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>
<div class="easyui-window" title="新增课程" id="addcourseWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addcourseForm" action="/course/add" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">课程信息</td>
                </tr>
                <tr>
                    <td>课程编号</td>
                    <td><input type="text" name="courseCode" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>课程名称</td>
                    <td><input type="text" name="courseName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>课程英文名</td>
                    <td><input type="text" name="courseEnglishname" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>课程简介</td>
                    <td colspan="3">
                        <textarea rows="5" cols="50" type="text" class="easyui-validatebox" name="courseDesc"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>排序号</td>
                    <td><input type="text" name="orderNo" class="easyui-validatebox" required="true"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 修改课程窗口 -->
<div class="easyui-window" title="修改课程" id="editcourseWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editcourseForm" action="/course/edit" method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">课程信息</td>
                </tr>
                <tr>
                    <td>课程编号</td>
                    <td><input type="text" name="courseCode" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>课程名称</td>
                    <td><input type="text" name="courseName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>课程英文名</td>
                    <td><input type="text" name="courseEnglishname" class="easyui-validatebox"/></td>
                </tr>
                <tr>
                    <td>课程简介</td>
                    <td colspan="3">
                        <textarea rows="5" cols="50" type="text" class="easyui-validatebox" name="courseDesc"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 关联指标点窗口 -->
<div class="easyui-window" title="选择专业" id="associationPointWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="associate" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >下一步</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="associationForm">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">关联指标点</td>
                </tr>
                <tr>
                    <td>选择专业</td>
                    <td>
                        <input class="easyui-combobox" name="majorId" required="true"
                               data-options="valueField:'id',textField:'majorName',mode:'remote',url:'/cslgClass/majorList'" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 关联指标点下一步窗口 -->
<div class="easyui-window" title="课程关联指标点" id="associationPointNextWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="associateNext" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >关联</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="associationPointForm" action="/course/associate" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">关联指标点</td>
                </tr>
                <tr>
                    <td>指标点</td>
                    <td id="point_checkbox">

                    </td>
                    <input type="hidden" name="majorId" id="majorId" />
                    <input type="hidden" name="courseId" id="courseId" />
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>