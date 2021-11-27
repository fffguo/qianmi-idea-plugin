package com.github.qianmi.action.packaging

import com.github.qianmi.action.BugattiAction
import com.github.qianmi.action.CopyAction
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

                if (ciBuildResult.running()) {
                    return
                }
                try {
                    //idea 通知
                    NotifyUtil.notifyInfoWithAction(project,
                        "构建成功啦，当前版本号：${ciBuildResult.version}",
                        listOf(
                            BugattiAction.instanceOf("Go Bugatti"),
                            CopyAction.instanceOf("复制版本信息",
                                "以下包需帮忙升下级：" +
                                        "\n----------------------------------------" +
                                        "\n\t项目名：${myProject.bugatti.projectName}" +
                                        "\n\t版本号：${ciBuildResult.version}" +
                                        "\n----------------------------------------")
                        ))
                } finally {
                    //取消定时任务
                    this.cancel()
                }
            }

        }, Date(), 5000L)
    }
}