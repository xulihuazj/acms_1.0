package cn.edu.haut.cssp.acms.common;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	public static final <T> T getBean(Class<T> beanType) {
		return context.getBean(beanType);
	}

	public static final Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static final <T> Collection<T> getBeansOfType(Class<T> beanType) {
		Map beans = context.getBeansOfType(beanType);
		if (beans != null) {
			return beans.values();
		}
		return Collections.emptyList();
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		context = context;
	}
}