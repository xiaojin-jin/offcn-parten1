package com.offcn.webui.vo.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressVo implements Serializable {

    //地址id
    private Integer id;

    //会员地址
    private String address;
}
