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
    val qianmiAdmin: QianmiAdminEnum,
    val consoleOfPc: ConsoleOfPcEnum,
    val consoleOfD2pMc: ConsoleOfD2pMcEnum,
    val consoleOfOms: ConsoleOfOmsEnum,
) {
    //  @formatter:off
    LAKALA("lakala", BugattiEnvEnum.LAKALA, DubboAdminEnum.LAKALA, RocketMqEnum.LAKALA, ActiveMqEnum.LAKALA, GavinEnum.LAKALA, TraceEnum.LAKALA, QianmiAdminEnum.LAKALA, ConsoleOfPcEnum.LAKALA,ConsoleOfD2pMcEnum.LAKALA, ConsoleOfOmsEnum.LAKALA),
    TEST0("test0", BugattiEnvEnum.TEST0, DubboAdminEnum.TEST0, RocketMqEnum.TEST0, ActiveMqEnum.TEST0, GavinEnum.TEST0, TraceEnum.TEST0, QianmiAdminEnum.TEST0, ConsoleOfPcEnum.TEST0,ConsoleOfD2pMcEnum.TEST0, ConsoleOfOmsEnum.TEST0),
    TEST1("test1", BugattiEnvEnum.TEST1, DubboAdminEnum.TEST1, RocketMqEnum.TEST1, ActiveMqEnum.TEST1, GavinEnum.TEST1, TraceEnum.TEST1, QianmiAdminEnum.TEST1, ConsoleOfPcEnum.TEST1,ConsoleOfD2pMcEnum.TEST1, ConsoleOfOmsEnum.TEST1),
    TEST2("test2", BugattiEnvEnum.TEST2, DubboAdminEnum.TEST2, RocketMqEnum.TEST2, ActiveMqEnum.TEST2, GavinEnum.TEST2, TraceEnum.TEST2, QianmiAdminEnum.TEST2, ConsoleOfPcEnum.TEST2,ConsoleOfD2pMcEnum.TEST2, ConsoleOfOmsEnum.TEST2),
    TEST4("test4", BugattiEnvEnum.TEST4, DubboAdminEnum.TEST4, RocketMqEnum.TEST4, ActiveMqEnum.TEST4, GavinEnum.TEST4, TraceEnum.TEST4, QianmiAdminEnum.TEST4, ConsoleOfPcEnum.TEST4,ConsoleOfD2pMcEnum.TEST4, ConsoleOfOmsEnum.TEST4),
    TEST5("test5", BugattiEnvEnum.TEST5, DubboAdminEnum.TEST5, RocketMqEnum.TEST5, ActiveMqEnum.TEST5, GavinEnum.TEST5, TraceEnum.TEST5, QianmiAdminEnum.TEST5, ConsoleOfPcEnum.TEST5,ConsoleOfD2pMcEnum.TEST5, ConsoleOfOmsEnum.TEST5),
    DEV("dev", BugattiEnvEnum.DEV, DubboAdminEnum.DEV, RocketMqEnum.DEV, ActiveMqEnum.DEV, GavinEnum.DEV,TraceEnum.DEV,QianmiAdminEnum.DEV,ConsoleOfPcEnum.DEV,ConsoleOfD2pMcEnum.DEV,ConsoleOfOmsEnum.DEV),
    PROD("prod", BugattiEnvEnum.PROD, DubboAdminEnum.PROD, RocketMqEnum.PROD, ActiveMqEnum.PROD, GavinEnum.PROD,TraceEnum.PROD,QianmiAdminEnum.PROD,ConsoleOfPcEnum.PROD,ConsoleOfD2pMcEnum.PROD,ConsoleOfOmsEnum.PROD);
    //  @formatter:on


    enum class BugattiEnvEnum(val envCode: String) {
        LAKALA("68"),
        TEST0("17"),
        TEST1("14"),
        TEST2("18"),
        TEST4("24"),
        TEST5("37"),
        DEV("12"),
        PROD("11");
    }

    enum class DubboAdminEnum(val url: String) {
        LAKALA("http://10.7.64.121:8080"),
        TEST0("http://172.19.67.104:8080"),
        TEST1("http://172.19.66.25:8080"),
        TEST2("http://172.19.67.127:8080"),
        TEST4("http://172.19.68.8:8080"),
        TEST5("http://172.21.33.162:8080"),
        DEV("http://172.19.65.13:8080"),
        PROD("http://dubbomanager.dev.qianmi.com")
    }

    enum class RocketMqEnum(val url: String) {
        LAKALA("http://10.7.65.127:8080/#/topic"),
        TEST0("http://172.19.67.236:8080/#/topic"),
        TEST1("http://172.21.34.235:8080/#/topic"),
        TEST2("http://172.21.4.241:8080/#/topic"),
        TEST4("http://172.21.36.197:8080/#/topic"),
        TEST5("http://172.21.33.247:8080/#/topic"),
        DEV("http://172.19.72.105:8080/#/topic"),
        PROD("")
    }

    enum class ActiveMqEnum(val url: String) {
        LAKALA("http://10.7.65.210:8161/admin/topics.jsp"),
        TEST0("http://172.19.67.50:8161/admin/topics.jsp"),
        TEST1("http://172.21.4.29:8161/admin/topics.jsp"),
        TEST2("http://172.21.3.144:8161/admin/topics.jsp"),
        TEST4("http://172.19.68.15:8161/admin/topics.jsp"),
        TEST5("http://172.21.33.195:8161/admin/topics.jsp"),
        DEV("http://172.19.65.38:8161/admin/topics.jsp"),
        PROD("http://mq.dev.qianmi.com/admin/topics.jsp")
    }

    enum class GavinEnum(val url: String) {
        LAKALA("http://10.7.65.139:8080/module/home"),
        TEST0("http://172.21.36.4:8080/module/home"),
        TEST1("http://172.19.66.177:8080/module/home"),
        TEST2("http://172.21.3.3:8080/module/home"),
        TEST4("http://172.19.68.118:8080/module/home"),
        TEST5("http://172.21.33.154:8080/module/home"),
        DEV("http://172.19.77.74:8080/module/home"),
        PROD("")
    }

    enum class TraceEnum(val url: String) {
        LAKALA(""),
        TEST0(""),
        TEST1("http://tracet1.dev.qianmi.com/search"),
        TEST2(""),
        TEST4(""),
        TEST5(""),
        DEV(""),
        PROD("http://trace.dev.qianmi.com/search")
    }

    enum class QianmiAdminEnum(val url: String) {
        LAKALA("http://www.1000.com.lakala.ck/#/main/"),
        TEST0("http://web.1000.com.test0.ck/#/main/"),
        TEST1("http://web.1000.com.test1.ck/#/main/"),
        TEST2("http://web.1000.com.test2.ck/#/main/"),
        TEST4("http://web.1000.com.test4.ck/#/main/"),
        TEST5("http://web.1000.com.test5.ck/#/main/"),
        DEV(""),
        PROD("https://www.1000.com/")
    }

    enum class ConsoleOfPcEnum(val url: String) {
        LAKALA(""),
        TEST0("http://172.21.36.47:8080/module/home"),
        TEST1("http://172.19.66.197:8080/module/home"),
        TEST2("http://172.21.3.34:8080/module/home"),
        TEST4("http://172.19.68.29:8080/module/home"),
        TEST5("http://172.21.33.153:8080/module/home"),
        DEV("http://172.19.65.203:8080/module/home"),
        PROD("")
    }

    enum class ConsoleOfOmsEnum(val url: String) {
        LAKALA(""),
        TEST0("http://172.21.36.109:8080/#/"),
        TEST1("http://172.21.34.139:8080/#/"),
        TEST2("http://172.21.34.17:8080/#/"),
        TEST4("http://172.19.68.123:8080/#/"),
        TEST5("http://172.21.33.20:8080/#/"),
        DEV("http://172.19.65.114:8080/#/"),
        PROD("http://oms-console.crm.qianmi.com/")
    }

    enum class ConsoleOfD2pMcEnum(val url: String) {
        LAKALA(""),
        TEST0("http://172.19.67.123:8080/#/"),
        TEST1("http://172.21.3.4:8080/#/"),
        TEST2("http://172.21.34.104:8080/#/"),
        TEST4("http://172.19.68.165:8080/#/"),
        TEST5("http://172.19.69.87:8080/#/"),
        DEV("http://172.19.72.47:8080/#/"),
        PROD("http://172.17.18.248:8080/#/")
    }
}