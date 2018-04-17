import com.myself.deployrequester.biz.config.impl.AbstractRabbitMQConfig

/**
 *   Created by QueRenJie on ${date}*/
class RabbitMQConfig extends AbstractRabbitMQConfig {

    @Override
    void buildRabbitMQConfig() {
        /**********配置主题和RabbitMQ配置的对应关系 (begin) **************************************************/
        //本地开发环境
        addSubjectRabbitMQConfigMapping("createDbscriptFile", "172.19.14.201", "5672", "querenjie", "querenjie", "message_queue_1");
        //正式环境
//        addSubjectRabbitMQConfigMapping("createDbscriptFile", "http://172.19.14.237", "5672", "querenjie", "querenjie", "message_queue_1");
        /**********配置主题和RabbitMQ配置的对应关系 ( end ) **************************************************/

    }
}