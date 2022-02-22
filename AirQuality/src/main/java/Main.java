

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	
	private static final String API_KEY = "**********ec23bdd000001b212112b23fc49ee4e1e94**********";

    private static final String BASE_URL = "https://api.data.gov.in/resource/3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69?api-key="+API_KEY+"&format=json&offset=0";
   
	
	public static void main(String[] args) {
		
		String state = "Karnataka";
		String city = "Chennai"; // Bengaluru, Patna, Delhi, Hyderabad
		String url = BASE_URL;
		
		boolean forState = false;
		
		if(forState) {
		   url = BASE_URL +"&filters[state]="+state;
		}else {
		   url = BASE_URL +"&filters[city]="+city;
		}
	
        showAQI(url); 
  	}
	
	public static void showAQI(String url) {
 
        HttpResponse httpResponse = sendHttpReq(url);
        
        JSONObject jsonObject = new JSONObject(httpResponse.getBody());
        
        JSONArray allRecords = jsonObject.getJSONArray("records");
 
        try {
        JSONObject firstRecord = (JSONObject) allRecords.get(0);
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

     
    }
	
	
	private static HttpResponse sendHttpReq(String url) {

		try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");

         
           
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            
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
