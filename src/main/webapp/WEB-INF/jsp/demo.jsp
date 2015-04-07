<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title>KindEditor JSP</title>
	<link rel="stylesheet" href="${sessionScope.apppath}/js/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="${sessionScope.apppath}/js/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="${sessionScope.apppath}/js/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="${sessionScope.apppath}/js/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${sessionScope.apppath}/js/kindeditor/plugins/code/prettify.js"></script>
	<script charset="utf-8" src="${sessionScope.apppath}/js/messager/my.messager.js"></script>
	<script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="body"]', {
				cssPath : '${sessionScope.apppath}/js/kindeditor/plugins/code/prettify.css',
				uploadJson : '/uploadServlet',
				fileManagerJson : '/fileManageServlet',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						//document.forms['example'].submit();
						submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						//document.forms['example'].submit();
						submit();
					});
				}
			});
			prettyPrint();
		});
	</script>
</head>
<body>
	<form id="example" name="example" method="post" >
		type:<select name="type">
			<option value="1">Java</option>
			<option value="2">Android</option>
			<option value="3">HTML5 / CSS3</option>
			<option value="4">Linux</option>
			<option value="5">技术探讨</option>
		</select><br />
		author:<input type="text" name="author"><br />
		title:<input type="text" name="title" /><br />
		summary:<input type="text" name="summary" /><br />
		<textarea name="body" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;"></textarea>
		<br />
		<input type="button" onclick="submit()" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
	</form>
</body>
<script type="text/javascript">
function submit(){
	$.ajax({
		type : "post",
		dataType : "json",
		url : "/imarkofu/clovec.do",
		data : $('#example').serializeArray(),
		contentType : 'application/x-www-form-urlencoded; charset=utf-8',
		success : function(data) {
			if (data != undefined) {
				show_my_dmessager(data.msg);
			} else {
				show_my_dmessager("网络错误");
			}
		}
	});
}
</script>
</html>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>