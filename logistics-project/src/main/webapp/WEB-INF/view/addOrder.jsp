<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>    
    
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE HTML>
<html>
<head>

<base href="<%=basePath%>">

<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<style type="text/css">
	.order_detail{
		background-color: durkgray;
		border: 1px solid black;
		border-right: 0; 
		height: 40px;
		line-height: 40px;
	}
	.order_detail input{
		height: 30px;
		text-align: center;
	}
	.order_add{
		border: 1px solid black;
	}
	#order_detail{
		text-align: center;
	}

</style>
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.min.css" />
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" method="post" action="${empty customer ? 'order/insert.do' : 'order/update.do'}" id="orderForm">
	<!-- 隐藏域 ，订单状态-->
	<input type="hidden" name="orderStatus" value="1">
	
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">业务员：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="userId">
					<c:forEach items="${users}" var="obj">
						<option value="${obj.userId}">${obj.realname}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">客户：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="customerId" id="customer">
					<c:forEach items="${customers}" var="obj">
						<!-- 
							html5 可以自定义 属性
							
							语法 ： data-xxx
							data-：固定语法
							xxx ：具体属性，见名知意
							如 ：
							data-customer_id : 客户id
							data-custoemr_name :客户名称
							
						 -->
					
						<option data-base-id="${obj.baseId}" value="${obj.customerId}">${obj.customerName}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">到达区域：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select id="interval" name="intervalId">
					<c:forEach items="${intervals}" var="obj">
						<option  value="${obj.baseId}">${obj.baseName}</option>						
					</c:forEach>
				</select>
			</div>
		</div>
	</div>

	
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">发货地址：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="" required="required"  placeholder="" id="shippingAddress" name="shippingAddress">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">发货人：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="" required="required"   placeholder="" id="shippingName" name="shippingName">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">发货联系电话：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="" required="required"  placeholder="" id="shippingPhone" name="shippingPhone">
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">付款方式：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="paymentMethodId">
					<c:forEach items="${payments}" var="obj">
						<option value="${obj.baseId}">${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">货运方式：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="freightMethodId">
					<c:forEach items="${freights}" var="obj">
						<option value="${obj.baseId}">${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">取件方式：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select name="takeMethodId">
					<c:forEach items="${fetchTypes}" var="obj">
						<option value="${obj.baseId}">${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">物流公司：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="xx物流公司"  disabled="disabled"  placeholder="" id="customerName" name="customerName">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">物流单号：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="243242343" disabled="disabled"   placeholder="" id="customerName" name="customerName">
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">收件人：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="张三" disabled="disabled"   placeholder="" id="customerName" name="customerName">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">收货地址：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="广州市天河区xxx" disabled="disabled"   placeholder="" id="customerName" name="customerName">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">联系电话：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="135xxxxx"  disabled="disabled"  placeholder="" id="customerName" name="customerName">
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">取件地址：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value=""   placeholder="" id="customerName" name="takeAddress">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">联系电话：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value=""   placeholder="" id="takePhone" name="takePhone">
			</div>
		</div>
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">取件联系人：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value=""   placeholder="" id="takeName" name="takeName">
			</div>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-4 col-sm-4">
			<label class="form-label col-xs-4 col-sm-4">订单备注：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<textarea name="orderRemark" cols="" rows="" class="textarea"  placeholder="" ></textarea>
			</div>
		</div>
	</div>
	
	
	
	<div  id="order_detail" class="row cl">
		<div>
			<div  class="col-xs-2 col-sm-2 order_detail">货物名称</div>
			<div  class="col-xs-2 col-sm-1 order_detail">数量</div>
			<div  class="col-xs-2 col-sm-1 order_detail">单位</div>
			<div  class="col-xs-2 col-sm-2 order_detail">单价</div>
			<div  class="col-xs-2 col-sm-2 order_detail">总价值</div>
			<div  class="col-xs-2 col-sm-3 order_detail">备注</div>
			<div  class="col-xs-2 col-sm-1 order_detail order_add">
				<span style="font: 30px;cursor: pointer;color: green" 
					class="glyphicon glyphicon-plus"
					onclick="add_goods_detail();"
					></span>
				</div>
		</div>
		
		<div id="goods_detail">
			<div  class="col-xs-2 col-sm-2 order_detail">
				<input type="text" name="orderDetails[][goodsName]">
			</div>
			<div  class="col-xs-2 col-sm-1 order_detail">
				<input type="text" size="3" onkeyup="operationTotlPrice(this);" name="orderDetails[][goodsNumber]">
			</div>
			<div  class="col-xs-2 col-sm-1 order_detail">
				<select name="orderDetails[][goodsUnit]">
					<c:forEach items="${units}" var="obj">
						<option value="${obj.baseId}">${obj.baseName}</option>
					</c:forEach>
				</select>
			</div>
			<div  class="col-xs-2 col-sm-2 order_detail">
				<input type="text"  onkeyup="operationTotlPrice(this);" name="orderDetails[][goodsUnitPrice]">
			
			</div>
			<div  class="col-xs-2 col-sm-2 order_detail">
				<input type="text" readonly="readonly" name="orderDetails[][goodsTotal]">
			</div>
			<div  class="col-xs-2 col-sm-3 order_detail">
				<input type="text" name="orderDetails[][goodsRemark]">
			</div>
			<div  class="col-xs-2 col-sm-1 order_detail order_add">
				<span style="font: 30px;cursor: pointer;color: red" 
				class="glyphicon glyphicon-remove"
				onclick="remove_goods_detail(this);"
				></span>
			</div>
		</div>
		
		
		
	</div>
	
	
	<div class="row cl">
		<div class="col-xs-12 col-sm-12 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="lib/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="lib/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript" src="lib/jquery/jquery.serializejson.min.js"></script>

<script type="text/javascript">
   
   
$(function(){
	 /*当元素的值改变时发生 change 事件（仅适用于表单字段）。
      change() 方法触发 change 事件，或规定当发生 change 事件时运行的函数*/
	 $("#customer").change(function(){    		 
		  /* change事件执行调用函数，change用于复选框 */
		  changeOption();     	 
 })
         /* 文档加载完毕马上执行函数 */
	          changeOption();
}) 	 	
  
    	function changeOption(){
		
	 	var baseId=$("#customer option:selected").data("base-id")
	 	 
	 	
	 	/* 获取interval中的所有option进行for循环 */
	 	var intervalOption=$("#interval option")     	 	    
	 	
	    for (var i=0;i<intervalOption.length;i++) {
	 	    
	 	     var option=intervalOption[i]
	 	     
	 	          
	 	     if(option.value==baseId){
	 	     	/* 在客户中baseId等于区间中baseId的时候进行选中 */
	 	     	option.selected='true'
	 	     	
	 	     } 
	   }     	 		 	
}

   
   function add_goods_detail(){
   	    /* clone克隆 */
   	   var goodsDetail=$("#goods_detail").clone()
   	    /*选中所有的input吧value值取出等于空*/
   	    goodsDetail.find("input").val("")
   	    
   	    /*获取父元素*/
   	   var orderDetail= $("#order_detail")
   	   /*在父元素中追加子元素*/
   	   orderDetail.append(goodsDetail)
   	   
   	 
   }
   
   function remove_goods_detail(obj){
   	   /*第一个parent()是包拢</span>标签的div，第二个parent()是包拢div的父元素div*/
   	   $(obj).parent().parent().remove()  	   
   }

  
 
   function operationTotlPrice(obj){
   	  /* 当函触发的时候，获取父元素div，最大div元素，获取所有的input标签  */
   	  var divBxo=$(obj).parent().parent()
   	  
   	  var input=divBxo.find("input")
   	     
   	  var getNum=input[1].value
   	  
   	  var getTotal=input[2].value
   	     
   	   if(getNum !="" && getTotal !=""){
   	   
   		  input[3].value=getNum*getTotal
   	   }  	   
   }
  
   $(function(){
		
		$("#orderForm").validate({
				
			
			submitHandler: function(form) {
			    
				/* 将表单数据转化成json格式 */
				var obj = $('#orderForm').serializeJSON();
				/* 将JSON格式转化成字符串形式 */
				var jsonString = JSON.stringify(obj);
				
				$.ajax({
					type:"post",
					url:"order/insert.do",
					contentType:"application/json; charset=utf-8",
					data:jsonString,
					success: function(data){
					         
					    layer.msg(data.msg,{icon:data.num,time:2000},function(){
					        
					    	parent.refreshTable();
					        
					    	parent.layer.closeAll();	
					    
					    });
					}
					
				});
			 }
  
		});
		
   });
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>