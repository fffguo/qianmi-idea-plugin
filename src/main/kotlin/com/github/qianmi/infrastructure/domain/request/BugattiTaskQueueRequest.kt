package com.github.qianmi.infrastructure.domain.request

class BugattiTaskQueueRequest(
    /**
     * 节点ID
     */
    var hostId: String,
    /**
     * 环境ID
     */
    var envId: String,
    /**
     * 项目ID
     */
    var projectId: String,
    /**
     * 版本ID
     */
    var versionId: String,
    /**
     * 模板ID
     */
    var templateId: String,
) {

}