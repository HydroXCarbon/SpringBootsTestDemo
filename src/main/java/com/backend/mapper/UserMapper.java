package com.backend.mapper;

import com.backend.entity.User;
import com.backend.model.MRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    MRegisterResponse toMRegisterResponse(User user);

}
