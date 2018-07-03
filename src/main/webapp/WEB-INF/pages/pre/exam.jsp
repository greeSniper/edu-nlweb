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
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/datepicker/WdatePicker.js"></script>
    <!-- 导入富文本编辑器kindeditor类库 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/kindeditor-4.1.10/themes/default/default.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/kindeditor.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/kindeditor-all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/lang/zh_CN.js"></script>

    <script type="text/javascript">

        //点击增加按钮
        function doAdd(){
            $('#addTestPaperWindow').window("open");
        }

        //批量作废
        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要作废的试卷！","warning");
            }else{
                //选中了试卷,弹出确认框
                $.messager.confirm("作废确认","你确定要作废选中的试卷吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的试卷的id
                        for(var i=0;i<rows.length;i++){
                            var e = rows[i];//json对象
                            var id = e.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/exam/deleteTestPaperBatch?ids="+ids;
                    }
                });
            }
        }

        //批量还原
        function doRestore(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要还原的试卷！","warning");
            }else{
                //选中了试卷,弹出确认框
                $.messager.confirm("还原确认","你确定要还原选中的试卷吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的试卷的id
                        for(var i=0;i<rows.length;i++){
                            var e = rows[i];//json对象
                            var id = e.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/exam/restoreTestPaperBatch?ids="+ids;
                    }
                });
            }
        }

        //点击出题按钮
        function doQuestion(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一张试卷操作！","warning");
            }else {
                //选中了一个试卷
                $('#addQuestionWindow').window('open');
                //为隐藏域设置试卷id
                $("input[name=tpId]").val($("input[name=id]:checked").val());

                //试卷id
                var id = $("input[name=id]:checked").val();

                //为指标点下拉选发送请求
                $('#combo').combobox('reload', "/exam/findZbbyqByTestPaperId?id="+id+"");
            }
        }

        //点击试卷题目按钮
        function doCheckQuestion(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一张试卷操作！","warning");
            }else {
                //选中了一个试卷
                var id = $("input[name=id]:checked").val(); //试卷id
                //发送请求跳转到试卷题目页面
                window.location.href = "/exam/toQuestionView?id="+id;
            }
        }

        //点击查看试卷按钮
        function doCheckTestPaper(){
            //获取当前数据表格所有选中的行，返回数组
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length != 1){
                //弹出提示
                $.messager.alert("提示信息","请选择一张试卷操作！","warning");
            }else {
                //选中了一个试卷
                var id = $("input[name=id]:checked").val(); //试卷id
                //发送请求跳转到查看试卷页面
                window.location.href = "/exam/toTestPaperView?id="+id;
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
            text : '作废',
            iconCls : 'icon-cancel',
            handler : doDelete
        }, {
            id : 'button-restore',
            text : '还原',
            iconCls : 'icon-cancel',
            handler : doRestore
        }, {
            id : 'button-question',
            text : '出题',
            iconCls : 'icon-add',
            handler : doQuestion
        }, {
            id : 'button-checkQuestion',
            text : '试卷题目',
            iconCls : 'icon-search',
            handler : doCheckQuestion
        }, {
            id : 'button-checkTestPaper',
            text : '查看试卷',
            iconCls : 'icon-search',
            handler : doCheckTestPaper
        }];
        
        // 定义列
        var columns = [ [ {
            field : 'id',
            checkbox : true,
        },{
            field : 'tpName',
            title : '试卷标题',
            width : 160,
            align : 'center'
        },{
            field : 'courseName',
            title : '课程',
            width : 160,
            align : 'center'
        }, {
            field : 'majorName',
            title : '专业',
            width : 160,
            align : 'center'
        }, {
            field : 'state',
            title : '状态',
            width : 100,
            align : 'center',
            formatter : function(data,row, index){
                if(data==1){
                    return "<font style='color:red;'>作废</font>";
                }else{
                    return "<font style='color:dodgerblue;'>使用</font>";
                }
            }
        }, {
            field : 'createTimeFormat',
            title : '出卷时间',
            width : 160,
            align : 'center'
        }, {
            field : 'teacherName',
            title : '出卷人',
            width : 100,
            align : 'center'
        }] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 添加试卷窗口
            $('#addTestPaperWindow').window({
                title: '添加试卷',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable:false
            });

            // 修改试卷窗口
            $('#editTestPaperWindow').window({
                title: '修改试卷',
                width: 400,
                modal: true, //遮罩效果
                shadow: true, //阴影效果
                closed: true, //关闭
                height: 200,
                resizable:false
            });

            // 出题窗口
            $('#addQuestionWindow').window({
                title: '为试卷添加题目',
                width: 900,
                modal: true,
                shadow: true,
                closed: true,
                height: 600,
                resizable:false
            });

            // 试卷表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/exam/pageQueryTestPaper",
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            //为保存按钮绑定事件
            $("#save").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#addTestPaperForm").form("validate");
                if(v){
                    $("#addTestPaperForm").submit();
                }
            });

            //为修改按钮绑定事件
            $("#edit").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#editTestPaperForm").form("validate");
                if(v){
                    $("#editTestPaperForm").submit();
                }
            });

            //为添加题目按钮绑定事件
            $("#addQuestion").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#addQuestionForm").form("validate");
                if(v){
                    $("#addQuestionForm").submit();
                }
            });

        });

        //数据表格绑定的双击行事件对应的函数
        function doDblClickRow(rowIndex, rowData){
            //打开修改试卷窗口
            $('#editTestPaperWindow').window("open");
            //使用form表单对象的load方法回显数据
            $("#editTestPaperForm").form("load", rowData);
        }

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>

