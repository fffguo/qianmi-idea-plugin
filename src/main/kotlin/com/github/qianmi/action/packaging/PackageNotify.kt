package com.github.qianmi.action.packaging

import com.github.qianmi.action.CopyAction
import com.github.qianmi.action.link.BugattiAction
import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.github.qianmi.infrastructure.util.NotifyUtil
import com.github.qianmi.ui.PackagePage
import com.intellij.openapi.project.Project
import java.util.*

/**
 * 打包回调
 */
class PackageNotify(project: Project, buildType: PackagePage.BuildType, version: String, branchName: String) {

    init {
        Timer().schedule(object : TimerTask() {

            override fun run() {
                val myProject = IdeaProject.getInstance(project)
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
                    if (ciBuildResult.isSuccess()) {
                        //idea 通知
                        NotifyUtil.notifyInfoWithAction(project,
                            "构建成功啦，当前版本号：${ciBuildResult.version}",
                            listOf(BugattiAction.defaultAction(), CopyAction.instanceOf("复制版本信息",
                                "以下项目有最新版本了：" +
                                        "\n----------------------------------------" +
                                        "\n\t项目名：${myProject.projectInfo.projectName}" +
                                        "\n\t版本号：${ciBuildResult.version}" +
                                        "\n----------------------------------------" +
                                        "\n变更内容：" +
                                        "\n\t1. " +
                                        "\n\t2. " +
                                        "\n\t3. ")))
                    } else {
                        //idea 通知
                        NotifyUtil.notifyInfoWithAction(project, "构建失败，出了点问题~", BugattiAction.defaultAction())
                    }
                } finally {
                    //取消定时任务
                    this.cancel()
                }
            }

        }, Date(), 5000L)
    }
}