package acme.me.http;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ApacheHttpClientView {
    public static void main(String[] args) throws Exception{
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            String url = "http://192.168.10.101:8080/angelcare/API/AppEditMember.html?userAccount=test&account=test&timeStamp=2014-12-1803:06&data=bdac53c3ce4794bd875578945b24ee1ea6aebf75e12fce87a75c1eb43005ba54&realName=陈明轩&Sex=2&Address=成都市高新区天府软件园D6401&Phone=15828548629";
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(URI.create(url));
            CloseableHttpResponse httpresponse = httpclient.execute(httpPost);
            HttpEntity entity = httpresponse.getEntity();
            System.out.println(entity.toString());
        } catch(Exception e){
            
        }finally {
            httpclient.close();
        }
    }
}
