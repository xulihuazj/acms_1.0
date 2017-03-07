package cn.edu.haut.cssp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 生成页面需要的配置
 * Description:
 * @author: xulihua
 * @date: 2017年1月19日上午9:37:05
 * @note
 */
public class PageConfig extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置响应的格式
		response.setContentType("appliction/javascript");
		// 设置响应的编码
		response.setCharacterEncoding("utf-8");
	}

}
