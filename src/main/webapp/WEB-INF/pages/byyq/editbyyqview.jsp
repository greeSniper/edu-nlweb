<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

    <style type="text/css">
        table {
            border-collapse: collapse;
            padding: 0px;
            font-size: 12px;
            font-weight: bold;
            table-layout: fixed;
        }
        td {
            word-break: break-all;
            word-wrap:break-word;
            text-align: center;
        }
    </style>

    <script type="text/javascript">

        if(window.self != window.top){
            window.top.location = window.location;
        }
//        var byyqVo = [{
//            "name":"毕业要求1",
//            "size":4,
//            "point":[{
//                "id":"1",
//                "name":"1.1"
//            }, {
//                "id":"2",
//                "name":"1.2"
//            }, {"id":"3",
//                "name":"1.3"
//            }, {"id":"4",
//                "name":"1.4"
//            }]
//        }, {
//            "name":"毕业要求2",
//            "size":4,
//            "point":[{
//                "id":"5",
//                "name":"2.1"
//            }, {
//                "id":"6",
//                "name":"2.2"
//            }, {"id":"7",
//                "name":"2.3"
//            }, {"id":"8",
//                "name":"2.4"
//            }]
//        }];
//
//        var courseVo = [{
//            "id":"1",
//            "orderNo":1,
//            "courseName":"高等数学",
//            "qzList":[{
//                "id":"1",
//                "quanzhong":0.2
//            }, {
//                "id":"7",
//                "quanzhong":0.3
//            }]
//        }, {
//            "id":"2",
//            "orderNo":2,
//            "courseName":"线性代数",
//            "qzList":[]
//        }, {
//            "id":"3",
//            "orderNo":3,
//            "courseName":"概率统计",
//            "qzList":[{
//                "id":"1",
//                "quanzhong":0.2
//            }]
//        }];

        //毕业要求模板id
        var mid = '${mid}';
        //毕业要求模板名称
        var name = '${mname}';

        $(function(){
            //加载页面时发送ajax请求获取编辑毕业要求模板数据
            $.ajax({
                url:"/byyq/getEditByyqmbData?mid="+mid,
                type:"post",
                data:{},
                dataType:"json",
                success:function(data){
                    var byyqVo = data.byyqVo;
                    var courseVo = data.courseVo;

                    //为表格提供数据
                    $("#title").append("<th width='1%' rowspan='2'>课程序号</th><th width='6%' rowspan='2'>课程\\毕业要求</th>");

                    var size = 0;
                    var tr = "<tr style='height: 20px;'>";
                    var trl = "<tr style='height: 20px;'><th width='1%'>课程序号</th><th width='6%' rowspan='2'>课程\\毕业要求</th>";
                    for(var i=0; i<byyqVo.length; i++) {
                        var fbyyq = byyqVo[i];
                        $("#title").append("<td style='text-align:center;' colspan='"+fbyyq.size+"'>"+fbyyq.name+"</td>");
                        for(var j=0; j<fbyyq.point.length; j++) {
                            var point = fbyyq.point[j];
                            tr += "<td id='"+point.id+"' style='text-align:center;'>"+point.name+"</td>";
                            trl += "<td id='"+point.id+"' style='text-align:center;'>"+point.name+"</td>";
                            size ++;
                        }
                    }
                    tr += "</tr>";
                    $("#byyqmbTable").append(tr);

                    for(var i=0; i<courseVo.length; i++) {
                        tr = "<tr>";
                        var course = courseVo[i];
                        tr += "<td id='"+course.id+"'>"+course.orderNo+"</td>";
                        tr += "<td>"+course.courseName+"</td>";
                        for(var j=0; j<size; j++) {
                            tr += "<td id='"+j+"'><input name='quanzhong' style='width:30px;text-align:center;' type='text' name='quanzhong' /></td>";
                        }
                        tr += "<td colspan=3>"+course.courseName+"</td>";
                        tr += "</tr>";
                        $("#byyqmbTable").append(tr);
                    }

                    //为表格赋权重
                    var quanzhong = $("input[name=quanzhong]");
                    for(var i=0; i<quanzhong.length; i++) {
                        var courseId = $(quanzhong[i]).parent().parent().children("td:first-child").attr("id");
                        var index = parseInt($(quanzhong[i]).parent().attr("id"));
                        var pointId = $(quanzhong[i]).parent().parent().parent().children("tr").eq(1).children("td").eq(index).attr("id");
                        for(var j=0; j<courseVo.length; j++) {
                            var course = courseVo[j];
                            var courseId2 = course.id;
                            for(var k=0; k<course.qzList.length; k++) {
                                //需要赋值的权重的指标点id
                                var pointId2 = course.qzList[k].id;
                                if(courseId2==courseId && pointId2==pointId) {
                                    //给input赋权重值
                                    $(quanzhong[i]).val(course.qzList[k].quanzhong);
                                    break;
                                }
                            }
                        }
                    }

                    //设置标题
                    $("#byyqmb_name").val(name);
                    trl += "<tr>";
                    $("#byyqmbTable").append(trl);
                },
                error:function(){
                    //alert("系统异常");
                }
            });

            //点击返回按钮
            $("#re_btn").click(function(){
                window.history.go(-1);
            });

            //点击提交按钮
            $("#save_btn").click(function(){
                var quanzhong = $("input[name=quanzhong]");
                //要提交的参数
                var courseIdPointIdQuanzhong = "";
                for(var i=0; i<quanzhong.length; i++) {
                    if($(quanzhong[i]).val() != "") {
                        var val = $(quanzhong[i]).val();
                        var courseId = $(quanzhong[i]).parent().parent().children("td:first-child").attr("id");
                        var index = parseInt($(quanzhong[i]).parent().attr("id"));
                        var pointId = $(quanzhong[i]).parent().parent().parent().children("tr").eq(1).children("td").eq(index).attr("id");
                        courseIdPointIdQuanzhong += courseId + "_" + pointId + "_" + val + ",";
                    }
                }

                var byyqmbName = $("#byyqmb_name").val();
                //发送ajax请求保存课程毕业要求权重关系
                $.ajax({
                    url: "/byyq/editByyqmb",
                    type: "post",
                    data: {"courseIdPointIdQuanzhong":courseIdPointIdQuanzhong, "byyqmbName":byyqmbName, "mid":mid},
                    dataType: "json",
                    success: function (data) {
                        if(data == "1") {
                            alert("提交成功");
                            window.history.go(-1);
                        }
                    },
                    error: function () {
                        $.messager.alert("提示信息","系统异常","warning");
                    }
                });

            });

        });

    </script>
</head>
<body>

    <a id="re_btn" href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-reload">返回</a>
    <div style="text-align: center;padding: 10px;width: 100%;">
        <span style="font-size: 16px;font-weight: bold;">标题：</span>
        <input style="font-size: 16px;width: 40%;text-align: center;" id="byyqmb_name" type="text" />
    </div>

    <table id="byyqmbTable" border="1" cellspacing="0" width="200%">
        <tr style="height: 30px;" id="title"></tr>
    </table>
    <div style="padding: 10px;">
        <a id="save_btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save',toggle:true">提交</a>
    </div>

</body>
</html>