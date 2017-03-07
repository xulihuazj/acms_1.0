package cn.edu.haut.cssp.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.xerces.impl.xpath.regex.REUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.tools.javac.file.FSInfo;

import cn.edu.haut.cssp.bean.Menu;
import cn.edu.haut.cssp.dao.AFunctionDao;
import cn.edu.haut.cssp.entity.AFunction;
import cn.edu.haut.cssp.entity.AcmsUser;
import cn.edu.haut.cssp.service.IFunctionService;

@Component
public class FunctionServiceImpl implements IFunctionService {

	@Autowired
	private AFunctionDao functionDao;
	
	@Override
	public Map<String, Menu> queryAllFunctions(AcmsUser user) {
		
		List<AFunction> functions = new ArrayList<>();
		//  如果是超级管理员，那么拥有所有的权限
		if (user.getType() == AcmsUser.ENUM_USER_TYPE.sysType.value) {
			functions = functionDao.queryAdminFunctions();
		}else{
			// 如果是普通管理员
			functions = functionDao.queryUserFunctions(user);
		}
		return queryMapFuctions(functions);
	}
	
	private Map<String,Menu> queryMapFuctions(List<AFunction> functions){
		Map<String, Menu> maps = new LinkedHashMap<>();
		Map<Long, Menu> tempFunctions = new LinkedHashMap<>();
		
		Menu menu = null;
			// 遍历functions
		for(AFunction function : functions){
			menu = function2Menu(function);
				// 判断是不是顶层的菜单属性
			if(function.getParentId() == 0L){
				tempFunctions.put(function.getId(), menu);
				maps.put(function.getId().toString(), menu);
			}else{
				Menu parentFunc = tempFunctions.get(function.getParentId());
				if(parentFunc != null){
					parentFunc.addChild(menu);
				}
				tempFunctions.put(function.getId(),menu);
			}
		}
		
		tempFunctions = null;
		return maps;
	}
	/**
	 * 将funciton实体对象转换成Menu
	 * @author: xulihua
	 * @date: 2017年2月6日上午11:39:40
	 * @return: Menu
	 */
	private Menu function2Menu(AFunction function){
		Menu menu = new Menu();
			// 设置id
		menu.setId(function.getId().toString());
			// 设置功能名称
		menu.setName(function.getName());
			// 获取批准
		menu.setPermissionKey(function.getObjName());
			// 功能图标
		menu.addProperty("icon",function.getIcon());
			// 添加菜单属性
		menu.addProperty("link",function.getLink());
		return menu;
		
	}

}
