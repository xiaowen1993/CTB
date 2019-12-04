package com.ctb.pharmacy.rest.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ctb.commons.entity.User;
import com.ctb.pharmacy.rest.service.UserService;
import com.ctb.resources.mapper.biz.UserMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	//支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(User user) throws Exception {
		userMapper.insert(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUser(User user) {
		/**
		 * 1、使用 updateByPrimaryKey 方法当你传的对象的字段并没有全部赋值时，它会将数据库中你
		 * 		没有赋值的字段值全部设置为 null
		 * 	2、updateByPrimaryKeySelective 不会
		 */
//		userMapper.updateByPrimaryKey(user);
		userMapper.updateByPrimaryKeySelective(user);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(String userId) {
		userMapper.deleteByPrimaryKey(userId);
	}

	//支持当前事务，如果当前没有事务，就以非事务方式执行。
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User queryUserById(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> queryUserList(User user) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty((user.getOpenId()))) {
//			criteria.andEqualTo("OPEN_ID", user.getOpenId());
			criteria.andLike("OPEN_ID", "%" + user.getOpenId() + "%");
		}

		if (!StringUtils.isEmpty(user.getUnionId())) {
			criteria.andLike("UNION_ID", "%" + user.getUnionId() + "%");
		}

		List<User> userList = userMapper.selectByExample(example);

		return userList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> queryUserListPaged(User user, Integer page, Integer pageSize) {
		// 开始分页（参数：当前页数、每页行数）
        PageHelper.startPage(page, pageSize);

		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(user.getOpenId())) {
			criteria.andLike("OPEN_ID", "%" + user.getOpenId() + "%");
		}
		example.orderBy("ID").asc();
		List<User> userList = userMapper.selectByExample(example);

		return userList;
	}

	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUserTransactional(User user) {

		userMapper.insert(user);

		int a = 1 / 0;

		user.setOpenId("xxx");;
		userMapper.updateByPrimaryKeySelective(user);
	}
}
