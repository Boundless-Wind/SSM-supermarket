package com.supermarket.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class Utils {

    /**
     * 分页处理
     *
     * @param pageNum    页码
     * @param tList      数据
     * @param pageSize   每页显示的行数
     * @param <T>        泛型
     */
    public static <T> PageInfo<T> getPageInfoAttribute(int pageNum, List<T> tList, int pageSize) {
        // 开启分页
        // 默认第一页开始、一行显示5个
        PageHelper.startPage(pageNum, pageSize);
        // list:必须先开启PageHelper，再获取list，可以尝试放在服务层中，等到项目结束在改进
        return new PageInfo<>(tList);
    }

    /**
     * 分页处理，默认每页显示5行
     *
     * @param pageNum    页码
     * @param tList      数据
     * @param <T>        泛型
     */
    public static <T> PageInfo<T> getPageInfoAttribute(int pageNum, List<T> tList) {
        return getPageInfoAttribute(pageNum, tList, 5);
    }
}
