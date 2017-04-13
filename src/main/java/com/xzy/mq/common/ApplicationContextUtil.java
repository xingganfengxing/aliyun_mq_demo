package com.xzy.mq.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by RuzzZZ on 2017/4/13.
 */
public class ApplicationContextUtil {

    /** Spring框架应用上下文对象 */
    private static ApplicationContext factory = getApplicationContext();

    static{
        getApplicationContext();
    }

    public static void setFactoryBean(ApplicationContext factory){
        ApplicationContextUtil.factory = factory;
    }
    /**
     * 获得Spring框架应用上下文对象
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext()
    {
        //判断如果 ApplicationContext 的对象 ＝＝ NULL
        if ( factory == null )
        {
            try
            {
                factory = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
            }
            catch ( Exception e1 )
            {
                e1.printStackTrace();
            }
        }
        //返回ApplicationContext
        return factory;
    }

    private ApplicationContextUtil() {}
}
