package myf.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import myf.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class NBLamda {

    public static void main(String[] asd) {
        log.info("sad");
        getData();
    }

    public static void mapLam() {

    }

    public static List<User> getData() {
        Map map1 = new HashMap();
        map1.put("name", "name" );
        map1.put("age", "age" );
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map map = new HashMap();
            map.put("name", "name" + i);
            map.put("age", "age" + i);
            data.add(map);
        }
        data.add(map1);
        List<User> listUser = data.stream().map(map -> {
            User user = new User();
            user.setName(map.get("name"));
            user.setAge(map.get("age"));
            return user;
        }).collect(Collectors.toList());
        log.info("1,{}", JSON.toJSON(listUser));
        List<String> stringData = new ArrayList<>(
                listUser.stream().collect(Collectors.groupingBy(User::getName)).keySet()
        );
        //list 分组
         List<Map<String,String>> data2= new ArrayList<>();
         listUser.stream().collect(Collectors.groupingBy(User::getName)).forEach((kye,vau)->{
           Map<String,String> map6=  vau.stream().collect(Collectors.toMap(User::getName,User::getAge));
             data2.add(map6);
        });

        log.info("2,{}", stringData);
        log.info("3,{}", JSON.toJSON(data2));

        return listUser;
    }
}
