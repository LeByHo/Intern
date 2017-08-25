package com.handycar.b2c;

import java.net.HttpURLConnection;
import java.net.URL;

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
}
