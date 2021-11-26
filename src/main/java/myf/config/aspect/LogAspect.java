//package myf.config.aspect;
//
//import com.google.gson.Gson;
//import myf.config.result.BaseFacadeImpl;
//import myf.config.result.NHttpstatusEnum;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
//
///**
// * aop引入日志拦截web请求
// */
////声明切面
//@Aspect
////声明spring管理的bean
//@Component
//
//public class LogAspect {
//
//    private Logger log = LoggerFactory.getLogger(LogAspect.class);
//    private Gson gson = new Gson();
//
//    /**无需方法上加注解，拦截一切myf包下的所有请求*/
//    @Pointcut("execution(* myf..*.*(..))")
//    public void webLog() {
//    }
//
//
//
//    @Before("webLog()")
//    public void methdbefore(JoinPoint joinPoint) {
//        System.err.println("***************");
//        //打印请求的结果
////        HttpServletRequest request =  BaseFacadeImpl.getRequest();
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        //打印请求内容
//        log.info("===============请求内容===============");
//        log.info("请求地址:" + request.getRequestURL().toString());
//        log.info("请求方式:" + request.getMethod());
//        log.info("请求类方法:" + joinPoint.getSignature());
//        log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
//        log.info("===============请求内容===============");
//
//    }
//
//    /**
//     * 环绕增强
//     *
//     * @param
//     */
//    public void AroundAspct() {
//
//        log.info("===============环绕增强===============");
//    }
//
//    /**
//     *
//     * 返回通知 //方法执行完打印返回的内容
//     * @param o
//     */
//    @AfterReturning(returning = "o", pointcut = "webLog()")
//    public void methodAfterReturing(Object o) {
//        log.info("返回的内容");
//        log.info("Response内容：" + gson.toJson(o));
//        log.info("------------返回内容--------------");
//
//    }
//
//    /**
//     * 后置增强
//     */
//    @After("webLog()")
//    public void logafter() {
//        log.info("------------已完成--------------");
//
//    }
//
//
//}
