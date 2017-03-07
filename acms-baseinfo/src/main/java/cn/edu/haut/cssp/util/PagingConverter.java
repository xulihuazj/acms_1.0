package cn.edu.haut.cssp.util;


import java.util.List;

import com.xdja.platform.rpc.proxy.page.LitePaging;

/**
 * 负责把指定的分页对象转换为简化的分页对象，在执行转换之前需要手动设置分页所需的5条信息对应的获取方法名
 * Description:
 * @author: xulihua
 * @date: 2017年1月21日下午4:51:54
 * @note
 */
public class PagingConverter {
	
	/**
	 * 获取当前页数的方法名
	 */
	public static String pageNoMethodName = "getPageNo";
	/**
	 * 获取每页显示数据条数的方法名
	 */
	public static String pageSizeMethodName = "getPageSize";
	/**
	 * 获取数据总条数的方法名
	 */
	public static String totalCountMethodName = "getTotalCount";
	/**
	 * 获取总页数的方法名
	 */
	public static String totalPageMethodName = "getTotalPage";
	/**
	 * 获取本页数据集合的方法名
	 */
	public static String dataListMethodName = "getDataList";
	
	/**
	 * 
	 * 把传入的分页对象转换为简化的分页对象
	 * @param pageSrcObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> LitePaging<T> convert(Object pageSrcObj) {
		Class<?> srcClass = pageSrcObj.getClass();
		
		LitePaging<T> paging = new LitePaging<T>();
		try {
			paging.setPageNo((Integer)srcClass.getMethod(pageNoMethodName).invoke(pageSrcObj));
			paging.setPageSize((Integer)srcClass.getMethod(pageSizeMethodName).invoke(pageSrcObj));
			paging.setTotalCount((Integer)srcClass.getMethod(totalCountMethodName).invoke(pageSrcObj));
			paging.setTotalPage((Integer)srcClass.getMethod(totalPageMethodName).invoke(pageSrcObj));
			paging.setDataList((List<T>)srcClass.getMethod(dataListMethodName).invoke(pageSrcObj));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return paging;
	}
}
