package com.github.qianmi.services.vo

class BugattiProjectBranchResult(
    var developersCanMerge: Boolean,
    var developersCanPush: Boolean,
    var merged: Boolean,
    /**
     * 分支名称
     */
    var name: String,
    var protected: Boolean,
) {


}