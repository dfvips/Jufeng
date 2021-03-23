package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class stream {
	public static void OutStream(String path,String name,String text) {
		File file = new File(path,name);
		BufferedWriter writer = null;
		if(!file.exists()){  
            try {  
                file.createNewFile(); 
            } catch (Exception e){}  
        }
		try {  
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "gbk")); 
			writer.write(text);
	    } catch (IOException e) {  
	    	e.printStackTrace();  
	    }finally {  
	    	try {  
	    		if(writer != null){  
	    			writer.close();  
	            }  
	        } catch (IOException e) {  
	        	e.printStackTrace();  
	        }  
	    }  
	}
	
	public static String InputStream(String path,String name) {
		String json = "";
		BufferedReader reader = null;
		File file = new File(path,name);

		if(!file.exists()){  
            try {  
                file.createNewFile(); 
            } catch (Exception e){}  
            
            JSONObject MyObject = new JSONObject();
    		JSONArray MyContact = new JSONArray();
    		MyObject.put("contact", MyContact);
    		stream.OutStream(path, name, MyObject.toString());
    		return MyObject.toString();
        }else {
    		try {  
    			FileInputStream fileInputStream = new FileInputStream(file);  
    	        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "gbk");  
    	        reader = new BufferedReader(inputStreamReader);  
    	        String tempString = null;  
    	        while ((tempString = reader.readLine()) != null) {  
    	        	json += tempString;  
    	        }  
    	        reader.close();  
    	    } catch (IOException e) {  
    	        e.printStackTrace();  
    	    } finally {  
    	        if (reader != null) {  
    	        	try {  
    	        		reader.close();  
    	            } catch (IOException e) {  
    	            	e.printStackTrace();  
    	            }  
    	        }  
    	    }  
    		return json;
        }

	}
}
