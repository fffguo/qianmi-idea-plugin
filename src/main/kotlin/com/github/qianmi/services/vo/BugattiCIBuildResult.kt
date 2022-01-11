package com.github.qianmi.services.vo

class BugattiCIBuildResult {

    lateinit var changes: String
    var dependency: Boolean = false
    var id: Int = 0

    //jenkins项目名
    lateinit var jenkins: String

    //发起人
    lateinit var jobNo: String

    //是否合并master
    var mergeMaster: Boolean = false

    //bugatti项目名
    lateinit var project: String

    //bugatti项目code
    var projectId: Int = 0

    //队列ID
    var queueId: Int = 0

    //状态 queue run success fail
    lateinit var state: String

    //步骤 build
    lateinit var step: String

    //版本号
    lateinit var version: String

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