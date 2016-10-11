package com.en.test.dao;

import java.util.List;

/**
 * Created by en on 2016/8/20.
 */
public interface BaseDao <T>{
    List<T> findAll();
}
