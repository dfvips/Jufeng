package bean;

public class ChatInfo {
	private String who;
	private String time;
	private String content;
	private String headurl;
	public ChatInfo(String who, String time, String content, String headurl) {
		super();
		this.who = who;
		this.time = time;
		this.content = content;
		this.headurl = headurl;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	@Override
	public String toString() {
		return "ChatInfo [who=" + who + ", time=" + time + ", content=" + content + ", headurl=" + headurl + "]";
	}
	

}
