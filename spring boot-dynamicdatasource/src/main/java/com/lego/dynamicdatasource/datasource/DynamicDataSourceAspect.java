package com.lego.dynamicdatasource.datasource;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用于处理自定义注解，根据注解来选择数据源
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.lego.dynamicdatasource.datasource.DynamicDataSource)")
    public void dataSourceCutpoint() {
    }

    @Around("dataSourceCutpoint() && @annotation(dynamicDataSource)")
    public Object switchDataSource(ProceedingJoinPoint joinPoint, DynamicDataSource dynamicDataSource) throws Throwable {
        String dataSourceType = dynamicDataSource.value().getValue();
        logger.info("数据源切换为：" + dataSourceType);

        DynamicDataSourceHolder.setDataSourceType(dataSourceType);

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            logger.info("切换为默认的主数据库！");
            DynamicDataSourceHolder.setDataSourceType(DynamicDataSourceType.secondary.getValue());
        }
    }
}
