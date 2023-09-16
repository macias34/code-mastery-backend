package com.macias34.codemastery.user.mapper;

import com.macias34.codemastery.course.mapper.CourseMapper;
import com.macias34.codemastery.user.dto.UserDto;
import com.macias34.codemastery.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = CourseMapper.class)

public interface UserMapper {
    @Mapping(target = "courses.chapters", ignore = true) // todo figure out how to ignore chapters
    UserDto fromEntityToDto(UserEntity user);
}
