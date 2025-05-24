package io.github.l4vid4.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.l4vid4.core.model.PageQuery;
import io.github.l4vid4.core.model.PageResult;
import io.github.l4vid4.core.model.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

public abstract class BaseController<T, S extends BaseService<T>> {

    @Autowired
    protected S service;

    @GetMapping("/getById/{id}")
    public ResultResponse<T> getById(@PathVariable Serializable id) {
        return ResultResponse.success(service.getById(id));
    }

    @GetMapping("/list")
    public ResultResponse<List<T>> list() {
        return ResultResponse.success(service.list());
    }

    @PostMapping("/listByCondition")
    public ResultResponse<List<T>> listByCondition(@RequestBody T entity) {
        return ResultResponse.success(service.list(new QueryWrapper<>(entity)));
    }

    @PostMapping("/page")
    public ResultResponse<Page<T>> page(@RequestBody PageQuery query) {
        return ResultResponse.success(service.page(query));
    }

    @PostMapping("/pageVo")
    public ResultResponse<PageResult<Object>> pageVo(@RequestBody PageQuery query) {
        // 默认直接返回原始实体，如果你不指定转换器
        Page<T> page = service.page(query);
        return ResultResponse.success(PageResult.of(page, t -> t));
    }

    @PostMapping("/save")
    public ResultResponse<Boolean> save(@RequestBody T entity) {
        return ResultResponse.success(service.save(entity));
    }

    @PostMapping("/update")
    public ResultResponse<Boolean> update(@RequestBody T entity) {
        return ResultResponse.success(service.updateById(entity));
    }

    @GetMapping("/deleteById/{id}")
    public ResultResponse<Boolean> deleteById(@PathVariable Serializable id) {
        return ResultResponse.success(service.removeById(id));
    }

    @PostMapping("/delete")
    public ResultResponse<Boolean> delete(@RequestBody List<Serializable> ids) {
        return ResultResponse.success(service.removeByIds(ids));
    }
}
