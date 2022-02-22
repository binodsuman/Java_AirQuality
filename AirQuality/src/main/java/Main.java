

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	
	  private static final int RETRY_TIMES = 3;
	  private static final int HTTP_TOO_MANY_REQ = 429;
	  private static final String API_KEY = "579b464db66ec23bdd000001b212112b23fc49ee4e1e9498063e0b1";

    private static final String BASE_URL = "https://api.data.gov.in/resource/3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69?api-key="+API_KEY+"&format=json&offset=0";
   
	
	public static void main(String[] args) {
		
		String state = "Karnataka";
		String city = "Pune"; // Bengaluru, Patna, Delhi, Hyderabad
		String url = BASE_URL;
		
		boolean forState = false;
		
		if(forState) {
		   url = BASE_URL +"&filters[state]="+state;
		}else {
		   url = BASE_URL +"&filters[city]="+city;
		}
	
        showAQI(url); // sequential processing
      
        //System.out.println("Done with main()");
	}
	
	public static void showAQI(String url) {
        

        HttpResponse outGetReq = sendHttpReqAndCheckThresholds(url, "GET", null);
        if (outGetReq.getResponseCode() != HttpsURLConnection.HTTP_OK) {
        	System.out.println("User not fetched correctly: " );
            return;
        }

        JSONObject jsonObject = new JSONObject(outGetReq.getBody());
        
        //System.out.println(jsonObject);
        
       // JSONObject json = new JSONObject("output");//it's object
       // JSONObject changelog = jsonObject.getJSONObject("output");
        JSONArray histories = jsonObject.getJSONArray("records");
        //String aa = histories.getJSONObject(0).getString("city"); //first json in histories
        try {
        JSONObject firstRecord = (JSONObject) histories.get(0);
        System.out.println("*************** Air Pollution Level *********************");
        System.out.println("City :"+firstRecord.getString("city"));
        System.out.println("State :"+firstRecord.getString("state"));
        System.out.println("On :"+firstRecord.getString("last_update"));
        System.out.println("Min Pollution :"+firstRecord.getString("pollutant_min"));
        System.out.println("Average Pollution :"+firstRecord.getString("pollutant_avg"));
        System.out.println("Max Pollution :"+firstRecord.getString("pollutant_max"));
        System.out.println("*****************************");
        }catch(Exception e) {
        	System.out.println("Please enter city/state name correctly");
        }

        

        //System.out.println(jsonObject);
        
    }
	
	private static HttpResponse sendHttpReqAndCheckThresholds(String url, String method, String body) {
        HttpResponse httpResponse = null;
        for (int i = 0; i < RETRY_TIMES; i++) {
            httpResponse = sendHttpReq(url);
            if (httpResponse.getResponseCode() != HttpsURLConnection.HTTP_INTERNAL_ERROR
                    && httpResponse.getResponseCode() != HTTP_TOO_MANY_REQ) {
                break;
            }
            
            System.out.println("HTTP Code is : " + httpResponse.getResponseCode() + " Retrying...");
            try {
                // wait 1-5 sec. and try again
                Thread.sleep((new Random().nextInt(5) + 1) * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return httpResponse;
    }
	
	private static HttpResponse sendHttpReq(String url) {
		//System.out.println("Sending request to URL : " + url.replaceAll("apikey=.*", "apikey=notOnLog"));
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");

           

            //System.out.println("Response Code : " + con.getResponseCode());

            BufferedReader in = null;
            if (con.getErrorStream() != null) {
            	//System.out.println("reading response ...");
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append(System.lineSeparator());
            }
            in.close();
            String out = response.toString().trim();

            
            con.disconnect();

            HttpResponse responseObj = new HttpResponse(out, con.getHeaderFields(), con.getResponseCode());

            return responseObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
