package com.sendgrid.smtpapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SMTPAPI {

  private JSONObject header = new JSONObject();

  public SMTPAPI()  {

  }

  public SMTPAPI(JSONObject header) {
    this.header = header;
  }

  private static String[] toArray(JSONArray json) throws JSONException {
    ArrayList<String> parse = new ArrayList<String>();
    for (int i = 0; i < json.length(); i++) {
      parse.add(json.getString(i));
    }
    return parse.toArray(new String[parse.size()]);
  }

  private static JSONArray fromArray(String[] array) {
    JSONArray jsonArr = new JSONArray();
    for (int i = 0; i < array.length; i++) {
      jsonArr.put(array[i]);
    }
    return jsonArr;
  }

  public SMTPAPI addTo(String to) throws JSONException {
    if (!this.header.has("to")) {
			this.header.put("to", new JSONArray());
		}
		this.header.accumulate("to", to);
    return this;
  }

  public SMTPAPI addTos(String[] to) throws JSONException {
    for (int i = 0; i < to.length; i ++) {
      addTo(to[i]);
    }
    return this;
  }

  public SMTPAPI setTos(String[] to) throws JSONException {
    this.header.put("to",fromArray(to));
    return this;
  }

  public String[] getTos() throws JSONException {
    return SMTPAPI.toArray(this.header.getJSONArray("to"));
  }

  public SMTPAPI addSubstitution(String key, String val) throws JSONException {
    if (this.header.isNull("sub")) {
      this.header.put("sub", new JSONObject());
    }
		JSONObject subs = this.header.getJSONObject("sub");
		if (!subs.has(key)) {
			subs.put(key, new JSONArray());
		}
    subs.accumulate(key, val);
    return this;
  }

  public SMTPAPI addSubstitutions(String key, String[] val) throws JSONException {
    for (int i = 0; i < val.length; i++) {
      addSubstitution(key, val[i]);
    }
    return this;
  }

  public SMTPAPI setSubstitutions(JSONObject subs) throws JSONException {
    this.header.put("sub", subs);
    return this;
  }

  public JSONObject getSubstitutions() throws JSONException {
    return this.header.getJSONObject("sub");
  }

  public SMTPAPI addUniqueArg(String key, String val) throws JSONException {
    if (this.header.isNull("unique_args")) {
      this.header.put("unique_args", new JSONObject());
    }
    this.header.getJSONObject("unique_args").put(key, val);
    return this;
  }

  public SMTPAPI setUniqueArgs(Map<String, String> args) throws JSONException {
    this.header.put("unique_args", args);
    return this;
  }

  public SMTPAPI setUniqueArgs(JSONObject args) throws JSONException {
    this.header.put("unique_args", args);
    return this;
  }

  public JSONObject getUniqueArgs() throws JSONException {
    return this.header.getJSONObject("unique_args");
  }

  public SMTPAPI addCategory(String val) throws JSONException {
    if (!this.header.has("category")) {
			this.header.put("category", new JSONArray());
		}
		this.header.accumulate("category", val);
    return this;
  }

  public SMTPAPI addCategories(String[] vals) throws JSONException {
    for (int i = 0; i < vals.length; i++) {
      addCategory(vals[i]);
    }
    return this;
  }

  public SMTPAPI setCategories(String[] cat) throws JSONException {
    this.header.put("category", cat);
    return this;
  }

  public String[] getCategories() throws JSONException {
    return SMTPAPI.toArray(this.header.getJSONArray("category"));
  }

  public SMTPAPI addSection(String key, String val) throws JSONException {
    if (this.header.isNull("section")) {
      this.header.put("section", new JSONObject());
    }
    this.header.getJSONObject("section").put(key, val);
    return this;
  }

  public SMTPAPI setSections(Map<String, String> sec) throws JSONException {
    return this.setSections(new JSONObject(sec));
  }


  public SMTPAPI setSections(JSONObject sec) throws JSONException {
    this.header.put("section", sec);
    return this;
  }

  public JSONObject getSections() throws JSONException {
    return this.header.getJSONObject("section");
  }

  public SMTPAPI addFilter(String filter, String setting, String val) throws JSONException {
    if (this.header.isNull("filters")) {
      this.header.put("filters", new JSONObject());
    }
    if (this.header.getJSONObject("filters").isNull(filter)) {
      this.header.getJSONObject("filters").put(filter, new JSONObject());
      this.header.getJSONObject("filters").getJSONObject(filter).put("settings", new JSONObject());
    }
    this.header.getJSONObject("filters").getJSONObject(filter).getJSONObject("settings").put(setting, val);
    return this;
  }

  public SMTPAPI addFilter(String filter, String setting, int val) throws JSONException {
    if (this.header.isNull("filters")) {
      this.header.put("filters", new JSONObject());
    }
    if (this.header.getJSONObject("filters").isNull(filter)) {
      this.header.getJSONObject("filters").put(filter, new JSONObject());
      this.header.getJSONObject("filters").getJSONObject(filter).put("settings", new JSONObject());
    }
    this.header.getJSONObject("filters").getJSONObject(filter).getJSONObject("settings").put(setting, val);
    return this;
  }

  public SMTPAPI setFilters(JSONObject filters) throws JSONException {
    this.header.put("filters", filters);
    return this;
  }

  public JSONObject getFilters() throws JSONException {
    return this.header.getJSONObject("filters");
  }

  private String escapeUnicode(String input) {
    StringBuilder sb = new StringBuilder();
    int len = input.length();
    for (int i = 0; i < len; i++) {
      int code = Character.codePointAt(input, i);
      if (code > 127) {
        sb.append(String.format("\\u%x", code));
      } else {
        sb.append(String.format("%c", code));
      }
    }
    return sb.toString();
  }

  public String jsonString() {
    return escapeUnicode(this.header.toString());
  }
}