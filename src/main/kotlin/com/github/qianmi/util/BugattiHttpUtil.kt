package com.github.qianmi.util

import cn.hutool.json.JSONUtil
import com.github.qianmi.action.SettingAction
import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.domain.project.link.Bugatti
import com.github.qianmi.domain.project.tools.ShellElement
import com.github.qianmi.services.request.CiBuildReleaseRequest
import com.github.qianmi.services.request.CiBuildRequest
import com.github.qianmi.services.vo.*
import com.github.qianmi.storage.BugattiCookie
import com.github.qianmi.storage.DomainConfig
import com.github.qianmi.util.CollectionUtil.firstDefault
import com.github.qianmi.util.HttpUtil.getCookie
import com.github.qianmi.util.HttpUtil.isOk
import com.github.qianmi.util.JsonUtil.toBean
import com.github.qianmi.util.JsonUtil.toJsonString
import com.github.qianmi.util.JsonUtil.toList
import com.github.qianmi.util.StringUtil.isNotBlank
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.net.http.HttpResponse
import java.util.*

object BugattiHttpUtil {

    private const val bugattiUrl = Bugatti.domainUrl

    /**
     * @param silent 静默模式
     */
    @JvmStatic
    @Nullable
    fun login(project: Project?, silent: Boolean): HttpResponse<String>? {
        try {
            val httpResponse = login(
                DomainConfig.getInstance().userName,
                DomainConfig.getInstance().passwd
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
        return HttpUtil.postJson("$bugattiUrl/login", body.toJsonString())
    }

    @NotNull
    @JvmStatic
    fun getProjectInfo(myProject: AllProject.MyProject): BugattiProjectInfoResult {
        val result = HttpUtil.get(
            "$bugattiUrl/project/${myProject.bugatti.projectCode}/${myProject.bugatti.envCode}",
            getCookie()
        ).body()
        return result.toBean()!!
    }

    @NotNull
    @JvmStatic
    fun getBranchList(myProject: AllProject.MyProject): List<BugattiProjectBranchResult> {
        val result = HttpUtil.get(
            "$bugattiUrl/ci/branchs?gitUrl=${myProject.gitlab.url}",
            getCookie()
        ).body()
        return result.toList()
    }

    /**
     * Returns 分支名称，版本信息
     */
    @NotNull
    @JvmStatic
    fun mapLastBetaVersion(myProject: AllProject.MyProject): Map<String, BugattiLastVersionResult> {
        val result = HttpUtil.get(
            "$bugattiUrl/project/${myProject.bugatti.projectCode}/versoin/lastbetaversion",
            getCookie()
        ).body()
        return result.toList<BugattiLastVersionResult>().associateBy { it.branch }
    }

    @NotNull
    @JvmStatic
    fun getLastReleaseVersion(myProject: AllProject.MyProject): BugattiLastVersionResult? {
        val result = HttpUtil.get(
            "$bugattiUrl/project/${myProject.bugatti.projectCode}/versoin/lastrelaseversion",
            getCookie()
        ).body()
        return result.toList<BugattiLastVersionResult>().firstOrNull()
    }

    @JvmStatic
    fun getShellElementList(bugattiProjectCode: String, env: EnvEnum): List<ShellElement> {
        //屏蔽生产环境
        if (EnvEnum.PROD == env) {
            return Collections.emptyList()
        }

        val result = HttpUtil.get(
            "$bugattiUrl/task/clusters?envId=${env.bugatti.envCode}&projectId=$bugattiProjectCode",
            getCookie()
        ).body()
        val shellEleList = ArrayList<ShellElement>()

        val hostList = JSONUtil.parse(result).getByPath(".host")


        for (ele in JSONUtil.parseArray(hostList).iterator()) {
            val eleObj = JSONUtil.parse(ele)
            if (eleObj.getByPath(".show") == null) {
                shellEleList.add(
                    ShellElement.instanceOf(BugattiShellInfoResult(
                        Optional.ofNullable(eleObj.getByPath(".group")).map { it as String }.orElse(""),
                        Optional.ofNullable(eleObj.getByPath(".ip")).map { it as String }.orElse(""),
                        Optional.ofNullable(eleObj.getByPath(".version")).map { it as String }.orElse(""),
                    )))
            }
        }
        return shellEleList
    }


    @NotNull
    @JvmStatic
    fun jenkinsCIBuild(myProject: AllProject.MyProject, branchName: String): BugattiResult {

        val result = HttpUtil.putJson(
            "$bugattiUrl/ci/build",
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
        myProject: AllProject.MyProject,
        branchName: String,
        version: String,
        snapshotVersion: String,
    ): BugattiResult {

        val result = HttpUtil.putJson(
            "$bugattiUrl/ci/release?force=true",
            CiBuildReleaseRequest.instanceOf(myProject, branchName, version, snapshotVersion).toJsonString(),
            getCookie()
        ).body()

        val releaseResult = result.toBean<BugattiCIReleaseResult>()!!

        return if (BugattiResult.SUCCESS == releaseResult.data) {
            BugattiResult.success()
        } else {
            BugattiResult.fail(releaseResult.data)
        }
    }

    @NotNull
    @JvmStatic
    fun ciBuildResult(myProject: AllProject.MyProject): BugattiCIBuildResult {
        val result = HttpUtil.get(
            "$bugattiUrl/ci/builds?projectId=${myProject.bugatti.projectCode}&page=0&pageSize=1",
            getCookie()
        ).body()

        return result.toList<BugattiCIBuildResult>().firstDefault(BugattiCIBuildResult())
    }

    @NotNull
    @JvmStatic
    fun ciReleaseResult(myProject: AllProject.MyProject, version: String, branchName: String): BugattiCIBuildResult {
        val result = HttpUtil.get(
            "$bugattiUrl/ci/releases?projectId=${myProject.bugatti.projectCode}&page=0&pageSize=1&version=${version}&user=&tag=${branchName}",
            getCookie()
        ).body()
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