package com.rs.aspect;

import static java.lang.System.currentTimeMillis;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ActionAspect {

	@Around(value = "ServiceLayerExecution()")
	public Object auditAroundService(ProceedingJoinPoint joinPoint) throws Throwable {

		Long startTime = currentTimeMillis();

		final Object[] args = joinPoint.getArgs();
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		final Method method = methodSignature.getMethod();
		Object obj = joinPoint.proceed();
		log.info("start time: {}", startTime);
		StringBuilder bd = new StringBuilder();
	
		for (Object arg : args) {
			if (arg != null)
				bd.append(arg.toString() + " ");
		}
		log.info("Calling Method({}) with Args {}", method.getName(), bd.toString());

		Long endTime = currentTimeMillis();
		log.info("end time: {}. Duration: {} ms Method: {} ", endTime, endTime - startTime,
				methodSignature.getMethod().getName());
		return obj;
	}

	@Pointcut("within(com.rs.service.*.*)")
	public void ServiceLayerExecution() {
	}
}
