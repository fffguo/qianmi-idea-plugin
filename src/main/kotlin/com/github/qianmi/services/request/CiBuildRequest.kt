package com.github.qianmi.services.request

import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.util.JsonUtil.toJsonString
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
                param.toJsonString(),
                myProject.bugatti.projectName,
                myProject.bugatti.projectCode,
                "build"
            )
        }
    }
}