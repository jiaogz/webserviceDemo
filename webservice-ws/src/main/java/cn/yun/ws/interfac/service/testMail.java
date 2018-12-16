package cn.yun.ws.interfac.service;

import javax.xml.ws.Endpoint;

public class testMail {

    public static void main(String[] args) {

        String address = "http://localhost:80/ws/JobService";
        Endpoint.publish(address,new JobServiceImpl());

        System.out.println("发布服务地址："+address+"?WSDL");
    }

}
