import com.myself.deployrequester.biz.config.impl.AbstractDBScriptDeployConfig
import com.myself.deployrequester.biz.config.spi.Config
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.myself.deployrequester.util.Log4jUtil;

/**
 *   Created by QueRenJie on ${date}*/
class DBScriptDeployConfig extends AbstractDBScriptDeployConfig {
    /** ��־ */
    private static final Logger logger = LogManager.getLogger(DBScriptDeployConfig.class);

    @Override
    void buildDBScriptDeployConfig() {
        Log4jUtil.info(logger, "����DBScriptDeployConfig.groovy...");

        //�������ݿ�ű���Ԥ����������Ȩ������
        addAllowedIpConfig("172.19.14.144", Config.DEPLOY_DBSCRIPT);
        addAllowedIpConfig("172.19.14.236", Config.DEPLOY_DBSCRIPT);        //��î
        addAllowedIpConfig("172.19.14.202", Config.DEPLOY_DBSCRIPT);        //��Ȼ
        addAllowedIpConfig("172.19.14.247", Config.DEPLOY_DBSCRIPT);        //����

        //�������ݿ�ű��ļ���Ȩ������
        addAllowedIpConfig("172.19.14.144", Config.GENERATE_DBSCRIPT_FILE);
        addAllowedIpConfig("172.19.14.236", Config.GENERATE_DBSCRIPT_FILE);        //��î
        addAllowedIpConfig("172.19.14.247", Config.GENERATE_DBSCRIPT_FILE);        //����

        //�޸��Ƿ������ʱ�����ű���Ȩ������
        addAllowedIpConfig("172.19.14.144", Config.CHANGE_CAN_EXEC_DBSCRIPT);
        addAllowedIpConfig("172.19.14.236", Config.CHANGE_CAN_EXEC_DBSCRIPT);        //��î
        addAllowedIpConfig("172.19.14.247", Config.CHANGE_CAN_EXEC_DBSCRIPT);        //����



        /**********���ݿ��������������Ϣ (begin) **************************************************/
        addDatabaseConfig("DBServer1","172.16.54.12", "template1", "postgres", "suneeedba", "5440","���Ԥ����������172.16.54.12:5440","1");//���Ԥ����
        addDatabaseConfig("DBServer2","172.16.54.11", "template1", "postgres", "suneeedba", "5441","���Ԥ����������172.16.54.11:5441","1");//���Ԥ����
        addDatabaseConfig("DBServer3","172.16.54.12", "template1", "postgres", "suneeedba", "5441","���Ԥ����������172.16.54.12:5441","1");//���Ԥ����


        addDatabaseConfig("DBServer4","172.16.41.14", "template1", "postgres", "suneeedba", "5432","����Ԥ����������172.16.41.14:5432","1");//����Ԥ����
        addDatabaseConfig("DBServer5","172.16.36.66", "template1", "postgres", "suneeedba", "5432","����Ԥ����������172.16.36.66:5432","1");//����Ԥ����

        addDatabaseConfig("DBServer6","172.16.41.20", "template1", "postgres", "suneeedba", "5432","����Ԥ����������172.16.41.20:5432","1");//����Ԥ����


        addDatabaseConfig("DBServer7","172.16.41.26", "template1", "postgres", "suneeedba", "5433","��������������172.16.41.26:5433","2");//��������
        addDatabaseConfig("DBServer8","172.16.41.24", "template1", "postgres", "suneeedba", "5433","��������������172.16.41.24:5433","2");//��������

        addDatabaseConfig("DBServer9","172.16.54.73", "template1", "postgres", "suneeedba", "5434","��������������172.16.54.73:5434","2");//��������
        addDatabaseConfig("DBServer10","172.16.54.72", "template1", "postgres", "suneeedba", "5432","��������������172.16.54.72:5432","2");//��������

        addDatabaseConfig("DBServer11","172.16.51.12", "template1", "postgres", "suneeedba", "5433","��������������172.16.51.12:5433","2");//��������
        addDatabaseConfig("DBServer12","172.16.51.11", "template1", "postgres", "suneeedba", "5433","��������������172.16.51.11:5433","2");//��������

        addDatabaseConfig("DBServer13","172.16.54.10", "template1", "postgres", "suneeedba", "5440","�������������172.16.54.10:5440","2");//�������
        addDatabaseConfig("DBServer14","172.16.54.11", "template1", "postgres", "suneeedba", "5441","�������������172.16.54.11:5441","2");//�������


        /**********���ݿ��������������Ϣ ( end ) **************************************************/

    }

}