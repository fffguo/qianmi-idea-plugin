package com.github.qianmi.action.`package`

import com.alibaba.fastjson.JSONObject
import com.github.qianmi.action.BugattiAction
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.ui.PackagePage
import com.github.qianmi.util.BugattiHttpUtil
import com.github.qianmi.util.NotifyUtil
import com.intellij.openapi.project.Project
import java.util.*

/**
 * 打包回调
 */
class PackageNotify(project: Project, buildType: PackagePage.BuildType, version: String, branchName: String) {


    init {

        Timer().schedule(object : TimerTask() {

            override fun run() {
                val myProject = AllProject.currentProject(project)
                val ciBuildResult =
                    when (buildType) {
                        PackagePage.BuildType.SNAPSHOT -> {
                            BugattiHttpUtil.ciBuildResult(myProject)
                        }
                        PackagePage.BuildType.BETA, PackagePage.BuildType.RELEASE -> {
                            BugattiHttpUtil.ciReleaseResult(myProject, version, branchName)
                        }
                    }

                println(JSONObject.toJSONString(ciBuildResult))
                if (ciBuildResult.running()) {
                    return
                }
                try {
                    val bugattiAction = BugattiAction()
                    bugattiAction.templatePresentation.text = "Go Bugatti Look Look"

                    var message = "构建成功啦，当前版本号：" + ciBuildResult.version
                    if (ciBuildResult.isFail()) {
                        message = "构建失败了，出了点小问题，快去瞅瞅啥情况吧~"
                    }
                    //idea 通知
                    NotifyUtil.notifyInfoWithAction(project, message, bugattiAction)
                } finally {
                    //取消定时任务
                    this.cancel()
                }
            }

        }, Date(), 5000L)
    }
}