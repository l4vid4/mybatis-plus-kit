package io.github.l4vid4.core.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.l4vid4.core.model.PageQuery;
import io.github.l4vid4.core.model.PageResult;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class BaseServiceImpl<M extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
    @Override
    public Page<T> page(PageQuery query) {
        return this.page(query.toMpPage(), new QueryWrapper<>());
    }

    @Override
    public <V> PageResult<V> page(PageQuery query, Function<T, V> convertor) {
        Page<T> page = this.page(query.toMpPage());
        return PageResult.of(page, convertor);
    }
}
