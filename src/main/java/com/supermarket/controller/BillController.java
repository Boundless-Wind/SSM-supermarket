package com.supermarket.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supermarket.domain.Bill;
import com.supermarket.domain.Provider;
import com.supermarket.domain.User;
import com.supermarket.service.BillService;
import com.supermarket.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class BillController {

    @Autowired
    private ProviderService providerService;
    @Autowired
    private BillService billService;

    /**
     * 订单列表
     *
     * @param model           模型
     * @param session         session
     * @param queryBillName   订单名称
     * @param queryProviderId 订单供货商
     * @param pageNum         页码
     * @return billlist.html
     */
    @RequestMapping("/billlist")
    public String getBillList(Model model, HttpSession session, @RequestParam(value = "queryBillName", required = false) String queryBillName, @RequestParam(value = "queryProviderId", required = false) String queryProviderId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        if (session.getAttribute("user") == null) {
            return "redirect:/syserror";
        }
        System.out.println("订单姓名:" + queryBillName + ",订单供货商:" + queryProviderId);

        // 将供货商id赋值给_queryProviderId
        int _queryProviderId = 0;  // 给订单供货商赋值
        if (queryProviderId != null && !queryProviderId.trim().isEmpty())   // 如果前端传过来的供货商不为空
            _queryProviderId = Integer.parseInt(queryProviderId);

        // 显示传入的参数
        model.addAttribute("queryProviderId", queryProviderId);// 传递查询的供应商id给前端
        model.addAttribute("queryBillName", queryBillName); // 传递查询的订单名给前端

        // 查询供应商信息
        List<Provider> providerList = providerService.getProviderListByPage(null);  // 读取供应商表信息，传入空值返回全部供应商
        model.addAttribute("providerList", providerList); // 传递供应商表值给前端

        // 开启分页，必须在查询之前开启
        PageHelper.startPage(pageNum, 5);
        List<Bill> billList = billService.getBillListByPage(queryBillName, _queryProviderId);// 查询订单列表
        model.addAttribute("pageInfo", new PageInfo<>(billList));// 将查询出的值传给前端

        return "bill/billlist";
    }

    /**
     * 查询供应商列表
     *
     * @return array-json对象
     */
    @RequestMapping(value = "/getproviderlist", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object getProviderList() {
        List<Provider> providerList = providerService.getProviderListByPage(null);
        if (providerList == null) providerList = Collections.emptyList();  // 创建一个空的list集合
        // 将data转为json对象,并将结果发回给当前页面
        return JSONArray.toJSONString(providerList);
    }

    /**
     * 添加订单页面
     *
     * @return billadd.html
     */
    @RequestMapping("/billadd")   // 打开添加页面
    public String addBill() {
        return "bill/billadd";
    }

    /**
     * 添加订单数据
     *
     * @param bill    订单
     * @param session session
     * @return billadd.html
     */
    @RequestMapping(value = "/billaddsave", method = RequestMethod.POST)
    public String addBillSave(Bill bill, HttpSession session) {
        bill.setCreatedBy(((User) session.getAttribute("user")).getId());   // 添加订单表的createBy值，代表由当前订单创建
        bill.setCreationDate(new Date());   // 添加订单表的createDate值
        if (billService.add(bill)) {    // 如果添加成功就返回userlist
            return "redirect:/billlist";
        }
        return "bill/billadd";   // 添加不成功留在useradd
    }

    /**
     * 修改订单页面
     *
     * @param billid 订单id
     * @param model  模型
     * @return billmodify.html
     */
    @RequestMapping("/billmodify")  // 打开修改页面
    // 直接标记为参数代表会尝试在post或get里找值，不写value就是get参数
    public String getBillById(@RequestParam int billid, Model model) {
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill", bill);
        return "bill/billmodify";
    }

    /**
     * 修改订单数据
     *
     * @param bill    订单
     * @param session session
     * @return billmodify.html
     */
    @RequestMapping(value = "/billmodifysave", method = RequestMethod.POST)
    public String modifyBillSave(Bill bill, HttpSession session) {
        bill.setModifyBy(((User) session.getAttribute("user")).getId());
        bill.setModifyDate(new Date());
        if (billService.modify(bill)) {
            return "redirect:/billlist";
        }
        return "bill/billmodify";
    }

    /**
     * 查看订单详情页面
     *
     * @param billid 订单id
     * @param model  模型
     * @return billview.html
     */
    @RequestMapping(value = "/billview")
    public String view(@RequestParam int billid, Model model) {
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill", bill);
        return "bill/billview";
    }

    /**
     * 删除订单数据
     *
     * @param billid 订单id
     * @return boolean-json对象
     */
    @RequestMapping("/delbill")
    @ResponseBody
    public Object delBill(@RequestParam int billid) {
        String data = "{\"delResult\":\"false\"}";  // 初始化字符串
        boolean result = billService.deleteBillById(billid);
        if (result)
            data = "{\"delResult\":\"true\"}"; // 删除成功
        else
            data = "{\"delResult\":\"false\"}"; // 删除失败
        return JSONArray.toJSONString(data);// 将data转为json对象,并将结果发回给当前页面
    }

}
