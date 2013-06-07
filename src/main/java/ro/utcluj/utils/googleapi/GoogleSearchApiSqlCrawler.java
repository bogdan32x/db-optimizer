package ro.utcluj.utils.googleapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GoogleSearchApiSqlCrawler {

	public static void main(String[] args) {
		List<String> linkList = new ArrayList<String>();

		try {
			initCustomGoogleSearch(linkList, 1, 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void initCustomGoogleSearch(List<String> sqlList, int startCounter, int maxCounter)
			throws IOException, InterruptedException {

		do {

			String google = "https://www.googleapis.com/customsearch/v1?";
			String key = "key=AIzaSyAvg5BlLiBzUdY8dXxrC3csJTTe8h8Zkic";
			String query = "&q=filetype:sql";
			String cx = "&cx=003791357122038516471%3A4n8gyxqmgzu";
			String start = "&start=" + startCounter;

			URL url = new URL(google + key + cx + query + start);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				if (output.contains("\"totalResults\": ")) {
					String totalResults = output.substring(output.indexOf("\"totalResults\": \"")
							+ ("\"totalResults\": \"").length(), output.indexOf("\","));
					maxCounter = Integer.valueOf(totalResults);
					System.out.println(maxCounter);
				}

				if (output.contains("\"link\": \"")) {
					String link = output.substring(output.indexOf("\"link\": \"") + ("\"link\": \"").length(),
							output.indexOf("\","));
					sqlList.add(link); // Will print the google search links
					//System.out.println(link);
				}
			}
			conn.disconnect();
			startCounter += 10;
			System.out.println(startCounter + " " + maxCounter + " " + sqlList.size());
			// Reader reader = new InputStreamReader(conn.getInputStream());
			// GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
			//
			// // Show title and URL of 1st result.
			// System.out.println(results);
			// System.out.println(results.getResponseData());
			// // System.out.println(results.getResponseData().getResults().get(0).getUrl());
			// System.out.println(url.getQuery());
			initCustomGoogleSearch(sqlList, startCounter, maxCounter);
			Thread.sleep(1000);
		} while (startCounter < maxCounter);
	}

}
