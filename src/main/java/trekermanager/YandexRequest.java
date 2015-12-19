package trekermanager;

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

    public YandexRequest() {
//        try {
//            sendUnirest();
//            
//        } catch (UnirestException ex) {
//            Logger.getLogger(YandexRequest.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //sendPost();
        sendData();
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

    public void sendData() {

//   json={"common": {"version": "1.0","api_key": "AAwkGkwBAAAA9muWLAMAKp9XjTBZtmOLeiBQJqHX6YEqNdUAAAAAAAAAAAAoEP1ZsBlcVFA_OpP55MK3Ek1r8A=="},
//   "gsm_cells": [{"countrycode": 250,"operatorid": 1,"cellid": 29016,"lac": 717,"signal_strength": -80,"age": 5555}],
//       
        String host = "api.lbs.yandex.net";
        int port = 80;

        Socket socket = new Socket();
        try {
            socket = new Socket(host, port);
        } catch (IOException ex) {
            Logger.getLogger(YandexRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        // пишем туда HTTP request
        String jsonA = "json=" + makeJson().toString();
        int k=jsonA.getBytes().length;

        String header = "POST /geolocation HTTP/1.1\n"
                + "Host:api.lbs.yandex.net\n"
                + "Accept-Encoding: identity\n"
                + "Content-length:" + k + "\n"
                + "Content-type: application/x-www-form-urlencoded\n"
                + "Cache-Control: no-cache\n"
                + "Postman-Token: f7308c39-1a7f-4326-459e-305d491d65c4\n";

        String request = header + jsonA;
        // String request = "json=" + makeJson() + "";

        System.out.println("request=");
        System.out.println(request);
        System.err.println("======================================");

        try {
            socket.getOutputStream().write(request.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(YandexRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            in = socket.getInputStream();

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

    public void sendPost() {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        String jsRequest = "json=" + makeJson() + "";
        RequestBody body = RequestBody.create(mediaType, jsRequest);
        Request request = new Request.Builder()
                .url("http://api.lbs.yandex.net/geolocation")
                .post(body)
                .addHeader("host", "api.lbs.yandex.net")
                .addHeader("accept-encoding", "identity")
                .addHeader("content-length", "742")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "0f17ea17-e408-6bd6-18b0-d94be704977f")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.message());
        } catch (IOException ex) {
            Logger.getLogger(YandexRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendUnirest() throws UnirestException {
        HttpResponse<String> response = Unirest.post("http://api.lbs.yandex.net/geolocation")
                .header("host", "api.lbs.yandex.net")
                .header("accept-encoding", "identity")
                .header("content-length", "742")
                .header("content-type", "application/x-www-form-urlencoded")
                .header("cache-control", "no-cache")
                .header("postman-token", "85fdf083-4f26-a87f-3658-982b336d10b3")
                .body("json={\"common\":{\"api_key\":\"AAwkGkwBAAAA9muWLAMAKp9XjTBZtmOLeiBQJqHX6YEqNdUAAAAAAAAAAAAoEP1ZsBlcVFA_OpP55MK3Ek1r8A==\",\"version\":\"1.0\"},\"gsm_cells\":[{\"countrycode\":\"250\",\"signal_strength\":\"-80\",\"cellid\":\"29016\",\"operatorid\":\"1\",\"age\":\"5555\",\"lac\":\"717\"}]}")
                .asString();
        System.err.println(response.getBody());
    }

    public void sendTry()throws IOException {
        String requestUrl = "http://api.lbs.yandex.net/geolocation";

        URL url = new URL(requestUrl);
        
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        
        httpConnection.connect();
        int rc = httpConnection.getResponseCode();

    }
}
