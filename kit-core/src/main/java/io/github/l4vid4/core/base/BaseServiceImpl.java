package io.github.l4vid4.core.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.l4vid4.core.model.PageQuery;
import io.github.l4vid4.core.model.PageResult;
import io.github.l4vid4.core.util.Tool;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class BaseServiceImpl<M extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public PageResult<T> pageQuery(PageQuery query) {
        Page<T> page = this.page(query.toMpPage(), new QueryWrapper<>());
        return PageResult.of(page, t -> t);
    }

    @Override
    public <V> PageResult<V> pageQuery(PageQuery query, Function<T, V> convertor) {
        Page<T> page = this.page(query.toMpPage());
        return PageResult.of(page, convertor);
    }

}
