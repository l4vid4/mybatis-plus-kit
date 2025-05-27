package io.github.l4vid4.example.service.impl;

import io.github.l4vid4.core.base.BaseServiceImpl;
import io.github.l4vid4.example.entity.User;
import io.github.l4vid4.example.service.UserService;
import io.github.l4vid4.example.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Lang
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-05-24 21:27:20
*/
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User>
    implements UserService {

}




