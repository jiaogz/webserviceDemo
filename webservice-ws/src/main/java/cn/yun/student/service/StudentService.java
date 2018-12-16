package cn.yun.student.service;

import cn.yun.pojo.Student;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * 发布webservice服务
 * 1.使用@webservice 声明需要发布的服务类
 * 2.使用
 */

//默认发布该类下所有public修饰的方法，serviceName修改服务类名，targetNamespace修改命名空间，默认包名反写
@WebService (serviceName = "studentService",targetNamespace = "student.yun.cn")
public class StudentService {
    /**
     * operationName:修改方法名
     * WebResult：修改返回值名，默认是return
     * WebParam：修改参数名，默认是arg0
     * @param name
     * @return
     */
    @WebMethod(operationName = "getStudent")
    public @WebResult(name = "student") Student getStudentInfo(@WebParam(name = "studentName") String name){
        Student student = new Student();
        if(name.equals("lisi")){
            student.setName(name);
            student.setAge(18);
            student.setSex("1");
        }

        return student;
    }
    //不发布
    @WebMethod(exclude = true)
    public void sayHello(String name){
        System.out.println("hello");
    }

    public static void main(String[] args) {
        /**
         * 发布服务：
         * 参数：1.地址
         *      2.需要发布的类
         */
        String adders = "http://localhost:80/ws/student/studentService";

        Endpoint.publish(adders,new StudentService());

        System.out.println("服务发布地址："+adders+"?WSDL");

    }

}