<!-- 新增试卷窗口 -->
<div class="easyui-window" title="新增试卷" id="addTestPaperWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addTestPaperForm" action="/exam/addTestPaper" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">试卷信息</td>
                </tr>
                <tr>
                    <td>试卷标题</td>
                    <td><input type="text" name="tpName" class="easyui-validatebox" required="true"/></td>
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
                               data-options="valueField:'id',textField:'courseName',mode:'remote',url:'/exam/findCourseByTeacherId'" />                    </td>
                </tr>
                <tr>
                    <td>选择指标点权重矩阵</td>
                    <td>
                        <input class="easyui-combobox" name="mid" required="true"
                               data-options="valueField:'id',textField:'name',mode:'remote',url:'/byyq/findAllByyqmb'" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 修改试卷窗口 -->
<div class="easyui-window" title="修改试卷" id="editTestPaperWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editTestPaperForm" action="/exam/editTestPaper" method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">试卷信息</td>
                </tr>
                <tr>
                    <td>试卷标题</td>
                    <td><input type="text" name="tpName" class="easyui-validatebox" required="true"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<!-- 出题窗口 -->
<div class="easyui-window" title="为试卷添加题目" id="addQuestionWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="addQuestion" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true">添加</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addQuestionForm" action="/exam/addQuestion2" method="post">
            <input type="hidden" name="tpId">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">题目信息</td>
                </tr>
                <tr>
                    <td colspan="2">题目名称（例：期末考试试题第一大题）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题号</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="text" name="questionName" class="easyui-validatebox" required="true" value="期末考试试题第一大题"/>
                        <input type="text" name="orderNo" class="easyui-numberbox" data-options="min:0" style="width:50px;margin-left:60px;" required="true">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">题目类型</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="radio" name="questionType" value="0" class="easyui-validatebox"/>选择题
                        <input type="radio" name="questionType" value="1" class="easyui-validatebox"/>填空题
                        <input type="radio" name="questionType" value="2" class="easyui-validatebox"/>判断题
                        <input type="radio" name="questionType" value="3" class="easyui-validatebox"/>简答题
                        <input type="radio" name="questionType" value="4" class="easyui-validatebox"/>计算题
                        <input type="radio" name="questionType" value="5" class="easyui-validatebox"/>综合题
                        <input type="radio" name="questionType" value="6" class="easyui-validatebox"/>其他
                    </td>
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
                        <input id="combo" class="easyui-combobox" name="pointId"
                               data-options="valueField:'id',textField:'name',mode:'remote'" />
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea rows="3" style="width:400px;" id="questionContent" name="questionContent" class="easyui-validatebox" data-options="required:true,validType:'length[1,1000000]'" invalidMessage="最大长度不能超过1000000">
                        </textarea>
                    </td>
                    <script>
                        var editor;
                        $(function() {
                            editor = KindEditor.create('textarea[name="questionContent"]',{resizeType : 1,width:"100%",height:"200px",afterChange:function(){
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