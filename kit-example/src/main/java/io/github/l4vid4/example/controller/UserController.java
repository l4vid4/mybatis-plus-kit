package io.github.l4vid4.example.controller;


import com.fasterxml.jackson.databind.util.BeanUtil;
import io.github.l4vid4.core.base.BaseController;
import io.github.l4vid4.core.model.PageQuery;
import io.github.l4vid4.core.model.PageResult;
import io.github.l4vid4.example.entity.User;
import io.github.l4vid4.example.entity.UserVO;
import io.github.l4vid4.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, UserService> {

    @Override
    protected Function<User, UserVO> voConvertor() {
        return user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        };
    }
}
