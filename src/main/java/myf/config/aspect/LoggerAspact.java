//package myf.config.aspect;
//
//
//import com.google.gson.Gson;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.stereotype.Component;
//
//import javax.naming.CommunicationException;
//import java.lang.reflect.Method;
//
///**
// * Demo class
// * @author  keriezhang
// * @date 2016/10/31
// */
//@Aspect  //切面类
////声明spring管理的bean
//@Component
////@EnableAutoConfiguration
//public class LoggerAspact {
//
//    private Gson gson = new Gson();
//
//
//    private Logger logger = LoggerFactory.getLogger(LoggerAspact.class);
//   //  @Pointcut("execution(* log*(..))")  /** Pointcut切入点, execution 匹配所有log开头的方法 */
//
//         /**谁 那个方法上用到这个注解就自动切入*/
//    @Pointcut("@annotation(myf.config.aspect.AfterLogger)")
//    public void log(){}
//
//    /**后置通知*/
//    @After("log()")
//    public void after(JoinPoint joinPoint){      //默认的参数Joinpoint
//        try {
//              /**植入点*/
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            Method method = signature.getMethod();           /**拿到method*/
//            /**读取目标方法上的注解 第一种方式*/
//            // int numm =  methoda.getAnnotation(AfterLogger.class).logType();
//            int logtype = 0;
//            String resource = null;
//            /**第二种方式*/
//            AfterLogger afterlogger = method.getAnnotation(AfterLogger.class);
//            if(afterlogger != null){
//                logtype = afterlogger.logType();
//                resource = afterlogger.resource();
//            }else{
//
//                throw new CommunicationException("操作日志失败");
//            }
//
//            /**获取访问的路径 包.类.方法*/
//            String methods = joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName();
//
//            /**获取请求的参数*/
//            String parameter = "";
//              Object[] parameterArray =   joinPoint.getArgs();
//                for(int i = 0;i<parameterArray.length;i++){
//                    parameter =parameter+parameterArray[i]+(i == parameter.length()-1?"":",");
//                }
//
//                        /**控制台输出*/
//            logger.info("*******Log start*******");
//            logger.info("请求方法:"+joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName());
//            logger.info("方法描述:" + resource+"\t"+logtype);
//            logger.info("请求参数：" +parameter);
//            String a = resource+logtype;
//                  System.err.println(a);
//            // 写入数据库
//            logger.info("*写入数据库*");
////             SystemLog systemlog = new SystemLog();
////             systemlog.setId(new Random().nextInt(9999)+1000);
////             systemlog.setCreateDate(new Date());
////            // systemlog.setParams();
////             systemlog.setParams(parameter);
////             systemlog.setDescription(resource+","+String.valueOf(logtype));
////             systemlog.setMethod(joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName());
////
////              boolean f = logService.addLogger(systemlog);
//              logger.info(true==true?"日志添加成功":"日志添加失败");
//
//
//            // TODO
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("错误信息");
//        }
//
//    }
//    /**
//     * 异常后置通知
//     */
////    @AfterThrowing(throwing = "e",pointcut = "log()")
////    public void logthrow(Joinpoint joinpoint,Exception e){
////        logger.info("方法异常");
////    }
//
//    /**
//     *
//     * 返回通知 //方法执行完打印返回的内容
//     * @param o
//     */
//    @AfterReturning(returning = "o", pointcut = "log()")
//    public void methodAfterReturing(Object o) {
//        logger.info("返回的内容");
//        logger.info("Response内容：" + gson.toJson(o));
//        logger.info("------------返回内容--------------");
//
//    }
//
//
//}
//
