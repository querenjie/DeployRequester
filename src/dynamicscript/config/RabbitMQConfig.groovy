import com.myself.deployrequester.biz.config.impl.AbstractRabbitMQConfig
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.myself.deployrequester.util.Log4jUtil;

/**
 *   Created by QueRenJie on ${date}*/
class RabbitMQConfig extends AbstractRabbitMQConfig {
    /** ��־ */
    private static final Logger logger = LogManager.getLogger(RabbitMQConfig.class);

    @Override
    void buildRabbitMQConfig() {
        Log4jUtil.info(logger, "����RabbitMQConfig.groovy...");

        /**********���������RabbitMQ���õĶ�Ӧ��ϵ (begin) **************************************************/
        //���ؿ�������
        addSubjectRabbitMQConfigMapping("createDbscriptFile", "172.19.14.201", "5672", "querenjie", "querenjie", "message_queue_1");
        //��ʽ����
//        addSubjectRabbitMQConfigMapping("createDbscriptFile", "172.19.14.237", "5672", "querenjie", "querenjie", "message_queue_1");
        /**********���������RabbitMQ���õĶ�Ӧ��ϵ ( end ) **************************************************/

    }
}