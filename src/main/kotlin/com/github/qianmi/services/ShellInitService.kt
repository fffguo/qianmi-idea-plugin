package com.github.qianmi.services

import cn.hutool.core.collection.CollectionUtil
import cn.hutool.http.HttpUtil
import com.alibaba.fastjson.JSONObject
import com.github.qianmi.config.BugattiConfig
import com.github.qianmi.domain.project.MyProject
import com.github.qianmi.domain.project.tools.Shell
import com.github.qianmi.services.vo.BugattiShellInfoResult
import com.intellij.openapi.project.Project


class ShellInitService(project: Project) {
    init {
        syncShellElement()
    }

    companion object {
        fun syncShellElement() {
            val result: String = HttpUtil.get(BugattiConfig.domain +
                    String.format("/task/clusters?envId=%s&projectId=%s",
                        MyProject.bugatti.envCode,
                        MyProject.bugatti.projectCode))

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
                MyProject.shell.isSupportShell = true
                MyProject.shell.eleList = shellEleList
                MyProject.shell.isNeedSyncEle = false
            }
        }
    }
}