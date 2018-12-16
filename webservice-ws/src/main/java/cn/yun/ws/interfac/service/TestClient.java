package cn.yun.ws.interfac.service;

import cn.yun.ws.interfac.client.JobServiceImplService;
import cn.yun.ws.interfac.client.Jobservice;

public class TestClient {

    public static void main(String[] args) {

        JobServiceImplService service = new JobServiceImplService();

        Jobservice port = service.getJobServiceImplPort();

        String job = port.getJob();

        System.out.println(job);
    }

}
