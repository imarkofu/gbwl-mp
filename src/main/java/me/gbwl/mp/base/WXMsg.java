package me.gbwl.mp.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class WXMsg {
	
	// MsgType 消息类型
	public static final String WX_MSGTYPE_TEXT = "text"; // 微信消息类型:普通消息
	public static final String WX_MSGTYPE_EVENT = "event"; // 微信消息类型:事件消息推送
	// Event 事件类型
	public static final String WX_EVENTTYPE_SUBSCRIBE = "subscribe"; // 微信事件类型 : subscribe
	public static final String WX_EVENTTYPE_UNSUBSCRIBE = "unsubscribe"; // 微信事件类型 : unsubscribe 
	public static final String WX_EVENTTYPE_CLICK = "CLICK"; // 微信事件类型 : CLICK (自定义菜单事件, 这时 EventKey 对应自定义菜单接口中KEY值)
	public static final String WX_EVENTTYPE_VIEW = "VIEW"; // 微信事件类型 : VIEW (点击菜单跳转链接时的事件推送, 这时 EventKey 事件KEY值，设置的跳转URL)
	
	private String URL;
	private String ToUserName; // 开发者微信账号(进行发送时相反)
	private String FromUserName;// 发送方账号(一个OpenID, 进行发送时相反)
	private String CreateTime;// 消息创建时间(整型)
	private String MsgType;// text(普通消息) event(消息类型)
	private String Content;// 文本消息内容
	private String MsgId; // 消息id, 64位整数
	private String Event;
	private String EventKey;
	private String Ticket;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		this.CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		this.MsgId = msgId;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String Ticket) {
		this.Ticket = Ticket;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WXMsg [URL=").append(URL).append(", ToUserName=")
				.append(ToUserName).append(", FromUserName=")
				.append(FromUserName).append(", CreateTime=")
				.append(CreateTime).append(", MsgType=").append(MsgType)
				.append(", Content=").append(Content).append(", MsgId=")
				.append(MsgId).append(", Event=").append(Event)
				.append(", EventKey=").append(EventKey).append(", Ticket=")
				.append(Ticket).append("]");
		return builder.toString();
	}

}
