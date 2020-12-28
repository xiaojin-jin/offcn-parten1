package com.offcn.webui.controller;


import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.service.UserServiceFeign;
import com.offcn.webui.vo.resp.ProjectVo;
import com.offcn.webui.vo.resp.UserRespVo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class DispathcherController {

    @Autowired
    private UserServiceFeign userServiceFeign;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Autowired
    private StringRedisTemplate redisTemplate;
//
//    @GetMapping("/")
//     public String toIndex(){
//        System.out.println("你已经进入主页面");
//         return "index";
//     }

     @RequestMapping("/doLogin")
    public String doLogin(String username, String password, HttpSession session){
         AppResponse<UserRespVo> appResponse = userServiceFeign.login(username, password);
         //获取响应数据
         UserRespVo userRespVo = appResponse.getData();
         log.info("登录账号:{},密码:{}",username,password);
         log.info("登录用户数据:{}",userRespVo);
         //判断账户是否存在
         if(userRespVo==null){
             //账号不存在，登录
             return "redirect:/login.html";
         }
         //用户存在，登录成功，把用户信息存储到session
         session.setAttribute("sessionMember",userRespVo);
         //从session获取前缀,登录超时，登录还回到这个页面
         String preUrl = (String) session.getAttribute("preUrl");
         //如果前缀不存在，跳转到默认首页
         if(StringUtils.isEmpty(preUrl)){
             return "redirect:/";
         }
         //如果存在就跳转到前缀地址
         return "redirect:/"+preUrl;
     }


    @RequestMapping("/")
    public String toIndex(Model model){
//从redis读取项目集合
//        List<ProjectVo> data = (List<ProjectVo>) redisTemplate.opsForValue().get("projectStr");
        String projectStr = redisTemplate.opsForValue().get("projectStr");
        List<ProjectVo> data  = JSON.parseArray(projectStr,ProjectVo.class);
//redis项目集合为空，就调用项目服务，读取全部项目
        if (data == null){
            AppResponse<List<ProjectVo>> allProject = projectServiceFeign.findAllProject();
            data = allProject.getData();
            String s = JSON.toJSONString(data);
            redisTemplate.opsForValue().set("projectStr",s,10, TimeUnit.DAYS);
        }
        model.addAttribute("projectList",data);
        return "index";
    }

    @RequestMapping("/login")
    public String loginHtml(){
        return "login";
    }
}
