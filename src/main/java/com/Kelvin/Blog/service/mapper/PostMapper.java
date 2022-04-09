package com.Kelvin.Blog.service.mapper;

import com.Kelvin.Blog.data.dto.PostDto;
import com.Kelvin.Blog.data.model.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PostMapper {


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post mapPostDtoToPost(PostDto postDto, @MappingTarget Post post);


}
