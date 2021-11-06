package com.github.qianmi.services

import cn.hutool.core.collection.CollectionUtil
import cn.hutool.http.HttpUtil
import com.alibaba.fastjson.JSONObject
import com.github.qianmi.config.BugattiConfig
import com.github.qianmi.project.MyProject
import com.github.qianmi.project.Shell
import com.github.qianmi.services.vo.BugattiShellInfoResult
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.NotNull


class ShellInitService(project: Project) {
    init {
        val eleList = getShellElementList()

        if (CollectionUtil.isNotEmpty(eleList)) {
            MyProject.shell.isSupportShell = true
            MyProject.shell.eleList = eleList
        }
    }

    @NotNull
    private fun getShellElementList(): List<Shell.Element> {
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
        return shellEleList
    }
}