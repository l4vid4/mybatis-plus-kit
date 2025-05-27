package io.github.l4vid4.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.l4vid4.core.annotation.DisableApis;
import io.github.l4vid4.core.enums.Api;
import io.github.l4vid4.core.model.PageQuery;
import io.github.l4vid4.core.model.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Function;

public abstract class BaseController<T, S extends BaseService<T>> {

    protected S service;

    protected Class<T> entityClass; // 新增字段

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @RequestMapping(path = "/getById", method = RequestMethod.GET)
    public T getById(@RequestParam String id) {
        checkDisabled(Api.GET_BY_ID);
        return service.getById(id);
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public List<T> list() {
        checkDisabled(Api.LIST);
        return service.list();
    }

    @RequestMapping(path = "/listByIds", method = RequestMethod.POST)
    public List<T> listByIds(@RequestBody List<String> ids) {
        checkDisabled(Api.LIST_BY_IDS);
        return service.listByIds(ids);
    }

    @RequestMapping(path = "/listByCondition", method = RequestMethod.POST)
    public List<T> listByCondition(@RequestBody T entity) {
        checkDisabled(Api.LIST_BY_CONDITION);
        return service.list(new QueryWrapper<>(entity));
    }

    @RequestMapping(path = "/page", method = RequestMethod.POST)
    public PageResult<T> page(@RequestBody PageQuery query) {
        checkDisabled(Api.PAGE);
        return service.pageQuery(query);
    }

    @RequestMapping(path = "/pageVo", method = RequestMethod.POST)
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

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public Boolean save(@RequestBody T entity) {
        checkDisabled(Api.SAVE);
        return service.save(entity);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public Boolean update(@RequestBody T entity) {
        checkDisabled(Api.UPDATE);
        return service.updateById(entity);
    }

    @RequestMapping(path = "/deleteById/{id}", method = RequestMethod.DELETE)
    public Boolean deleteById(@PathVariable("id") String id) {
        checkDisabled(Api.DELETE_BY_ID);
        return service.removeById(id);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public Boolean delete(@RequestBody List<String> ids) {
        checkDisabled(Api.DELETE);
        return service.removeByIds(ids);
    }

    protected void checkDisabled(Api api) {
        DisableApis entityAnnotation = entityClass.getAnnotation(DisableApis.class);
        DisableApis thisAnnotation = this.getClass().getAnnotation(DisableApis.class);

        if (entityAnnotation != null) {
            for (Api disabled : entityAnnotation.value()) {
                if (disabled == api) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "该API已被禁用");
                }
            }
        }

        if (thisAnnotation != null) {
            for (Api disabled : thisAnnotation.value()) {
                if (disabled == api) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "该API已被禁用");
                }
            }
        }
    }

}
