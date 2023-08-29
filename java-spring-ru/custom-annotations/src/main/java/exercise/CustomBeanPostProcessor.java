package exercise;

import java.lang.reflect.Proxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> beansWithAnnotation = new HashMap<>();
    private Map<String, String> beansLoggingLevel = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            beansWithAnnotation.put(beanName, bean.getClass());
            String logLevel = bean.getClass().getAnnotation(Inspect.class).level();
            beansLoggingLevel.put(beanName, logLevel);
        }

        return bean;

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = beansWithAnnotation.get(beanName);

        if (beanClass == null) return bean;

        String logLevel = beansLoggingLevel.get(beanName);

        return Proxy.newProxyInstance(
                beanClass.getClassLoader(),
                beanClass.getInterfaces(),
                (proxy, method, args) -> {
                    String message = String.format(
                            "Was called method: %s() with arguments: %s",
                            method.getName(), Arrays.toString(args));
                    if (logLevel.equals("info")) {
                        LOGGER.info(message);
                    } else {
                        LOGGER.debug(message);
                    }
                    return method.invoke(bean, args);
                });
    }
    
}
// END
