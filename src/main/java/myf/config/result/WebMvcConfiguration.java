package myf.config.result;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 马永福
 * Date: 2020/2/28
 * Time: 20:36
 * 它继承自WebMvcConfigurerAdapter类，负责注册并生效 我们自己定义的拦截器配置；
 *  * 在这里要注意定义好拦截路径和排除拦截的路径；
 */
@SpringBootConfiguration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginPtor(){return new LoginInterceptor();};


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(this.getLoginPtor()).addPathPatterns("/**") //add拦截所有
                .excludePathPatterns("/myf/api/user-center/**",
                        "/myf/api/user-center/update-pass",
                        "/api/freemarker/**");//需要排除的路径


    /**第二种方式*/
//        //  LoginInterceptor loginInterceptor = new LoginInterceptor();
//        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
//        // 拦截路径,拦截所有的
//        loginRegistry.addPathPatterns("/**");
//        // 排除路径
//        loginRegistry.excludePathPatterns("/");
//        loginRegistry.excludePathPatterns();
//        loginRegistry.excludePathPatterns("/myf/api/user-center/loginout");
//        // 排除资源请求的路径
//        loginRegistry.excludePathPatterns("/css/login/*.css");
//        loginRegistry.excludePathPatterns("/js/login/**/*.js");
//        loginRegistry.excludePathPatterns("/image/login/*.png");
    }


}
