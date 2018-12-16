package cn.yun.student.client;


import cn.yun.pojo.Student;

/**
 * 使用wsimport访问
 */
public class testMain {

    public static void main(String[] args) {
        //服务

        StudentServiceService service = new StudentServiceService();

        Student student = service.getStudentServicePort().getStudentInfo("lisi");


        System.out.println(student.getAge());

    }
}
