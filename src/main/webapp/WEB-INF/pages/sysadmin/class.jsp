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
        function doSearch(){
            $('#searchWindow').window("open");
        }

        function doAdd(){
            $('#addClassWindow').window("open");
        }

        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要删除的班级！","warning");
            }else{
                //选中了班级,弹出确认框
                $.messager.confirm("删除确认","你确定要删除选中的班级吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的班级的id
                        for(var i=0;i<rows.length;i++){
                            var cc = rows[i];//json对象
                            var id = cc.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/cslgClass/deleteBatch?ids="+ids;
                    }
                });
            }
        }

        //点击班级选课按钮
        function doAssociations(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一个班级操作！","warning");
            }else{
                //选中了一个班级
                var id = rows[0].id;
                var majorId = rows[0].majorId;
                var className = rows[0].className;
                location.href = "/cslgClass/selectCourse?id="+id+"&className="+className+"&majorId="+majorId;
            }
        }

        //点击已选课程按钮
        function doHasSelect(){
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一个班级操作！","warning");
            }else{
                //选中了一个班级
                var id = rows[0].id;
                location.href = "/cslgClass/toClassCourseHasSelectedView?id="+id;
            }
        }

        //点击关联指标点权重矩阵按钮
        function doAssociationByyqmb(){
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一个班级操作！","warning");
            }else{
                //选中了一个班级
                $('#associationByyqmbWindow').window("open");
                var id = rows[0].id;
                $("input[name=classId]").val(id);
            }
        }

        //点击解除关联指标点权重矩阵按钮
        function doDelAssociationByyqmb(){
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一个班级操作！","warning");
            }else{
                //选中了一个班级
                $('#delAssociationByyqmbWindow').window("open");
                var id = rows[0].id;
                $("input[name=classId]").val(id);
                $.post("/byyq/findByyqmbByClassId", {"classId":id}, function(data){
                    $("#byyqmbId").val(data.id);
                    $("#byyqmbName").val(data.name);
                }, "json");
            }
        }

        //工具栏
        var toolbar = [{
            id : 'button-search',
            text : '查询',
            iconCls : 'icon-search',
            handler : doSearch
        }, {
            id : 'button-add',
            text : '增加',
            iconCls : 'icon-add',
            handler : doAdd
        }, {
            id : 'button-delete',
            text : '删除',
            iconCls : 'icon-cancel',
            handler : doDelete
        }, {
            id : 'button-association',
            text : '班级选课',
            iconCls : 'icon-sum',
            handler : doAssociations
        }, {
            id : 'button-association',
            text : '已选课程',
            iconCls : 'icon-sum',
            handler : doHasSelect
        }, {
            id : 'button-associationbyyqmb',
            text : '关联指标点权重矩阵',
            iconCls : 'icon-sum',
            handler : doAssociationByyqmb
        }, {
            id : 'button-delassociationbyyqmb',
            text : '解除关联指标点权重矩阵',
            iconCls : 'icon-cancel',
            handler : doDelAssociationByyqmb
        }];
        // 定义列
        var columns = [ [ {
            field : 'id',
            checkbox : true,
        },{
            field : 'className',
            title : '班级名称',
            width : 200,
            align : 'center'
        },{
            field : 'classCode',
            title : '班级编号',
            width : 200,
            align : 'center'
        }, {
            field : 'majorName',
            title : '专业',
            width : 200,
            align : 'center'
        }, {
            field : 'grade',
            title : '年级',
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

            // 查询班级
            $('#searchWindow').window({
                title: '查询班级',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable:false
            });

            // 班级信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/cslgClass/pageQuery",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            // 添加班级窗口
            $('#addClassWindow').window({
                title: '添加班级',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable:false
            });

            // 修改班级窗口
            $('#editClassWindow').window({
                title: '修改班级',
                width: 400,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 400,
                resizable:false
            });

            // 班级关联指标点权重矩阵窗口
            $('#associationByyqmbWindow').window({
                title: '班级关联指标点权重矩阵',
                width: 400,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 160,
                resizable:false
            });

            // 解除班级关联指标点权重矩阵窗口
            $('#delAssociationByyqmbWindow').window({
                title: '解除关联',
                width: 400,
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
            //打开修改班级窗口
            $('#editClassWindow').window("open");
            //使用form表单对象的load方法回显数据
            $("#editClassForm").form("load", rowData);
        }

        $(function(){
            //为保存按钮绑定事件
            $("#save").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#addClassForm").form("validate");
                if(v){
                    $("#addClassForm").submit();
                }
            });

            //为修改按钮绑定事件
            $("#edit").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#editClassForm").form("validate");
                if(v){
                    $("#editClassForm").submit();
                }
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

            //为关联按钮绑定事件
            $("#associationByyqmbBtn").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#associationByyqmbForm").form("validate");
                if(v){
                    $("#associationByyqmbForm").submit();
                }
            });

            //为解除关联按钮绑定事件
            $("#delAssociationByyqmbBtn").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#delAssociationByyqmbForm").form("validate");
                if(v){
                    $("#delAssociationByyqmbForm").submit();
                }
            });

        });

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>
<div class="easyui-window" title="新增班级" id="addClassWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addClassForm" action="/cslgClass/add" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">班级信息</td>
                </tr>
                <tr>
                    <td>所属专业</td>
                    <td>
                        <input class="easyui-combobox" name="majorId" required="true"
                               data-options="valueField:'id',textField:'majorName',mode:'remote',url:'/cslgClass/majorList'" />
                    </td>
                </tr>
                <tr>
                    <td>班级名称</td>
                    <td><input type="text" name="className" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>班级编号</td>
                    <td><input type="text" name="classCode" class="easyui-validatebox" required="true"/></td>
                </tr>
                <td>年级</td>
                <td>
                    <input type="text" style="width:90px;" name="grade" onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy'});" />
                </td>
            </table>
        </form>
    </div>
