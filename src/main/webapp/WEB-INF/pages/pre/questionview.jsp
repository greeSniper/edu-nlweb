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

        //所选试卷id
        var tpId = "${tpId}";

        //批量删除
        function doDelete(){
            //获取数据表格中所有选中的行，返回数组对象
            var rows = $("#grid").datagrid("getSelections");
            if(rows.length == 0){
                //没有选中记录，弹出提示
                $.messager.alert("提示信息","请选择需要删除的题目！","warning");
            }else{
                //选中了题目,弹出确认框
                $.messager.confirm("删除确认","你确定要删除选中的题目吗？",function(r){
                    if(r){
                        var array = new Array();
                        //确定,发送请求
                        //获取所有选中的题目的id
                        for(var i=0;i<rows.length;i++){
                            var q = rows[i];//json对象
                            var id = q.id;
                            array.push(id);
                        }
                        var ids = array.join(",");//1,2,3,4,5
                        location.href = "/exam/deleteQuestionBatch?ids="+ids+"&tpId="+tpId;
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
            text : '删除',
            iconCls : 'icon-cancel',
            handler : doDelete
        }];

        // 定义列
        var columns = [ [ {
            field : 'id',
            checkbox : true,
        },{
            field : 'questionName',
            title : '题目名称',
            width : 200,
            align : 'center'
        },{
            field : 'questionType',
            title : '题目类型',
            width : 100,
            align : 'center',
            formatter : function(data,row, index){
                //0:选择题, 1:填空题, 2:判断题, 3:简答题, 4:计算题, 5:综合题, 6:其他
                if(data=="0"){
                    return "选择题"
                }else if(data=="1"){
                    return "填空题";
                }else if(data=="2"){
                    return "判断题";
                }else if(data=="3"){
                    return "简答题";
                }else if(data=="4"){
                    return "计算题";
                }else if(data=="5"){
                    return "综合题";
                }else if(data=="6"){
                    return "其他";
                }
            }
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
            field : 'tpName',
            title : '所属试卷',
            width : 200,
            align : 'center'
        }] ];

        $(function(){
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            // 修改题目窗口
            $('#editQuestionWindow').window({
                title: '修改题目',
                width: 900,
                modal: true,
                shadow: true,
                closed: true,
                height: 600,
                resizable:false
            });

            // 题目信息表格
            $('#grid').datagrid( {
                iconCls : 'icon-forward',
                fit : true,
                border : false,
                rownumbers : true,
                striped : true,
                pageList: [30,50,100],
                pagination : true,
                toolbar : toolbar,
                url : "/exam/pageQuestionQuery?tpId="+tpId,
                idField : 'id',
                columns : columns,
                onDblClickRow : doDblClickRow
            });

            //为修改按钮绑定事件
            $("#edit").click(function(){
                //表单校验，如果通过，提交表单
                var v = $("#editQuestionForm").form("validate");
                if(v){
                    $("input[name=tpId]").val(tpId);
                    $("#editQuestionForm").submit();
                }
            });

        });

        //数据表格绑定的双击行事件对应的函数
        function doDblClickRow(rowIndex, rowData){
            //为指标点下拉选发送请求
            //$('#combo').combobox('reload', "/exam/findPointByTestPaperId?id="+tpId+"");
            $('#combo').combobox('reload', "/exam/findZbbyqByTestPaperId?id="+tpId+"");

            //打开修改题目窗口
            $('#editQuestionWindow').window("open");
            //使用form表单对象的load方法回显数据
            $("#editQuestionForm").form("load", rowData);

            //设置富文本编辑器回显
            var rows = $("#grid").datagrid("getSelections");
            var questionContent = rows[0].questionContent;
            KindEditor.html('#questionContent',questionContent);
        }

    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>

<!-- 修改题目窗口 -->
<div class="easyui-window" title="对题目进行修改" id="editQuestionWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editQuestionForm" action="/exam/editQuestion2" method="post">
            <input type="hidden" name="id">
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
                        <input type="text" name="questionName" class="easyui-validatebox" required="true"/>
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