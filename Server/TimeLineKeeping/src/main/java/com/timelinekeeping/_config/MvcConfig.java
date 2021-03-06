package com.timelinekeeping._config;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.constant.I_URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by lethanhtan on 9/2/16.
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.timelinekeeping._config")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Value("${file.store.path}")
    private String path;

    public static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"};

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(IViewConst.LOGIN_VIEW);
        registry.addViewController(I_URI.WEB_LOGIN).setViewName(IViewConst.LOGIN_VIEW);
        registry.addViewController(I_URI.WEB_ERROR_INVALID).setViewName(IViewConst.INVALID_VIEW);
        registry.addViewController(I_URI.WEB_ERROR_PERMISSION).setViewName(IViewConst.PERMISSION_DENIED_VIEW);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        }
        if (!registry.hasMappingForPattern(IContanst.PREFIX_STORE + "/**")) {
            registry.addResourceHandler(IContanst.PREFIX_STORE + "/**").addResourceLocations("file:///" + path);
        }
        System.out.println(path);
    }

    @Bean
    public EmotionInterceptor interceptor() {
        return new EmotionInterceptor();
    }

    @Bean
    public AuthenInterceptor authenInterceptor() {
        return new AuthenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenInterceptor());
        registry.addInterceptor(interceptor()).addPathPatterns(I_URI.API_EMOTION + I_URI.API_EMOTION_UPLOAD_IMAGE)
                .addPathPatterns(I_URI.API_EMOTION + I_URI.API_EMOTION_GET_EMOTION);
    }
}