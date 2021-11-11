package com.github.qianmi.util

import cn.hutool.http.ContentType
import cn.hutool.http.HttpUtil
import com.alibaba.fastjson.JSONObject
import com.github.qianmi.config.BugattiConfig
import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.domain.project.tools.Shell
import com.github.qianmi.services.vo.BugattiProjectInfoResult
import com.github.qianmi.services.vo.BugattiShellInfoResult
import org.jetbrains.annotations.NotNull
import java.net.HttpCookie
import java.util.*

object BugattiHttpUtil {

    private var cookie: HttpCookie? = null

    @JvmStatic
    fun loginBugatti(): HttpCookie? {
        val body: MutableMap<String, Any> = HashMap(8)
        body["userName"] = BugattiConfig.userName
        body["password"] = BugattiConfig.password
        cookie = HttpUtil
            .createPost(BugattiConfig.domain + "/login")
            .body(JSONObject.toJSONString(body), ContentType.JSON.value)
            .execute()
            .getCookie("SESSION")
        return cookie
    }

    @NotNull
    @JvmStatic
    fun getProjectInfo(myProject: AllProject.MyProject): BugattiProjectInfoResult {
        val result: String = HttpUtil
            .createGet(BugattiConfig.domain + String.format("/project/%s/%s",
                myProject.bugatti.projectCode,
                myProject.bugatti.envCode))
            .cookie(getCookie())
            .execute()
            .body()

        return JSONObject.parseObject(result, BugattiProjectInfoResult::class.java)
    }

    @JvmStatic
    fun getShellElementList(bugattiProjectCode: String, env: EnvEnum): List<Shell.Element> {
        //屏蔽生产环境
        if (EnvEnum.PROD == env) {
            return Collections.emptyList()
        }
        val result: String = HttpUtil.createGet(BugattiConfig.domain
                + String.format("/task/clusters?envId=%s&projectId=%s", env.bugatti.envCode, bugattiProjectCode))
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

    private fun getCookie(): HttpCookie? {
        if (cookie != null) {
            return cookie
        }
        return loginBugatti()
    }

    init {
        //2小时更新一次 布加迪cookie
        Timer().schedule(object : TimerTask() {
            override fun run() {
            }
        }, Date(), 7200000L)
    }

}