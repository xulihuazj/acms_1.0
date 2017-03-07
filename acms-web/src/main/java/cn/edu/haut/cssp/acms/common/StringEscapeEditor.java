package cn.edu.haut.cssp.acms.common;

import java.beans.PropertyEditorSupport;

/**
 * String转义工具
 *
 */
public class StringEscapeEditor extends PropertyEditorSupport {

	public StringEscapeEditor() {
		super();
	}

	@Override
	public void setAsText(String text) {

		if (text == null) {
			setValue(null);
		} else {
			//String value = XSSEscapeUtils.simpleClean(text);
			String value = XSSEscapeUtils.cleanWithPolicy(text);
			setValue(value);
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return value != null ? value.toString() : "";
	}
}
