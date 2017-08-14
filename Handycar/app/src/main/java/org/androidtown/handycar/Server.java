package org.androidtown.handycar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by GE62 on 2017-07-17.
 */

public class Server {
    public HttpURLConnection getConnectionurl(String str) {
        try {
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return con;
        } catch (Exception e) {
            return null;
        }
    }
    public HttpURLConnection getConnection(String method, String path) {
        try {
            URL url = new URL("http://192.168.219.194:3000" + path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            return con;
        } catch (Exception e) {
            return null;
        }
    }
    public JSONArray readJson(HttpURLConnection con) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new JSONArray(response.toString());
        } catch (Exception e) {
            return null;
        }
    }
    private void sendJson(HttpURLConnection con, JSONObject json) {
        try {
            OutputStream out = con.getOutputStream();
            out.write(json.toString().getBytes());
            out.flush();
        } catch (Exception e) { }
    }
}
