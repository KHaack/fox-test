package haack.fox_test.extractor.Spotlight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import haack.fox_test.extractor.AbstractExtractor;

/**
 * 
 * https://github.com/dbpedia-spotlight/dbpedia-spotlight
 * https://hub.docker.com/r/dbpedia/spotlight-english/
 * @author Kevin Haack
 *
 */
public class ExtractorSpotlight extends AbstractExtractor {

	private static String SPOTLIGHT_QUERY_URL = "https://github.com/dbpedia-spotlight/dbpedia-spotlightrest/annotate";
	//private static String SPOTLIGHT_QUERY_URL = "http://spotlight.dbpedia.org/dev/rest/annotate";
	// private static String dev = "http://localhost:2223/rest/annotate";

	@Override
	public String extract(String toExtract) {
//		RestTemplate restTemplate = new RestTemplate();
//		String annotation = restTemplate.getForObject(SPOTLIGHT_QUERY_URL, String.class);
//		return annotation;
		
		
		System.out.println("*** HTTP Client");
		String disambiguator = "Default";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(SPOTLIGHT_QUERY_URL);
		post.addHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		post.addHeader("Accept", "application/json");
		
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("disambiguator", disambiguator));
		nvps.add(new BasicNameValuePair("confidence", "-1"));
		nvps.add(new BasicNameValuePair("support", "-1"));
		nvps.add(new BasicNameValuePair("text", toExtract));
		
		
		CloseableHttpResponse response = null;
		StringBuilder builder = new StringBuilder();
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps));
			
			response = httpclient.execute(post);

		    System.out.println(response.getStatusLine());
		    HttpEntity entity = response.getEntity();

		    builder.append(ExtractorSpotlight.consumeContent(entity.getContent()));
		    
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(null != response) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return builder.toString();
	}

	private static String consumeContent(InputStream is) {
		StringBuffer jsonString = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;

			while ((line = br.readLine()) != null) {
				jsonString.append(line);
				jsonString.append("\n");
			}
			System.out.println(jsonString);
			br.close();
		} catch (Exception ex) {

		}
		
		return jsonString.toString();
	}

}
