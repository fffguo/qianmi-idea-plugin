package com.github.qianmi.infrastructure.domain.request

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.extend.JsonExtend.toJsonString
import org.jetbrains.annotations.NotNull

class CiBuildReleaseRequest(
    var artifactId: String,
    var jenkins: String,
    var param: String,
    var project: String,
    var projectId: String,
    var type: String,
) {

    class Parameter(var name: String, var value: String) {
    }

    companion object {

        @NotNull
        fun instanceOf(
            myProject: IdeaProject.MyProject,
            branchName: String,
            version: String,
            snapshotVersion: String,
        ): CiBuildReleaseRequest {
            val param = mapOf(
                "releaseVersion" to version,
                "developmentVersion" to snapshotVersion,
                "json" to mapOf(
                    "parameter" to listOf(Parameter("branch", branchName))
                ).toJsonString()
            )
            return CiBuildReleaseRequest(
                myProject.projectInfo.jenkins,
                myProject.projectInfo.jenkins,
                param.toJsonString(),
                myProject.bugattiLink.name,
                myProject.bugattiLink.code,
                "release"
            )
        }
    }
}