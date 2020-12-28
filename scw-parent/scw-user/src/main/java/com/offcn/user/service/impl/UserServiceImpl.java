package com.offcn.user.service.impl;

import com.offcn.user.enums.UserExceptionEnum;
import com.offcn.user.exception.UserException;
import com.offcn.user.mapper.TMemberAddressMapper;
import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;
import com.offcn.user.pojo.TMemberAddressExample;
import com.offcn.user.pojo.TMemberExample;
import com.offcn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TMemberMapper memberMapper;

    @Autowired
    private TMemberAddressMapper memberAddressMapper;

    @Override
    public void  registerUser(TMember member) {
        //查看手机号码是否存在
        TMemberExample example = new TMemberExample();
        String loginacct = member.getLoginacct();
        example.createCriteria().andLoginacctEqualTo(loginacct);
        int i = memberMapper.countByExample(example);
        if (i > 0) {
            throw new UserException(UserExceptionEnum.LOGINACCT_EXIST);
        }
        //手机号码未被注册，设置相关参数，保存注册信息
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(member.getUserpswd());
        //设置密码
        member.setUserpswd(encode);
        member.setUsername(member.getLoginacct());
        member.setEmail(member.getEmail());
        //实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证
        member.setAuthstatus("0");
        //用户类型: 0 - 个人， 1 - 企业
        member.setUsertype("0");
        //账户类型: 0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府
        member.setAccttype("2");
        System.out.println("插入数据:" + member.getLoginacct());
        memberMapper.insertSelective(member);//选择性插入insertSelective（）
    }




    @Override
    public TMember login(String username, String password) {
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        TMemberExample example = new TMemberExample();
        example.createCriteria().andLoginacctEqualTo(username);
        List<TMember> list = memberMapper.selectByExample(example);
        if(list!=null&&list.size()==1){
            TMember member = list.get(0);
            boolean matches = encode.matches(password, member.getUserpswd());
            return matches?member:null;
        }

        return null;
    }

    @Override
    public TMember findTmemberById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TMemberAddress> findAddressList(Integer memberId) {
        TMemberAddressExample example = new TMemberAddressExample();
        example.createCriteria().andMemberidEqualTo(memberId);
        return  memberAddressMapper.selectByExample(example);

    }
}