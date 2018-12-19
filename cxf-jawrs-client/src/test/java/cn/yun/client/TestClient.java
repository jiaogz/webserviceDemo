package cn.yun.client;

import cn.yun.pojo.Car;
import cn.yun.pojo.User;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class TestClient {

    @Test
    public void saveUser(){
        Car car = new Car();
        car.setCarName("兰博");
        car.setJg(10000);
        List<Car> cars = new ArrayList<>();
        cars.add(car);


        User user = new User();
        user.setId(1);
        user.setName("客户端");
        user.setAge(18);
        user.setCars(cars);

        WebClient
                //请求的地址
                .create("http://localhost:8080/rs/userService/user")
                //数据传输的格式，默认是XML
                .type(MediaType.APPLICATION_JSON_TYPE)
                .encoding("UTF-8")
                //请求的方式
                .post(user);
    }

    @Test
    public void getUserById(){

        User user = WebClient
                .create("http://localhost:8080/rs/userService/user/78")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(User.class);

        System.out.println(user);

    }

    @Test
    public void findAllUsers(){
        Response response = WebClient
                .create("http://localhost:8080/rs/userService/user")
                .get();

        System.out.println(response);
    }

}
