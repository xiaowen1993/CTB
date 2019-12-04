package com.ctb.mobile.rest.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ctb.commons.entity.User;
import com.ctb.commons.entity.UserInfo;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.mobile.rest.service.UserInfoService;
import com.ctb.mobile.rest.service.UserService;
import com.ctb.resources.mapper.biz.UserInfoMapper;
import com.ctb.resources.mapper.biz.UserMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserInfoMapper userInfoMapper;
	
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
            criteria.andEqualTo("openId", user.getOpenId());
            //criteria.andLike("OPEN_ID", "%" + user.getOpenId() + "%");
        }

        if (!StringUtils.isEmpty(user.getUnionId())) {
            criteria.andEqualTo("unionId", user.getUnionId());
            //criteria.andLike("unionId", "%" + user.getUnionId() + "%");
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

	@Override
	public boolean isFocuseOn(String unionId) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("unionId", unionId);
		User user= userMapper.selectOneByExample(example);
		if(user==null) {
			return false;
		}else {
			if("1".equals(user.getStatus())) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int saveAndUpdate(User user,UserInfo userInfo) {
		// TODO Auto-generated method stub
		int res=0;
		try {
			Example example = new Example(User.class);
			Example.Criteria criteria = example.createCriteria();
			if(StringUtils.isNotBlank(user.getUnionId())) {			    
			    criteria.andEqualTo("unionId", user.getUnionId());
			}
			if(StringUtils.isNotBlank(user.getOpenId())) {              
                criteria.andEqualTo("openId", user.getOpenId());
            }
			User userRes= userMapper.selectOneByExample(example);
			if(userRes!=null) {
				userRes.setStatus(user.getStatus());
				res = userMapper.updateByPrimaryKey(userRes);
			}else {
				user.setId(PKGenerator.generateId());
				res = userMapper.insert(user);
			}
			//是否存在userInfo信息
			boolean isUnique = userInfoService.isUnique(userInfo);
			if(res==1&&isUnique==false&&"1".equals(user.getStatus())) {//关注保存用户绑卡数据
			    userInfo.setRefUnionId(user.getUnionId());
				userInfo.setId(PKGenerator.generateId());
				userInfoMapper.insert(userInfo);
			}
			
			if(res==0) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			}
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			logger.error("保存/修改用户关注状态失败！");
			return 0;
		}
		return res;
	}
}
