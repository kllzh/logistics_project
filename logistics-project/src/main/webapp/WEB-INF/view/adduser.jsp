<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<!-- 设置页面的 基本路径，页面所有资源引入和页面的跳转全部基于 base路径 -->
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>添加管理员 - 管理员管理 - H-ui.admin v3.1</title>
<meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
 <form class="form form-horizontal" id="userform" action=${empty user? 'admin/insert.do':'admin/update.do'}>
	<!-- hidden:隐藏域；不显示 -->
	<input type="hidden" name="userId" value="${user.userId}">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<!--empty user:判断是否为空，是的话返回true不是的话返回false-->
			<input type="text" class="input-text" value="${user.username}" ${empty user? '':'disabled'} placeholder="" id="username" name="username">
		</div>
	</div>
	
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>真实姓名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${user.realname}" placeholder="" id="realname" name="realname">
		</div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>初始密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" placeholder="密码" id="password" name="password">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>确认密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" placeholder="确认新密码" id="password2" name="password2">
		</div>
	</div>
	<!-- <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="sex" type="radio" id="sex-1" checked>
				<label for="sex-1">男</label>
			</div>
			<div class="radio-box">
				<input type="radio" id="sex-2" name="sex">
				<label for="sex-2">女</label>
			</div>
		</div>
	</div> -->
	
	
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">角色：</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="roleId" size="1">
				<option value="0">请选择</option>
				
				<c:forEach items="${role}" var="item">
				<!-- selected ：默认显示当前值-->
				<option value="${item.roleId}" ${user.roleId eq item.roleId ?'selected':''}>${item.rolename}</option>
				
				</c:forEach>
								
			</select>
			</span> </div>
	</div>
	
	
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去--> 
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">

$(function(){
	
	$("#userform").validate({
		rules:{
			
			username:{
				required:true,
				minlength:2,
				//异步验证，验证用户是否存在
				remote: {
				    url: "admin/checkUsername.do",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	//将属性传给后台，后台获取属性对应的值，查询数据判断是否
				    	//存在用户，存在返回false不存在返回true；响应数据过来
				    	//校验false不成立，true成立
				        username: function() {
				            return $("#username").val();
				        }
				    }
				}
			},
			
			realname:{
				required:true,
				minlength:2
			},
			
			password:{
				required:true,
				minlength:3
			},
			
			password2:{
				 required:true,
				 minlength:3,
				 equalTo:"#password"
			},
			
			roleId:{
				required:true,
				min:1
			},
			
		},
		
		messages:{
			username:{
				required:"名字不为空",
				minlength:"用户名最小长度为2",
				remote:"用户名已存在"
			},
		
			realname:{
				required:"真实名不为空",
				minlength:"真实名最小长度为2"
			},
			
			password:{
				required:"密码不为空",
				minlength:"密码最小长度为最小长度为3"
			},
			
			password2:{
				required:"确认密码不为空",
				minlength:"密码最小长度为最小长度为3",
				equalTo: "两次密码不一致"
			},
			
			roleId:{
				required:"请选择你的角色",
				min:"请选择一个角色"
			},
			
		},
		
		
		submitHandler:function(form){
			
			//校验成功以后回调函数  将表单form请求转发出去；
			$(form).ajaxSubmit(function(data){
								
				layer.confirm(data.msg,{icon:1}, function(index){
							
					if (data.num==1) {
						//刷新父页的表格:parent属性返回当前窗口的父窗口
						//parent父属性可以调用父页方法
						parent.refreshTable();
						
						//关闭父页弹出的模态框，closeAll：疯狂模式，关闭所有层
						//凡是父页的框全部关闭
						parent.layer.closeAll();	
					}
																		  
	          });				        
			
	     })
			
	 }
 });
		
});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>