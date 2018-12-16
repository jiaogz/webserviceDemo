package cn.yun.ws.interfac.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 */
@WebService(serviceName = "JobService")
public interface Jobservice {
    @WebMethod(operationName = "getJob")
    String getJob();
}
