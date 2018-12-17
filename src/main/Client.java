package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

import com.sun.net.httpserver.Authenticator.Success;

public class Client {

    private final String USER_AGENT = "Mozilla/5.0";
    private String[] IPs;
    int[] living;
    int coordinator;

    Client() {
        this.IPs = new String[]{ "localhost:8000"};
        this.living = new int[IPs.length];
    }

    public boolean heartbeat(int age) {
        int i = 0;
        int max = -1;
        int idx = -1;
        for (i = 0; i < IPs.length; i++){
            String url = IPs[i];
            try {
                int res = Integer.valueOf(sendGet(url, "heartbeat"));
                if (res > 0) {
                    this.living[i] = res;
                    if (max < res) {
                        max = res;
                        idx = i;
                    }
                    System.out.println(url + " is alive: "+ String.valueOf(res));
                } else {
                    this.living[i] = -1;
                    System.out.println(url + " is dead");
                }
            } catch (Exception e) {
                this.living[i] = -1;
                System.out.println(url + " is dead ");
                //e.printStackTrace();
            }
        }
        this.coordinator = idx;
        return (max == age)? true : false;
    }

    public boolean commitProcedure(HashMap<String, String> data){
        String params = paramSerializer(data);
        System.out.println("Comitting: "+params);
        String url = IPs[coordinator];
        boolean success;
        try {
            success = Boolean.valueOf(sendGet(url, "commit", params));
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }

    /*public boolean pushProcedure(HashMap<String, String> data) {
        String params = paramSerializer(data);
        String url = IPs[coordinator];
        return Boolean.valueOf(sendGet(url, "commit", params));
    }*/

    // HTTP GET request
    public String sendGet(String url, String route) throws Exception {
        return sendGet(url, route, "");
    }

    public String sendGet(String url, String route, String params) throws Exception {
        String urlReq = "http://" +url+ "/" + route;
        if (!params.equals("")) urlReq += "/" + params;

        URL obj = new URL(urlReq);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        int responseCode = 0;
        // add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        return response.toString();

    }

    // SERIALIZACION DE PARAMETROS PARA PASO POR GET
    public String paramSerializer(HashMap<String, String> data){
        Iterator it = data.entrySet().iterator();
        String serialized = "?";
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            serialized += pair.getKey() + "=" + pair.getValue() + "&";
        }
        return serialized.substring(0, serialized.length()-1);
    }

}