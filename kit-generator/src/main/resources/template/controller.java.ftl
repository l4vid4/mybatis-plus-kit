package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if generateService>
 import ${package.Service}.${table.serviceName};
</#if>
import io.github.l4vid4.core.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
 * ${table.comment!} 前端控制器
 * </p>
*
* @author ${author}
* @since ${date}
*/
@RestController
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
public class ${table.controllerName} extends BaseController<${entity}, ${table.serviceName}> {

}