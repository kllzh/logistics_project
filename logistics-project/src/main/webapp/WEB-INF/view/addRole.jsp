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
<meta rolename="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta rolename="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css"  href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css">
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>添加管理员 - 管理员管理 - H-ui.admin v3.1</title>
<meta rolename="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta rolename="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
 <form class="form form-horizontal" id="userform" action=${empty role? 'role/insert.do':'role/update.do'}>
	<!-- hidden:隐藏域；不显示 -->
	<input type="hidden" name="roleId" value="${role.roleId}">
	<input type="hidden" name="permissionIds" id="permissionIds" value="">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<!--empty user:判断是否为空，是的话返回true不是的话返回false-->
			<input type="text" class="input-text" value="${role.rolename}" placeholder="" id="rolename" name="rolename">
		</div>
	</div>
	
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">描述：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea name="remark"  cols="" rows="" class="textarea"  placeholder="说点什么...100个字符以内" dragonfly="true" >${role.remark}</textarea>
			<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
		</div>
	</div>
	
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">描述：</label>
		<div class="formControls col-xs-8 col-sm-9">
			
			<ul id="permissionTree" class="ztree"></ul>
			
		</div>
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
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>



<script type="text/javascript">



$(function(){
	
	$("#userform").validate({
		rules:{
			
			rolename:{
				required:true,
				
				//异步验证，验证用户是否存在
				 <c:if test="${empty role}">
				remote: {
				    url: "role/checkRolerolename.do",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	//将属性传给后台，后台获取属性对应的值，查询数据判断是否
				    	//存在用户，存在返回false不存在返回true；响应数据过来
				    	//校验false不成立，true成立
				        rolename: function() {
				            return $("#rolename").val();
				        }
				    }
				}
	              </c:if>
			},

			remark:{
				 required:true, 
			},
			
	
		},
		
		messages:{
			rolename:{
				required:"角色名称不能为空",
				
				remote:"角色已经存在"
			},
				
			remark:{
				required:"描述不能为空"
			},

		},
		
		
		submitHandler:function(form){
			
			getztreeobj();
	 
			//校验成功以后回调函数  将表单form请求转发出去；
			$(form).ajaxSubmit(function(data){
				
			
				layer.confirm(data.msg,{icon:1},function(index){
						
					if (data.num==1) {
						//刷新父页的表格:parent属性返回当前窗口的父窗口
						//parent父属性可以调用父页方法
						layer.msg("修改成功",{icon:6});
					
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


function getztreeobj() {
	 
      
	/*
	  ztree树中有一个方法getCheckedNodes；用于获取勾选中的节点
	
	获取ztree对象 ,注意获取 ztree对象的时候不加#号*/
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	
	/* 获取选择的节点，true表示获取选择的勾，false则相反 */
	var nodes = treeObj.getCheckedNodes(true);
	
	var str=[]
	
	nodes.forEach(function(item,index){
	    
	    str.push(item.permissionId)
	})
	
	var string=str.toString()

	$("#permissionIds").val(string)
}



function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    /* 获取stree对象 */
 var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
 
    var permissionIds="${role.permissionIds}"
    
    var permissionIdsArr=permissionIds.split(",")
    
    for (var i = 0; i<permissionIdsArr.length; i++) {
		
    	var permissionId=permissionIdsArr[i]
    	
    	console.log(permissionId)
    	
    	/*getNodeByParam：通过ztree数据属于的id对应的值获取对应的节点  
    	
    	   查找id=permissionId;获取对应的节点
    	*/
    	var node = treeObj.getNodeByParam("id",permissionId, null);
    	  
    	 /* 获取到的节点进行勾选，ztree对象中有 checkNode方法，将获取到的节点
    	 
    	 进勾选，第一个true表示勾选获取到的节点，第二个true表示父子节点连选
    	 
    	 */
    	treeObj.checkNode(node, true, false);
	} 
}; 


//定义zTree的设置 
var setting = {	
		/* 开启复选框 */
		check: {
			enable: true
		},
		
		data: {
			/*simpleData：简单的数，enable：执行简单的树true */
			simpleData: {
				enable: true
			}
		},
		async: {
			/* 异步加载数据 ：
			   dataFilter：数据过滤器；用于提供给ztree树插件能识别的属性；
			   
			     后台传来的数据，要形成一个树的结构，使用ztree插件，需要由
			     
			     一个父pId和一个子id的两个属性进行父子分层；后台传来的属性名若不匹配
			     
			     该固定的属性名，那么可以使用过滤，将后台传来的属性值赋给自定义的pId和id；
			     
			    
			*/
			enable: true,
			url:"role/ztree.do",				
			dataFilter: filter
		},

		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess
		} 
};
       /* 过滤函数 */
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].id = childNodes[i].permissionId
				childNodes[i].pId = childNodes[i].parentId
				childNodes[i].open=true
			}
			return childNodes;
		}

  

$(function(){
	
	//初始化zTree
	$.fn.zTree.init($("#permissionTree"), setting);
})   

</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>