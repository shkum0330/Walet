package com.ssafy.notification.api.mapper;

import com.ssafy.notification.api.request.FcmTokenSaveRequest;
import com.ssafy.notification.db.entity.FcmToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FcmTokenMapper {
    FcmTokenMapper INSTANCE = Mappers.getMapper(FcmTokenMapper.class);

    FcmToken requestToEntity(FcmTokenSaveRequest fcmTokenSaveRequest);
}
