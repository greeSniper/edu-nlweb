<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <!-- 导入amChart核心类库 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/amchartNew/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/amchartNew/amcharts/amcharts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/amchartNew/amcharts/radar.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/amchartNew/amcharts/serial.js"></script>
    <!-- 导入jquery核心类库 -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>

    <style>
        .chart {
            float:left;
        }
    </style>

    <script type="text/javascript">
        var chart;

        //能力值图数据
        var chartData = ${result};
//        var chartData = [
//            [
//                {
//                    "data": "通信原理",
//                    "litres": 0
//                },
//                {
//                    "data": "1.2",
//                    "litres": 0.82
//                },
//                {
//                    "data": "1.3",
//                    "litres": 0.76
//                },
//                {
//                    "data": "1.4",
//                    "litres": 0.79
//                },
//                {
//                    "data": "1.5",
//                    "litres": 0.68
//                },
//                {
//                    "data": "1.6",
//                    "litres": 0.92
//                }
//            ],
//            [
//                {
//                    "data": "数字信号处理",
//                    "litres": 0
//                },
//                {
//                    "data": "2.2",
//                    "litres": 0.92
//                },
//                {
//                    "data": "2.3",
//                    "litres": 0.66
//                },
//                {
//                    "data": "2.4",
//                    "litres": 0.59
//                },
//                {
//                    "data": "2.5",
//                    "litres": 0.88
//                },
//                {
//                    "data": "2.6",
//                    "litres": 0.72
//                },
//                {
//                    "data": "1.6",
//                    "litres": 0.92
//                }
//            ]
//        ];

        //动态生成多个能力值图div
        $(function(){
            for(var i=0; i<chartData.length; i++) {
                var id = "chartdiv"+(i+1);
                $("#div1").append("<div id="+id+" class='chart' style='width:360px; height:240px;'></div>");
            }
        });

        //生成能力值图
        AmCharts.ready(function () {
            for(var i=0; i<chartData.length; i++) {
                // RADAR CHART
                chart = new AmCharts.AmRadarChart();
                chart.dataProvider = chartData[i];
                chart.categoryField = "data";
                chart.startDuration = 2;	//图像弹出速度

                // VALUE AXIS
                var valueAxis = new AmCharts.ValueAxis();
                valueAxis.axisAlpha = 0.15;	//刻度线深度
                valueAxis.minimum = 0;
                valueAxis.dashLength = 3;	//虚线
                valueAxis.axisTitleOffset = 20;	//字与图的距离
                valueAxis.gridCount = 4;	//刻度层次
                chart.addValueAxis(valueAxis);

                // GRAPH
                var graph = new AmCharts.AmGraph();
                graph.valueField = "litres";
                graph.bullet = "round";
                graph.balloonText = "达成度：[[value]]";
                chart.addGraph(graph);

                // WRITE
                var id = "chartdiv"+(i+1);
                chart.write(id);
            }
        });

        //-----------------------------------------------------------------
        var chart2;

        //折线图数据
        var chartData2 = ${lineResult};
//        var chartData2 = [
//            {
//                "pointName": "毕业要求 1.1",
//                "reach": 0.67,
//            },
//            {
//                "pointName": "毕业要求 1.2",
//                "reach": 0.88,
//            },
//            {
//                "pointName": "毕业要求 1.3",
//                "reach": 0.74,
//            },
//            {
//                "pointName": "毕业要求 1.4",
//                "reach": 0.59,
//            },
//            {
//                "pointName": "毕业要求 1.5",
//                "reach": 0.92,
//            },
//            {
//                "pointName": "毕业要求 1.6",
//                "reach": 0.83,
//            }
//        ];

        //生成折线图
        AmCharts.ready(function () {
            // SERIAL CHART
            chart2 = new AmCharts.AmSerialChart();
            chart2.dataProvider = chartData2;
            chart2.categoryField = "pointName";
            chart2.startDuration = 0.5;
            chart2.balloon.color = "#000000";

            // AXES
            // category
            var categoryAxis = chart2.categoryAxis;
            categoryAxis.fillAlpha = 1;
            categoryAxis.fillColor = "#FAFAFA";
            categoryAxis.gridAlpha = 0;
            categoryAxis.axisAlpha = 0;
            categoryAxis.gridPosition = "start";
            categoryAxis.position = "top";

            // value
            var valueAxis = new AmCharts.ValueAxis();
            valueAxis.title = "指标点达成度";
            valueAxis.dashLength = 5;
            valueAxis.axisAlpha = 0;
            valueAxis.minimum = 0;
            valueAxis.maximum = 1;
            valueAxis.integersOnly = true;
            valueAxis.gridCount = 10;
            valueAxis.reversed = false; // this line makes the value axis reversed
            chart2.addValueAxis(valueAxis);

            // GRAPHS
            // 达成度 graph
            var graph = new AmCharts.AmGraph();
            graph.title = "达成度";
            graph.valueField = "reach";
            //graph.hidden = true;  this line makes the graph initially hidden
            graph.balloonText = "达成度: [[value]]";
            graph.lineAlpha = 1;
            graph.bullet = "round";
            chart2.addGraph(graph);

            // CURSOR
            var chartCursor = new AmCharts.ChartCursor();
            chartCursor.cursorPosition = "mouse";
            chartCursor.zoomable = false;
            chartCursor.cursorAlpha = 0;
            chart2.addChartCursor(chartCursor);

            // LEGEND
            var legend = new AmCharts.AmLegend();
            legend.useGraphSettings = true;
            chart2.addLegend(legend);

            // WRITE
            chart2.write("chartdiv");
        });

    </script>

</head>
<body>
    <!-- 返回按钮 -->
    <input type="button" value="返回" onclick="window.history.go(-1);" />
    <!-- 能力值图 -->
    <div id="div1"></div>
    <!-- 折线图 -->
    <div id="chartdiv" style="width: 100%; height: 400px;"></div>
</body>
</html>