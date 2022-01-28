package com.github.qianmi.infrastructure.domain.request

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.extend.JsonExtend.toJsonString
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
        fun instanceOf(
            myProject: IdeaProject.MyProject,
            branchName: String,
        ): CiBuildRequest {
            val param = mapOf("branch" to branchName)
            return CiBuildRequest(
                myProject.projectInfo.jenkins,
                param.toJsonString(),
                myProject.bugattiLink.name,
                myProject.bugattiLink.code,
                "build"
            )
        }
    }
}