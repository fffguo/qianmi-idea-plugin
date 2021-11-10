package com.github.qianmi.domain.enums

/**
 * 环境
 */
enum class EnvEnum(
    val envName: String,
    val bugatti: BugattiEnvEnum,
    val dubboAdmin: DubboAdminEnum,
    val rocketMq: RocketMqEnum,
    val activeMq: ActiveMqEnum,
    val gavin: GavinEnum,
    val trace: TraceEnum,
) {
    //  @formatter:off
    TEST0("test0", BugattiEnvEnum.TEST0, DubboAdminEnum.TEST0, RocketMqEnum.TEST0, ActiveMqEnum.TEST0, GavinEnum.TEST0, TraceEnum.TEST0),
    TEST1("test1", BugattiEnvEnum.TEST1, DubboAdminEnum.TEST1, RocketMqEnum.TEST1, ActiveMqEnum.TEST1, GavinEnum.TEST1, TraceEnum.TEST1),
    TEST2("test2", BugattiEnvEnum.TEST2, DubboAdminEnum.TEST2, RocketMqEnum.TEST2, ActiveMqEnum.TEST2, GavinEnum.TEST2, TraceEnum.TEST2),
    TEST4("test4", BugattiEnvEnum.TEST4, DubboAdminEnum.TEST4, RocketMqEnum.TEST4, ActiveMqEnum.TEST4, GavinEnum.TEST4, TraceEnum.TEST4),
    TEST5("test5", BugattiEnvEnum.TEST5, DubboAdminEnum.TEST5, RocketMqEnum.TEST5, ActiveMqEnum.TEST5, GavinEnum.TEST5, TraceEnum.TEST5),
    DEV("dev", BugattiEnvEnum.DEV, DubboAdminEnum.DEV, RocketMqEnum.DEV, ActiveMqEnum.DEV, GavinEnum.DEV,TraceEnum.DEV),
    PROD("prod", BugattiEnvEnum.PROD, DubboAdminEnum.PROD, RocketMqEnum.PROD, ActiveMqEnum.PROD, GavinEnum.PROD,TraceEnum.PROD);
    //  @formatter:on


    /**
     * bugatti 环境配置
     */
    enum class BugattiEnvEnum(val envCode: String) {
        TEST0("17"),
        TEST1("14"),
        TEST2("18"),
        TEST4("24"),
        TEST5("37"),
        DEV("12"),
        PROD("11");
    }

    /**
     * dubbo admin环境
     */
    enum class DubboAdminEnum(val url: String) {
        TEST0("http://172.19.67.104:8080"),
        TEST1("http://172.19.66.25:8080"),
        TEST2("http://172.19.67.127:8080"),
        TEST4("http://172.19.68.8:8080"),
        TEST5("http://172.21.33.162:8080"),
        DEV("http://172.19.65.13:8080"),
        PROD("http://dubbomanager.dev.qianmi.com")
    }

    /**
     * rocketMq环境
     */
    enum class RocketMqEnum(val url: String) {
        TEST0("http://172.19.67.236:8080/#/topic"),
        TEST1("http://172.21.34.235:8080/#/topic"),
        TEST2("http://172.21.4.241:8080/#/topic"),
        TEST4("http://172.21.36.197:8080/#/topic"),
        TEST5("http://172.21.33.247:8080/#/topic"),
        DEV("http://172.19.72.105:8080/#/topic"),
        PROD("")
    }

    /**
     * activeMq
     */
    enum class ActiveMqEnum(val url: String) {
        TEST0("http://172.19.67.50:8161/admin/topics.jsp"),
        TEST1("http://172.21.4.29:8161/admin/topics.jsp"),
        TEST2("http://172.21.3.144:8161/admin/topics.jsp"),
        TEST4("http://172.19.68.15:8161/admin/topics.jsp"),
        TEST5("http://172.21.33.195:8161/admin/topics.jsp"),
        DEV("http://172.19.65.38:8161/admin/topics.jsp"),
        PROD("http://mq.dev.qianmi.com/admin/topics.jsp")
    }

    /**
     * gavin
     */
    enum class GavinEnum(val url: String) {
        TEST0("http://172.21.36.4:8080/module/home"),
        TEST1("http://172.19.66.177:8080/module/home"),
        TEST2("http://172.21.3.3:8080/module/home"),
        TEST4("http://172.19.68.118:8080/module/home"),
        TEST5("http://172.21.33.154:8080/module/home"),
        DEV("http://172.19.77.74:8080/module/home"),
        PROD("")
    }

    /**
     * trace
     */
    enum class TraceEnum(val url: String) {
        TEST0(""),
        TEST1("http://tracet1.dev.qianmi.com/search"),
        TEST2(""),
        TEST4(""),
        TEST5(""),
        DEV(""),
        PROD("http://trace.dev.qianmi.com/search")
    }
}