package cn.edu.haut.cssp.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdja.cssp.oms.system.business.IFunctionBusiness;
import com.xdja.cssp.oms.system.dao.FunctionDao;
import com.xdja.cssp.oms.system.entity.Function;
import com.xdja.cssp.oms.system.entity.TUser;
import com.xdja.platform.security.bean.Menu;

/**
 * 菜单权限查询实现类
 * 
 * @ProjectName：pms-service-security
 * @ClassName：FunctionBusinessImpl
 * @Description：
 * @author 马德成
 * @date 2015-10-21
 *
 *修改：更改实体名称
 *@author fy
 *@date 2015-04-28
 */
@Service
public class FunctionBusinessImpl implements IFunctionBusiness {
	
	@Autowired
	private AFunctionDao aFunctionDao;

	@Override
	public Map<String, Menu> queryAllFunctions(TUser user) {
		List<Function> functions = new ArrayList<Function>();
		
		if (user.getType() == TUser.ENUM_USER_TYPE.systemUser.value) {
			functions = aFunctionDao.queryAdminFunctions();
		} else {
			functions = aFunctionDao.queryUserFunctions(user);
		}
		
		return queryMapFunctions(functions);
	}
	
	private Map<String, Menu> queryMapFunctions(List<Function> functionList) {
		Map<String, Menu> functions = new LinkedHashMap<String, Menu>();
		Map<Long, Menu> tempFunctions = new LinkedHashMap<Long, Menu>();
		Menu menu = null;
		for (Function function : functionList) {
			menu = function2Menu(function);

			if (function.getParentId() == 0L) {
				tempFunctions.put(function.getId(), menu);
				functions.put(function.getId().toString(), menu);
			} else {
				Menu parentFunc = tempFunctions.get(function.getParentId());

				if (parentFunc != null) {
					parentFunc.addChild(menu);
				}
				tempFunctions.put(function.getId(), menu);
			}
		}

		tempFunctions = null;
		return functions;
	}
	
	private Menu function2Menu(Function function) {
		Menu menu = new Menu();
		menu.setId(function.getId().toString());
		menu.setName(function.getName());
		menu.setPermissionKey(function.getObjName());
		menu.addProperty("icon", function.getIcon());
		menu.addProperty("link", function.getLink());
		return menu;
	}
	
}
