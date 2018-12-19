package cn.yun.pojo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 基于Rest风格的WebService：可以传递XML、JSON
 *
 * @XmlRootElement：指定对象序列化为XML、JSON数据时根节点的名称
 * XML:
 *  <User>
 *      <id></id>
 *      <name></name>
 *  </User>
 *
 *  JSON:
 *  {"User":{"id":100,"username":"张三"}}
 */
@XmlRootElement(name = "User")
public class User {
     private Integer id;
     private String name;
     private Integer age;
    private List<Car> cars;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", cars=" + cars +
                '}';
    }
}
