package com.github.qianmi.services.vo

data class BugattiResult(var success: Boolean, var errMsg: String) {

    companion object {
        const val SUCCESS = "SUCCESS"

        fun success(): BugattiResult {
            return BugattiResult(true, "请求成功！")
        }

        fun fail(errMsg: String): BugattiResult {
            return BugattiResult(false, errMsg)
        }
    }

}