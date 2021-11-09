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

