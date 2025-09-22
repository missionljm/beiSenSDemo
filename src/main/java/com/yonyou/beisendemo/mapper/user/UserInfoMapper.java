package com.yonyou.beisendemo.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yonyou.beisendemo.entity.user.UserDO;
import com.yonyou.beisendemo.entity.user.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoDO> {

}
