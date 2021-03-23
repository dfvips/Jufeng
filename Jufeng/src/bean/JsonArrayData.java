package bean;


import java.io.Serializable;

import net.sf.json.JSONArray;

public class JsonArrayData implements Serializable{
	private JSONArray data;
	
	public JSONArray getData() {
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}
	
}
