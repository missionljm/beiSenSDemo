package com.yonyou.beisendemo.services.user.converter;


import com.yonyou.beisendemo.constants.user.FollowStateEnum;
import com.yonyou.beisendemo.constants.user.UserAIStatEnum;
import com.yonyou.beisendemo.context.ReqInfoContext;
import com.yonyou.beisendemo.dto.user.BaseUserInfoDTO;
import com.yonyou.beisendemo.dto.user.SimpleUserInfoDTO;
import com.yonyou.beisendemo.dto.user.UserStatisticInfoDTO;
import com.yonyou.beisendemo.entity.user.UserDO;
import com.yonyou.beisendemo.entity.user.UserInfoDO;
import com.yonyou.beisendemo.entity.user.UserRelationDO;
import com.yonyou.beisendemo.utils.JsonUtil;
import com.yonyou.beisendemo.vo.constants.RoleEnum;
import com.yonyou.beisendemo.vo.user.UserInfoSaveReq;
import com.yonyou.beisendemo.vo.user.UserRelationReq;
import com.yonyou.beisendemo.vo.user.UserSaveReq;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

/**
 * 用户转换
 *
 * @author louzai
 * @date 2022-07-20
 */
public class UserConverter {

    public static UserDO toDO(UserSaveReq req) {
        if (req == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        userDO.setId(req.getUserId());
        userDO.setThirdAccountId(req.getThirdAccountId());
        userDO.setLoginType(req.getLoginType());
        return userDO;
    }

    public static UserInfoDO toDO(UserInfoSaveReq req) {
        if (req == null) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setUserId(req.getUserId());
        userInfoDO.setUserName(req.getUserName());
        userInfoDO.setPhoto(req.getPhoto());
        userInfoDO.setPosition(req.getPosition());
        userInfoDO.setCompany(req.getCompany());
        userInfoDO.setProfile(req.getProfile());
        userInfoDO.setEmail(req.getEmail());
        if (!CollectionUtils.isEmpty(req.getPayCode())) {
            userInfoDO.setPayCode(JsonUtil.toStr(req.getPayCode()));
        }
        return userInfoDO;
    }

//    public static BaseUserInfoDTO toDTO(UserInfoDO info, UserAiDO userAiDO) {
//        BaseUserInfoDTO user = toDTO(info);
//        if (userAiDO != null) {
//            // 获取星球账号
//            user.setStarStatus(UserAIStatEnum.fromCode(userAiDO.getState()));
//            user.setStarNumber(userAiDO.getStarNumber());
//            user.setExpireTime(userAiDO.getStarExpireTime());
//        }
//        return user;
//    }

    public static BaseUserInfoDTO toDTO(UserInfoDO info) {
        if (info == null) {
            return null;
        }
        BaseUserInfoDTO user = new BaseUserInfoDTO();
        // todo 知识点，bean属性拷贝的几种方式， 直接get/set方式，使用BeanUtil工具类(spring, cglib, apache, objectMapper)，序列化方式等
        BeanUtils.copyProperties(info, user);
        // 设置用户最新登录地理位置
        user.setRegion(info.getIp().getLatestRegion());
        // 设置用户角色
        user.setRole(RoleEnum.role(info.getUserRole()));
        return user;
    }

    public static SimpleUserInfoDTO toSimpleInfo(UserInfoDO info) {
        return new SimpleUserInfoDTO().setUserId(info.getUserId())
                .setName(info.getUserName())
                .setAvatar(info.getPhoto())
                .setProfile(info.getProfile());
    }

    public static UserRelationDO toDO(UserRelationReq req) {
        if (req == null) {
            return null;
        }
        UserRelationDO userRelationDO = new UserRelationDO();
        userRelationDO.setUserId(req.getUserId());
        userRelationDO.setFollowUserId(ReqInfoContext.getReqInfo().getUserId());
        userRelationDO.setFollowState(req.getFollowed() ? FollowStateEnum.FOLLOW.getCode() : FollowStateEnum.CANCEL_FOLLOW.getCode());
        return userRelationDO;
    }

    public static UserStatisticInfoDTO toUserHomeDTO(UserStatisticInfoDTO userHomeDTO, BaseUserInfoDTO baseUserInfoDTO) {
        if (baseUserInfoDTO == null) {
            return null;
        }
        BeanUtils.copyProperties(baseUserInfoDTO, userHomeDTO);
        return userHomeDTO;
    }
}
