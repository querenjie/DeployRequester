import com.myself.deployrequester.biz.config.impl.AbstractModulesConfig
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.myself.deployrequester.util.Log4jUtil;

/**
 *   Created by QueRenJie on ${date}*/
class _ModulesConfig extends AbstractModulesConfig {
    /** ��־ */
    private static final Logger logger = LogManager.getLogger(_ModulesConfig.class);

    @Override
    void buildModulesConfig() {
        Log4jUtil.info(logger, "����ModulesConfig.groovy...");

        /**********������Ŀ (begin) **************************************************/
        addProject(1, "����");
        addProject(2, "����");
        addProject(3, "���ڽ���");
        addProject(4, "���");
        /**********������Ŀ ( end ) **************************************************/

        /**********����ģ�� (begin) **************************************************/
        addModule(Short.valueOf("1"), "system", "ϵͳ����");
        addModule(Short.valueOf("2"), "base", "������Դ");
        addModule(Short.valueOf("3"), "contract", "��ͬ����");
        addModule(Short.valueOf("4"), "finance", "�������");
        addModule(Short.valueOf("5"), "reportform", "�������");
        addModule(Short.valueOf("6"), "goods", "��Ʒ����");
        addModule(Short.valueOf("7"), "purchase", "�ɹ�����");
        addModule(Short.valueOf("8"), "sale", "���۹���");
        addModule(Short.valueOf("9"), "supplier", "��Ӧ�̹���");
        addModule(Short.valueOf("10"), "wholesale", "��������");
        addModule(Short.valueOf("11"), "kitchen", "�������");
        addModule(Short.valueOf("12"), "market", "�г�Ӫ��");
        addModule(Short.valueOf("13"), "storage", "�ִ�����");
        addModule(Short.valueOf("14"), "bkcontract", "���ڽ��׺�ͬ����");
        addModule(Short.valueOf("15"), "bkfinance", "���ڽ��ײ������");
        addModule(Short.valueOf("16"), "bksale", "���ڽ������۹���");
        addModule(Short.valueOf("17"), "bksettlement", "���ڽ��׽������");
        addModule(Short.valueOf("18"), "bktender", "���ڽ�����Ͷ�����");
        addModule(Short.valueOf("19"), "bktransportmanage", "���ڽ����������");
        addModule(Short.valueOf("20"), "bkstorage", "���ڽ��ײִ�����");
        addModule(Short.valueOf("21"), "bkreportform", "���ڽ��ױ���ģ��");
        /**********����ģ�� ( end ) **************************************************/

        /**********����ģ������ (begin) **************************************************/
        addModuleType(Short.valueOf("1"), "rest");
        addModuleType(Short.valueOf("2"), "provider");
        /**********����ģ������ ( end ) **************************************************/


    }

}