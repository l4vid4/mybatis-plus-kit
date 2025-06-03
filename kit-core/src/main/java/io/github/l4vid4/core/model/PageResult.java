package io.github.l4vid4.core.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 通用分页结果封装
 * @author l4vid4
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    private Long total;
    private Long pages;
    private List<T> list;

    /**
     * 返回空分页结果
     * @param p MybatisPlus的分页结果
     * @param &lt;V&gt; 目标VO类型
     * @param &lt;P&gt; 原始PO类型
     * @return VO的分页对象
     */
    public static <V, P> PageResult<V> empty(Page<P> p){
        return new PageResult<>(p.getTotal(), p.getPages(), Collections.emptyList());
    }

    /**
     * 将MybatisPlus分页结果转为 VO分页结果
     * @param p MybatisPlus的分页结果
     * @param voClass 目标VO类型的字节码
     * @param &lt;V&gt; 目标VO类型
     * @param &lt;P&gt; 原始PO类型
     * @return VO的分页对象
     */
    public static <V, P> PageResult<V> of(Page<P> p, Class<V> voClass) {
        List<P> records = p.getRecords();
        if (records == null || records.isEmpty()) {
            return empty(p);
        }
        List<V> vos = BeanUtil.copyToList(records, voClass);
        return new PageResult<>(p.getTotal(), p.getPages(), vos);
    }

    /**
     * 将MybatisPlus分页结果转为 VO分页结果，允许用户自定义PO到VO的转换方式
     * @param p MybatisPlus的分页结果
     * @param convertor PO到VO的转换函数
     * @param &lt;V&gt; 目标VO类型
     * @param &lt;P&gt; 原始PO类型
     * @return VO的分页对象
     */
    public static <V, P> PageResult<V> of(Page<P> p, Function<P, V> convertor) {
        List<P> records = p.getRecords();
        if (records == null || records.isEmpty()) {
            return empty(p);
        }
        List<V> vos = records.stream().map(convertor).collect(Collectors.toList());
        return new PageResult<>(p.getTotal(), p.getPages(), vos);
    }
}
