package com.github.qianmi.domain.enums

/**
 * 环境
 */
enum class EnvEnum(val envName: String, val bugatti: BugattiEnvEnum, val dubboAdmin: DubboAdminEnum) {
    TEST0("test0", BugattiEnvEnum.TEST0, DubboAdminEnum.TEST0),
    TEST1("test1", BugattiEnvEnum.TEST1, DubboAdminEnum.TEST1),
    TEST2("test2", BugattiEnvEnum.TEST2, DubboAdminEnum.TEST2),
    TEST4("test4", BugattiEnvEnum.TEST4, DubboAdminEnum.TEST4),
    TEST5("test5", BugattiEnvEnum.TEST5, DubboAdminEnum.TEST5),
    DEV("dev", BugattiEnvEnum.DEV, DubboAdminEnum.DEV),
    PROD("prod", BugattiEnvEnum.PROD, DubboAdminEnum.PROD);


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
     * 账号 root
     * 密码 master123
     */
    enum class DubboAdminEnum(val domain: String) {
        TEST0("http://172.19.67.104:8080"),
        TEST1("http://172.19.66.25:8080"),
        TEST2("http://172.19.67.127:8080"),
        TEST4("http://172.19.68.8:8080"),
        TEST5("http://172.21.33.162:8080"),
        DEV("http://172.19.65.13:8080"),
        PROD("http://dubbomanager.dev.qianmi.com")
    }

}