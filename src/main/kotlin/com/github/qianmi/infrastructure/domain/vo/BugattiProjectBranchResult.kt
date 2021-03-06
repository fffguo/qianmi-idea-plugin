package com.github.qianmi.infrastructure.domain.vo

class BugattiProjectBranchResult {

    var developersCanMerge: Boolean = false
    var developersCanPush: Boolean = false
    var merged: Boolean = false

    /**
     * 分支名称
     */
    lateinit var name: String
    var protected: Boolean = false
}