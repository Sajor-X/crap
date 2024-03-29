package work.sajor.crap.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import work.sajor.crap.generator.config.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * <p>
 * 生成器
 * </p>
 *
 * @author Sajor
 * @since 2022-11-10
 */
public class CrapGenerator {

    private static String author = "Sajor";

    private static String host = "127.0.0.1";

    private static String port = "3306";

    private static String database = "crap";

    private static String user = "root";

    private static String password = "123456";

    private static String baseMapper = "work.sajor.crap.core.mybatis.mapper.Mapper";

    private static String baseDao = "work.sajor.crap.core.mybatis.dao.BaseDao";

    private static String baseEntity = "work.sajor.crap.core.mybatis.facade.Entity";

    /**
     * 获取配置文件的配置
     */
    public static void loadProperties() {
        try {
            ResourceBundle properties = ResourceBundle.getBundle("generator");
            author = properties.getString("author");
            host = properties.getString("host");
            port = properties.getString("port");
            database = properties.getString("database");
            user = properties.getString("user");
            password = properties.getString("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CrapGenerator().run();
    }

    public void run() {
        generate(
                "work.sajor.crap.core.dao",
                "/crap-core",
                "crap_",
                "crap_",
                new String[]{},
                new String[]{},
                true
        );
    }

    /**
     * 交互方式
     *
     * @param tip
     * @return
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 字段自动填充
     */
    protected List<TableFill> getTableFillList() {
        ArrayList<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        return tableFillList;
    }


    protected String getBaseDao() {
        return baseDao;
    }

    protected String getDatabase() {
        return database;
    }


    /**
     * 生成
     *
     * @param packageName 包名
     * @param packagePath 路径
     * @param prefix      可通过前缀设定生成范围
     * @param trimPrefix  清除前缀
     * @param tables      包含的表, 与 prefix 指定的范围合并
     * @param excludes    排除的表, 在 prefix 指定的范围中排除
     * @param override    是否覆盖
     */
    public void generate(String packageName, String packagePath, String prefix, String trimPrefix, String[] tables, String[] excludes, boolean override) {

        loadProperties();

        String JDBC_CONNECTOR = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false&characterEncoding=utf8", host, port, getDatabase());

        String OUTPUT_DIR = String.format("%s%s/src/main/java", System.getProperty("user.dir"), packagePath);


        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(OUTPUT_DIR);
        globalConfig.setFileOverride(override);
        globalConfig.setOpen(false);
        globalConfig.setEnableCache(false);
        globalConfig.setAuthor(author);
        globalConfig.setSwagger2(true);
        globalConfig.setActiveRecord(false);
        globalConfig.setBaseResultMap(false);
        globalConfig.setBaseColumnList(false);
        globalConfig.setEntityName("%sBase");
        globalConfig.setServiceName("%sDao");
        globalConfig.setServiceImplName("%sEnum");
        globalConfig.setControllerName("%s");
        globalConfig.setIdType(IdType.ASSIGN_ID);
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(JDBC_CONNECTOR);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername(user);
        dataSourceConfig.setPassword(password);
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageName);
        packageConfig.setService("dao");
        packageConfig.setEntity("entity.base");
        packageConfig.setController("entity");
        packageConfig.setServiceImpl("entity");
        generator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            public void initMap() {
            }
        };

        injectionConfig.setFileCreate((configBuilder, fileType, filePath) -> {

            if (fileType == FileType.SERVICE_IMPL) {
                return false;
            }

            // entity.base.* 对应数据表, 不应该直接修改, 因此强制覆写 entity.base
            if (fileType == FileType.ENTITY) {
                return true;
            }

            // 全局判断【默认】
            File file = new File(filePath);
            boolean exist = file.exists();
            if (!exist) {
                //noinspection ResultOfMethodCallIgnored
                file.getParentFile().mkdirs();
            }

            return !exist || configBuilder.getGlobalConfig().isFileOverride();
        });
        generator.setCfg(injectionConfig);

        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("entityBase.java");
        templateConfig.setService("dao.java");
        templateConfig.setController("entity.java");
        templateConfig.setMapper("mapper.java");
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        // 根据前缀生成
        if (prefix != null && !prefix.equals("")) {
            strategy.setLikeTable(new LikeTable(prefix.replace("_", "\\_"), SqlLike.RIGHT));
        }

        // 移除前缀
        if (trimPrefix != null && !trimPrefix.equals("")) {
            strategy.setTablePrefix(trimPrefix);
        }
        strategy.setInclude(tables);
        strategy.setEntityLombokModel(true);
        strategy.setExclude(excludes);
        strategy.setLogicDeleteFieldName("delete_flag");
        strategy.setVersionFieldName("sys_version");
        strategy.setSuperMapperClass(baseMapper);
        strategy.setSuperServiceClass(getBaseDao());
        strategy.setTableFillList(getTableFillList());                          // 自动填充
        generator.setStrategy(strategy);


        // 模板配置
        FreemarkerTemplateEngine freemarkerTemplateEngine = new FreemarkerTemplateEngine();
        freemarkerTemplateEngine.setTrimPrefix(trimPrefix);
        freemarkerTemplateEngine.setBaseEntity(baseEntity);
        generator.setTemplateEngine(freemarkerTemplateEngine);

        generator.execute();
    }

}
