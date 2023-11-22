package com.codestates.sebmainproject009.user.mapper;

import com.codestates.sebmainproject009.user.dto.UserPatchDto;
import com.codestates.sebmainproject009.user.dto.UserPostDto;
import com.codestates.sebmainproject009.user.dto.UserResponseDto;
import com.codestates.sebmainproject009.user.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User userPostDtoToUser(UserPostDto userPostDto);
    User userPatchDtoToUser(UserPatchDto userPatchDto);
    default UserResponseDto userToUserResponseDto(User user){

        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        if ( user.getUserId() != null ) {
            userResponseDto.setUserId( user.getUserId() );
        }
        userResponseDto.setEmail( user.getEmail() );
        userResponseDto.setDisplayName( user.getDisplayName() );

        if ( user.getAllergy() != null ) {

            userResponseDto.setAllergy( user.getAllergy().name() );

            if(user.getAllergy().toString().equals("OTHER")) {
                userResponseDto.setAllergy( user.getOtherAllergy() );
            }
        }

        userResponseDto.setProfileImgUrl( user.getProfileImgUrl() );

        return userResponseDto;
    }
}
