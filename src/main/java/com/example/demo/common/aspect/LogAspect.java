package com.example.demo.common.aspect;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 切面日志
 *
 * @author duanxiaoduan
 * @version 2019-04-30
 */
@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
    ThreadLocal<StopWatch> currentThread = new ThreadLocal<StopWatch>();

    @Pointcut("execution(public * com.example.demo.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        currentThread.set(stopWatch);
        final Object[] args = joinPoint.getArgs();
        // 记录下请求内容
        StringBuilder stringBuilder = new StringBuilder(256);
        stringBuilder.append("[URL = ").append(request.getRequestURL().toString())
                .append(", HTTP_METHOD = ").append(request.getMethod())
                .append(", IP = ").append(request.getRemoteAddr())
                .append(", CLASS_METHOD = ").append(joinPoint.getSignature().getDeclaringTypeName()).append(".").append(joinPoint.getSignature().getName())
                .append(", ARGS = ").append(Arrays.toString(args));
        logger.info("start **************************************");
        logger.info(stringBuilder.toString());
        request.setAttribute("body", args[0]);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.debug("return : {}", ret);
    }

    //后置异常通知
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }


    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp){
        StopWatch stopWatch = currentThread.get();
        stopWatch.split();
        long splitTime = stopWatch.getSplitTime();
        logger.debug("[ s = {}ms ]", splitTime);
        logger.debug("end **************************************");
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        final long start = System.currentTimeMillis();
        System.out.println("方法环绕start.....");
        try {
            Object o =  pjp.proceed();
            System.out.println("方法环绕proceed，结果是 :" + o);
            System.out.println(System.currentTimeMillis() - start);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
