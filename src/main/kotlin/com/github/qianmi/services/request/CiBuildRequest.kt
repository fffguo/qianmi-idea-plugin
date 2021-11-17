package com.github.qianmi.services.request

import com.alibaba.fastjson.JSONObject
import com.github.qianmi.domain.project.AllProject
import org.jetbrains.annotations.NotNull

class CiBuildRequest(
    var jenkins: String,
    var param: String,
    var project: String,
    var projectId: String,
    var type: String,
) {
    companion object {

        @NotNull
        fun instanceOf(myProject: AllProject.MyProject, branchName: String): CiBuildRequest {
            val param = mapOf("branch" to branchName)
            return CiBuildRequest(
                myProject.jenkins.projectName,
                JSONObject.toJSONString(param),
                myProject.bugatti.projectName,
                myProject.bugatti.projectCode,
                "build"
            )
        }
    }
}