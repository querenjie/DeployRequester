import com.myself.deployrequester.biz.config.impl.AbstractRabbitMQConfig

/**
 *   Created by QueRenJie on ${date}*/
class RabbitMQConfig extends AbstractRabbitMQConfig {

    @Override
    void buildRabbitMQConfig() {
        /**********���������RabbitMQ���õĶ�Ӧ��ϵ (begin) **************************************************/
        //���ؿ�������
        addSubjectRabbitMQConfigMapping("createDbscriptFile", "172.19.14.201", "5672", "querenjie", "querenjie", "message_queue_1");
        //��ʽ����
//        addSubjectRabbitMQConfigMapping("createDbscriptFile", "http://172.19.14.237", "5672", "querenjie", "querenjie", "message_queue_1");
        /**********���������RabbitMQ���õĶ�Ӧ��ϵ ( end ) **************************************************/

    }
}