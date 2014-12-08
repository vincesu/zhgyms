package com.vincesu.framework.model;

import com.vincesu.framework.entity.SysUser;
import com.vincesu.persistence.PMF;

public class UserModel extends AbstractModel<SysUser> {

	public UserModel(PMF p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	public boolean isExist(Long id)
	{
		return false;
	}
	
	public boolean isExist(String username)
	{
		return false;
	}

}
