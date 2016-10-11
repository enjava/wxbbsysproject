package com.en.test.service;

import com.en.test.dao.impl.BaseDaoImpl;
import com.en.test.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by en on 2016/8/20.
 */
@Service("testUserService")
@Transactional
public class TestUserServiceImpl extends BaseDaoImpl<User> implements TestUserService {
}
