package acme.me.j2ee.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class CommonsHttpClientView {

    public static void main(String[] args){
        try{
            HttpClient httpClient = new HttpClient();
            String url = "http://192.168.10.101:8080/angelcare/API/AppEditMember.html";
            PostMethod postMethod = new PostMethod(url);

            NameValuePair[] nameValue = new NameValuePair[8] ;
            nameValue[0] = new NameValuePair("userAccount", "test");
            nameValue[1] = new NameValuePair("account", "test");
            nameValue[2] = new NameValuePair("timeStamp", "2014-12-1802:36");
            nameValue[3] = new NameValuePair("data", "55dc6020b89d2ca6f2f224d3d8fa9e660e53031f81ca1b518ee390f0f36d4433");
            nameValue[4] = new NameValuePair("realName", "陈明轩");
            nameValue[5] = new NameValuePair("Sex", "2");
            nameValue[6] = new NameValuePair("Address", "成都市高新区天府软件园D6401");
            nameValue[7] = new NameValuePair("Phone", "15828548629");

            postMethod.setRequestBody(nameValue);
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

            httpClient.executeMethod(postMethod);
            String responseBody = new String(postMethod.getResponseBodyAsString());
            System.out.println(">>>>"+responseBody);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
