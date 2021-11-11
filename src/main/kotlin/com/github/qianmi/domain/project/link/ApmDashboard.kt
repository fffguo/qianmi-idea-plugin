package com.github.qianmi.domain.project.link

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
        fun defaultApmDashboard(): ApmDashboard {
            return ApmDashboard(true, "http://apm.dev.qianmi.com/")
        }
    }
}