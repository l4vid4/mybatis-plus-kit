package io.github.l4vid4.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.l4vid4.core.enums.DisableApis;
import io.github.l4vid4.core.model.PageQuery;
import io.github.l4vid4.core.model.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public abstract class BaseController<T, S extends BaseService<T>> {

    @Autowired
    protected S service;

    @GetMapping("/getById/{id}")
    public T getById(@PathVariable Serializable id) {
        checkDisabled(DisableApis.Api.GET_BY_ID);
        return service.getById(id);
    }

    @GetMapping("/list")
    public List<T> list() {
        checkDisabled(DisableApis.Api.LIST);
        return service.list();
    }

    @GetMapping("/listByIds")
    public List<T> listByIds(@RequestBody List<Serializable> ids) {
        checkDisabled(DisableApis.Api.LIST_BY_IDS);
        return service.listByIds(ids);
    }

    @PostMapping("/listByCondition")
    public List<T> listByCondition(@RequestBody T entity) {
        checkDisabled(DisableApis.Api.LIST_BY_CONDITION);
        return service.list(new QueryWrapper<>(entity));
    }

    @PostMapping("/page")
    public PageResult<T> page(@RequestBody PageQuery query) {
        checkDisabled(DisableApis.Api.PAGE);
        return service.pageQuery(query);
    }

    @PostMapping("/pageVo")
    public PageResult<?> pageVo(@RequestBody PageQuery query) {
        Function<T, ?> convertor = voConvertor();
        return service.pageQuery(query, convertor);
    }

    /**
     * @author l4vid4
     * @description //如果要使用pageVO返回VO的逻辑，请重写此方法。
     * @return java.util.function.Function<T,?>
     **/
    protected Function<T, ?> voConvertor() {
        return t->t;
    }

    @PostMapping("/save")
    public Boolean save(@RequestBody T entity) {
        checkDisabled(DisableApis.Api.SAVE);
        return service.save(entity);
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody T entity) {
        checkDisabled(DisableApis.Api.UPDATE);
        return service.updateById(entity);
    }

    @GetMapping("/deleteById/{id}")
    public Boolean deleteById(@PathVariable Serializable id) {
        checkDisabled(DisableApis.Api.DELETE_BY_ID);
        return service.removeById(id);
    }

    @PostMapping("/delete")
    public Boolean delete(@RequestBody List<Serializable> ids) {
        checkDisabled(DisableApis.Api.DELETE);
        return service.removeByIds(ids);
    }

    protected void checkDisabled(DisableApis.Api api) {
        DisableApis annotation = this.getClass().getAnnotation(DisableApis.class);
        if (annotation != null && Arrays.asList(annotation.value()).contains(api)) {
            throw new UnsupportedOperationException("该接口已禁用：" + api.name());
        }
    }

}
