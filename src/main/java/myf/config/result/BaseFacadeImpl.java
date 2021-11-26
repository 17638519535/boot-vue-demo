package myf.config.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mayongfu
 * Date: 2020/2/28
 * Time: 15:17
 * Description: No Description
 */

public class BaseFacadeImpl {
    private Logger logger = LoggerFactory.getLogger(BaseFacadeImpl.class);


    public HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       return request;
    }
    public HttpServletResponse getResponse(){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
      return response;
    }

    public ResponseEntity<Map<String, Object>> responseEntity(NHttpstatusEnum httpStatus){
        Map<String, Object> map = new HashMap<>();
        map.put("code",httpStatus.getCode());
        map.put("message",httpStatus.getMessage());
        map.put("status",httpStatus.getStatus());

        return ResponseEntity.status(httpStatus.getStatus()).body(map);
    };

    public ResponseEntity<Map<String, Object>> responseEntity(String code, int status, String message ){
        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("message",message);
        map.put("status",status);

        return ResponseEntity.status(status).body(map);
    };

    public ResponseEntity<Map<String, Object>> responseEntity(NHttpstatusEnum httpStatus, String message ){
        Map<String, Object> map = new HashMap<>();
        map.put("code",httpStatus.getCode());
        map.put("message",message);
        map.put("status",httpStatus.getStatus());
        return ResponseEntity.status(httpStatus.getStatus()).body(map);
    };
    public ResponseEntity<Map<String, Object>> responseEntity(NHttpstatusEnum httpStatus, Object data){
        Map<String, Object> map = new HashMap<>();
        map.put("code",httpStatus.getCode());
        map.put("message",httpStatus.getMessage());
        map.put("data",data);
        map.put("status",httpStatus.getStatus());

        return ResponseEntity.status(httpStatus.getStatus()).body(map);
    };

    public ResponseEntity<Map<String, Object>> responseEntity(NHttpstatusEnum httpStatus, Object data, Object data2){
        Map<String, Object> map = new HashMap<>();
        map.put("code",httpStatus.getCode());
        map.put("message",httpStatus.getMessage());
        map.put("data",data);
        map.put("data2",data2);
        map.put("status",httpStatus.getStatus());

        return ResponseEntity.status(httpStatus.getStatus()).body(map);
    };
}
