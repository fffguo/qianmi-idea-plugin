package com.github.qianmi.services

import cn.hutool.core.collection.CollectionUtil
import cn.hutool.http.HttpUtil
import com.alibaba.fastjson.JSONObject
import com.github.qianmi.config.BugattiConfig
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.domain.project.tools.Shell
import com.github.qianmi.services.vo.BugattiShellInfoResult
import com.intellij.openapi.project.Project


class ShellInitService(project: Project) {
    init {
        try {
            val myProject = AllProject.currentProject(project)
            syncShellElement(myProject)
        } catch (e: Exception) {

        }
    }

    companion object {
        fun syncShellElement(myProject: AllProject.MyProject) {
            val result: String = HttpUtil.get(BugattiConfig.domain +
                    String.format("/task/clusters?envId=%s&projectId=%s",
                        myProject.bugatti.envCode,
                        myProject.bugatti.projectCode))

            val shellEleList = ArrayList<Shell.Element>()

            for (ele in JSONObject.parseObject(result).getJSONArray("host").toList()) {
                val eleObj = ele as JSONObject
                val show = eleObj.getString("show")

                if (show == null) {
                    shellEleList.add(
                        Shell.Element.instanceOf(BugattiShellInfoResult(
                            eleObj.getString("group"),
                            eleObj.getString("ip"),
                            eleObj.getString("version"))))
                }
            }
            if (CollectionUtil.isNotEmpty(shellEleList)) {
                myProject.shell.isSupportShell = true
                myProject.shell.eleList = shellEleList
                myProject.shell.isNeedSyncEle = false
            }
        }
    }
}