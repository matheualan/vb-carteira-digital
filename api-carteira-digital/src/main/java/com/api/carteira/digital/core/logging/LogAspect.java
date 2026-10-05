package com.api.carteira.digital.core.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    private static final DateTimeFormatter BR_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    //intercepta as requisicoes nos controllers
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {
    }

    @Around("controllerMethods()") //intercepta antes e depois do metodo real
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//      String dataHora = DateUtil.dateFormatterBR(LocalDateTime.now());
        String dataHora = LocalDateTime.now().format(BR_FORMAT);
        String methodHttp = request.getMethod();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("[INIT] {} {} {}.{}() Args: {}",
                dataHora, methodHttp, className, methodName, Arrays.toString(args));

        long countInit = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed(); //executa o metodo real no controller

            long duration = System.currentTimeMillis() - countInit;

            log.info("[END] {} {}.{}() Status: {} Time: {} ms",
                    methodHttp, className, methodName, response.getStatus(), duration);

            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - countInit;

            log.error("[ERROR] {} {}.{}() Time: {} ms - Message: {}",
                    methodHttp, className, methodName, duration, e.getMessage(), e);

            throw e; // mantém a exceção para o fluxo normal de erro
        }

    }

}