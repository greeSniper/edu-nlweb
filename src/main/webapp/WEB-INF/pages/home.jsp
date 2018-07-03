<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<meta name="keywords" content="常熟理工学院" />
<meta name="description" content="卓越人才能力培养过程监控系统" />
<meta name="author" content="author,email address" />

<title>卓越人才能力培养过程监控系统</title>
<link rel="stylesheet" type="text/css" href="cxylogin/css/introduce.css">
<link rel="stylesheet" type="text/css" href="cxylogin/css/jquery.fullPage.css">
<link rel="stylesheet" type="text/css" href="gc/css/css.css">

<script src="cxylogin/js/jquery-1.8.3.min.js"></script>
<script src="cxylogin/js/jquery-ui-1.10.3.min.js"></script>
<script src="cxylogin/js/jquery.fullPage.min.js"></script>
<script src="frame/js/control.js"></script>
<script type="text/javascript">
if (window != top)
{
top.location.href = location.href; //跳出框架
}
</script>
    <script type="text/javascript">
        $(document).ready(function() {

            if(($.browser.msie)){
                $(".arr").css({"bottom":"150px"});
            }

            $('.arr').on( 'click', function(e){
                e.preventDefault();
                $.fn.fullpage.moveSectionDown();
            }).fullpage({
                'verticalCentered': false,
                'anchors': ['page1', 'page2', 'page3', 'page4'],
                'css3': true,
                'slidesColor': ['#339be2', '#0a69c3', '#49c179', '#18a27e'],
                'navigation': true,
                'navigationPosition': 'right',
                'navigationTooltips': ['物理与电子工程学院', '标准化', '数字化', '可视化'],

                'onLeave': function(index, direction){
                    if (index == 3 && direction == 'down'){
                        $('.section').eq(index -1).removeClass('moveDown').addClass('moveUp');
                        $('.arr').insertBefore('#superContainer').hide();
                    }
                    else if(index == 3 && direction == 'up'){
                        $('.section').eq(index -1).removeClass('moveUp').addClass('moveDown');
                    }else{
                        $('.arr').insertBefore('#superContainer').show();
                        console.log(index);
                    }
                },

                afterRender: function(index){
                    $('.head').insertBefore('#superContainer').show();

                }

            });
        });
    </script>
    <style>
</style>
<style>
	.wrap{width:80%;  margin:0 auto;}
	.one_font{width:55%; background-size: 100% auto;}
	.two_text{width:42%;}
	.two_text{padding-top:0;}
	.four_tu {width:65%;}
	.two_dcb{top:0}
	.three_quan , .two_tree{width:35%; margin-top:-10%;}
	@media screen and (max-width:1200px){
		.three_quan , .two_tree{ margin-top:0;}
		}

</style>
</head>
<body>
<!--head S-->
<div class="head none">
    <div class="wrap">   
        <div class="logoBj" style="background:url(${pageContext.request.contextPath }/cxylogin/images/logocssy.png) no-repeat center;"></div>
        <div class="nav newnav fl">
            <a class="seled" href=""><b>首页</b></a>
            <a class="seled" href="http://10.28.200.38:8080/Index.aspx"><b>学生信息查询系统</b></a>
            <a class="seled" href="http://10.28.200.187/jwglxt/xtgl/login_slogin.html?language=zh_CN&_t=1511849762512"><b>教学综合信息服务平台</b></a>
            <a class="seled" href="http://10.28.102.155:8090/Index.aspx"><b>实验实践管理系统</b></a>
            <a class="seled" href="http://61.155.18.20:8882/jwh/"><b>素质拓展管理系统</b></a>
            <a class="seled" href="http://bylw.cslg.edu.cn/"><b>毕业设计（论文）管理系统</b></a>
            <a class="seled" href="http://sjcx.cslg.cn/"><b>创新创业训练计划管理系统</b></a>
            <a class="seled" href="/tologin"><b>卓越人才培养能力达成度评估系统</b></a>
        </div>
    </div>
</div>
<!--head E-->
<!--content S-->
<div id="fullpage" class="content">
    <!--第一屏-->
    <div class="section sec_one active" data-anchor="page1">
        <img class="cloud1" src="${pageContext.request.contextPath }/cxylogin/images/cloud1.png" alt="">
        <img class="cloud2" src="${pageContext.request.contextPath }/cxylogin/images/cloud2.png" alt="">
        <img class="cloud3" src="${pageContext.request.contextPath }/cxylogin/images/cloud3.png" alt="">
        <img class="one_bt" src="${pageContext.request.contextPath }/cxylogin/images/one_bottom.png" alt="">
        <img class="one_tu" src="${pageContext.request.contextPath }/cxylogin/images/one_tu.png" alt="">
        <div class="wrap clearfix one_mian">
            <div class="one_font" >
                <img class="plane" src="${pageContext.request.contextPath }cxylogin/images/plane.png" alt="">
            </div>
        </div>
    </div>
    <!--第二屏-->
    <div class="section sec_two moveDown" data-anchor="page2">
        <div class="wrap two_main clearfix">
            <div class="two_text">
                <img class="two_dcb zhuan" src="cxylogin/images/two_font.png" alt="低成本">
                <h4 class="two_tit">工程教育、审核评估、卓越工程</h4>
                <p class="two_mess"> 建立以学生为中心的人才培养体系，遵循国际工程教育认证标准，国家高等本科教育审核性评估，卓越工程师计划制定培养标准，以企业、同行、社会、校友、学生、专家等多视角，以问卷、走访、座谈、会议、听课、学评教等多种方式收集意见完善培养标准。</p>
            </div>
            <img class="fr two_tree" src="cxylogin/images/two_tree.png" alt="">
        </div>
    </div>
    <!--第三屏-->
    <div class="section sec_three moveDown" data-anchor="page3">
        <div class="wrap two_main clearfix">
            <div class="two_text">
                <img class="two_dcb zhuan" src="cxylogin/images/three_font.png" alt="超安全">
                <h4 class="two_tit">教学量化、培养量化、评价量化</h4>
                <p class="two_mess three_mess">建立培养标准、教学质量和学生培养及其评价的量化体系，确保标准依据科学，教学质量可控，培养环节可管，学生评价够全面。在国内实现了毕业生能力达成度的立体化、信息化的评价机制，具有典型的示范意义。
</p>
            </div>
            <img class="fr three_quan" src="cxylogin/images/three_quan.png" alt="">
        </div>
    </div>
    <!--第四屏-->
    <div class="section sec_four moveDown" data-anchor="page4">
        <div class="wrap two_main clearfix" style="position: relative;">
            <div class="two_text">
                <img class="two_dcb zhuan" src="cxylogin/images/four_font.png" alt="高效率">
                <h4 class="two_tit">教学监控、培养监督、巡检监察</h4>
                <p class="two_mess three_mess">建立教学和培养的监控体系，实现教学环节的即时监控，培养过程的即时预警，持续改进的即时推送，培养结果的常态化公示。</p>
            </div>
            <img class="four_tu" src="cxylogin/images/four_tu.png" alt="">
        </div>
        <!-- <p class="copyright">版权所有：<a href="http://www.cslg.cn/" target="_blank">唐哲</a> Copyright © 2017-2200 www.daqsoft.com 苏ICP备88888888号  咨询电话：18852937197</p> -->
	</div>
    <a class="arr none" href="javascript:;"></a>
</div>
<!--content E-->
</body>
</html>