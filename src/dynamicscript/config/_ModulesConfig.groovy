import com.myself.deployrequester.biz.config.impl.AbstractModulesConfig
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.myself.deployrequester.util.Log4jUtil;

/**
 *   Created by QueRenJie on ${date}*/
class _ModulesConfig extends AbstractModulesConfig {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(_ModulesConfig.class);

    @Override
    void buildModulesConfig() {
        Log4jUtil.info(logger, "加载ModulesConfig.groovy...");

        /**********配置项目 (begin) **************************************************/
        addProject(1, "翌能");
        addProject(2, "宁家");
        addProject(3, "大宗交易");
        addProject(4, "翌捷");
        /**********配置项目 ( end ) **************************************************/

        /**********配置模块 (begin) **************************************************/
        addModule(Short.valueOf("1"), "system", "系统管理");
        addModule(Short.valueOf("2"), "base", "基础资源");
        addModule(Short.valueOf("3"), "contract", "合同管理");
        addModule(Short.valueOf("4"), "finance", "财务管理");
        addModule(Short.valueOf("5"), "reportform", "报表管理");
        addModule(Short.valueOf("6"), "goods", "商品管理");
        addModule(Short.valueOf("7"), "purchase", "采购管理");
        addModule(Short.valueOf("8"), "sale", "零售管理");
        addModule(Short.valueOf("9"), "supplier", "供应商管理");
        addModule(Short.valueOf("10"), "wholesale", "批发管理");
        addModule(Short.valueOf("11"), "kitchen", "后厨管理");
        addModule(Short.valueOf("12"), "market", "市场营销");
        addModule(Short.valueOf("13"), "storage", "仓储管理");
        addModule(Short.valueOf("14"), "bkcontract", "大宗交易合同管理");
        addModule(Short.valueOf("15"), "bkfinance", "大宗交易财务管理");
        addModule(Short.valueOf("16"), "bksale", "大宗交易零售管理");
        addModule(Short.valueOf("17"), "bksettlement", "大宗交易结算管理");
        addModule(Short.valueOf("18"), "bktender", "大宗交易招投标管理");
        addModule(Short.valueOf("19"), "bktransportmanage", "大宗交易运输管理");
        addModule(Short.valueOf("20"), "bkstorage", "大宗交易仓储管理");
        addModule(Short.valueOf("21"), "bkreportform", "大宗交易报表模块");
        /**********配置模块 ( end ) **************************************************/

        /**********配置模块类型 (begin) **************************************************/
        addModuleType(Short.valueOf("1"), "rest");
        addModuleType(Short.valueOf("2"), "provider");
        /**********配置模块类型 ( end ) **************************************************/


    }

}