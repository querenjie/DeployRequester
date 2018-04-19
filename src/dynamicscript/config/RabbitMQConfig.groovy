import com.myself.deployrequester.biz.config.impl.AbstractRabbitMQConfig
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.myself.deployrequester.util.Log4jUtil;

/**
 *   Created by QueRenJie on ${date}*/
class RabbitMQConfig extends AbstractRabbitMQConfig {
    /** 日志 */
    private static final Logger logger = LogManager.getLogger(RabbitMQConfig.class);

    @Override
    void buildRabbitMQConfig() {
        Log4jUtil.info(logger, "加载RabbitMQConfig.groovy...");

        /**********配置主题和RabbitMQ配置的对应关系 (begin) **************************************************/
        //本地开发环境
        addSubjectRabbitMQConfigMapping("createDbscriptFile", "172.19.14.201", "5672", "querenjie", "querenjie", "message_queue_1");
        //正式环境
//        addSubjectRabbitMQConfigMapping("createDbscriptFile", "172.19.14.237", "5672", "querenjie", "querenjie", "message_queue_1");
        /**********配置主题和RabbitMQ配置的对应关系 ( end ) **************************************************/

    }
}