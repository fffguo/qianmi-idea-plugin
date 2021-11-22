package com.github.qianmi.util

import cn.hutool.core.collection.CollectionUtil
import cn.hutool.core.date.DateField
import cn.hutool.core.date.DateUtil
import cn.hutool.http.ContentType
import cn.hutool.http.HttpResponse
import cn.hutool.http.HttpUtil
import cn.hutool.http.Method
import com.alibaba.fastjson.JSONObject
import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.domain.project.link.Bugatti
import com.github.qianmi.domain.project.tools.Shell
import com.github.qianmi.services.request.CiBuildReleaseRequest
import com.github.qianmi.services.request.CiBuildRequest
import com.github.qianmi.services.vo.*
import com.github.qianmi.storage.BugattiCookie
import com.github.qianmi.storage.DomainConfig
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.NotNull
import java.util.*

object BugattiHttpUtil {

    private const val bugattiUrl = Bugatti.domainUrl

    @JvmStatic
    fun login(): HttpResponse {
        return login(
            DomainConfig.getInstance().userName,
            DomainConfig.getInstance().passwd
        )
    }

    @JvmStatic
    fun login(userName: String, password: String): HttpResponse {
        val body: MutableMap<String, Any> = HashMap(8)
        body["userName"] = userName
        body["password"] = password
        return HttpUtil
            .createPost("$bugattiUrl/login")
            .body(JSONObject.toJSONString(body), ContentType.JSON.value)
            .execute()
    }

    @NotNull
    @JvmStatic
    fun getProjectInfo(myProject: AllProject.MyProject): BugattiProjectInfoResult {
        val result: String = HttpUtil
            .createGet("$bugattiUrl/project/${myProject.bugatti.projectCode}/${myProject.bugatti.envCode}")
            .cookie(getCookie())
            .execute()
            .body()

        return JSONObject.parseObject(result, BugattiProjectInfoResult::class.java)
    }

    @NotNull
    @JvmStatic
    fun getBranchList(myProject: AllProject.MyProject): List<BugattiProjectBranchResult> {
        val result: String = HttpUtil
            .createGet("$bugattiUrl/ci/branchs?gitUrl=${myProject.gitlab.url}")
            .cookie(getCookie())
            .execute()
            .body()

        return CollectionUtil.emptyIfNull(JSONObject.parseArray(result, BugattiProjectBranchResult::class.java))
    }

    /**
     * Returns 分支名称，版本信息
     */
    @NotNull
    @JvmStatic
    fun mapLastBetaVersion(myProject: AllProject.MyProject): Map<String, BugattiLastVersionResult> {
        val result: String = HttpUtil
            .createGet("$bugattiUrl/project/${myProject.bugatti.projectCode}/versoin/lastbetaversion")
            .cookie(getCookie())
            .execute()
            .body()

        return CollectionUtil.emptyIfNull(JSONObject.parseArray(result, BugattiLastVersionResult::class.java))
            .associateBy { it.branch }

    }

    @NotNull
    @JvmStatic
    fun getLastReleaseVersion(myProject: AllProject.MyProject): BugattiLastVersionResult? {
        val result: String = HttpUtil
            .createGet("$bugattiUrl/project/${myProject.bugatti.projectCode}/versoin/lastrelaseversion")
            .cookie(getCookie())
            .execute()
            .body()

        val resultList = CollectionUtil.emptyIfNull(JSONObject.parseArray(result, BugattiLastVersionResult::class.java))
        if (CollectionUtil.isNotEmpty(resultList)) {
            return resultList[0]
        }
        return null
    }

    @JvmStatic
    fun getShellElementList(bugattiProjectCode: String, env: EnvEnum): List<Shell.Element> {
        //屏蔽生产环境
        if (EnvEnum.PROD == env) {
            return Collections.emptyList()
        }
        val result = HttpUtil
            .createGet("$bugattiUrl/task/clusters?envId=${env.bugatti.envCode}&projectId=$bugattiProjectCode")
            .cookie(getCookie())
            .execute()
            .body()

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


    @NotNull
    @JvmStatic
    fun jenkinsCIBuild(myProject: AllProject.MyProject, branchName: String): BugattiResult {

        val result: String = HttpUtil
            .createRequest(Method.PUT, "$bugattiUrl/ci/build")
            .body(JSONObject.toJSONString(CiBuildRequest.instanceOf(myProject, branchName)))
            .cookie(getCookie())
            .execute()
            .body()
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

        val result: String = HttpUtil
            .createRequest(Method.PUT, "$bugattiUrl/ci/release?force=true")
            .body(JSONObject.toJSONString(CiBuildReleaseRequest.instanceOf(myProject,
                branchName,
                version,
                snapshotVersion)))
            .cookie(getCookie())
            .execute()
            .body()
        val releaseResult = JSONObject.parseObject(result, BugattiCIReleaseResult::class.java)

        return if (BugattiResult.SUCCESS == releaseResult.data) {
            BugattiResult.success()
        } else {
            BugattiResult.fail(releaseResult.data)
        }
    }

    @NotNull
    @JvmStatic
    fun ciBuildResult(myProject: AllProject.MyProject): BugattiCIBuildResult {
        val result: String = HttpUtil
            .createGet("$bugattiUrl/ci/builds?projectId=${myProject.bugatti.projectCode}&page=0&pageSize=1")
            .cookie(getCookie())
            .execute()
            .body()
        val resultList = JSONObject.parseArray(result, BugattiCIBuildResult::class.java)
        if (CollectionUtil.isNotEmpty(resultList)) {
            return resultList[0]
        }
        return BugattiCIBuildResult()
    }

    @NotNull
    @JvmStatic
    fun ciReleaseResult(myProject: AllProject.MyProject, version: String, branchName: String): BugattiCIBuildResult {
        val result: String = HttpUtil
            .createGet("$bugattiUrl/ci/releases?projectId=${myProject.bugatti.projectCode}&page=0&pageSize=1&version=${version}&user=&tag=${branchName}")
            .cookie(getCookie())
            .execute()
            .body()
        val resultList = JSONObject.parseArray(result, BugattiCIBuildResult::class.java)
        if (CollectionUtil.isNotEmpty(resultList)) {
            return resultList[0]
        }
        return BugattiCIBuildResult()
    }


    @JvmStatic
    fun clearCookie() {
        BugattiCookie.getInstance().cookie = ""
    }

    private fun getCookie(): String {
        val storage = BugattiCookie.getInstance()
        if (StringUtil.isNotBlank(storage.cookie)) {
            return storage.cookie
        }
        val cookie = login().getCookie("SESSION").toString()
        storage.cookie = cookie
        return cookie
    }

    fun refreshCookie(project: Project?): Boolean {
        val httpResponse = login()
        return if (httpResponse.isOk) {
            BugattiCookie.getInstance().cookie = httpResponse.getCookie("SESSION").toString()
            true
        } else {
            NotifyUtil.notifyError(project, "登录Bugatti失败！请检查账号密码是否正确; 内网网络是否通畅(VPN)")
            false
        }
    }

    init {
        //2小时更新一次 布加迪cookie
        val time = 7200000L
        Timer().schedule(object : TimerTask() {
            override fun run() {
                refreshCookie(null)
            }
        }, DateUtil.offset(Date(), DateField.SECOND, time.toInt()), time)
    }
}