package cn.yun.service;

import cn.yun.pojo.Employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmployeeManageImpl implements EmployeeManage{

    private List<Employee> employees = new ArrayList<>();

    public void add(Employee employee){
        employees.add(employee);
    }

    public Employee findEmpById(int id){
        Iterator<Employee> iterator = employees.iterator();
        while(iterator.hasNext()){
            Employee e = iterator.next();
            if(id == e.getId()){
                return e;
            }
        }
        return null;
    }

}
