package com.en.test.action;

import com.en.test.entity.User;
import com.en.test.service.TestUserService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by en on 2016/8/20.
 */
@Controller("testAction")
@Scope("prototype")
public class TestAction extends ActionSupport {

    private String message;
    private List<User> userlist;
    @Resource
    private  TestUserService testUserService;
    @Override
    public String execute() throws Exception {
        userlist=testUserService.findAll();
        this.message = "配置成功";
        return super.execute();
    }

    public String getMessage() {
        return message;
    }

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }
}
