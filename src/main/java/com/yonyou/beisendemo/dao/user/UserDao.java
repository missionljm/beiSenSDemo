package com.yonyou.beisendemo.dao.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yonyou.beisendemo.constants.user.YesOrNoEnum;
import com.yonyou.beisendemo.entity.user.UserDO;
import com.yonyou.beisendemo.entity.user.UserInfoDO;
import com.yonyou.beisendemo.mapper.user.UserInfoMapper;
import com.yonyou.beisendemo.mapper.user.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends ServiceImpl<UserInfoMapper , UserInfoDO> {

    @Autowired
    private UserMapper userMapper;

    public UserDO getUserByUserName(String userName) {
        LambdaQueryWrapper<UserDO> queryUser = Wrappers.lambdaQuery();
        queryUser.eq(UserDO::getUserName, userName)
                .eq(UserDO::getDr, YesOrNoEnum.NO.getCode())
                .last("limit 1");
//        UserInfoMapper userInfoMapper = getBaseMapper();
        return userMapper.selectOne(queryUser);
    }

    public UserInfoDO getByUserId(Long userId) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.eq(UserInfoDO::getUserId, userId)
                .eq(UserInfoDO::getDr, YesOrNoEnum.NO.getCode());
        return baseMapper.selectOne(query);
    }

}
