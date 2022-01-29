package com.github.qianmi.infrastructure.util

import cn.hutool.json.JSONUtil
import com.github.qianmi.action.SettingAction
import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.domain.project.link.GitlabLink
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement
import com.github.qianmi.infrastructure.domain.request.BugattiTaskQueueRequest
import com.github.qianmi.infrastructure.domain.request.CiBuildReleaseRequest
import com.github.qianmi.infrastructure.domain.request.CiBuildRequest
import com.github.qianmi.infrastructure.domain.vo.*
import com.github.qianmi.infrastructure.extend.CollectionExtend.firstDefault
import com.github.qianmi.infrastructure.extend.HttpExtend.getCookie
import com.github.qianmi.infrastructure.extend.HttpExtend.isOk
import com.github.qianmi.infrastructure.extend.JsonExtend.toBean
import com.github.qianmi.infrastructure.extend.JsonExtend.toJsonString
import com.github.qianmi.infrastructure.extend.JsonExtend.toList
import com.github.qianmi.infrastructure.extend.StringExtend.isNotBlank
import com.github.qianmi.infrastructure.storage.AccountConfig
import com.github.qianmi.infrastructure.storage.BugattiCookie
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.net.http.HttpResponse
import java.util.*

object BugattiHttpUtil {
    const val domain = "bugatti.dev.qianmi.com"
    const val httpDomainUrl = "http://$domain"
    const val wsDomainUrl = "ws://$domain"

    /**
     * @param silent 静默模式
     */
    @JvmStatic
    @Nullable
    fun login(project: Project?, silent: Boolean): HttpResponse<String>? {
        try {
            val httpResponse = login(
                AccountConfig.getInstance().userName,
                AccountConfig.getInstance().passwd
            )
            if (httpResponse.statusCode() == 406) {
                if (!silent) {
                    val settingAction = SettingAction()
                    settingAction.templatePresentation.text = "配置域账号"
                    NotifyUtil.notifyErrorWithAction(project, "登录Bugatti失败！账号或密码错误~", settingAction)
                }

            } else {
                BugattiCookie.getInstance().cookie = "SESSION=${httpResponse.getCookie("SESSION")}"
                if (!silent) {
                    NotifyUtil.notifyInfo(project, "登录成功，已与Bugatti建立连接~")
                }
            }
            return httpResponse
        } catch (_: Exception) {
            if (!silent) {
                NotifyUtil.notifyError(project, "登录Bugatti失败！请检查网络(VPN)是否通畅~")
            }
        }
        return null
    }

    @JvmStatic
    fun isLoginSuccess(httpResponse: HttpResponse<String>?): Boolean {
        return httpResponse != null
                && httpResponse.isOk()
    }

    @JvmStatic
    fun login(userName: String, password: String): HttpResponse<String> {
        val body: MutableMap<String, Any> = HashMap(8)
        body["userName"] = userName
        body["password"] = password
        return HttpUtil.postJson("$httpDomainUrl/login", body.toJsonString())
    }

    @NotNull
    @JvmStatic
    fun getProjectInfo(project: BugattiProjectEnum): BugattiProjectInfoResult {
        val url = "$httpDomainUrl/project/${project.code}/${EnvConfig.getInstance().env.envCode}"
        return HttpUtil.get(url, getCookie()).body().toBean()!!
    }

    @NotNull
    @JvmStatic
    fun getBranchList(project: Project): List<BugattiProjectBranchResult> {
        val url = "$httpDomainUrl/ci/branchs?gitUrl=${GitlabLink.getInstance().getBrowserUrl(project)}"
        return HttpUtil.get(url, getCookie()).body().toList()
    }

    /**
     * Returns 分支名称，版本信息
     */
    @NotNull
    @JvmStatic
    fun getVersionList(myProject: IdeaProject.MyProject, env: EnvEnum): List<BugattiProjectVersionResult> {
        val url = "$httpDomainUrl/project/${myProject.bugattiLink.code}/version?envId=${env.envCode}"
        return HttpUtil.get(url, getCookie()).body().toList()
    }

    @NotNull
    @JvmStatic
    fun getVersionList(myProject: IdeaProject.MyProject): List<BugattiProjectVersionResult> {
        return getVersionList(myProject, EnvConfig.getInstance().env)
    }

    /**
     * Returns 分支名称，版本信息
     */
    @NotNull
    @JvmStatic
    fun mapLastBetaVersion(myProject: IdeaProject.MyProject): Map<String, BugattiLastVersionResult> {
        val url = "$httpDomainUrl/project/${myProject.bugattiLink.code}/versoin/lastbetaversion"
        return HttpUtil.get(url, getCookie()).body().toList<BugattiLastVersionResult>().associateBy { it.branch }
    }

    @NotNull
    @JvmStatic
    fun getLastReleaseVersion(myProject: IdeaProject.MyProject): BugattiLastVersionResult? {
        val url = "$httpDomainUrl/project/${myProject.bugattiLink.code}/versoin/lastrelaseversion"
        return HttpUtil.get(url, getCookie()).body().toList<BugattiLastVersionResult>().firstOrNull()
    }

    @NotNull
    @JvmStatic
    fun taskOfInstall(
        myProject: IdeaProject.MyProject, hostId: String,
        env: EnvEnum, versionId: String,
    ): String {
        val request = BugattiTaskQueueRequest(hostId, env.envCode, myProject.bugattiLink.code, versionId, "248890")
        return taskQueue(request)
    }


