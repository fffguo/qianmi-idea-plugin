package com.github.qianmi.domain.enums

import com.intellij.util.containers.stream

/**
 * bugatti项目枚举
 */
enum class BugattiProjectEnum(val projectName: String, val bugattiProjectCode: String) {
    NONE("NONE", ""),
    PLUG_DEMO("plugin-demo", "1403"),
    PURCHASE_WX_BFF("purchase-wx-bff", "1403"),
    D2P_ADMIN_BFF("d2p-admin-bff", "598"),
    B2B_PURCHASE_MICRO_SERVICE("b2b-purchase-micro-service", "1497"),
    WECHAT_GATEWAY("wechat-gateway", "1483"),
    ACCT_CASHIER_NEW("acctcashier_new", "247"),
    ACCT_MICRO_GATEWAY("acct-micro-gateway", "1091"),
    ACCT_MICRO_PAYMENT("acct-micro-payment", "963"),
    ACCT_PAY_NEW("acct-pay-new", "360"),
    ADMIN_API("admin-api", "524"),
    ADMIN_CRM_API("admin-crm-api", "691"),
    ADMIN_D_2_C_API("admin-d2c-api", "704"),
    ADMIN_MICRO_NOTICE("admin-micro-notice", "1088"),
    BI_ADMIN_BFF("bi-admin-bff", "1118"),
    CLOUD_SHOP_PLATFORM("cloudshop-platform", "400"),
    D2P_MONGO_RIVER_MYSQL("d2p_mongo_river_mysql", "1124"),
    D2P_CART("d2p-cart", "658"),
    D2P_MARKETING_CENTER("d2p-marketing-center", "1388"),
    D2P_MC_CONSOLE("d2p-mc-console", "1393"),
    D2P_MC_MYSQL("d2p-mc-mysql", "1386"),
    D2P_MC_TASK("d2p-mc-task", "1390"),
    D2P_MC_WEB_API("d2p-mc-web-api", "1389"),
    D2P_MONGO_RIVER("d2p-mongo-river", "1082"),
    D2P_ORDER("d2p-order", "800"),
    D2P_ORDER_ROUTER("d2p-order-router", "842"),
    D2P_ORDER_TASK("d2p-order-task", "802"),
    D2P_PURCHASE_CART("d2p-purchase-cart", "1084"),
    D2P_RETURN_ORDER("d2p-return-order", "801"),
    GAVIN("gavin", "84"),
    MARKETING_CENTER("marketing-center", "606"),
    MC_FACADE("mc-facade", "1191"),
    MC_PLUGIN("mc-plugin", "1422"),
    OMS("oms", "646"),
    OMS_COMMON_UTILS("oms-common-utils", "1114"),
    PC_ADMIN_BFF("pc-admin-bff", "813"),
    PC_ADMIN_TASK("pc-admin-task", "841"),
    PC_DATA_MANAGEMENT("pc-data-management", "611"),
    PC_ES_CENTER("pc-es-center", "580"),
    PC_EVENT_LOG("pc-event-log", "1424"),
    PC_MESSAGE("pc-message", "706"),
    PC_MICRO_CATS("pc-micro-cats", "766"),
    PC_MICRO_ITEM("pc-micro-item", "767"),
    PC_MICRO_PRICE("pc-micro-price", "561"),
    PC_MICRO_STOCK("pc-micro-stock", "551"),
    PRODUCT_CENTER_NEW("product-center-new", "10"),
    PURCHASE_VIEW("purchase-view", "1262"),
    QM_ASSISTANT("qm-assistant", "1101"),
    QM_BSC("qmbsc", "286"),
    QM_COURIER("qm-courier", "1252"),
    QM_CSP("qm-csp", "990"),
    QM_MC_TASK("qmmc-task", "106"),
    QM_OMS_API("qm-oms-api", "663"),
    QM_UC("qm-qmuc", "11"),
    QM_SELF_ORDER("qm-self-order", "954"),
    QM_UC_MESSAGE("qmuc-message", "1186"),
    QM_UC_MICRO_QSTORE("qmuc-micro-qstore", "845"),
    QM_UC_MICRO_QUERY("qmuc-micro-query", "923"),
    QM_USER_CENTER("qm-user-center", "11"),
    SCM_BI_MYSQL("scm_bi_mysql", "1453"),
    TMS("tms", "795"),
    UC_CONN_RETAIL("uc-conn-retail", "1194"),
    WMS("wms", "1421"),
    YUN_XIAO_TASK("yunxiao-task", "198"),
    YX_TC_TASK("yxtc-task", "460"),
    YX_TRADE_CENTER("yx-trade-center", "228"),

    //数谋项目
    SM_SQL("sm_sql", "1770"),
    SM_DW("sm-dw", "1766"),
    SM_EMPOWER("sm-empower", "1781"),
    SM_VAS("sm-vas", "1782"),
    SM_SETTING("sm-setting", "1780"),
    SM_MESSAGE("sm-message", "1783"),
    SM_BFF("sm-bff", "1779"),
    SM_BFF_BOSS("sm-bff-boss", "1779"),
    SM_BFF_GUIDE("sm-bff-guide", "1778"),
    SM_CRM("sm-crm", "1769"),
    SM_GATEWAY("sm-gateway", "1768"),
    ;


    companion object {
        fun instanceOf(projectName: String): BugattiProjectEnum {
            return values()
                .stream()
                .filter { project -> project.projectName == projectName }
                .findFirst().orElse(NONE)
        }
    }
}

