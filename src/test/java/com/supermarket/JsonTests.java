package com.supermarket;
import com.alibaba.fastjson.JSON;
import com.supermarket.domain.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JsonTests {

    /**
     * 测试字符串json
     *
     */
    @Test
    public void testStringJson() {
        //字符串直接转json
        // String data="{'name':'张三','age':24}";
        String data="{\"name\":\"张三\",\"age\":24}"; // 要求必须用双引号
        String json = JSON.toJSONString(data);
        System.out.println(json);
        // "{\"name\":\"张三\",\"age\":24}"
    }

    /**
     * 测试对象json
     */
    @Test
    public void testObjectJson() {
        //将对象解析成Json格式：
        User user=new User();
        user.setId(111);
        user.setUserCode("usercode1");
        user.setUserName("张三");
        user.setGender(1);
        //将对象转为JSON格式
        String json1 = JSON.toJSONString(user);
        System.out.println(json1);  //输出
        // {"createdBy":0,"gender":1,"id":111,"modifyBy":0,"userCode":"usercode1","userName":"张三","userRole":0}
    }

    /**
     * 测试集合json
     */
    @Test
    public void testCollectionJson() {
        //集合转成JSON格式
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        String json2 = JSON.toJSONString(list);
        System.out.println(json2);
        // ["aa","bb","cc"]
    }

    /**
     * 测试泛型对象json
     */
    @Test
    public void testGenericObjectJson() {
        //泛型对象转为JSON格式
        User u1 = new User();
        u1.setUserCode("aa");
        u1.setUserName("11");
        u1.setUserPassword("1234567");

        User u2 = new User();
        u2.setUserCode("bb");
        u2.setUserName("22");
        u2.setUserPassword("2345678");

        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);

        String json3 = JSON.toJSONString(userList);
        System.out.println(json3);
        // [
        //     {
        //         "createdBy":0,
        //         "gender":0,
        //         "id":0,
        //         "modifyBy":0,
        //         "userCode":"aa",
        //         "userName":"11",
        //         "userPassword":"1234567",
        //         "userRole":0
        //     },
        //     {"createdBy":0,"gender":0,"id":0,"modifyBy":0,"userCode":"bb","userName":"22","userPassword":"2345678","userRole":0}
        // ]
    }

    /**
     * 测试哈希表json
     */
    @Test
    public void testHashMapJson() {
        // 将哈希表转为json对象
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("name", "张三");
        resultMap.put("age", "24");
        resultMap.put("address", "广州");
        String json4 = JSON.toJSONString(resultMap);
        System.out.println(JSON.toJSONString(json4));
        // {"address":"广州","age":"24","name":"张三"}
    }

}
