package cn.yun.ws.interfac.service;

import javax.jws.WebService;

/**
 * endpointInterface:制定需要发布的服务
 */
@WebService(endpointInterface = "cn.yun.ws.interfac.service.Jobservice")
public class JobServiceImpl implements Jobservice {

    public String getJob() {
        return "jave|c++|C#";
    }

    //不会发布
    public void sayHello(){
        System.out.println("Hello");
    }
}
