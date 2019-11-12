<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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

 <form class="form form-horizontal" id="userform" action="${empty CustomerObject ? 'customer/insert.do':'customer/update.do'}">
	<!-- hidden:隐藏域；不显示 -->
	
	<input type="hidden" name="customerId" value="${CustomerObject.customerId}">
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<!--empty user:判断是否为空，是的话返回true不是的话返回false-->
			<input type="text" class="input-text" value="${CustomerObject.customerName}" placeholder="" id="customerName" name="customerName">
		</div>
	</div>
	
	
	  <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户手机：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<!--empty user:判断是否为空，是的话返回true不是的话返回false-->
			<input type="text" class="input-text" value="${CustomerObject.phone}" placeholder="" id="phone" name="phone">
		</div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户邮箱：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${CustomerObject.email}" placeholder="" id="email" name="email">
		</div>
	</div>
	
	
	    <div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户地址：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${CustomerObject.address}" placeholder="" id="address" name="address">
				</div>
	   </div>
	
	  <div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户描述：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${CustomerObject.remark}" placeholder="" id="remark" name="remark">
				</div>
	   </div>
	   
	   
	   <div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户身份证：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${CustomerObject.idCard}" placeholder="" id="idCard" name="idCard">
				</div>
	   </div>
	  
	  
	  
	 <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户性别：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="gender" type="radio" id="sex-1" value="1" ${CustomerObject.gender eq '1'? 'checked':''}>
				<label for="sex-1">男</label>
			</div>
			<div class="radio-box">
				<input type="radio" id="sex-2" name="gender" value="2" ${CustomerObject.gender eq '2'? 'checked':''}>
				<label for="sex-2">女</label>
			</div>
		  </div>
	 </div> 
	  
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">业务员：</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			
			<c:choose>
			
			<c:when test="${user.rolename=='业务员'}">
			         <shiro:principal property="realname"/>  
			</c:when>
				
			<c:otherwise>
			    <select class="select" name="userId" size="1">			   		
				<option value="0">请选择</option>				
				<c:forEach items="${realname}" var="realname">
				<!-- selected ：默认显示当前值-->				
				<option value="${realname.userId}" ${CustomerObject.userId eq realname.userId?'selected':''}>${realname.realname}</option>				
				</c:forEach>											
		       </select>
			</c:otherwise>
         </c:choose>			   
	   </span> </div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">区间范围：</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="baseId" size="1">
				<option value="0">请选择</option>				
				<c:forEach items="${basename}" var="basename">
               	<option value="${basename.baseId}" ${CustomerObject.baseId eq basename.baseId?'selected':''}>${basename.baseName}</option>				
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
			
			customerName:{
			required:true,
				
				//异步验证，验证用户是否存在
				 <c:if test="${empty CustomerObject}">
				remote: {
				    url: "customer/checkbaseName.do",    //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	//将属性传给后台，后台获取属性对应的值，查询数据判断是否
				    	//存在用户，存在返回false不存在返回true；响应数据过来
				    	//校验false不成立，true成立
				        customerName: function() {
				            return $("#customerName").val();
				        }
				    }
				}
	                   </c:if>
			},
				
			
			phone:{
				 required:true,
				 
			},
				
			email:{

				 required:true,
				 
			},
			
			address:{

				 required:true,
				 
			},
			
			gendeer:{

				 required:true,
				
			},
			
			
			idCard:{

				 required:true,
				 
			},
			
			userId:{

				 
				 min:1
			},
			
			baseId:{
				 
				 min:1
			},
		},
		
		messages:{
			customerName:{
				required:"用户名不能为空",				
				remote:"用户已经存在"
			},
		
			phone:{
				required:"手机号不能为空"
			},
			
			email:{
				required:"邮箱不能为空"
			},
			
			address:{
				required:"地址不能为空"
			},
			
			gendeer:{

				 required:"性别不能为空"
				
			},
			
			idCard:{
				required:"身份证不能为空"
			},
			

			userId:{
				
				min:"业务员不能为空"
			},
			
			baseId:{

				min:"区间范围不能为空"				 
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