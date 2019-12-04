package com.ctb.mobile.rest.service;

import java.util.List;

import com.ctb.commons.entity.User;
import com.ctb.commons.entity.UserInfo;

public interface UserService {

	public void saveUser(User user) throws Exception;

	public void updateUser(User user);

	public void deleteUser(String userId);

	public User queryUserById(String userId);

	public List<User> queryUserList(User user);

	public List<User> queryUserListPaged(User user, Integer page, Integer pageSize);

	public void saveUserTransactional(User user);
	
	/**
	 * 根据unionId、处方公众号openId判断关注状态
	 * @Title: isFocuseOn
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月3日 下午5:25:06
	 * @param opneId
	 * @param unionId
	 * @return
	 */
	public boolean isFocuseOn(String unionId);
	
	public int saveAndUpdate(User user,UserInfo userInfo);
}
