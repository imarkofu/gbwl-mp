/**
 * 自定义一个消息对话框
 * @author gbwl
 */
$(function(){
	$(document.body).append(
			'<div id="my_message" style="width:213px;height:156px;border:1px solid #f9eda6;position:absolute;bottom:2px;right:2px;z-index:999;position:fixed;display:none;box-shadow: -2px 2px 5px 0px #333;background:#ffffff;">' +
			'<div style="height:26px;color:#696864;background:#f9eda6;">' +
	    	'<div id="my_message_title" style="margin-top:6px;margin-left:15px;float:left;font-size:12px;"></div>' +
	        '<div id="my_message_close" style="width:10px;height:10px;margin-top:8px;margin-right:15px;cursor:pointer;float:right;background:url(/images/my_messager_close.png) no-repeat center;">&nbsp;</div>' +
	        '<div style="clear:both;"></div>' +
	        '</div>' +
	        '<div id="my_message_content" style="margin:48px 28px 0px 28px;;font-size:15px;">' +
	    	'</div>' +
	    	'</div>');
	$("#my_message_close").click(function(){
		$("#my_message").slideUp(1000);
	});
});

/**
 * 显示一个提示框
 * @param title		提示框的标题
 * @param content	提示框的内容
 */
function show_my_messager(title, content) {
	$("#my_message").hide();	//避免两次点击时没有弹的问题
	$("#my_message_title").text("");
	$("#my_message_title").append(title);
	$("#my_message_content").text("");
	$("#my_message_content").append(content);
	$("#my_message").slideDown(1000);
	setTimeout(function () { 
		$("#my_message").slideUp(1000);
	}, 3000);
}

function show_my_dmessager(content) {
	show_my_messager("消息提示", content);
}
