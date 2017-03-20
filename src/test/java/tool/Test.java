package tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		 Pattern pattern = Pattern.compile("^http://www.app-echo.com/sound/\\d+");
//		  Matcher matcher = pattern.matcher("http://www.app-echo.com/sound/619842");
//		  System.out.println(matcher.matches());
//		  Pattern pattern = Pattern.compile("page_sound_obj = (.*);");
		  Document doc=urlToDoc("http://www.app-echo.com/sound/619842");
//		  System.out.println(doc.html());
//		  System.out.println(doc.getElementsByAttributeValue("class","main-part clearfix").html());
//		  Matcher matcher=pattern.matcher(doc.getElementsByAttributeValue("class","main-part clearfix").html());
		  String html=doc.getElementsByAttributeValue("class","main-part clearfix").html();
		  html=html.substring(html.indexOf("page_sound_obj = ")+17,html.indexOf("};")+1);
		  System.out.println(html);
	}

	public static Document urlToDoc(String url) {
        Document doc = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        RequestConfig requestconfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000)
                .setCookieSpec(CookieSpecs.BEST_MATCH).build();
        httpget.setConfig(requestconfig);

        try {
            response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err
                        .println("Method failed: " + response.getStatusLine());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                doc = Jsoup.parse(in, "utf-8", url);

            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                Thread.sleep(50);
                response.close();
                httpclient.close();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
        return doc;
    }
}
