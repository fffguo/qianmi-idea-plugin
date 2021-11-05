package com.github.qianmi.enums

/**
 * bugatti环境
 */
enum class BugattiEnum(val env: EnvEnum, val envCode: String) {
    TEST0(EnvEnum.TEST0, "17"),
    TEST1(EnvEnum.TEST1, "14"),
    TEST2(EnvEnum.TEST2, "18"),
    TEST4(EnvEnum.TEST4, "24"),
    TEST5(EnvEnum.TEST5, "37"),
    DEV(EnvEnum.DEV, "12"),
    PROD(EnvEnum.PROD, "11");
}

