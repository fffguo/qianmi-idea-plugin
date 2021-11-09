package com.github.qianmi.services

import cn.hutool.http.ContentType
import cn.hutool.http.HttpUtil
import com.alibaba.fastjson.JSONObject
import com.github.qianmi.config.BugattiConfig
import com.github.qianmi.domain.project.MyProject
import com.github.qianmi.services.vo.BugattiProjectInfoResult
import com.github.qianmi.util.StringUtil
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.NotNull


class DefaultConfigInitService(project: Project) {
    init {
        //登录
        loginBugatti()
        //获取项目信息
        val bugattiProjectResult = getProjectInfo()

        //更新gitlab属性
        updateAttrWithGitLab(bugattiProjectResult)

        //更新bugatti属性
        updateAttrWithBugatti(bugattiProjectResult)
    }

    private fun loginBugatti() {
        val body: MutableMap<String, Any> = HashMap(8)
        body["userName"] = BugattiConfig.userName
        body["password"] = BugattiConfig.password
        HttpUtil.createPost(BugattiConfig.domain + "/login")
            .body(JSONObject.toJSONString(body), ContentType.JSON.value)
            .execute()
    }

    @NotNull
    private fun getProjectInfo(): BugattiProjectInfoResult {
        val result: String = HttpUtil.get(BugattiConfig.domain +
                String.format("/project/%s/%s",
                    MyProject.bugatti.projectCode,
                    MyProject.bugatti.envCode))

        return JSONObject.parseObject(result, BugattiProjectInfoResult::class.java)
    }

    private fun updateAttrWithGitLab(result: BugattiProjectInfoResult) {
        if (StringUtil.isNotBlank(result.git)) {
            MyProject.gitlab.isSupport = true
            MyProject.gitlab.url = result.git
        } else {
            MyProject.gitlab.isSupport = false
        }
    }

    private fun updateAttrWithBugatti(result: BugattiProjectInfoResult) {
        MyProject.bugatti.desc = result.description
        MyProject.bugatti.projectName = result.projectName
        MyProject.bugatti.isSupport = true
    }

}
