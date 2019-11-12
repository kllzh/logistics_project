<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
/* http://localhost:8080/logistics/ */
%>

<!DOCTYPE HTML>
<html>
<head>
<!-- 设置页面的 基本路径，页面所有资源引入和页面的跳转全部基于 base路径 -->
<base href="<%=basePath%>">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"  href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css">

<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
</head>
<body>




<ul id="permissionTree" class="ztree"></ul>

<script type="text/javascript">
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
			
			/* 
			     再ztree中有个 onAsyncSuccess；里面有三个方法，用于异步加载成功的时候触发onAsyncSuccess
			     
			     从而进行一个回调函数
			     
			     callback:就是三个属性中的之一；
			  */
			    			
		 	
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

	            
	       
			 function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			        /* 获取stree对象 */
				 var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
				 
				    var permissionIds="49,31,34,35"
				    
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
	
	$(function() {
		$.fn.zTree.init($("#permissionTree"), setting);
	})

</script>


</body>
</html>