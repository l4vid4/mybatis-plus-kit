package io.github.l4vid4.example.mapper;

import io.github.l4vid4.example.entity.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lang
* @description 针对表【address】的数据库操作Mapper
* @createDate 2025-05-26 23:07:30
* @Entity io.github.l4vid4.example.entity.Address
*/
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

}




