package io.github.l4vid4.example.mapper;

import io.github.l4vid4.example.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lang
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2025-05-24 21:27:20
* @Entity io.github.l4vid4.example.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




