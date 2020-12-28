package com.offcn.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserRegistVo {
    @ApiModelProperty("手机号码")
    private String  loginacct;//手机号码
    @ApiModelProperty("密码")
    private String userpswd;//密码
    @ApiModelProperty("邮箱")
    private String email;//邮箱
    @ApiModelProperty("验证码")
    private String code;


}
