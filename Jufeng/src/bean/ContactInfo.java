package bean;

import net.sf.json.JSONArray;

public class ContactInfo {
	private String id;
	private String headurl;
	private String name;
	private JSONArray chat;
	public ContactInfo(String id, String headurl, String name, JSONArray chat) {
		super();
		this.id = id;
		this.headurl = headurl;
		this.name = name;
		this.chat = chat;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JSONArray getChat() {
		return chat;
	}
	public void setChat(JSONArray chat) {
		this.chat = chat;
	}
	
	
}
