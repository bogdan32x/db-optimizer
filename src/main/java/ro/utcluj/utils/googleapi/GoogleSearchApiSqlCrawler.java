package ro.utcluj.utils.googleapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleSearchApiSqlCrawler {

	private static final String	destinationDir	= "D:\\rawSql";

	public static void main(final String[] args) {
		try {
			GoogleSearchApiSqlCrawler.initCustomGoogleSearch(1, -1);
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void initCustomGoogleSearch(int startCounter, int maxCounter) throws IOException,
	InterruptedException {

		final String google = "https://www.googleapis.com/customsearch/v1?";
		final String key = "key=AIzaSyAvg5BlLiBzUdY8dXxrC3csJTTe8h8Zkic";
		final String query = "&q=filetype:sql";
		//final String cx = "&cx=003791357122038516471%3A4n8gyxqmgzu";
		final String cx = "&cx=003791357122038516471:jxd-oevi9bq";
		final String start = "&start=" + startCounter;

		final URL url = new URL(google + key + cx + query + start);
		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		final BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {
			if (output.contains("\"totalResults\": ")) {
				final String totalResults = output.substring(output.indexOf("\"totalResults\": \"")
						+ ("\"totalResults\": \"").length(), output.indexOf("\","));
				maxCounter = Integer.valueOf(totalResults);
			}

			if (output.contains("\"link\": \"")) {
				final String link = output.substring(output.indexOf("\"link\": \"") + ("\"link\": \"").length(),
						output.indexOf("\","));
				UrlDownload.fileDownload(link, GoogleSearchApiSqlCrawler.destinationDir);
			}
		}
		conn.disconnect();
		startCounter += 10;
		Thread.sleep(1000);
		if (startCounter < maxCounter || maxCounter == -1) {
			GoogleSearchApiSqlCrawler.initCustomGoogleSearch(startCounter, maxCounter);
		}
	}
}
