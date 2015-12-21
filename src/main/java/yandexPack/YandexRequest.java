package yandexPack;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author RusTe
 */
public class YandexRequest {

    private byte[] incoming;
    private int length = 0;
    private InputStream in;
    private URLConnection conn;
    private OutputStreamWriter out;

    public YandexRequest() {
        sendDataURL();
    }

    public static void main(String args[]) {
        new YandexRequest();
    }

    private JSONObject makeJson() {
        JSONObject json = new JSONObject();
        Map common = new LinkedHashMap();

        common.put("api_key", "AAwkGkwBAAAA9muWLAMAKp9XjTBZtmOLeiBQJqHX6YEqNdUAAAAAAAAAAAAoEP1ZsBlcVFA_OpP55MK3Ek1r8A==");
        common.put("version", "1.0");

        Map gsm_cell = new LinkedHashMap();
        gsm_cell.put("countrycode", "250");
        gsm_cell.put("operatorid", "1");
        gsm_cell.put("cellid", "29016");
        gsm_cell.put("lac", "717");
        gsm_cell.put("signal_strength", "-80");
        gsm_cell.put("age", "5555");

        JSONArray gsm_cells = new JSONArray();
        gsm_cells.put(gsm_cell);

        json.put("common", common);
        json.put("gsm_cells", gsm_cells);
        // System.out.println(json);
        return json;
    }

    public void sendDataURL() {

//   json={"common": {"version": "1.0","api_key": "AAwkGkwBAAAA9muWLAMAKp9XjTBZtmOLeiBQJqHX6YEqNdUAAAAAAAAAAAAoEP1ZsBlcVFA_OpP55MK3Ek1r8A=="},
//   "gsm_cells": [{"countrycode": 250,"operatorid": 1,"cellid": 29016,"lac": 717,"signal_strength": -80,"age": 5555}],
//       
        String host = "http://api.lbs.yandex.net/geolocation";

        try {
            conn = new URL(host).openConnection();
        } catch (MalformedURLException ex) {
            Logger.getLogger(YandexRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(YandexRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jsonA = "json=" + makeJson().toString();
        int k = jsonA.getBytes().length;
        String header = "POST /geolocation HTTP/1.1\n"
                + "Host:api.lbs.yandex.net\n"
                + "Accept-Encoding: identity\n"
                + "Content-length:" + k + "\n"
                + "Content-type: application/x-www-form-urlencoded\n"
                + "Cache-Control: no-cache\n"
                + "Postman-Token: f7308c39-1a7f-4326-459e-305d491d65c4\n";
        conn.setDoOutput(true);// Triggers POST.
        //conn.setRequestProperty("POST", "/geolocation HTTP/1.1\n");
        conn.setRequestProperty("Host", "api.lbs.yandex.net");
        conn.setRequestProperty("Accept-Encoding", "identity");
        conn.setRequestProperty("Content-length", k + "");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Postman-Token", "f7308c39-1a7f-4326-459e-305d491d65c4");

        // пишем туда HTTP request
        String request = jsonA;
        // String request = "json=" + makeJson() + "";

        System.out.println("request=");
        System.out.println(request);
        System.err.println("======================================");

        try {
            out = new OutputStreamWriter(conn.getOutputStream(), "ASCII");
            out.write(request);
            out.write("\r\n");
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(YandexRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            in = conn.getInputStream();

            incoming = new byte[64 * 1024];
            length = in.read(incoming);
        } catch (IOException ex) {
            Logger.getLogger(YandexRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

//                JSONObject jAnsw = new JSONObject(new String(incoming));
//                System.out.println("jAnsw=" + jAnsw);
        System.out.println("Client query(" + length + " bytes):\n" + new String(incoming).trim());
        // закрываем файл
    }

}
