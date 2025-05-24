package io.github.l4vid4.core.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.l4vid4.core.model.PageQuery;
import io.github.l4vid4.core.model.PageResult;

import java.util.function.Function;

public interface BaseService<T> extends IService<T> {

    Page<T> page(PageQuery query);

    <V> PageResult<V> page(PageQuery query, Function<T, V> convertor);
}
