package Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lincoln on 12/27/2017.
 */
//59ecdc0cd0258aeee83ae3898de821cb
public class Utils {
    public static final String  API_KEY= "&APPID=accc17237ce7a1cb9b6ecfb06f090752";
    public static final String Base_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static final String ICON_URL = "https://api.openweathermap.org/img/w/";

    public static JSONObject getObject(String tagname, JSONObject jsonObject)throws JSONException{
        JSONObject jObj = jsonObject.getJSONObject(tagname);
        return jObj;
    }

    public static String getString(String tagname, JSONObject jsonObject)throws JSONException{
        return jsonObject.getString(tagname);
    }

    public static Float getFloat(String tagname, JSONObject jsonObject)throws JSONException{
        return (float) jsonObject.getDouble(tagname);
    }

    public static double getDobule(String tagname, JSONObject jsonObject)throws  JSONException{
        return (float)jsonObject.getDouble(tagname);
    }

    public static int getInt(String tagname, JSONObject jsonObject)throws JSONException{
        return (int)jsonObject.getInt(tagname);
    }
}
