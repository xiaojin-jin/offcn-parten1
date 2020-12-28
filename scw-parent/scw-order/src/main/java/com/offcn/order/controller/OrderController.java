package com.offcn.order.controller;


import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.pojo.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "保存订单")
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    @ApiOperation("保存订单")
    public AppResponse createOrder(@RequestBody OrderInfoSubmitVo vo) {
        //查看是否登录
        String memberId = redisTemplate.opsForValue().get(vo.getAccessToken());
        if (memberId==null){
            return AppResponse.fail("请登录");
        }
        try {
            TOrder order = orderService.saveOrder(vo);
            AppResponse<TOrder> orderAppResponse = AppResponse.ok(order);
            return orderAppResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }
}