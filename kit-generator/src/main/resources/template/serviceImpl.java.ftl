package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
<#if generateService>
 import ${package.Service}.${table.serviceName};
</#if>
import io.github.l4vid4.core.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
 * ${table.comment!} 服务实现类
 * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
 open class ${table.serviceImplName} : BaseServiceImpl<${table.mapperName}, ${entity}>()<#if generateService>, ${table.serviceName}</#if> {

 }
<#else>
 public class ${table.serviceImplName} extends BaseServiceImpl<${table.mapperName}, ${entity}><#if generateService> implements ${table.serviceName}</#if> {

 }
</#if>
