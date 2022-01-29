package com.github.qianmi.infrastructure.domain.vo

/**
 * webSocket 返回值
 */
class BugattiWebSocketMsgResult {
    //环境ID
    lateinit var envId: String

    //环境名称
    lateinit var envName: String

    //节点id
    var hostId: String? = null

    //项目ID
    lateinit var projectId: String

    //项目名称
    lateinit var projectName: String

    //任务ID
    lateinit var taskId: String

    //任务名称：安装应用
    lateinit var taskName: String

    //命令
    var command: Command? = null

    class Command {
        //命令名称：创建springboot环境
        lateinit var sls: String

        //机器信息：lin-19-33-12.localdomain
        lateinit var machine: String
    }

}