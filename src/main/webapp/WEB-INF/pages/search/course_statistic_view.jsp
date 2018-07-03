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
            font-size: 14px;
            table-layout: fixed;
        }
        th {
            font-size: 16px;
            font-weight: bold;
            line-height: 30px;
        }
        td {
            word-break: break-all;
            word-wrap: break-word;
            text-align: center;
            line-height: 24px;
            font-size: 14px;
        }
        .tabletitle {
            font-size: 14px;
            line-height: 30px;
            padding-left: 20px;
        }
    </style>

    <script type="text/javascript">
        var statisticList = ${statisticList};
        var cname = '${cname}';
//        var statisticList = [{
//            "name" : "毕业要求1",
//            "pointList": [{
//                "pointId":"1",
//                "pointName":"指标点1.1",
//                "desc":"能够将数学、物理等基本知识用于复杂电子信息工程问题的建模和求解；",
//                "courseList":[{
//                    "courseId":"1",
//                    "courseName":"高等数学",
//                    "quanzhong":"（20%）"
//                }, {
//                    "courseId":"2",
//                    "courseName":"线性代数",
//                    "quanzhong":"（20%）"
//                }, {
//                    "courseId":"3",
//                    "courseName":"概率统计",
//                    "quanzhong":"（20%）"
//                }, {
//                    "courseId":"4",
//                    "courseName":"大学物理",
//                    "quanzhong":"（20%）"
//                }, {
//                    "courseId":"5",
//                    "courseName":"复变函数与积分变换",
//                    "quanzhong":"（20%）"
//                }]
//            }, {
//                "pointId":"2",
//                "pointName":"指标点1.2",
//                "desc":"掌握电路、电子技术、信号与系统、电磁场及相关工程基础知识，能将其用于分析电子信息领域工程问题中的电子电路、电磁场与信号及相关问题；",
//                "courseList":[{
//                    "courseId":"6",
//                    "courseName":"电路分析（电路分析基础（1））",
//                    "quanzhong":"（30%）"
//                }, {
//                    "courseId":"7",
//                    "courseName":"模拟电子技术（模拟电子技术（1））",
//                    "quanzhong":"（20%）"
//                }, {
//                    "courseId":"8",
//                    "courseName":"信号与系统",
//                    "quanzhong":"（10%）"
//                }]
//            }],
//            "courseList":[{
//                "courseName":"高等数学",
//                "pointList":[{
//                    "pointId":"1",
//                    "value":"0.82（20%）"
//                }]
//            }, {
//                "courseName":"线性代数",
//                "pointList":[{
//                    "pointId":"1",
//                    "value":"0.79（20%）"
//                }]
//            }, {
//                "courseName":"概率统计",
//                "pointList":[{
//                    "pointId":"2",
//                    "value":"0.85（20%）"
//                }]
//            }]
//        }];

        $(function(){
            for(var i=0; i<statisticList.length; i++) {
                var statistic = statisticList[i];
                $("#content").append("<div class='tabletitle'>"+statistic.name+"达成度评价内容与评价过程</div>");
                var table = "<table border='1' cellspacing='0' width='60%'><tr><th>指标点</th><th>相关教学活动</th></tr>";

                var title2 = "<tr><th>"+statistic.name+"</th>";
                for(var j=0; j<statistic.pointList.length; j++) {
                    var point = statistic.pointList[j];
                    title2 += "<th id='"+point.pointId+"'>"+point.pointName+"</th>";
                    var tr = "<tr><td width='90%' rowspan='"+point.courseList.length+"'>" + point.pointName + "：" + point.desc + "</td>";
                    for(var k=0; k<point.courseList.length; k++) {
                        var course = point.courseList[k];
                        if(k == 0) {
                            if(course.courseName == cname) {
                                tr += "<td width='10%'><font color='red'>" + course.courseName + " " + course.quanzhong + "</font></td></tr>";
                            } else {
                                tr += "<td width='10%'>" + course.courseName + " " + course.quanzhong + "</td></tr>";
                            }
                        } else {
                            if(course.courseName == cname) {
                                tr += "<tr width='10%'><td><font color='red'>" + course.courseName + " " + course.quanzhong + "</font></td></tr>";
                            } else {
                                tr += "<tr width='10%'><td>" + course.courseName + " " + course.quanzhong + "</td></tr>";
                            }
                        }
                    }
                    table += tr;
                }
                $("#content").append(table + "</table>");
                $("#content").append("<br/><div class='tabletitle'>"+statistic.name+"达成度评价表</div>");
                title2 += "</tr>";
                var tid = "table" + i;
                var table2 = "<table id='"+tid+"' border='1' cellspacing='0' width='60%'>"+title2+"</table>";
                $("#content").append(table2);
                for(var x=0; x<statistic.courseList.length; x++) {
                    var course = statistic.courseList[x];
                    if(course.courseName == cname) {
                        var tr = "<tr><td><font color='red'>"+course.courseName+"</font></td>";
                    } else {
                        var tr = "<tr><td>"+course.courseName+"</td>";
                    }
                    for(var y=0; y<statistic.pointList.length; y++) {
                        tr += "<td id='"+statistic.pointList[y].pointId+"'>";
                        for(var yy=0; yy<course.pointList.length; yy++) {
                            var p = course.pointList[yy];
                            if(p.pointId === statistic.pointList[y].pointId) {
                                tr += p.value;
                            }
                        }
                        tr += "</td>";
                    }
                    tr += "</tr>";
                    $("#table"+i).append(tr);
                }
                $("#content").append("<br/><br/><hr/><br/><br/>");
            }
        });

    </script>
</head>
<body>

    <a id="re_btn" onclick="window.history.go(-1);" href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-reload">返回</a>

    <div id="content">
        <%--<table id="table1" border="1" cellspacing="0"></table>--%>
    </div>

</body>
</html>