</div>

<!-- 修改班级窗口 -->
<div class="easyui-window" title="修改班级" id="editClassWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editClassForm" action="/cslgClass/edit" method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">班级信息</td>
                </tr>
                <tr>
                    <td>所属专业</td>
                    <td>
                        <input class="easyui-combobox" name="majorId" required="true"
                               data-options="valueField:'id',textField:'majorName',mode:'remote',url:'/cslgClass/majorList'" />
                    </td>
                </tr>
                <tr>
                    <td>班级名称</td>
                    <td><input type="text" name="className" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>班级编号</td>
                    <td><input type="text" name="classCode" class="easyui-validatebox" required="true"/></td>
                </tr>
                <td>年级</td>
                <td>
                    <input type="text" style="width:90px;" name="grade" onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy'});" />
                </td>
            </table>
        </form>
    </div>
</div>

<!-- 查询班级 -->
<div class="easyui-window" title="查询班级窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="searchForm">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">查询条件</td>
                </tr>
                <tr>
                    <td>所属专业</td>
                    <td><input type="text" name="majorName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>班级名称</td>
                    <td><input type="text" name="className" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>班级编号</td>
                    <td><input type="text" name="classCode" class="easyui-validatebox" required="true"/></td>
                </tr>
                <td>年级</td>
                <td>
                    <input type="text" style="width:90px;" name="grade" onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy'});" />
                </td>
                <tr>
                    <td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 班级关联指标点权重矩阵窗口 -->
<div class="easyui-window" title="班级关联指标点权重矩阵窗口" id="associationByyqmbWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="associationByyqmbForm" action="/cslgClass/associationByyqmb" method="post">
            <input type="hidden" name="classId" />
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">选择指标点权重矩阵</td>
                </tr>
                <tr>
                    <td>
                        <input class="easyui-combobox" name="mid" required="true"
                               data-options="valueField:'id',textField:'name',mode:'remote',url:'/byyq/findAllByyqmb'" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><a id="associationByyqmbBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">关联</a> </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 解除班级关联指标点权重矩阵窗口 -->
<div class="easyui-window" title="解除关联窗口" id="delAssociationByyqmbWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="delAssociationByyqmbForm" action="/cslgClass/delAssociationByyqmb" method="post">
            <input type="hidden" name="classId" />
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">指标点权重矩阵</td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" id="byyqmbId" name="mid" />
                        <input type="text" id="byyqmbName" class="easyui-validatebox" readonly/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><a id="delAssociationByyqmbBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">解除关联</a> </td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>