    @NotNull
    @JvmStatic
    fun taskOfStart(
        myProject: IdeaProject.MyProject, hostId: String,
        env: EnvEnum, versionId: String,
    ): String {
        val request = BugattiTaskQueueRequest(hostId, env.envCode, myProject.bugattiLink.code, versionId, "248891")
        return taskQueue(request)
    }


    /**
     * 任务队列
     * @return 任务ID
     */
    @NotNull
    @JvmStatic
    private fun taskQueue(request: BugattiTaskQueueRequest): String {
        val url = "$httpDomainUrl/task/newTaskQueue"
        val req = mapOf("taskQueue" to request)
        return HttpUtil.postJson(url, req.toJsonString(), getCookie()).body()
    }

    @JvmStatic
    @NotNull
    fun getShellElementList(bugattiProjectCode: String): List<ShellElement> {
        return getShellElementList(bugattiProjectCode, EnvConfig.getInstance().env)
    }

    @JvmStatic
    @NotNull
    fun getShellElementList(bugattiProjectCode: String, env: EnvEnum): List<ShellElement> {
        //屏蔽生产环境
        if (EnvEnum.PROD == env) {
            return Collections.emptyList()
        }
        val url = "$httpDomainUrl/task/clusters?envId=${env.envCode}&projectId=$bugattiProjectCode"
        val result = HttpUtil.get(url, getCookie()).body()
        val shellEleList = ArrayList<ShellElement>()

        val hostList = JSONUtil.parse(result).getByPath(".host")

        for (ele in JSONUtil.parseArray(hostList).iterator()) {
            val eleObj = JSONUtil.parse(ele)
            if (eleObj.getByPath(".show") == null) {
                shellEleList.add(
                    ShellElement.instanceOf(BugattiShellInfoResult(
                        Optional.ofNullable(eleObj.getByPath(".id")).map { it as String }.orElse(""),
                        Optional.ofNullable(eleObj.getByPath(".group")).map { it as String }.orElse(""),
                        Optional.ofNullable(eleObj.getByPath(".ip")).map { it as String }.orElse(""),
                        Optional.ofNullable(eleObj.getByPath(".version")).map { it as String }.orElse(""),
                        Optional.ofNullable(eleObj.getByPath(".tag")).map { it as String }.orElse("")
                    )))
            }
        }
        return shellEleList
    }

    @NotNull
    @JvmStatic
    fun jenkinsCIBuild(myProject: IdeaProject.MyProject, branchName: String): BugattiResult {

        val result = HttpUtil.putJson(
            "$httpDomainUrl/ci/build",
            CiBuildRequest.instanceOf(myProject, branchName).toJsonString(),
            getCookie()
        ).body()

        return if (BugattiResult.SUCCESS == result) {
            BugattiResult.success()
        } else {
            BugattiResult.fail(result)
        }
    }

    @NotNull
    @JvmStatic
    fun jenkinsCIRelease(
        myProject: IdeaProject.MyProject, branchName: String,
        version: String, snapshotVersion: String,
    ): BugattiResult {
        val url = "$httpDomainUrl/ci/release?force=true"
        val request = CiBuildReleaseRequest.instanceOf(myProject, branchName, version, snapshotVersion).toJsonString()
        val releaseResult = HttpUtil.putJson(url, request, getCookie()).body().toBean<BugattiCIReleaseResult>()!!

        return if (BugattiResult.SUCCESS == releaseResult.data) {
            BugattiResult.success()
        } else {
            BugattiResult.fail(releaseResult.data)
        }
    }

    @NotNull
    @JvmStatic
    fun ciBuildResult(myProject: IdeaProject.MyProject): BugattiCIBuildResult {
        val url = "$httpDomainUrl/ci/builds?projectId=${myProject.projectInfo.projectId}&page=0&pageSize=1"
        val result = HttpUtil.get(url, getCookie()).body()
        return result.toList<BugattiCIBuildResult>().firstDefault(BugattiCIBuildResult())
    }

    @NotNull
    @JvmStatic
    fun ciReleaseResult(myProject: IdeaProject.MyProject, version: String, branchName: String): BugattiCIBuildResult {
        val url =
            "$httpDomainUrl/ci/releases?projectId=${myProject.bugattiLink.code}&page=0&pageSize=1&version=$version&user=&tag=$branchName"
        val result = HttpUtil.get(url, getCookie()).body()
        return result.toList<BugattiCIBuildResult>().firstDefault(BugattiCIBuildResult())
    }

    @JvmStatic
    fun clearCookie() {
        BugattiCookie.getInstance().cookie = ""
    }

    private fun getCookie(): String {
        val storage = BugattiCookie.getInstance()
        if (storage.cookie.isNotBlank()) {
            return storage.cookie
        }
        val login = login(null, false)
        val loginSuccess = isLoginSuccess(login)
        if (loginSuccess) {
            return storage.cookie
        }
        return ""
    }

    fun refreshCookie(project: Project?, silent: Boolean) {
        login(project, silent)
    }

    init {
        //2小时更新一次 布加迪cookie
        val time = 7200000L
        Timer().schedule(object : TimerTask() {
            override fun run() {
                refreshCookie(null, true)
            }
        }, time, time)
    }
}