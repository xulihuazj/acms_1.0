package cn.edu.haut.cssp.acms.bean;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @ClassName: Operator
 * @Description: 登录系统的操作员类
 */
public class Operator implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String OPERATOR = "operator";

	//登录系统的用户名
	private String username;
	//用户的可用菜单
	private Map<String, Menu> menus = new LinkedHashMap<String, Menu>();
	//用户拥有的权限表达式集合
	private Set<String> permissions = new HashSet<String>();
	
	private Object currUser;
	
	/**
	 * @Description：构造函数
	 * @param username 用户名
	 * @param menus 用户拥有的菜单
	 */
	public Operator(String username, Map<String, Menu> menus) {
		this.username = username;
		
		if (menus != null && !menus.isEmpty()) {
			this.menus.putAll(menus);
			this.parserPermission(menus.values());
		}
		
	}
	
	/**
	 * 
	 * @Description：构造函数
	 * @param username 用户名
	 * @param menus 用户拥有的菜单
	 * @param currUser 当前用户信息，具体内容由调用方确定
	 */
	public <T> Operator(String username, Map<String, Menu> menus, T currUser) {
		this(username, menus);
		
		setCurrUser(currUser);
	}
	
	/**
	 * 
	 * @Title: parserPermission
	 * @Description: 解析菜单中的权限表达式
	 * @param menus 用户拥有的菜单
	 */
	private void parserPermission(Collection<Menu> menus) {
		for (Menu menu : menus) {
			if (StringUtils.isNotBlank(menu.getPermission())) {
				permissions.add(menu.getPermission());
			}
			
			parserPermission(menu.getChildren());
		}
	}
	
	/**
	 * 
	 * @Title: getUsername
	 * @Description: 获取登录系统的用户名
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @Title: getPermissions
	 * @Description: 获取用户拥有的权限表达式集合
	 * @return Set<String>
	 */
	public Set<String> getPermissions() {
	    return Collections.unmodifiableSet(permissions);
	}
	
	/**
	 * 
	 * @Title: getTopMenus
	 * @Description: 获取顶层菜单列表
	 * @return Collection<Menu>
	 */
	public Collection<Menu> getTopMenus() {
		return menus.values();
	}

	/**
	 * 
	 * @Title: getTopFunction
	 * @Description: 获取指定的顶层菜单
	 * @param menuId 菜单编号
	 * @return Menu 对应的菜单
	 */
	public Menu getTopMenu(String menuId) {
		return menus.get(menuId);
	}
	
	/**
	 * 
	 * 设置当前操作员对应的用户信息，具体内容由调用方确定，该方法不建议重复调用，
	 * 如果使用构造函数Operator(String username, Map<String, Menu> menus)，该方法建议调用一次，
	 * 如果使用构造函数Operator(String username, Map<String, Menu> menus, T currUser)，则不建议调用该方法
	 * @param currUser
	 */
	public <T> void setCurrUser(T currUser) {
		this.currUser = currUser;
	}
	
	/**
	 * 
	 * 获取当前操作员对应的用户信息
	 * @return 可能返回null，请自行进行判断处理
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCurrUser() {
		return (T) this.currUser;
	}
}
