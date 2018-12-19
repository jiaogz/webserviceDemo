package cn.yun.service;

import cn.yun.pojo.Car;
import cn.yun.pojo.User;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public void saveUser(User user) {
        System.out.println("添加User:"+user);
    }

    @Override
    public User updateUser(Integer userId) {
        Car car = new Car();
        car.setCarName("兰博");
        car.setJg(10000);
        List<Car> cars = new ArrayList<>();
        cars.add(car);


        User user = new User();
        user.setId(userId);
        user.setName("展示");
        user.setAge(18);
        user.setCars(cars);

        return user;
    }

    @Override
    public List<User> findAllUser() {

        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setName("张三");
        user.setAge(18);

        User user2 = new User();
        user.setId(2);
        user.setName("李四");
        user.setAge(18);


        users.add(user);
        users.add(user2);

        return users;
    }

    @Override
    public User findByUserId(Integer id) {
        Car car = new Car();
        car.setCarName("兰博");
        car.setJg(10000);
        List<Car> cars = new ArrayList<>();
        cars.add(car);


        User user = new User();
        user.setId(id);
        user.setName("展示");
        user.setAge(18);
        user.setCars(cars);

        return user;
    }

    /**
     * 发布服务：
     *
     * @param args
     */
    public static void main(String[] args) {

        //1.创建工厂
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        //2.设置地址:http://localhost:8080/rs/userService/user
        factoryBean.setAddress("http://localhost:8080/rs/");
        //3.设置服务接口类
        factoryBean.setServiceBean(new UserServiceImpl());
        //4.添加日志拦截器
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());

        //5.发布服务
        factoryBean.create();

        System.out.println("发布服务成功:http://localhost:8080/rs/userService/user");

    }


}
