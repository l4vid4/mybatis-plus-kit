package io.github.l4vid4.core.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.l4vid4.core.model.PageQuery;
import io.github.l4vid4.core.model.PageResult;

import java.io.Serializable;
import java.util.function.Function;

public interface BaseService<T> extends IService<T> {

    PageResult<T> pageQuery(PageQuery query);

    <V> PageResult<V> pageQuery(PageQuery query, Function<T, V> convertor);
}
