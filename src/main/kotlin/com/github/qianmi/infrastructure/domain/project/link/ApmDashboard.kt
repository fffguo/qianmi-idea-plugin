package com.github.qianmi.infrastructure.domain.project.link

/**
 * apm 仪表盘（全链路大数据统计）
 */
class ApmDashboard(
    /**
     * 是否支持
     */
    var isSupport: Boolean,

    /**
     * 地址
     */
    var url: String,
) {
    companion object {
        fun defaultApmDashboard(): com.github.qianmi.infrastructure.domain.project.link.ApmDashboard {
            return com.github.qianmi.infrastructure.domain.project.link.ApmDashboard(true, "http://apm.dev.qianmi.com/")
        }
    }
}