package com.github.qianmi.services.request

import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.util.JsonUtil.toJsonString
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
            myProject: AllProject.MyProject,
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
                myProject.jenkins.projectName,
                myProject.jenkins.projectName,
                param.toJsonString(),
                myProject.bugatti.projectName,
                myProject.bugatti.projectCode,
                "release"
            )
        }
    }
}