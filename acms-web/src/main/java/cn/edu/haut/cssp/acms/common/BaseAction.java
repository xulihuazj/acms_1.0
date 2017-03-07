package cn.edu.haut.cssp.acms.common;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.edu.haut.cssp.acms.kit.json.JSONException;
import cn.edu.haut.cssp.acms.kit.json.JsonMapper;
import cn.edu.haut.cssp.exception.ServiceException;



/**
 *
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:16:37
 */
public class BaseAction {

	protected final transient Logger logger = LoggerFactory.getLogger(BaseAction.class);
	public static final String SUCCESS = "success";

	public BaseAction() {
		this.logger = LoggerFactory.getLogger(super.getClass());
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
		for (PropertyEditorSupport editor : SpringBeanUtil.getBeansOfType(PropertyEditorSupport.class))
			binder.registerCustomEditor(String.class, editor);
	}

	public void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	public void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	public void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	public void renderTemplate(HttpServletRequest request, HttpServletResponse response, String viewName,
			ModelMap model) {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		try {
			WebApplicationContext wac = RequestContextUtils.getWebApplicationContext(request);
			Locale locale = RequestContextUtils.getLocale(request);
			((RichFreeMarkerViewResolver) wac.getBean(RichFreeMarkerViewResolver.class))
					.resolveViewName(viewName, locale).render(model, request, response);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}
	}

	public void render(HttpServletResponse response, String contentType, String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			this.logger.error(e.getMessage(), e);
		}
	}

	public String toJsonStr(Object value) {
		try {
			return JsonMapper.alwaysMapper().toJson(value);
		} catch (JSONException e) {
			throw ServiceException.create(0, "把value转换为json字符串。error(" + e.getMessage() + ")。", e);
		}
	}

	@ExceptionHandler
	public String exceptionHandle(HttpServletRequest request, Exception ex) {
		ActionConfig config = ActionConfig.DEFAULT_CONFIG;
		try {
			config = (ActionConfig) SpringBeanUtil.getBean(ActionConfig.class);
		} catch (NoSuchBeanDefinitionException e) {
		}
		request.setAttribute("showST", (config.isShowST()) ? "1" : "0");

		if (!(ex instanceof ServiceException)) {
			ServiceException se = ServiceException.create(335806464, ex.getMessage(), ex);
			request.setAttribute("ex", se);
			this.logger.error("未知异常(错误代码：0x14040000)", ex);
		} else {
			ServiceException se = (ServiceException) ex;
			request.setAttribute("ex", ex);
			this.logger.error(ex.getMessage() + "(错误代码：" + se.getErrCodeHexString() + ")", ex);
		}

		String viewName = findMatchingViewName(config.getExceptionMappings(), ex);
		if (StringUtils.isBlank(viewName)) {
			if ((StringUtils.isNotBlank(request.getHeader("x-flash-version")))
					|| ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))))
				viewName = "/error/serviceException";
			else {
				viewName = "/error/serviceExceptionNormal";
			}
		}

		return viewName;
	}

	protected String findMatchingViewName(Properties exceptionMappings, Exception ex) {
		if (exceptionMappings == null)
			return null;

		String viewName = null;
		String dominantMapping = null;
		int deepest = 2147483647;
		for (Enumeration names = exceptionMappings.propertyNames(); names.hasMoreElements();) {
			String exceptionMapping = (String) names.nextElement();
			int depth = getDepth(exceptionMapping, ex);
			if ((depth >= 0) && (((depth < deepest) || ((depth == deepest) && (dominantMapping != null)
					&& (exceptionMapping.length() > dominantMapping.length()))))) {
				deepest = depth;
				dominantMapping = exceptionMapping;
				viewName = exceptionMappings.getProperty(exceptionMapping);
			}
		}

		if ((viewName != null) && (this.logger.isDebugEnabled())) {
			this.logger.debug("Resolving to view '{}' for exception of type [{}], based on exception mapping [{}]",
					new Object[] { viewName, ex.getClass().getName(), dominantMapping });
		}

		return viewName;
	}

	protected int getDepth(String exceptionMapping, Exception ex) {
		return getDepth(exceptionMapping, ex.getClass(), 0);
	}

	private int getDepth(String exceptionMapping, Class<?> exceptionClass, int depth) {
		if (exceptionClass.getName().contains(exceptionMapping)) {
			return depth;
		}

		if (exceptionClass.equals(Throwable.class)) {
			return -1;
		}
		return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
	}
}
