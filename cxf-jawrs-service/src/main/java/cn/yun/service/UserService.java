package cn.yun.service;

import cn.yun.pojo.User;

import javax.ws.rs.*;
import java.util.List;

/**
 * @Path:访问当前接口的路径
 * @Produces：服务器支持返回的数据类型
 *  @Consumes：服务器支持的请求的数据格式类型
 */
@Path("/userService")
@Produces("*/*")
public interface UserService {

    //请求方式：新增
    @POST
    //访问当前方法的路径：http://localhost:8080/userService/user
    @Path("/user")
    //服务器支持请求的数据格式类型
    @Consumes({"application/xml","application/json"})
    void saveUser(User user);


    @PUT
    @Path("/user")
    @Consumes({"application/xml","application/json"})
    @Produces({"application/xml","application/json"})
    User updateUser(Integer userId);

    @GET
    @Path("/user")
    @Produces({"application/xml","application/json"})
    List<User> findAllUser();

    @GET
    @Path("/user/{id}")
    @Consumes({"application/xml","application/json"})
    @Produces({"application/json"})
    User findByUserId(@PathParam("id") Integer id);

}
