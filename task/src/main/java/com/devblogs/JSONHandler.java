package com.devblogs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class JSONHandler {
	public String readStringField(JSONObject jsonObj, String fieldName) {
        Object obj = jsonObj.get(fieldName);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public Boolean readBooleanField(JSONObject jsonObj, String fieldName) {
        Object obj = jsonObj.get(fieldName);
        if (obj != null) {
            String objAsString = obj.toString();
            if (!objAsString.equalsIgnoreCase("null") && !objAsString.equalsIgnoreCase("")) {
                return Boolean.parseBoolean(objAsString);
            }
        }
        return null;
    }

    public Date readDateField(JSONObject jsonObj, String fieldName) throws Exception {
        Object dateAsObject = jsonObj.get(fieldName);
        if (dateAsObject != null) {
            String dateAsString = dateAsObject.toString();
            if (dateAsString != null && !dateAsString.equalsIgnoreCase("null") && !dateAsString.equalsIgnoreCase("")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                return formatter.parse(dateAsString);
            }
        }
        return null;
    }

    public static Date readDateFieldSimpleFormat(JSONObject jsonObj, String fieldName) throws Exception {
        Object dateAsObject = jsonObj.get(fieldName);
        if (dateAsObject != null) {
            String dateAsString = dateAsObject.toString();
            if (dateAsString != null && !dateAsString.equalsIgnoreCase("null") && !dateAsString.equalsIgnoreCase("")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date date = formatter.parse(dateAsString);
                return date;
            }
        }
        return null;
    }
}