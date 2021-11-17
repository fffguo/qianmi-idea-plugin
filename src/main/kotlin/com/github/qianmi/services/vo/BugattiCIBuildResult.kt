package com.github.qianmi.services.vo

class BugattiCIBuildResult(
    var changes: String = "",
    var dependency: Boolean = false,
    var id: Int = 0,
    //jenkins项目名
    var jenkins: String = "",
    //发起人
    var jobNo: String = "",
    //是否合并master
    var mergeMaster: Boolean = false,
    //bugatti项目名
    var project: String = "",
    //bugatti项目code
    var projectId: Int = 0,
    //队列ID
    var queueId: Int = 0,
    //状态 queue run success fail
    var state: String = "queue",
    //步骤 build
    var step: String = "",
    //版本号
    var version: String = "",
) {

    fun running(): Boolean {
        return queue == state || run == state
    }

    fun isSuccess(): Boolean {
        return success == state
    }

    fun isFail(): Boolean {
        return fail == state
    }

    companion object {
        private const val success = "success"
        private const val fail = "fail"
        private const val queue = "queue"
        private const val run = "run"
    }

}