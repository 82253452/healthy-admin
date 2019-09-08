package com.zlsx.comzlsx.mapper;

import com.zlsx.comzlsx.domain.UserInfo;
import java.util.List;

import com.zlsx.comzlsx.dto.request.GetUserListRequest;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserInfoMapper extends Mapper<UserInfo> {
    int batchInsert(@Param("list") List<UserInfo> list);

    int insertOrUpdate(UserInfo record);

    int insertOrUpdateSelective(UserInfo record);

    List<UserInfo> selectUserList(GetUserListRequest request);
}