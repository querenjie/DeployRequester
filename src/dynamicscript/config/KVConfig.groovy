import com.myself.deployrequester.biz.config.impl.AbstractConfig
import com.myself.deployrequester.biz.config.sharedata.RoleEnum;
import com.myself.deployrequester.biz.config.spi.Config;

/**
 *   Created by QueRenJie on ${date}*/
class KVConfig extends AbstractConfig {

    void buildConfig() {
        /**********������Ŀ (begin) **************************************************/
        addProject(1, "����");
        addProject(2, "����");
        addProject(3, "���ڽ���");
        //addProject(4, "ϵͳ");
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

        /**********�����޸����� (begin) **************************************************/
        addModifyType(1, "bug fix");
        addModifyType(2, "new feature development");
        addModifyType(3, "feature modification");
        addModifyType(4, "redoplyment");
        /**********�����޸����� ( end ) **************************************************/

        /**********����ģ�鷢����URL (begin) **************************************************/
        //1:����
        addModuleDeployURL(1, "system", "ϵͳ����(vr-system)", "rest", "http://172.16.36.69:8080/deploy/system/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/system/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "system", "ϵͳ����(vr-system)", "provider", "http://172.16.36.69:8080/deploy/system/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/system/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "base", "������Դ(vr-base)", "rest", "http://172.16.36.69:8080/deploy/base/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/base/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "base", "������Դ(vr-base)", "provider", "http://172.16.36.69:8080/deploy/base/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/base/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "contract", "��ͬ����(vr-contract)", "rest", "http://172.16.36.69:8080/deploy/contract/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/contract/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "contract", "��ͬ����(vr-contract)", "provider", "http://172.16.36.69:8080/deploy/contract/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/contract/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "finance", "�������(vr-finance)", "rest", "http://172.16.36.69:8080/deploy/finance/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/finance/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "finance", "�������(vr-finance)", "provider", "http://172.16.36.69:8080/deploy/finance/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/finance/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "reportform", "�������(vr-reportform)", "rest", "http://172.16.36.69:8080/deploy/reportform/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/reportform/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "reportform", "�������(vr-reportform)", "provider", "http://172.16.36.69:8080/deploy/reportform/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/reportform/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "goods", "��Ʒ����(vr-goods)", "rest", "http://172.16.36.69:8080/deploy/goods/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/goods/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "goods", "��Ʒ����(vr-goods)", "provider", "http://172.16.36.69:8080/deploy/goods/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/goods/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "purchase", "�ɹ�����(vr-purchase)", "rest", "http://172.16.36.69:8080/deploy/purchase/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/purchase/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "purchase", "�ɹ�����(vr-purchase)", "provider", "http://172.16.36.69:8080/deploy/purchase/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/purchase/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "sale", "���۹���(vr-sale)", "rest", "http://172.16.36.69:8080/deploy/sale/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/sale/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "sale", "���۹���(vr-sale)", "provider", "http://172.16.36.69:8080/deploy/sale/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/sale/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "supplier", "��Ӧ�̹���(vr-supplier)", "rest", "http://172.16.36.69:8080/deploy/supplier/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/supplier/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "supplier", "��Ӧ�̹���(vr-supplier)", "provider", "http://172.16.36.69:8080/deploy/supplier/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/supplier/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "wholesale", "��������(vr-wholesale)", "rest", "http://172.16.36.69:8080/deploy/wholesale/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/wholesale/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "wholesale", "��������(vr-wholesale)", "provider", "http://172.16.36.69:8080/deploy/wholesale/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/wholesale/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "storage", "�ִ�����(vr-storage)", "rest", "http://172.16.36.69:8080/deploy/storage/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/storage/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(1, "storage", "�ִ�����(vr-storage)", "provider", "http://172.16.36.69:8080/deploy/storage/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/storage/provider/DAXtC80JSV5oK2xw");

        //2:����
        addModuleDeployURL(2, "system", "ϵͳ����(vr-wnnj-system)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/system/rest/v1.0.0/30917/8000/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "system", "ϵͳ����(vr-wnnj-system)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/system/provider/v1.0.0/20917/20917/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "base", "������Դ(vr-wnnj-base)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/base/rest/v1.0.0/40881/8103/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "base", "������Դ(vr-wnnj-base)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/base/provider/v1.0.0/20881/20881/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "contract", "��ͬ����(vr-wnnj-contract)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/contract/rest/v1.0.0/40882/8112/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "contract", "��ͬ����(vr-wnnj-contract)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/contract/provider/v1.0.0/20882/20882/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "finance", "�������(vr-wnnj-finance)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/finance/rest/v1.0.0/40883/8110/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "finance", "�������(vr-wnnj-finance)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/finance/provider/v1.0.0/20883/20883/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "reportform", "�������(vr-wnnj-reportform)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/reportform/rest/v1.0.0/40886/8129/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "reportform", "�������(vr-wnnj-reportform)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/reportform/provider/v1.0.0/20886/20886/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "goods", "��Ʒ����(vr-wnnj-goods)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/goods/rest/v1.0.0/40884/8104/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "goods", "��Ʒ����(vr-wnnj-goods)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/goods/provider/v1.0.0/20884/20884/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "purchase", "�ɹ�����(vr-wnnj-purchase)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/purchase/rest/v1.0.0/40885/8106/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "purchase", "�ɹ�����(vr-wnnj-purchase)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/purchase/provider/v1.0.0/20885/20885/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "sale", "���۹���(vr-wnnj-sale)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/sale/rest/v1.0.0/40887/8107/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "sale", "���۹���(vr-wnnj-sale)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/sale/provider/v1.0.0/20887/20887/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "supplier", "��Ӧ�̹���(vr-wnnj-supplier)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/supplier/rest/v1.0.0/40889/8125/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "supplier", "��Ӧ�̹���(vr-wnnj-supplier)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/supplier/provider/v1.0.0/20889/20889/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "kitchen", "�������(vr-wnnj-kitchen)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/kitchen/rest/v1.0.0/40894/8116/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "kitchen", "�������(vr-wnnj-kitchen)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/kitchen/provider/v1.0.0/20894/20894/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "market", "�г�Ӫ��(vr-wnnj-market)", "rest", "http://172.16.36.69:8081/deploy/njxs/market/rest/v1.0.0/40895/8120/test/172.16.36.71/172.16.44.16", "");
        addModuleDeployURL(2, "market", "�г�Ӫ��(vr-wnnj-market)", "provider", "http://172.16.36.69:8081/deploy/njxs/market/provider/v1.0.0/20895/20895/test/172.16.36.68/172.16.44.16", "");
//        addModuleDeployURL(2, "wholesale", "��������(vr-wnnj-wholesale)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/wholesale/rest/v1.0.0/40891/8111/test/172.16.44.16/172.16.44.16", "");
//        addModuleDeployURL(2, "wholesale", "��������(vr-wnnj-wholesale)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/wholesale/provider/v1.0.0/20891/20891/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "wholesale", "��������(vr-wnnj-wholesale)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/wholesale/rest/v1.0.0/40890/8108/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "wholesale", "��������(vr-wnnj-wholesale)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/wholesale/provider/v1.0.0/20890/20890/test/172.16.44.16/host", "");
        addModuleDeployURL(2, "storage", "�ִ�����(vr-wnnj-storage)", "rest", "http://172.16.36.69:8081/deploy/vr-wnnj/storage/rest/v1.0.0/40891/8111/test/172.16.44.16/172.16.44.16", "");
        addModuleDeployURL(2, "storage", "�ִ�����(vr-wnnj-storage)", "provider", "http://172.16.36.69:8081/deploy/vr-wnnj/storage/provider/v1.0.0/20891/20891/test/172.16.44.16/host", "");

        //3:���ڽ���
        addModuleDeployURL(3, "bkcontract", "���ڽ��׺�ͬ����(vr-bulkstock-contract)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/contract/rest/v1.0.0/35001/35001/test/172.16.36.71/172.16.44.18", "");
        addModuleDeployURL(3, "bkcontract", "���ڽ��׺�ͬ����(vr-bulkstock-contract)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/contract/provider/v1.0.0/25001/25001/test/172.16.36.68/172.16.44.18", "");
        addModuleDeployURL(3, "bkfinance", "���ڽ��ײ������(vr-bulkstock-finance)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/finance/rest/v1.0.0/35008/35008/test/172.16.36.71/172.16.44.18", "");
        addModuleDeployURL(3, "bkfinance", "���ڽ��ײ������(vr-bulkstock-finance)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/finance/provider/v1.0.0/25008/25008/test/172.16.36.68/172.16.44.18", "");
        addModuleDeployURL(3, "bksale", "���ڽ������۹���(vr-bulkstock-sale)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/sale/rest/v1.0.0/35005/35005/test/172.16.36.71/172.16.44.18", "");
        addModuleDeployURL(3, "bksale", "���ڽ������۹���(vr-bulkstock-sale)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/sale/provider/v1.0.0/25005/25005/test/172.16.36.68/172.16.44.18", "");
        addModuleDeployURL(3, "bksettlement", "���ڽ��׽������(vr-bulkstock-settlement)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/settlement/rest/v1.0.0/35009/35009/test/172.16.36.71/172.16.44.18", "");
        addModuleDeployURL(3, "bksettlement", "���ڽ��׽������(vr-bulkstock-settlement)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/settlement/provider/v1.0.0/25009/25009/test/172.16.36.68/172.16.44.18", "");
        addModuleDeployURL(3, "bktender", "���ڽ�����Ͷ�����(vr-bulkstock-tender)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/tender/rest/v1.0.0/35004/35004/test/172.16.36.71/172.16.44.18", "");
        addModuleDeployURL(3, "bktender", "���ڽ�����Ͷ�����(vr-bulkstock-tender)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/tender/provider/v1.0.0/25004/25004/test/172.16.36.68/172.16.44.18", "");
        addModuleDeployURL(3, "bktransportmanage", "���ڽ����������(vr-bulkstock-transportmanage)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/transportmanage/rest/v1.0.0/35011/35011/test/172.16.36.71/172.16.44.18", "");
        addModuleDeployURL(3, "bktransportmanage", "���ڽ����������(vr-bulkstock-transportmanage)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/transportmanage/provider/v1.0.0/25011/25011/test/172.16.36.68/172.16.44.18", "");
        addModuleDeployURL(3, "bkstorage", "���ڽ��ײִ�����(vr-bulkstock-storage)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/storage/rest/v1.0.0/35012/35012/test/172.16.36.71/172.16.44.18", "");
        addModuleDeployURL(3, "bkstorage", "���ڽ��ײִ�����(vr-bulkstock-storage)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/storage/provider/v1.0.0/25012/25012/test/172.16.36.68/172.16.44.18", "");
        addModuleDeployURL(3, "bkreportform", "���ڽ��ױ������(vr-bulkstock-reportform)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/reportform/rest/v1.0.0/35013/35013/test/172.16.36.71/172.16.44.18", "");
        addModuleDeployURL(3, "bkreportform", "���ڽ��ױ������(vr-bulkstock-reportform)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/reportform/provider/v1.0.0/25013/25013/test/172.16.36.68/172.16.44.18", "");

        addModuleDeployURL(3, "system", "ϵͳ����(vr-bulkstock-system)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/system/rest/v1.0.0/30917/8000/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "system", "ϵͳ����(vr-bulkstock-system)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/system/provider/v1.0.0/20917/20917/test/172.16.53.8/host", "");
        addModuleDeployURL(3, "base", "������Դ(vr-bulkstock-base)", "rest", "http://172.16.36.69:8081/deploy/vr-bulkstock/base/rest/v1.0.0/40881/8103/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "base", "������Դ(vr-bulkstock-base)", "provider", "http://172.16.36.69:8081/deploy/vr-bulkstock/base/provider/v1.0.0/20881/20881/test/172.16.53.8/host", "");
        addModuleDeployURL(3, "contract", "��ͬ����(vr-bk-common-contract)", "rest", "http://172.16.36.69:8081/deploy/vr-bk-common/contract/rest/v1.0.0/40882/8112/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "contract", "��ͬ����(vr-bk-common-contract)", "provider", "http://172.16.36.69:8081/deploy/vr-bk-common/contract/provider/v1.0.0/20882/20882/test/172.16.53.8/host", "");
        addModuleDeployURL(3, "finance", "�������(vr-bk-common-finance)", "rest", "http://172.16.36.69:8081/deploy/vr-bk-common/finance/rest/v1.0.0/40883/8110/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "finance", "�������(vr-bk-common-finance)", "provider", "http://172.16.36.69:8081/deploy/vr-bk-common/finance/provider/v1.0.0/20883/20883/test/172.16.53.8/host", "");
        addModuleDeployURL(3, "goods", "��Ʒ����(vr-bk-common-goods)", "rest", "http://172.16.36.69:8081/deploy/vr-bk-common/goods/rest/v1.0.0/40884/8104/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "goods", "��Ʒ����(vr-bk-common-goods)", "provider", "http://172.16.36.69:8081/deploy/vr-bk-common/goods/provider/v1.0.0/20884/20884/test/172.16.53.8/host", "");
        addModuleDeployURL(3, "purchase", "�ɹ�����(vr-bk-common-purchase)", "rest", "http://172.16.36.69:8081/deploy/vr-bk-common/purchase/rest/v1.0.0/40885/8106/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "purchase", "�ɹ�����(vr-bk-common-purchase)", "provider", "http://172.16.36.69:8081/deploy/vr-bk-common/purchase/provider/v1.0.0/20885/20885/test/172.16.53.8/host", "");
        addModuleDeployURL(3, "sale", "���۹���(vr-bk-common-sale)", "rest", "http://172.16.36.69:8081/deploy/vr-bk-common/sale/rest/v1.0.0/40887/8107/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "sale", "���۹���(vr-bk-common-sale)", "provider", "http://172.16.36.69:8081/deploy/vr-bk-common/sale/provider/v1.0.0/20887/20887/test/172.16.53.8/host", "");
        addModuleDeployURL(3, "storage", "�ִ�����(vr-bk-common-storage)", "rest", "http://172.16.36.69:8081/deploy/vr-bk-common/storage/rest/v1.0.0/40891/8111/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "storage", "�ִ�����(vr-bk-common-storage)", "provider", "http://172.16.36.69:8081/deploy/vr-bk-common/storage/provider/v1.0.0/20891/20891/test/172.16.53.8/host", "");
        addModuleDeployURL(3, "supplier", "��Ӧ�̹���(vr-bk-common-supplier)", "rest", "http://172.16.36.69:8081/deploy/vr-bk-common/supplier/rest/v1.0.0/40889/8125/test/172.16.53.8/172.16.44.18", "");
        addModuleDeployURL(3, "supplier", "��Ӧ�̹���(vr-bk-common-supplier)", "provider", "http://172.16.36.69:8081/deploy/vr-bk-common/supplier/provider/v1.0.0/20889/20889/test/172.16.53.8/host", "");
        //4:ϵͳ
        addModuleDeployURL(4, "system", "ϵͳ����", "rest", "http://172.16.36.69:8080/deploy/system/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/system/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(4, "system", "ϵͳ����", "provider", "http://172.16.36.69:8080/deploy/system/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/system/provider/DAXtC80JSV5oK2xw");
        addModuleDeployURL(4, "base", "������Դ", "rest", "http://172.16.36.69:8080/deploy/base/rest/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/base/rest/DAXtC80JSV5oK2xw");
        addModuleDeployURL(4, "base", "������Դ", "provider", "http://172.16.36.69:8080/deploy/base/provider/habjZOqHMFg1425Z", "http://172.16.36.69:8080/produceDeploy/base/provider/DAXtC80JSV5oK2xw");
        /**********����ģ�鷢����URL (end) **************************************************/

        /**********���ò��Ի�������־URL (begin) **************************************************/
        //1:����
        addTestLogURL(1, "system", "ϵͳ����", "rest", "http://172.16.36.69:8080/tlogs/system/rest", "http://172.16.36.69:8080/tslogs/system/rest", "http://172.16.36.69:8080/tstatus/system/rest");
        addTestLogURL(1, "system", "ϵͳ����", "provider", "http://172.16.36.69:8080/tlogs/system/provider", "http://172.16.36.69:8080/tslogs/system/provider", "http://172.16.36.69:8080/tstatus/system/provider");
        addTestLogURL(1, "base", "������Դ", "rest", "http://172.16.36.69:8080/tlogs/base/rest", "http://172.16.36.69:8080/tslogs/base/rest", "http://172.16.36.69:8080/tstatus/base/rest");
        addTestLogURL(1, "base", "������Դ", "provider", "http://172.16.36.69:8080/tlogs/base/provider", "http://172.16.36.69:8080/tslogs/base/provider", "http://172.16.36.69:8080/tstatus/base/provider");
        addTestLogURL(1, "contract", "��ͬ����", "rest", "http://172.16.36.69:8080/tlogs/contract/rest", "http://172.16.36.69:8080/tslogs/contract/rest", "http://172.16.36.69:8080/tstatus/contract/rest");
        addTestLogURL(1, "contract", "��ͬ����", "provider", "http://172.16.36.69:8080/tlogs/contract/provider", "http://172.16.36.69:8080/tslogs/contract/provider", "http://172.16.36.69:8080/tstatus/contract/provider");
        addTestLogURL(1, "finance", "�������", "rest", "http://172.16.36.69:8080/tlogs/finance/rest", "http://172.16.36.69:8080/tslogs/finance/rest", "http://172.16.36.69:8080/tstatus/finance/rest");
        addTestLogURL(1, "finance", "�������", "provider", "http://172.16.36.69:8080/tlogs/finance/provider", "http://172.16.36.69:8080/tslogs/finance/provider", "http://172.16.36.69:8080/tstatus/finance/provider");
        addTestLogURL(1, "reportform", "�������", "rest", "http://172.16.36.69:8080/tlogs/reportform/rest", "http://172.16.36.69:8080/tslogs/reportform/rest", "http://172.16.36.69:8080/tstatus/reportform/rest");
        addTestLogURL(1, "reportform", "�������", "provider", "http://172.16.36.69:8080/tlogs/reportform/provider", "http://172.16.36.69:8080/tslogs/reportform/provider", "http://172.16.36.69:8080/tstatus/reportform/provider");
        addTestLogURL(1, "goods", "��Ʒ����", "rest", "http://172.16.36.69:8080/tlogs/goods/rest", "http://172.16.36.69:8080/tslogs/goods/rest", "http://172.16.36.69:8080/tstatus/goods/rest");
        addTestLogURL(1, "goods", "��Ʒ����", "provider", "http://172.16.36.69:8080/tlogs/goods/provider", "http://172.16.36.69:8080/tslogs/goods/provider", "http://172.16.36.69:8080/tstatus/goods/provider");
        addTestLogURL(1, "purchase", "�ɹ�����", "rest", "http://172.16.36.69:8080/tlogs/purchase/rest", "http://172.16.36.69:8080/tslogs/purchase/rest", "http://172.16.36.69:8080/tstatus/purchase/rest");
        addTestLogURL(1, "purchase", "�ɹ�����", "provider", "http://172.16.36.69:8080/tlogs/purchase/provider", "http://172.16.36.69:8080/tslogs/purchase/provider", "http://172.16.36.69:8080/tstatus/purchase/provider");
        addTestLogURL(1, "sale", "���۹���", "rest", "http://172.16.36.69:8080/tlogs/sale/rest", "http://172.16.36.69:8080/tslogs/sale/rest", "http://172.16.36.69:8080/tstatus/sale/rest");
        addTestLogURL(1, "sale", "���۹���", "provider", "http://172.16.36.69:8080/tlogs/sale/provider", "http://172.16.36.69:8080/tslogs/sale/provider", "http://172.16.36.69:8080/tstatus/sale/provider");
        addTestLogURL(1, "supplier", "��Ӧ�̹���", "rest", "http://172.16.36.69:8080/tlogs/supplier/rest", "http://172.16.36.69:8080/tslogs/supplier/rest", "http://172.16.36.69:8080/tstatus/supplier/rest");
        addTestLogURL(1, "supplier", "��Ӧ�̹���", "provider", "http://172.16.36.69:8080/tlogs/supplier/provider", "http://172.16.36.69:8080/tslogs/supplier/provider", "http://172.16.36.69:8080/tstatus/supplier/provider");
        addTestLogURL(1, "wholesale", "��������", "rest", "http://172.16.36.69:8080/tlogs/wholesale/rest", "http://172.16.36.69:8080/tslogs/wholesale/rest", "http://172.16.36.69:8080/tstatus/wholesale/rest");
        addTestLogURL(1, "wholesale", "��������", "provider", "http://172.16.36.69:8080/tlogs/wholesale/provider", "http://172.16.36.69:8080/tslogs/wholesale/provider", "http://172.16.36.69:8080/tstatus/wholesale/provider");
        addTestLogURL(1, "storage", "�ִ�����", "rest", "http://172.16.36.69:8080/tlogs/storage/rest", "http://172.16.36.69:8080/tslogs/storage/rest", "http://172.16.36.69:8080/tstatus/storage/rest");
        addTestLogURL(1, "storage", "�ִ�����", "provider", "http://172.16.36.69:8080/tlogs/storage/provider", "http://172.16.36.69:8080/tslogs/storage/provider", "http://172.16.36.69:8080/tstatus/storage/provider");

        //2:����
        addTestLogURL(2, "system", "ϵͳ����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/system/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/system/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "system", "ϵͳ����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/system/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/system/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "base", "������Դ", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/base/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/base/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "base", "������Դ", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/base/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/base/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "contract", "��ͬ����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/contract/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/contract/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "contract", "��ͬ����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/contract/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/contract/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "finance", "�������", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/finance/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/finance/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "finance", "�������", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/finance/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/finance/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "reportform", "�������", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/reportform/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/reportform/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "reportform", "�������", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/reportform/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/reportform/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "goods", "��Ʒ����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/goods/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/goods/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "goods", "��Ʒ����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/goods/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/goods/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "purchase", "�ɹ�����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/purchase/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/purchase/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "purchase", "�ɹ�����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/purchase/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/purchase/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "sale", "���۹���", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/sale/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/sale/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "sale", "���۹���", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/sale/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/sale/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "supplier", "��Ӧ�̹���", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/supplier/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/supplier/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "supplier", "��Ӧ�̹���", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/supplier/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/supplier/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "kitchen", "�������", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/kitchen/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/kitchen/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "kitchen", "�������", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/kitchen/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/kitchen/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "market", "�г�Ӫ��", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/market/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/market/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "market", "�г�Ӫ��", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/market/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/market/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "wholesale", "��������", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/wholesale/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/wholesale/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "wholesale", "��������", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/wholesale/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/wholesale/provider/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "storage", "�ִ�����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/storage/rest/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/storage/rest/v1.0.0/test/172.16.44.16", "");
        addTestLogURL(2, "storage", "�ִ�����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/storage/provider/v1.0.0/test/172.16.44.16", "http://172.16.36.69:8081/slogs/vr-wnnj/storage/provider/v1.0.0/test/172.16.44.16", "");

        //���ڽ���
        addTestLogURL(3, "bkcontract", "���ڽ��׺�ͬ����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/contract/rest/v1.0.0/test/172.16.36.71", "http://172.16.36.69:8081/slogs/vr-bulkstock/contract/rest/v1.0.0/test/172.16.36.71", "");
        addTestLogURL(3, "bkcontract", "���ڽ��׺�ͬ����", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/contract/provider/v1.0.0/test/172.16.36.68", "http://172.16.36.69:8081/slogs/vr-bulkstock/contract/provider/v1.0.0/test/172.16.36.68", "");
        addTestLogURL(3, "bkfinance", "���ڽ��ײ������", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/finance/rest/v1.0.0/test/172.16.36.71", "http://172.16.36.69:8081/slogs/vr-bulkstock/finance/rest/v1.0.0/test/172.16.36.71", "");
        addTestLogURL(3, "bkfinance", "���ڽ��ײ������", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/finance/provider/v1.0.0/test/172.16.36.68", "http://172.16.36.69:8081/slogs/vr-bulkstock/finance/provider/v1.0.0/test/172.16.36.68", "");
        addTestLogURL(3, "bksale", "���ڽ������۹���", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/sale/rest/v1.0.0/test/172.16.36.71", "http://172.16.36.69:8081/slogs/vr-bulkstock/sale/rest/v1.0.0/test/172.16.36.71", "");
        addTestLogURL(3, "bksale", "���ڽ������۹���", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/sale/provider/v1.0.0/test/172.16.36.68", "http://172.16.36.69:8081/slogs/vr-bulkstock/sale/provider/v1.0.0/test/172.16.36.68", "");
        addTestLogURL(3, "bksettlement", "���ڽ��׽������", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/settlement/rest/v1.0.0/test/172.16.36.71", "http://172.16.36.69:8081/slogs/vr-bulkstock/settlement/rest/v1.0.0/test/172.16.36.71", "");
        addTestLogURL(3, "bksettlement", "���ڽ��׽������", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/settlement/provider/v1.0.0/test/172.16.36.68", "http://172.16.36.69:8081/slogs/vr-bulkstock/settlement/provider/v1.0.0/test/172.16.36.68", "");
        addTestLogURL(3, "bktender", "���ڽ�����Ͷ�����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/tender/rest/v1.0.0/test/172.16.36.71", "http://172.16.36.69:8081/slogs/vr-bulkstock/tender/rest/v1.0.0/test/172.16.36.71", "");
        addTestLogURL(3, "bktender", "���ڽ�����Ͷ�����", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/tender/provider/v1.0.0/test/172.16.36.68", "http://172.16.36.69:8081/slogs/vr-bulkstock/tender/provider/v1.0.0/test/172.16.36.68", "");
        addTestLogURL(3, "bktransportmanage", "���ڽ����������", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/transportmanage/rest/v1.0.0/test/172.16.36.71", "http://172.16.36.69:8081/slogs/vr-bulkstock/transportmanage/rest/v1.0.0/test/172.16.36.71", "");
        addTestLogURL(3, "bktransportmanage", "���ڽ����������", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/transportmanage/provider/v1.0.0/test/172.16.36.68", "http://172.16.36.69:8081/slogs/vr-bulkstock/transportmanage/provider/v1.0.0/test/172.16.36.68", "");
        addTestLogURL(3, "bkstorage", "���ڽ��ײִ�����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/storage/rest/v1.0.0/test/172.16.36.71", "http://172.16.36.69:8081/slogs/vr-bulkstock/storage/rest/v1.0.0/test/172.16.36.71", "");
        addTestLogURL(3, "bkstorage", "���ڽ��ײִ�����", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/storage/provider/v1.0.0/test/172.16.36.68", "http://172.16.36.69:8081/slogs/vr-bulkstock/storage/provider/v1.0.0/test/172.16.36.68", "");
        addTestLogURL(3, "bkreportform", "���ڽ�����ģ��", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/reportform/rest/v1.0.0/test/172.16.36.71", "http://172.16.36.69:8081/slogs/vr-bulkstock/reportform/rest/v1.0.0/test/172.16.36.71", "");
        addTestLogURL(3, "bkreportform", "���ڽ�����ģ��", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/reportform/provider/v1.0.0/test/172.16.36.68", "http://172.16.36.69:8081/slogs/vr-bulkstock/reportform/provider/v1.0.0/test/172.16.36.68", "");

        addTestLogURL(3, "system", "ϵͳ����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/system/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bulkstock/system/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "system", "ϵͳ����", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/system/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bulkstock/system/provider/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "base", "������Դ", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/base/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bulkstock/base/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "base", "������Դ", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/base/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bulkstock/base/provider/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "contract", "��ͬ����", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/contract/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/contract/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "contract", "��ͬ����", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/contract/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/contract/provider/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "finance", "�������", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/finance/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/finance/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "finance", "�������", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/finance/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/finance/provider/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "goods", "��Ʒ����", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/goods/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/goods/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "goods", "��Ʒ����", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/goods/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/goods/provider/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "purchase", "�ɹ�����", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/purchase/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/purchase/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "purchase", "�ɹ�����", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/purchase/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/purchase/provider/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "sale", "���۹���", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/sale/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/sale/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "sale", "���۹���", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/sale/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/sale/provider/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "supplier", "��Ӧ�̹���", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/supplier/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/supplier/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "supplier", "��Ӧ�̹���", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/supplier/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/supplier/provider/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "storage", "�ִ�����", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/storage/rest/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/storage/rest/v1.0.0/test/172.16.53.8", "");
        addTestLogURL(3, "storage", "�ִ�����", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/storage/provider/v1.0.0/test/172.16.53.8", "http://172.16.36.69:8081/slogs/vr-bk-common/storage/provider/v1.0.0/test/172.16.53.8", "");
        /**********���ò��Ի�������־URL (end) **************************************************/

        /**********����������������־URL (begin) **************************************************/
        //1:����
        addProductLogURL(1, "system", "ϵͳ����", "rest", "http://172.16.36.69:8081/logs/common/system/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/common/system/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "system", "ϵͳ����", "provider", "http://172.16.36.69:8081/logs/common/system/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/common/system/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "base", "������Դ", "rest", "http://172.16.36.69:8081/logs/common/base/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/common/base/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "base", "������Դ", "provider", "http://172.16.36.69:8081/logs/common/base/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/common/base/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "contract", "��ͬ����", "rest", "http://172.16.36.69:8081/logs/finance/contract/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/finance/contract/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "contract", "��ͬ����", "provider", "http://172.16.36.69:8081/logs/finance/contract/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/finance/contract/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "finance", "�������", "rest", "http://172.16.36.69:8081/logs/finance/finance/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/finance/finance/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "finance", "�������", "provider", "http://172.16.36.69:8081/logs/finance/finance/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/finance/finance/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "reportform", "�������", "rest", "http://172.16.36.69:8081/logs/finance/reportform/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/finance/reportform/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "reportform", "�������", "provider", "http://172.16.36.69:8081/logs/finance/reportform/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/finance/reportform/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "goods", "��Ʒ����", "rest", "http://172.16.36.69:8081/logs/market/goods/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/market/goods/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "goods", "��Ʒ����", "provider", "http://172.16.36.69:8081/logs/market/goods/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/market/goods/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "purchase", "�ɹ�����", "rest", "http://172.16.36.69:8081/logs/market/purchase/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/market/purchase/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "purchase", "�ɹ�����", "provider", "http://172.16.36.69:8081/logs/market/purchase/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/market/purchase/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "sale", "���۹���", "rest", "http://172.16.36.69:8081/logs/market/sale/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/market/sale/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "sale", "���۹���", "provider", "http://172.16.36.69:8081/logs/market/sale/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/market/sale/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "supplier", "��Ӧ�̹���", "rest", "http://172.16.36.69:8081/logs/market/supplier/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/market/supplier/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "supplier", "��Ӧ�̹���", "provider", "http://172.16.36.69:8081/logs/market/supplier/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/market/supplier/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");
        addProductLogURL(1, "wholesale", "��������", "rest", "", "", "");
        addProductLogURL(1, "wholesale", "��������", "provider", "", "", "");
        addProductLogURL(1, "storage", "�ִ�����", "rest", "http://172.16.36.69:8081/logs/scm/storage/rest/v1.0.0/40891/8111/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8081/slogs/scm/storage/rest/v1.0.0/40891/8111/produce/172.16.41.11,172.16.41.12", "");
        addProductLogURL(1, "storage", "�ִ�����", "provider", "http://172.16.36.69:8081/logs/scm/storage/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "http://172.16.36.69:8081/slogs/scm/storage/provider/v1.0.0/produce/172.16.41.16,172.16.41.18", "");

//        addProductLogURL(1, "system", "ϵͳ����", "rest", "http://172.16.36.69:8081/logs/common/system/rest/v1.0.0/produce/172.16.41.11,172.16.41.12", "http://172.16.36.69:8080/pslogs/system/rest", "http://172.16.36.69:8080/pstatus/system/rest");
//        addProductLogURL(1, "system", "ϵͳ����", "provider", "http://172.16.36.69:8080/plogs/system/provider", "http://172.16.36.69:8080/pslogs/system/provider", "http://172.16.36.69:8080/pstatus/system/provider");
//        addProductLogURL(1, "base", "������Դ", "rest", "http://172.16.36.69:8080/plogs/base/rest", "http://172.16.36.69:8080/pslogs/base/rest", "http://172.16.36.69:8080/pstatus/base/rest");
//        addProductLogURL(1, "base", "������Դ", "provider", "http://172.16.36.69:8080/plogs/base/provider", "http://172.16.36.69:8080/pslogs/base/provider", "http://172.16.36.69:8080/pstatus/base/provider");
//        addProductLogURL(1, "contract", "��ͬ����", "rest", "http://172.16.36.69:8080/plogs/contract/rest", "http://172.16.36.69:8080/pslogs/contract/rest", "http://172.16.36.69:8080/pstatus/contract/rest");
//        addProductLogURL(1, "contract", "��ͬ����", "provider", "http://172.16.36.69:8080/plogs/contract/provider", "http://172.16.36.69:8080/pslogs/contract/provider", "http://172.16.36.69:8080/pstatus/contract/provider");
//        addProductLogURL(1, "finance", "�������", "rest", "http://172.16.36.69:8080/plogs/finance/rest", "http://172.16.36.69:8080/pslogs/finance/rest", "http://172.16.36.69:8080/pstatus/finance/rest");
//        addProductLogURL(1, "finance", "�������", "provider", "http://172.16.36.69:8080/plogs/finance/provider", "http://172.16.36.69:8080/pslogs/finance/provider", "http://172.16.36.69:8080/pstatus/finance/provider");
//        addProductLogURL(1, "reportform", "�������", "rest", "http://172.16.36.69:8080/plogs/reportform/rest", "http://172.16.36.69:8080/pslogs/reportform/rest", "http://172.16.36.69:8080/pstatus/reportform/rest");
//        addProductLogURL(1, "reportform", "�������", "provider", "http://172.16.36.69:8080/plogs/reportform/provider", "http://172.16.36.69:8080/pslogs/reportform/provider", "http://172.16.36.69:8080/pstatus/reportform/provider");
//        addProductLogURL(1, "goods", "��Ʒ����", "rest", "http://172.16.36.69:8080/plogs/goods/rest", "http://172.16.36.69:8080/pslogs/goods/rest", "http://172.16.36.69:8080/pstatus/goods/rest");
//        addProductLogURL(1, "goods", "��Ʒ����", "provider", "http://172.16.36.69:8080/plogs/goods/provider", "http://172.16.36.69:8080/pslogs/goods/provider", "http://172.16.36.69:8080/pstatus/goods/provider");
//        addProductLogURL(1, "purchase", "�ɹ�����", "rest", "http://172.16.36.69:8080/plogs/purchase/rest", "http://172.16.36.69:8080/pslogs/purchase/rest", "http://172.16.36.69:8080/pstatus/purchase/rest");
//        addProductLogURL(1, "purchase", "�ɹ�����", "provider", "http://172.16.36.69:8080/plogs/purchase/provider", "http://172.16.36.69:8080/pslogs/purchase/provider", "http://172.16.36.69:8080/pstatus/purchase/provider");
//        addProductLogURL(1, "sale", "���۹���", "rest", "http://172.16.36.69:8080/plogs/sale/rest", "http://172.16.36.69:8080/pslogs/sale/rest", "http://172.16.36.69:8080/pstatus/sale/rest");
//        addProductLogURL(1, "sale", "���۹���", "provider", "http://172.16.36.69:8080/plogs/sale/provider", "http://172.16.36.69:8080/pslogs/sale/provider", "http://172.16.36.69:8080/pstatus/sale/provider");
//        addProductLogURL(1, "supplier", "��Ӧ�̹���", "rest", "http://172.16.36.69:8080/plogs/supplier/rest", "http://172.16.36.69:8080/pslogs/supplier/rest", "http://172.16.36.69:8080/pstatus/supplier/rest");
//        addProductLogURL(1, "supplier", "��Ӧ�̹���", "provider", "http://172.16.36.69:8080/plogs/supplier/provider", "http://172.16.36.69:8080/pslogs/supplier/provider", "http://172.16.36.69:8080/pstatus/supplier/provider");
//        addProductLogURL(1, "wholesale", "��������", "rest", "http://172.16.36.69:8080/plogs/wholesale/rest", "http://172.16.36.69:8080/pslogs/wholesale/rest", "http://172.16.36.69:8080/pstatus/wholesale/rest");
//        addProductLogURL(1, "wholesale", "��������", "provider", "http://172.16.36.69:8080/plogs/wholesale/provider", "http://172.16.36.69:8080/pslogs/wholesale/provider", "http://172.16.36.69:8080/pstatus/wholesale/provider");
//        addProductLogURL(1, "storage", "�ִ�����", "rest", "http://172.16.36.69:8080/plogs/storage/rest", "http://172.16.36.69:8080/pslogs/storage/rest", "http://172.16.36.69:8080/pstatus/storage/rest");
//        addProductLogURL(1, "storage", "�ִ�����", "provider", "http://172.16.36.69:8080/plogs/storage/provider", "http://172.16.36.69:8080/pslogs/storage/provider", "http://172.16.36.69:8080/pstatus/storage/provider");

        //2:����
        addProductLogURL(2, "system", "ϵͳ����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/system/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/system/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "system", "ϵͳ����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/system/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "http://172.16.36.69:8081/slogs/vr-wnnj/system/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "");
        addProductLogURL(2, "base", "������Դ", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/base/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/base/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "base", "������Դ", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/base/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "http://172.16.36.69:8081/slogs/vr-wnnj/base/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "");
        addProductLogURL(2, "contract", "��ͬ����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/contract/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/contract/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "contract", "��ͬ����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/contract/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "http://172.16.36.69:8081/slogs/vr-wnnj/contract/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "");
        addProductLogURL(2, "finance", "�������", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/finance/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/finance/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "finance", "�������", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/finance/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "http://172.16.36.69:8081/slogs/vr-wnnj/finance/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "");
        addProductLogURL(2, "reportform", "�������", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/reportform/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/reportform/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "reportform", "�������", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/reportform/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "http://172.16.36.69:8081/slogs/vr-wnnj/reportform/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "");
        addProductLogURL(2, "goods", "��Ʒ����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/goods/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/goods/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "goods", "��Ʒ����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/goods/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "http://172.16.36.69:8081/slogs/vr-wnnj/goods/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "");
        addProductLogURL(2, "purchase", "�ɹ�����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/purchase/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/purchase/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "purchase", "�ɹ�����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/purchase/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "http://172.16.36.69:8081/slogs/vr-wnnj/purchase/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "");
        addProductLogURL(2, "sale", "���۹���", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/sale/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/sale/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "sale", "���۹���", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/sale/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "http://172.16.36.69:8081/slogs/vr-wnnj/sale/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "");
        addProductLogURL(2, "supplier", "��Ӧ�̹���", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/supplier/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/supplier/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "supplier", "��Ӧ�̹���", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/supplier/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "http://172.16.36.69:8081/slogs/vr-wnnj/supplier/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "");
        addProductLogURL(2, "kitchen", "�������", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/kitchen/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/kitchen/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "kitchen", "�������", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/kitchen/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "http://172.16.36.69:8081/slogs/vr-wnnj/kitchen/provider/v1.0.0/produce/172.16.51.8,172.16.51.11", "");
        addProductLogURL(2, "market", "�г�Ӫ��", "rest", "", "", "");
        addProductLogURL(2, "market", "�г�Ӫ��", "provider", "", "", "");
        addProductLogURL(2, "wholesale", "��������", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/wholesale/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/wholesale/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "wholesale", "��������", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/wholesale/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "http://172.16.36.69:8081/slogs/vr-wnnj/wholesale/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "");
        addProductLogURL(2, "storage", "�ִ�����", "rest", "http://172.16.36.69:8081/logs/vr-wnnj/storage/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "http://172.16.36.69:8081/slogs/vr-wnnj/storage/rest/v1.0.0/produce/172.16.51.9,172.16.51.10", "");
        addProductLogURL(2, "storage", "�ִ�����", "provider", "http://172.16.36.69:8081/logs/vr-wnnj/storage/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "http://172.16.36.69:8081/slogs/vr-wnnj/storage/provider/v1.0.0/produce/172.16.51.8,172.16.51.12", "");

        //3:���ڽ���
        addProductLogURL(3, "bkcontract", "���ڽ��׺�ͬ����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/contract/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/contract/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bkcontract", "���ڽ��׺�ͬ����", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/contract/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/contract/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bkfinance", "���ڽ��ײ������", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/finance/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/finance/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bkfinance", "���ڽ��ײ������", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/finance/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/finance/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bksale", "���ڽ������۹���", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/sale/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/sale/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bksale", "���ڽ������۹���", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/sale/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/sale/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bksettlement", "���ڽ��׽������", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/settlement/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/settlement/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bksettlement", "���ڽ��׽������", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/settlement/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/settlement/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bktender", "���ڽ�����Ͷ�����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/tender/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/tender/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bktender", "���ڽ�����Ͷ�����", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/tender/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/tender/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bktransportmanage", "���ڽ����������", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/transportmanage/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/transportmanage/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bktransportmanage", "���ڽ����������", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/transportmanage/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/transportmanage/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bkstorage", "���ڽ��ײִ�����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/storage/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/storage/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bkstorage", "���ڽ��ײִ�����", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/storage/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/storage/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bkreportform", "���ڽ��ױ���ģ��", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/reportform/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/reportform/rest/v1.0.0/produce/172.16.54.72,172.16.54.73", "");
        addProductLogURL(3, "bkreportform", "���ڽ��ױ���ģ��", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/reportform/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "http://172.16.36.69:8081/slogs/vr-bulkstock/reportform/provider/v1.0.0/produce/172.16.54.72,172.16.54.73", "");

        addProductLogURL(3, "system", "ϵͳ����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/system/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bulkstock/system/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "system", "ϵͳ����", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/system/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bulkstock/system/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "base", "������Դ", "rest", "http://172.16.36.69:8081/logs/vr-bulkstock/base/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bulkstock/base/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "base", "������Դ", "provider", "http://172.16.36.69:8081/logs/vr-bulkstock/base/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bulkstock/base/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "contract", "��ͬ����", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/contract/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/contract/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "contract", "��ͬ����", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/contract/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/contract/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "finance", "�������", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/finance/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/finance/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "finance", "�������", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/finance/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/finance/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "goods", "��Ʒ����", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/goods/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/goods/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "goods", "��Ʒ����", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/goods/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/goods/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "purchase", "�ɹ�����", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/purchase/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/purchase/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "purchase", "�ɹ�����", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/purchase/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/purchase/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "sale", "���۹���", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/sale/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/sale/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "sale", "���۹���", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/sale/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/sale/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "supplier", "��Ӧ�̹���", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/supplier/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/supplier/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "supplier", "��Ӧ�̹���", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/supplier/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/supplier/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "storage", "�ִ�����", "rest", "http://172.16.36.69:8081/logs/vr-bk-common/storage/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/storage/rest/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        addProductLogURL(3, "storage", "�ִ�����", "provider", "http://172.16.36.69:8081/logs/vr-bk-common/storage/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "http://172.16.36.69:8081/slogs/vr-bk-common/storage/provider/v1.0.0/produce/172.16.53.9,172.16.53.10", "");
        /**********����������������־URL (end) **************************************************/

        /**********����Ԥ������������־URL (begin) **************************************************/
        //1:����
        addPreLogURL(1, "system", "ϵͳ����", "rest", "http://172.16.36.69:8081/logs/common/system/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/common/system/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "system", "ϵͳ����", "provider", "http://172.16.36.69:8081/logs/common/system/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/common/system/provider/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "base", "������Դ", "rest", "http://172.16.36.69:8081/logs/common/base/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/common/base/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "base", "������Դ", "provider", "http://172.16.36.69:8081/logs/common/base/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/common/base/provider/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "contract", "��ͬ����", "rest", "http://172.16.36.69:8081/logs/finance/contract/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/finance/contract/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "contract", "��ͬ����", "provider", "http://172.16.36.69:8081/logs/finance/contract/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/finance/contract/provider/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "finance", "�������", "rest", "http://172.16.36.69:8081/logs/finance/finance/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/finance/finance/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "finance", "�������", "provider", "http://172.16.36.69:8081/logs/finance/finance/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/finance/finance/provider/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "reportform", "�������", "rest", "http://172.16.36.69:8081/logs/finance/reportform/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/finance/reportform/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "reportform", "�������", "provider", "http://172.16.36.69:8081/logs/finance/reportform/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/finance/reportform/provider/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "goods", "��Ʒ����", "rest", "http://172.16.36.69:8081/logs/market/goods/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/market/goods/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "goods", "��Ʒ����", "provider", "http://172.16.36.69:8081/logs/market/goods/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/market/goods/provider/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "purchase", "�ɹ�����", "rest", "http://172.16.36.69:8081/logs/market/purchase/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/market/purchase/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "purchase", "�ɹ�����", "provider", "http://172.16.36.69:8081/logs/market/purchase/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/market/purchase/provider/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "sale", "���۹���", "rest", "http://172.16.36.69:8081/logs/market/sale/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/market/sale/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "sale", "���۹���", "provider", "http://172.16.36.69:8081/logs/market/sale/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/market/sale/provider/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "supplier", "��Ӧ�̹���", "rest", "http://172.16.36.69:8081/logs/market/supplier/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/market/supplier/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "supplier", "��Ӧ�̹���", "provider", "http://172.16.36.69:8081/logs/market/supplier/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/market/supplier/provider/v1.0.0/pre/172.16.41.27", "");
//        addPreLogURL(1, "wholesale", "��������", "rest", "http://172.16.36.69:8080/tlogs/wholesale/rest", "http://172.16.36.69:8080/tslogs/wholesale/rest", "http://172.16.36.69:8080/tstatus/wholesale/rest");
//        addPreLogURL(1, "wholesale", "��������", "provider", "http://172.16.36.69:8080/tlogs/wholesale/provider", "http://172.16.36.69:8080/tslogs/wholesale/provider", "http://172.16.36.69:8080/tstatus/wholesale/provider");
        addPreLogURL(1, "storage", "�ִ�����", "rest", "http://172.16.36.69:8081/logs/scm/storage/rest/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/scm/storage/rest/v1.0.0/pre/172.16.41.27", "");
        addPreLogURL(1, "storage", "�ִ�����", "provider", "http://172.16.36.69:8081/logs/scm/storage/provider/v1.0.0/pre/172.16.41.27", "http://172.16.36.69:8081/slogs/scm/storage/provider/v1.0.0/pre/172.16.41.27", "");
        /**********����Ԥ������������־URL ( end ) **************************************************/

        /**********���÷����Ľ�� (begin) **************************************************/
        addOutcomeConfig("startup", true, "web ģ��������");
        addOutcomeConfig("other", false, "�����ڷ������ģ��");
        addOutcomeConfig("mvn package_failure", false, "�����������");
        addOutcomeConfig("is error", false, "δ֪����");
        addOutcomeConfig("rest started success", true, "rest������������");
        addOutcomeConfig("docker image", false, "�������̲��ȶ���������������һ��");
        addOutcomeConfig("provider started success", true, "provider������������");
        addOutcomeConfig("ERROR does not started", false, "����ʧ����");
        addOutcomeConfig("the python server has error", false, "����ʧ���ˡ������ķ�������������⡣");
        /**********���÷����Ľ�� (end) **************************************************/

        /*********************��ӿͻ���ip��ַ�Ͷ�Ӧ�Ŀ�����Ա��ӳ���ϵ (begin) **********************/
        addIpAndCrewNameMapping("172.19.14.144", "���ͽ�", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.173", "�����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.134", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.157", "��ˬ", RoleEnum.DEVELOPER);
//        addIpAndCrewNameMapping("172.19.14.159", "������", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.190", "������", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.169", "������", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.172", "������", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.143", "��", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.160", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.214", "�κ�", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.154", "��Т��", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.177", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.198", "��־ΰ", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.236", "��î", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.162", "������", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.178", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.218", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.131", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.174", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.181", "��ΰ", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.247", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.212", "����", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.164", "Ԫ��", RoleEnum.DEVELOPER);
        addIpAndCrewNameMapping("172.19.14.213", "����", RoleEnum.DEVELOPER);

        addIpAndCrewNameMapping("172.19.14.211", "��־��", RoleEnum.TESTER);
        addIpAndCrewNameMapping("172.19.14.151", "��ΰ", RoleEnum.TESTER);
        addIpAndCrewNameMapping("172.19.14.239", "������", RoleEnum.TESTER);
        addIpAndCrewNameMapping("172.19.14.180", "����", RoleEnum.TESTER);
        addIpAndCrewNameMapping("172.19.14.182", "������", RoleEnum.TESTER);
        addIpAndCrewNameMapping("172.19.14.170", "��ޱޱ", RoleEnum.TESTER);
        addIpAndCrewNameMapping("172.19.14.226", "���", RoleEnum.TESTER);
        /*********************��ӿͻ���ip��ַ�Ͷ�Ӧ�Ŀ�����Ա��ӳ���ϵ ( end ) **********************/

        /**********����������ʴ˷�������ϵͳ��ip��ַ (begin) **************************************************/
        //����Ȩ������(Ӧ�õķ��������ݿ�ű��ķ���)
        addAllowedIpConfig("172.19.14.144", Config.USE_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.173", Config.USE_DEPLOY_URL);     //�����
        addAllowedIpConfig("172.19.14.134", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.157", Config.USE_DEPLOY_URL);     //��ˬ
        addAllowedIpConfig("172.19.14.190", Config.USE_DEPLOY_URL);     //������
        addAllowedIpConfig("172.19.14.169", Config.USE_DEPLOY_URL);     //������
        addAllowedIpConfig("172.19.14.172", Config.USE_DEPLOY_URL);     //������
        addAllowedIpConfig("172.19.14.143", Config.USE_DEPLOY_URL);     //��
        addAllowedIpConfig("172.19.14.160", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.214", Config.USE_DEPLOY_URL);     //�κ�
        addAllowedIpConfig("172.19.14.154", Config.USE_DEPLOY_URL);     //��Т��
        addAllowedIpConfig("172.19.14.177", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.198", Config.USE_DEPLOY_URL);     //��־ΰ
        addAllowedIpConfig("172.19.14.236", Config.USE_DEPLOY_URL);     //��î
        addAllowedIpConfig("172.19.14.162", Config.USE_DEPLOY_URL);     //������
        addAllowedIpConfig("172.19.14.178", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.218", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.131", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.174", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.181", Config.USE_DEPLOY_URL);     //��ΰ
        addAllowedIpConfig("172.19.14.247", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.212", Config.USE_DEPLOY_URL);     //����
        addAllowedIpConfig("172.19.14.164", Config.USE_DEPLOY_URL);     //Ԫ��
        addAllowedIpConfig("172.19.14.213", Config.USE_DEPLOY_URL);     //����

        //��ʾ����·����Ȩ������
        addAllowedIpConfig("172.19.14.144", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.173", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.134", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.157", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.190", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.169", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.172", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.143", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.160", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.214", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.154", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.177", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.198", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.236", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.162", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.178", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.218", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.131", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.174", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.212", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.164", Config.VIEW_DEPLOY_URL);
        addAllowedIpConfig("172.19.14.213", Config.VIEW_DEPLOY_URL);

        //��ʶ�Ѿ�������������Ȩ������
        addAllowedIpConfig("172.19.14.144", Config.MARK_PRODUCT_DEPLOY);
        addAllowedIpConfig("172.19.14.236", Config.MARK_PRODUCT_DEPLOY);    //��î

        //�������������Ȩ������
        addAllowedIpConfig("172.19.14.144", Config.LOCK_DEPLOY_REQUEST);
        addAllowedIpConfig("172.19.14.236", Config.LOCK_DEPLOY_REQUEST);    //��î

        //�������ݿ�ű���Ԥ����������Ȩ������
        addAllowedIpConfig("172.19.14.144", Config.DEPLOY_DBSCRIPT);
        addAllowedIpConfig("172.19.14.236", Config.DEPLOY_DBSCRIPT);        //��î

        //�޸��Ƿ������ʱ�����ű���Ȩ������
        addAllowedIpConfig("172.19.14.144", Config.CHANGE_CAN_EXEC_DBSCRIPT);
        addAllowedIpConfig("172.19.14.236", Config.CHANGE_CAN_EXEC_DBSCRIPT);        //��î
        /**********����������ʴ˷�������ϵͳ��ip��ַ (end) **************************************************/
    }
}