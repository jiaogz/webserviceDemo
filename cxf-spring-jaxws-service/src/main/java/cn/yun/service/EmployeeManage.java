package cn.yun.service;

import cn.yun.pojo.Employee;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName = "EmployeeManage")
public interface EmployeeManage {

    void add(@WebParam(name = "Employee") Employee employee);

    @WebResult(name = "Employee") Employee findEmpById(@WebParam(name = "EmployeeID") int id);
}
