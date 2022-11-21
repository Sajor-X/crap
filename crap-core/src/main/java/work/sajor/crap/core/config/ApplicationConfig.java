package work.sajor.crap.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 收集配置
 * </p>
 *
 * @author Sajor
 * @since 2022-11-21
 */
@Configuration
@Data
public class ApplicationConfig {

    private static ApplicationConfig self;

    @Autowired
    private void setKconeConfig(ApplicationConfig applicationConfig) {
        self = applicationConfig;
    }

    /**
     * 应用节点标识
     */
    @Value("${crap.id}")
    private String nodeId;

    // ------------------------------ 临时目录 ------------------------------

    @Value("${crap.path.tmp}")
    private String tmpPath = "/tmp";

    // ------------------------------ static method ------------------------------

    public static ApplicationConfig get() {
        return self == null ? new ApplicationConfig() : self;
    }
}
