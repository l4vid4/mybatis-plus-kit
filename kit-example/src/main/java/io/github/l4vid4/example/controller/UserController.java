package io.github.l4vid4.example.controller;


import io.github.l4vid4.core.base.BaseController;
import io.github.l4vid4.example.entity.User;
import io.github.l4vid4.example.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, UserService> {

}
