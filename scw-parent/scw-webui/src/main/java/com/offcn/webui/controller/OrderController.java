package com.offcn.webui.controller;


import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.OrderServiceFeign;
import com.offcn.webui.vo.resp.OrderFormInfoSubmitVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import com.offcn.webui.vo.resp.TOrder;
import com.offcn.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private OrderServiceFeign orderServiceFeign;

    //保存订单
    //@ResponseBody
    @RequestMapping("/order/pay")
    public String OrderPay(OrderFormInfoSubmitVo vo, HttpSession session){
        //获取用户信息
        UserRespVo userResponseVo = (UserRespVo) session.getAttribute("sessionMember");
        if (userResponseVo == null){//判断用户是否存在
            return "redirect:/login";
        }
        //把令牌存入vo userRespVo.getAccessToken
        String accessToken = userResponseVo.getAccessToken();
        vo.setAccessToken(accessToken);
        //获取支付回报
        ReturnPayConfirmVo confirmVo = (ReturnPayConfirmVo) session.getAttribute("returnConfirmSession");
        if (confirmVo == null){
            return "redirect:/login";
        }
        vo.setProjectid(confirmVo.getProjectId());
        vo.setReturnid(confirmVo.getId());
        vo.setRtncount(confirmVo.getNum());
        //远程调用Feigen 进行存储
        AppResponse<TOrder> order = orderServiceFeign.createOrder(vo);
//        TOrder tOrder = order.getData();
//
//        //下单成功，打印相关信息待处理
//        log.info("orderNum:{}",tOrder.getOrdernum());
//        log.info("money:{}",tOrder.getMoney());
//        log.info("orderName:{}",confirmVo.getProjectName());
//        log.info("remark:{}",tOrder.getRemark());
        return "/member/minecrowdfunding";
        // return  "redirect:"+result;
    }
}