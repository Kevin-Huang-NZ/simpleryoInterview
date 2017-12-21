package co.nz.simpleryo.shop.domain.service.impl;


import tk.mybatis.mapper.entity.Example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.nz.simpleryo.shop.domain.common.BaseService;
import co.nz.simpleryo.shop.domain.model.User;
import co.nz.simpleryo.shop.domain.service.UserService;
import co.nz.simpleryo.shop.exception.ObjectExistException;
import co.nz.simpleryo.shop.util.StringFormat;

import java.util.List;

/**
 * Created by huanghao on 2016-8-16.
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {
    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    
    @Override
    public int save(User user) throws ObjectExistException {
        // 拼接检索条件
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("email", user.getEmail());
        List<User> existsUsers = selectByExample(example);
        if (existsUsers != null && existsUsers.size() > 0) {
        	throw new ObjectExistException(StringFormat.format("邮箱{}已经注册。", user.getEmail()));
        }
    	
        return mapper.insert(user);
    }
}
