package myf.config.result;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * User: mayongfu
 * @Author: 2020/2/28
 * Time: 14:22
 * Description: No Description
 * 该配置类解决前后端联调跨域的问题
 */
@SpringBootConfiguration
public class CorsConfig {
    static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedMethods(ORIGINS);
            }
        };
    }

}
