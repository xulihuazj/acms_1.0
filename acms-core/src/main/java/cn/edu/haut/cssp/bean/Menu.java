package cn.edu.haut.cssp.bean;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

/**
 * 功能菜单类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午3:50:31
 * @note
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = -8395719172587581540L;

	// 菜单标识
	private String id;
	// 菜单名称
	private String name;
	// 权限主键
	private String permissionKey;
	// 权限表达式
	private String permission;
	// 下级菜单
	private Map<String, Menu> children = new LinkedHashMap<String, Menu>();
	// 属性（如：图标）
	private Map<String, Object> properties = new HashMap<String, Object>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

	/**
	 * 
	 * @Title: addChild
	 * @Description: 添加下级菜单
	 * @param child 下级菜单
	 */
	public void addChild(Menu child) {
		children.put(child.getId(), child);
	}
	
	/**
	 * 
	 * 设置该菜单的下级菜单
	 * @param children
	 */
	public void setChildren(Collection<Menu> children) {
		if (CollectionUtils.isNotEmpty(children)) {
			for (Menu menu : children) {
				addChild(menu);
			}
		}
	}
	
	/**
	 * 
	 * @Title: getChildren
	 * @Description: 获取下级菜单
	 * @return Collection<Menu<ID>>
	 */
	public Collection<Menu> getChildren() {
		return children.values();
	}
	
	/**
	 * 
	 * @Title: hasChildren
	 * @Description: 判断该菜单是否包含下级菜单
	 * @return boolean
	 */
	public boolean hasChildren() {
		return !this.children.isEmpty();
	}

	/**
	 * 
	 * @Title: getProperties
	 * @Description: 获取菜单属性
	 * @return Map<Object,Object>
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * 
	 * @Title: addProperty
	 * @Description: 添加菜单属性
	 * @param properties 菜单属性
	 */
	public void addProperty(Map<String, Object> properties) {
		this.properties.putAll(properties);
	}
	
	/**
	 * 
	 * @Title: addProperty
	 * @Description: 添加菜单属性
	 * @param key 菜单属性标识
	 * @param property 菜单属性值
	 */ 
	public void addProperty(String key, Object property) {
		this.properties.put(key, property);
	}
}
