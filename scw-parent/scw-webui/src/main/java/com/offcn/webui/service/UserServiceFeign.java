package com.offcn.webui.service;


import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.service.impl.UserServiceFeignException;
import com.offcn.webui.vo.resp.UserAddressVo;
import com.offcn.webui.vo.resp.UserRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "SCW-USER",configuration = FeignConfig.class,fallback = UserServiceFeignException.class)
public interface UserServiceFeign {
    @PostMapping("/user/login")
    public AppResponse<UserRespVo> login(@RequestParam("username") String username,
                                         @RequestParam("password") String password);

    @GetMapping("/user/findUser/{id}")
    public AppResponse<UserRespVo> findUser(@PathVariable("id") Integer id);

    @GetMapping("/user/findAddressList")
    public AppResponse<List<UserAddressVo>> findAddressList(@RequestParam("accessToken") String accessToken);

}
