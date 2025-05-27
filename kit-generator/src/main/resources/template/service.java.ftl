package ${package.Service};

import ${package.Entity}.${entity};
import io.github.l4vid4.core.base.BaseService;

/**
* <p>
 * ${table.comment!} 服务类
 * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
 interface ${table.serviceName} : BaseService<${entity}>
<#else>
 public interface ${table.serviceName} extends BaseService<${entity}> {

 }
</#if>