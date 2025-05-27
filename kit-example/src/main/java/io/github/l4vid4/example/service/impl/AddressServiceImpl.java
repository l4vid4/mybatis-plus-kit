package io.github.l4vid4.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.l4vid4.core.base.BaseServiceImpl;
import io.github.l4vid4.example.entity.Address;
import io.github.l4vid4.example.service.AddressService;
import io.github.l4vid4.example.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author Lang
* @description 针对表【address】的数据库操作Service实现
* @createDate 2025-05-26 23:07:30
*/
@Service
public class AddressServiceImpl extends BaseServiceImpl<AddressMapper, Address>
    implements AddressService{

}




