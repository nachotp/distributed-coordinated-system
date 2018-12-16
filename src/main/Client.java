package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Client {

    private final String USER_AGENT = "Mozilla/5.0";
    private String[] IPs;
    boolean[] living;

    Client() {
        this.IPs = new String[]{ "localhost:8000"};
        this.living = new boolean[IPs.length];
    }

    public void heartbeat(int age) {
        int i = 0;
        for (String url : this.IPs){
            try {
                String res = sendGet(url, "heartbeat");
                if (res.equals("alive")) {
                    this.living[i] = true;
                    System.out.println(url + " is alive");
                } else {
                    this.living[i] = false;
                    System.out.println(url + " is dead");
                }
            } catch (Exception e) {
                this.living[i] = false;
                System.out.println(url + " is dead ");
                //e.printStackTrace();
            }
            
            i++;
        } 
    }

    // HTTP GET request
    public String sendGet(String url, String route) throws Exception {

        URL obj = new URL("http://" +url+ "/" + route);
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

    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());

    }

}