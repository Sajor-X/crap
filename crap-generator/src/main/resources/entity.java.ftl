package ${package.Controller};

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonValue;
import work.sajor.crap.core.mybatis.facade.FieldEnum;
import ${package.Entity}.${table.controllerName}Base;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
// import org.springframework.data.elasticsearch.annotations.Document;

/**
 * ${table.comment!}
 * 实体扩展
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "${table.name}", autoResultMap = true)
// @Document(indexName = "${table.name}")
public class ${table.controllerName} extends ${table.controllerName}Base {

 <#list table.fields as field>
  <#if field.comment?starts_with("enum") && !field.comment?starts_with("enum(")>
    /**
      * ${field.comment}
      */
    @Getter
    @AllArgsConstructor
    --- Rewrite this ---
    public enum ${field.propertyName?cap_first}Enum implements FieldEnum<${field.propertyType}> {
      ON(1, "启用"),
      OFF(0, "禁用");

      @JsonValue
      private Integer value;
      private String name;
    }
  </#if>
  <#if field.comment?starts_with("json") && !field.comment?starts_with("json(")>
     /**
     * ${field.comment}
     */
     @Data
     --- Rewrite this ---
     public static class ${field.propertyName?cap_first}Bean {
     }
   </#if>
 </#list>
}