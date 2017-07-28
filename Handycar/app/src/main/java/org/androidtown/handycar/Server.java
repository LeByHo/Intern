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
    public void Insertfuel(final String name,final String date, final String price) {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = getConnection("POST","/fuel");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name",name);
                    jsonObject.put("date",date);
                    jsonObject.put("price",price);
                } catch (Exception e) {
                }
                sendJson(con, jsonObject);
                try {
                    System.out.println("code1" + con.getResponseCode());
                    System.out.println("Result is: " + jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void insertcol(final String col){
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = getConnection("POST","/addGroup");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("col",col);
                } catch (Exception e) {
                }
                sendJson(con, jsonObject);
                try {
                    con.getResponseCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void deletecol(final String col){
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = getConnection("POST","/delGroup");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("col",col);
                } catch (Exception e) {
                }
                sendJson(con, jsonObject);
                try {
                    con.getResponseCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void updategrp(final String gname, final Map map){
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = getConnection("POST","/upGroup");
                JSONObject jsonObject = new JSONObject();
                JSONObject json = new JSONObject();
                try {
                    jsonObject.put("name",gname);
                    Set<Map.Entry<String, Integer>> entries = map.entrySet();
                    Iterator<Map.Entry<String, Integer>> i = entries.iterator();

                    while(i.hasNext()) {
                        Map.Entry<String, Integer> entry = i.next();
                        json.put(entry.getKey(),entry.getValue());
                    }
                    jsonObject.put("map",json);
                } catch (Exception e) {
                }
                sendJson(con, jsonObject);
                try {
                    con.getResponseCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public HttpURLConnection getConnection(String method, String path) {
        try {
            URL url = new URL("http://192.168.0.42:3000" + path);
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
