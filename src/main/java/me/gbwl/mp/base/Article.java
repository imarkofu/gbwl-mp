package me.gbwl.mp.base;

/**
 * @Title: Article.java<br>
 * @package: me.gbwl.mp.base<br>
 * @Description:TODO<br>
 * @author gbwl<br>
 * @date 2015年3月27日 下午5:30:51<br>
 */
public class Article {

	private String	title;
	private String	url;
	private String	summary;
	private String	date;
	private Integer type;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article [title=").append(title).append(", url=")
				.append(url).append(", summary=").append(summary)
				.append(", date=").append(date).append(", type=").append(type)
				.append("]");
		return builder.toString();
	}
}
