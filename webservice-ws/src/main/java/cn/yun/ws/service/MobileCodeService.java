package cn.yun.ws.service;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 调用第三方服务，获取电话号码信息
 *
 *
 */
public class MobileCodeService {
    /**
     * Http get请求
     */
    public static  void get(String mobileCode,String userID) throws Exception{

        //请求地址
        URL url = new URL("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo?mobileCode="+mobileCode+"&userID="+userID);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        if(connection.getResponseCode() == connection.HTTP_OK){

            InputStream is = connection.getInputStream();
            //内存流
            ByteArrayOutputStream bous =new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;

            while ((len = is.read(buffer)) !=-1){
                bous.write(buffer,0,len);
            }

            System.out.println("返回数据："+bous.toString());

            bous.close();
            is.close();

        }
    }

    /**
     * Post请求 ：通过Http-Client 框架来模拟实现 Http请求
     */
    public static void post(String mobileCode ,String userID) throws Exception{
        /**HttpClient访问网络的实现步骤：
         *  1. 准备一个请求客户端:浏览器
         *  2. 准备请求方式： GET 、POST
         *  3. 设置要传递的参数
         *  4.执行请求
         *  5. 获取结果
         */
        HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo");
        //3.设置请求参数
        postMethod.setParameter("mobileCode", mobileCode);
        postMethod.setParameter("userID", userID);
        //4.执行请求 ,结果码
        int code=client.executeMethod(postMethod);
        //5. 获取结果
        String result=postMethod.getResponseBodyAsString();
        System.out.println("Post请求的结果："+result);
    }

    /**
     * 2.Soap请求 ：通过Http-Client 框架来模拟实现 Http请求
     * @throws Exception
     */
    public static void soap() throws Exception{

        HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
        //3.设置请求参数
        postMethod.setRequestBody(new FileInputStream("c:/soap.xml"));
        //修改请求的头部
        postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
        //4.执行请求 ,结果码
        int code=client.executeMethod(postMethod);
        System.out.println("结果码:"+code);
        //5. 获取结果
        String result=postMethod.getResponseBodyAsString();
        System.out.println("Post请求的结果："+result);
    }

    public static void main(String[] args) throws Exception{

//        get("18924742402","");

        post("18924742402","");
    }
}
