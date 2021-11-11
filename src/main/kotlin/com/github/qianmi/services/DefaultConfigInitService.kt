package com.github.qianmi.services

import cn.hutool.http.ContentType
import cn.hutool.http.HttpUtil
import com.alibaba.fastjson.JSONObject
import com.github.qianmi.config.BugattiConfig
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.services.vo.BugattiProjectInfoResult
import com.github.qianmi.util.StringUtil
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.NotNull


class DefaultConfigInitService(project: Project) {
    init {
        try {
            val myProject = AllProject.currentProject(project)
            //登录
            loginBugatti()
            //获取项目信息
            val bugattiProjectResult = getProjectInfo(myProject)

            //更新gitlab属性
            updateAttrWithGitLab(myProject, bugattiProjectResult)

            //更新bugatti属性
            updateAttrWithBugatti(myProject, bugattiProjectResult)
        } catch (e: Exception) {

        }
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
    private fun getProjectInfo(myProject: AllProject.MyProject): BugattiProjectInfoResult {
        val result: String = HttpUtil.get(BugattiConfig.domain +
                String.format("/project/%s/%s",
                    myProject.bugatti.projectCode,
                    myProject.bugatti.envCode))

        return JSONObject.parseObject(result, BugattiProjectInfoResult::class.java)
    }

    private fun updateAttrWithGitLab(myProject: AllProject.MyProject, result: BugattiProjectInfoResult) {
        if (StringUtil.isNotBlank(result.git)) {
            myProject.gitlab.isSupport = true
            myProject.gitlab.url = result.git
        } else {
            myProject.gitlab.isSupport = false
        }
    }

    private fun updateAttrWithBugatti(myProject: AllProject.MyProject, result: BugattiProjectInfoResult) {
        myProject.bugatti.desc = result.description
        myProject.bugatti.projectName = result.projectName
        myProject.bugatti.isSupport = true
    }

}
