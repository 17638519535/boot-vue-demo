package myf.utils;

import com.alibaba.fastjson.JSON;
import com.xiaoleilu.hutool.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import myf.entity.User;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class NBLambda {

    public static void main(String[] asd) {

        //lambdaData()

//        Collections.sort(data,new Price);
//      log.info(returnFa());
        listData();
    }

    /**
     * 集合
     */
  public static void listData(){
      List<User> data =  getData();
      log.info("正序{}",JSON.toJSONString(data.stream().sorted( Comparator.comparing(User::getAge )).collect(Collectors.toList())));
      log.info("倒序{}",JSON.toJSONString(data.stream().sorted( Comparator.comparing(User::getAge ).reversed()).collect(Collectors.toList())));
      //      Collections.sort(data,new Comparator<User>(){
//          @Override
//          public int compare(User o1, User o2) {
//
//              return 0;
//          }
//      });
  }
    /**
     * Lambda
     * @return
     */
    public static List<User> getData() {
        Map map1 = CollUtil.newHashMap();
        map1.put("name", "name"+6 );
        map1.put("age", "5" +1);
        List<Map<String, String>> data = CollUtil.newArrayList();
        for (int i = 0; i < 5; i++) {
            Map<String,String> map = CollUtil.newHashMap();
            map.put("name", "name" + i);
            map.put("age", "3" + i);
            data.add(map);
        }
        data.add(map1);
        List<User> listUser = data.stream().map((map) -> {
            User user = new User();
            user.setName(map.get("name"));
            user.setAge(map.get("age"));
            return user;
        }).collect(Collectors.toList());


        return listUser;
    }

    /**
     * lambda
     */
   public static void lambdaData(){
       List<User> listUser = getData();
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
       Map<String,List<User>> data3= listUser.stream().collect(Collectors.groupingBy(User::getName));
       log.info("4,{}", Objects.toString(data3));
   }
}
