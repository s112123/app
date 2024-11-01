package org.demo.mapper.module.file.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.demo.mapper.module.file.response.FileInformation;

@Mapper
public interface FileMapper {

    // 회원 이미지 등록
    void saveProfileImage(@Param("memberId") Long memberId, @Param("information") FileInformation information);
}
