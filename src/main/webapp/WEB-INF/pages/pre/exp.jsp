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

        //点击增加按钮
        function doAdd(){
            $('#addExpWindow').window("open");
        }

        //批量删除
        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要删除的实验！","warning");
            }else{
                //选中了实验,弹出确认框
                $.messager.confirm("删除确认","你确定要删除选中的实验吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的实验的id
                        for(var i=0;i<rows.length;i++){
                            var exp = rows[i];//json对象
                            var id = exp.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        //location.href = "/exp/deleteBatch?ids="+id;
                        location.href = "/exp/deleteBatch2?ids="+id;
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
                $.messager.alert("提示信息","请选择一个实验操作！","warning");
            }else{
                //选中了一个课程
                //设置实验id
                var rows = $("#grid").datagrid("getSelections");
                var id = rows[0].id;
                $("input[name=id]").val(id);

                var courseId = rows[0].courseId;
                //为指标点下拉选发送请求
                //$('#combo2').combobox('reload', "/exp/findPointByCourseId?courseId="+courseId+"");
                $('#combo2').combobox('reload', "/exp/findZbbyqByCourseId?courseId="+courseId+"");

                //设置富文本编辑器回显
                var expContent = rows[0].expContent;
                KindEditor.html('#expContent3',expContent);

                $('#associationPointWindow').window('open');
            }
        }

        //工具栏
        var toolbar = [{
            id : 'button-add',
            text : '增加',
            iconCls : 'icon-add',
            handler : doAdd
        }, {
            id : 'button-association',
            text : '关联指标点',
            iconCls : 'icon-sum',
            handler : doAssociations
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
            field : 'expName',
            title : '实验名称',
            width : 200,
            align : 'center'
        }, {
            field : 'score',
            title : '分值',
            width : 100,
            align : 'center'
        }, {
            field : 'pointName',
            title : '对应指标点',
            width : 100,
            align : 'center'
        }, {
            field : 'courseName',
            title : '课程',
            width : 200,
            align : 'center'
        }, {
            field : 'majorName',
            title : '专业',
            width : 200,
            align : 'center'
        }, {
            field : 'teacherName',
            title : '实验负责人',
            width : 120,
            align : 'center'
        }] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 出题窗口
            $('#addExpWindow').window({
                title: '添加实验',
                width: 900,
                modal: true,
                shadow: true,
                closed: true,
                height: 600,
                resizable:false
            });
            
            // 修改实验窗口
            $('#editExpWindow').window({
                title: '修改实验',
                width: 900,
                modal: true,
                shadow: true,
                closed: true,
                height: 600,
                resizable:false
            });

            // 关联指标点窗口
            $('#associationPointWindow').window({
                title: '关联指标点',
                width: 900,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 500,
                resizable:false
            });

            // 实验信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/exp/pageQuery",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            //为保存按钮绑定事件
            $("#save").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#addExpForm").form("validate");
                if(v){
                    $("#addExpForm").submit();
                }
            });
            
            //为修改按钮绑定事件
            $("#edit").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#editExpForm").form("validate");
                if(v){
                    $("#editExpForm").submit();
                }
            });

            //为关联按钮绑定事件
            $("#associate").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#associationPointForm").form("validate");
                if(v){
                    $("#associationPointForm").submit();
                }
            });

        });

        //数据表格绑定的双击行事件对应的函数
        function doDblClickRow(rowIndex, rowData){
            var rows = $("#grid").datagrid("getSelections");
            var courseId = rows[0].courseId;
            //为指标点下拉选发送请求
            //$('#combo').combobox('reload', "/exp/findPointByCourseId?courseId="+courseId+"");
            $('#combo').combobox('reload', "/exp/findZbbyqByCourseId?courseId="+courseId+"");

            //打开修改题目窗口
            $('#editExpWindow').window("open");
            //使用form表单对象的load方法回显数据
            $("#editExpForm").form("load", rowData);

            //设置富文本编辑器回显
            var expContent = rows[0].expContent;
            KindEditor.html('#expContent2',expContent);
        }

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>

