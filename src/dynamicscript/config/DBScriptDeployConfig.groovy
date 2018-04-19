import com.myself.deployrequester.biz.config.impl.AbstractDBScriptDeployConfig
import com.myself.deployrequester.biz.config.spi.Config
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.myself.deployrequester.util.Log4jUtil;

/**
 *   Created by QueRenJie on ${date}*/
class DBScriptDeployConfig extends AbstractDBScriptDeployConfig {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(DBScriptDeployConfig.class);

    @Override
    void buildDBScriptDeployConfig() {
        Log4jUtil.info(logger, "加载DBScriptDeployConfig.groovy...");

        //发布数据库脚本到预发和生产的权限配置
        addAllowedIpConfig("172.19.14.144", Config.DEPLOY_DBSCRIPT);
        addAllowedIpConfig("172.19.14.236", Config.DEPLOY_DBSCRIPT);        //正卯
        addAllowedIpConfig("172.19.14.202", Config.DEPLOY_DBSCRIPT);        //淡然
        addAllowedIpConfig("172.19.14.247", Config.DEPLOY_DBSCRIPT);        //恒砺

        //生成数据库脚本文件的权限配置
        addAllowedIpConfig("172.19.14.144", Config.GENERATE_DBSCRIPT_FILE);
        addAllowedIpConfig("172.19.14.236", Config.GENERATE_DBSCRIPT_FILE);        //正卯
        addAllowedIpConfig("172.19.14.247", Config.GENERATE_DBSCRIPT_FILE);        //恒砺

        //修改是否可以随时发布脚本的权限配置
        addAllowedIpConfig("172.19.14.144", Config.CHANGE_CAN_EXEC_DBSCRIPT);
        addAllowedIpConfig("172.19.14.236", Config.CHANGE_CAN_EXEC_DBSCRIPT);        //正卯
        addAllowedIpConfig("172.19.14.247", Config.CHANGE_CAN_EXEC_DBSCRIPT);        //恒砺



        /**********数据库服务器的配置信息 (begin) **************************************************/
        addDatabaseConfig("DBServer1","172.16.54.12", "template1", "postgres", "suneeedba", "5440","翌捷预发布服务器172.16.54.12:5440","1");//翌捷预发布
        addDatabaseConfig("DBServer2","172.16.54.11", "template1", "postgres", "suneeedba", "5441","翌捷预发布服务器172.16.54.11:5441","1");//翌捷预发布
        addDatabaseConfig("DBServer3","172.16.54.12", "template1", "postgres", "suneeedba", "5441","翌捷预发布服务器172.16.54.12:5441","1");//翌捷预发布


        addDatabaseConfig("DBServer4","172.16.41.14", "template1", "postgres", "suneeedba", "5432","宁家预发布服务器172.16.41.14:5432","1");//宁家预发布
        addDatabaseConfig("DBServer5","172.16.36.66", "template1", "postgres", "suneeedba", "5432","宁家预发布服务器172.16.36.66:5432","1");//宁家预发布

        addDatabaseConfig("DBServer6","172.16.41.20", "template1", "postgres", "suneeedba", "5432","翌能预发布服务器172.16.41.20:5432","1");//翌能预发布


        addDatabaseConfig("DBServer7","172.16.41.26", "template1", "postgres", "suneeedba", "5433","翌能生产服务器172.16.41.26:5433","2");//翌能生产
        addDatabaseConfig("DBServer8","172.16.41.24", "template1", "postgres", "suneeedba", "5433","翌能生产服务器172.16.41.24:5433","2");//翌能生产

        addDatabaseConfig("DBServer9","172.16.54.73", "template1", "postgres", "suneeedba", "5434","大宗生产服务器172.16.54.73:5434","2");//大宗生产
        addDatabaseConfig("DBServer10","172.16.54.72", "template1", "postgres", "suneeedba", "5432","大宗生产服务器172.16.54.72:5432","2");//大宗生产

        addDatabaseConfig("DBServer11","172.16.51.12", "template1", "postgres", "suneeedba", "5433","宁家生产服务器172.16.51.12:5433","2");//宁家生产
        addDatabaseConfig("DBServer12","172.16.51.11", "template1", "postgres", "suneeedba", "5433","宁家生产服务器172.16.51.11:5433","2");//宁家生产

        addDatabaseConfig("DBServer13","172.16.54.10", "template1", "postgres", "suneeedba", "5440","翌捷生产服务器172.16.54.10:5440","2");//翌捷生产
        addDatabaseConfig("DBServer14","172.16.54.11", "template1", "postgres", "suneeedba", "5441","翌捷生产服务器172.16.54.11:5441","2");//翌捷生产


        /**********数据库服务器的配置信息 ( end ) **************************************************/

    }

}