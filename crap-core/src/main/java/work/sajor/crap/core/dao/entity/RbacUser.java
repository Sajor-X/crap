package work.sajor.crap.core.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import work.sajor.crap.core.dao.entity.base.RbacUserBase;

import java.util.ArrayList;
import java.util.List;
// import org.springframework.data.elasticsearch.annotations.Document;

/**
 * RBAC 用户
 *
 * 实体扩展
 *
 * @author Sajor
 * @since 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "crap_rbac_user", autoResultMap = true)
// @Document(indexName = "crap_rbac_user")
public class RbacUser extends RbacUserBase {

    /**
     * 密码
     */
    @TableField("`password`")
//    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password;

    public List<Long> getRoleIds() {
        return roleIds == null ? new ArrayList<>() : roleIds;
    }

    public List<Long> getDepartmentIds() {
        return departmentIds == null ? new ArrayList<>() : departmentIds;
    }
}