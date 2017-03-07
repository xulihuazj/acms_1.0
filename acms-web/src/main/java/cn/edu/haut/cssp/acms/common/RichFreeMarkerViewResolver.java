package cn.edu.haut.cssp.acms.common;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
/**
 *
 * Description:
 * @author: xulihua
 * @date: 2017年1月5日上午11:29:24
 */
public class RichFreeMarkerViewResolver extends AbstractTemplateViewResolver {
	public RichFreeMarkerViewResolver() {
		setViewClass(RichFreeMarkerView.class);
	}

	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);
		if (viewName.startsWith("/")) {
			view.setUrl(viewName + getSuffix());
		}
		return view;
	}
}