<!-- 新增实验窗口 -->
<div class="easyui-window" title="新增实验" id="addExpWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addExpForm" action="/exp/add2" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">实验信息</td>
                </tr>
                <tr>
                    <td>实验名称</td>
                    <td><input type="text" name="expName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>选择专业</td>
                    <td>
                        <input class="easyui-combobox" name="majorId" required="true"
                               data-options="valueField:'id',textField:'majorName',mode:'remote',url:'/exam/findMajorByDepartId'" />
                    </td>
                </tr>
                <tr>
                    <td>选择课程</td>
                    <td>
                        <input class="easyui-combobox" name="courseId" required="true"
                               data-options="valueField:'id',textField:'courseName',mode:'remote',url:'/exam/findCourseByTeacherId'" />
                    </td>
                </tr>
                <tr>
                    <td>选择指标点权重矩阵</td>
                    <td>
                        <input class="easyui-combobox" name="mid" required="true"
                               data-options="valueField:'id',textField:'name',mode:'remote',url:'/byyq/findAllByyqmb'" />
                    </td>
                </tr>
                <tr>
                    <td>分值</td>
                    <td><input type="text" name="score" class="easyui-numberbox" min="0" max="100" precision="1" required="true"/></td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea rows="3" style="width:400px;" id="expContent" name="expContent" class="easyui-validatebox" data-options="required:true,validType:'length[1,1000000]'" invalidMessage="最大长度不能超过1000000">
                        </textarea>
                    </td>
                </tr>
                <script>
                    var editor;
                    $(function() {
                        editor = KindEditor.create('textarea[name="expContent"]',{resizeType : 1,width:"100%",height:"200px",afterChange:function(){
                            this.sync();
                        },afterBlur:function(){
                            this.sync();
                        }});
                    });
                </script>
            </table>
        </form>
    </div>
</div>

<!-- 修改实验窗口 -->
<div class="easyui-window" title="对实验进行修改" id="editExpWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editExpForm" action="/exp/edit2" method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">实验信息</td>
                </tr>
                <tr>
                    <td>实验名称</td>
                    <td><input type="text" name="expName" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td>分值</td>
                    <td><input type="text" name="score" class="easyui-numberbox" min="0" max="100" precision="1" required="true"/></td>
                </tr>
                <tr>
                    <td>权重</td>
                    <td><input type="text" name="quanzhong" class="easyui-numberbox" min="0" max="1" precision="2" required="true"/></td>
                </tr>
                <tr>
                    <td>选择指标点</td>
                    <td>
                        <input id="combo" class="easyui-combobox" name="pointId" data-options="valueField:'id',textField:'name',mode:'remote'" />
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea rows="3" style="width:400px;" id="expContent2" name="expContent2" class="easyui-validatebox" data-options="required:true,validType:'length[1,1000000]'" invalidMessage="最大长度不能超过1000000">
                        </textarea>
                    </td>
                    <script>
                        var editor;
                        $(function() {
                            editor = KindEditor.create('textarea[name="expContent2"]',{resizeType : 1,width:"100%",height:"200px",afterChange:function(){
                                this.sync();
                            },afterBlur:function(){
                                this.sync();
                            }});
                        });
                    </script>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 关联指标点窗口 -->
<div class="easyui-window" title="关联指标点" id="associationPointWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="associate" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">关联</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="associationPointForm" action="/exp/associate2" method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">关联指标点</td>
                </tr>
                <tr>
                    <td>指标点</td>
                    <td>
                        <input id="combo2" class="easyui-combobox" name="pointId" data-options="valueField:'id',textField:'name',mode:'remote'" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>权重</td>
                    <td><input type="text" name="quanzhong" class="easyui-numberbox" min="0" max="1" precision="2" required="true"/></td>
                </tr>
                <tr>
                    <td>实验内容</td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea rows="3" style="width:400px;" id="expContent3" name="expContent3" class="easyui-validatebox" data-options="required:true,validType:'length[1,1000000]'" invalidMessage="最大长度不能超过1000000">
                        </textarea>
                    </td>
                    <script>
                        var editor;
                        $(function() {
                            editor = KindEditor.create('textarea[name="expContent3"]',{resizeType : 1,width:"100%",height:"200px",afterChange:function(){
                                this.sync();
                            },afterBlur:function(){
                                this.sync();
                            }});
                        });
                    </script>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>