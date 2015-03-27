<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
%>
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
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
	</script>
</head>
<body>
	<%=htmlData%>
	<form name="example" method="post" action="${sessionScope.apppath}/imarkofu/clovec.do">
		type:<input type="text" name="type" /><br />
		author:<input type="text" name="author"><br />
		title:<input type="text" name="title" /><br />
		summary:<input type="text" name="summary" /><br />
		<textarea name="body" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;"><%=htmlspecialchars(htmlData)%></textarea>
		<br />
		<input type="submit" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
	</form>
</body>
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