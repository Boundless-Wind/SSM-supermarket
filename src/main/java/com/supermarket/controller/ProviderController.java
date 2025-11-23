package com.supermarket.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supermarket.domain.Provider;
import com.supermarket.domain.User;
import com.supermarket.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    /**
     * 供应商列表
     *
     * @param model             模型
     * @param session           session
     * @param queryProviderName 供应商名称
     * @param pageNum           页码
     * @return providerlist.html
     */
    @RequestMapping("/providerlist")
    public String getProviderList(Model model, HttpSession session, @RequestParam(value = "queryProviderName", required = false) String queryProviderName, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        if (session.getAttribute("user") == null) {
            return "redirect:/syserror";
        }
        System.out.println("供应商姓名:" + queryProviderName);

        // 显示传入的参数
        model.addAttribute("queryProviderName", queryProviderName); // 传递查询的供应商名给前端

        // 开启分页
        PageHelper.startPage(pageNum, 5);
        List<Provider> providerList = providerService.getProviderListByPage(queryProviderName);// 查询供应商列表
        model.addAttribute("pageInfo", new PageInfo<>(providerList));// 将查询出的值传给前端

        return "provider/providerlist";
    }

    /**
     * 添加供应商页面
     *
     * @return provideradd.html
     */
    @RequestMapping("/provideradd")   // 打开添加页面
    public String addProvider() {
        return "provider/provideradd";
    }

    /**
     * 添加供应商数据
     *
     * @param provider 供应商
     * @param session  session
     * @return provideradd.html
     */
    @RequestMapping(value = "/provideraddsave", method = RequestMethod.POST)
    public String addProviderSave(Provider provider, HttpSession session) {
        provider.setCreatedBy(((User) session.getAttribute("user")).getId());   // 添加供应商表的createBy值，代表由当前供应商创建
        provider.setCreationDate(new Date());   // 添加供应商表的createDate值
        if (providerService.add(provider)) {    // 如果添加成功就返回providerlist
            return "redirect:/providerlist";
        }
        return "provider/provideradd";   // 添加不成功留在provideradd
    }

    /**
     * 修改供应商页面
     *
     * @param proid 供应商id
     * @param model 模型
     * @return providermodify.html
     */
    @RequestMapping("/providermodify")  // 打开修改页面
    // 直接标记为参数代表会尝试在post或get里找值，不写value就是get参数
    public String getProviderById(@RequestParam int proid, Model model) {
        Provider provider = providerService.getProviderById(proid);
        model.addAttribute("provider", provider);
        return "provider/providermodify";
    }

    /**
     * 修改供应商数据
     *
     * @param provider 供应商
     * @param session  session
     * @return provider.html
     */
    @RequestMapping(value = "/providermodifysave", method = RequestMethod.POST)
    public String modifyProviderSave(Provider provider, HttpSession session) {
        provider.setModifyBy(((User) session.getAttribute("user")).getId());
        provider.setModifyDate(new Date());
        if (providerService.modify(provider)) {
            return "redirect:/providerlist";
        }
        return "provider/providermodify";
    }

    /**
     * 查看供应商详情页面
     *
     * @param proid 供应商id
     * @param model 模型
     * @return providerview.html
     */
    @RequestMapping(value = "/providerview")
    public String view(@RequestParam int proid, Model model) {
        Provider provider = providerService.getProviderById(proid);
        model.addAttribute("provider", provider);
        return "provider/providerview";
    }

    /**
     * 删除供应商数据
     *
     * @param proid 供应商id
     * @return boolean-json对象
     */
    @RequestMapping("/delprovider")
    @ResponseBody
    public Object deluser(@RequestParam int proid) {
        String data = "{\"delResult\":\"false\"}";  // 初始化字符串
        boolean result = providerService.deleteProviderById(proid);
        if (result)
            data = "{\"delResult\":\"true\"}"; // 删除成功
        else
            data = "{\"delResult\":\"false\"}"; // 删除失败
        return JSONArray.toJSONString(data);// 将data转为json对象,并将结果发回给当前页面
    }

}
