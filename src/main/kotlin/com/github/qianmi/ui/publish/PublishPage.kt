package com.github.qianmi.ui.publish

import cn.hutool.core.thread.ThreadUtil
import cn.hutool.core.util.RandomUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import com.github.qianmi.action.link.BugattiAction
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement
import com.github.qianmi.infrastructure.domain.vo.BugattiProjectVersionResult
import com.github.qianmi.infrastructure.domain.vo.BugattiWebSocketMsgResult
import com.github.qianmi.infrastructure.extend.CollectionExtend.isNotEmpty
import com.github.qianmi.infrastructure.extend.JsonExtend.toBean
import com.github.qianmi.infrastructure.storage.AccountConfig
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.github.qianmi.infrastructure.util.NotifyUtil
import com.github.qianmi.infrastructure.util.WebSocketUtil
import com.github.qianmi.ui.MyJDialog
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.util.containers.toArray
import java.awt.Component
import java.awt.Dimension
import java.net.URI
import java.net.http.WebSocket
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.CompletionStage
import javax.swing.*
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableCellRenderer
import kotlin.math.max


class PublishPage(override var project: Project) : MyJDialog() {

    //根容器
    lateinit var contentPanel: JPanel

    private lateinit var eleList: List<ShellElement>

    //进度条，hostId->进度条
    private var mapProgressIndicator: MutableMap<String, ProgressIndicator> = mutableMapOf()

    //发布中的hostId列表
    private var publishingHostIds: MutableList<String> = mutableListOf()

    //版本列表
    private lateinit var versionList: List<BugattiProjectVersionResult>


    //环境切换
    private lateinit var envComboBox: JComboBox<String>

    private lateinit var jTable: JTable

    //发布版本
    private lateinit var publishVersionLabel: JLabel
    private lateinit var publishVersionComboBox: JComboBox<String>

    //发布方式
    private lateinit var publishWayLabel: JLabel

    //顺序发布
    private lateinit var publishWayRadioByOrder: JRadioButton

    //同时发布
    private lateinit var publishWayRadioBySameTime: JRadioButton

    //发布按钮
    private lateinit var publishButton: JButton

    private var tableAlign = JLabel.CENTER

    init {
        //环境下拉框
        initEnvComboBox()
        //节点表格
        refreshTable()
        //发布方式
        initPublishWay()
        //版本下拉框
        initVersionComboBox()
        //发布
        initPublishButton()
    }

    override fun getTitle(): String {
        return "发布"
    }

    private fun getTableSelectEleList(): MutableList<ShellElement> {
        val eleList = mutableListOf<ShellElement>()
        for (rowIdx in 0 until jTable.rowCount) {

            val value = jTable.getValueAt(rowIdx, 0)
            if (value as Boolean) {
                val ip = jTable.getValueAt(rowIdx, 2) as String
                eleList.add(this.eleList.first { it.ip == ip })
            }
        }
        return eleList
    }

    fun publish(versionId: String) {
        isVisible = false
        val env = getCurrentSelectEnv()
        val eleList = getTableSelectEleList()
        //创建webSocket链接
        val webSocket = createWebSocket(env, eleList)

        //顺序发布
        Thread {
            if (this.publishWayRadioByOrder.isSelected) {
                eleList.forEach { ele ->
                    //安装、启动
                    BugattiHttpUtil.taskOfInstall(this.getMyProject(), ele.id, env, versionId)
                    BugattiHttpUtil.taskOfStart(this.getMyProject(), ele.id, env, versionId)
                    publishingHostIds.add(ele.id)
                    //进度条
                    createProcessIndicator(ele)
                    while (publishingHostIds.isNotEmpty()) {
                        ThreadUtil.sleep(1000L)
                    }
                }
                webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok").join()
                this.notifyPublishEnd()
            }
            if (this.publishWayRadioBySameTime.isSelected) {
                //同时发布

                eleList.forEach { ele ->
                    //安装、启动
                    BugattiHttpUtil.taskOfInstall(this.getMyProject(), ele.id, env, versionId)
                    BugattiHttpUtil.taskOfStart(this.getMyProject(), ele.id, env, versionId)
                    publishingHostIds.add(ele.id)
                    //进度条
                    this.createProcessIndicator(ele)
                }
                while (publishingHostIds.isNotEmpty()) {
                    ThreadUtil.sleep(1000L)
                }
                webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok").join()
                notifyPublishEnd()
            }
        }.start()
    }


    private fun notifyPublishEnd() {
        val env = getCurrentSelectEnv()
        val bugattiLinkAction = BugattiAction.defaultAction(env, this.getMyProject().bugattiLink.code)
        NotifyUtil.notifyInfoWithAction(this.project, "节点已全部发布成功", bugattiLinkAction)
    }

    /**
     * 创建进度条
     */
    private fun createProcessIndicator(ele: ShellElement) {
        object : Task.Backgroundable(this.project, "Publish: 正在发布[${ele.ip}]", true, ALWAYS_BACKGROUND) {
            override fun run(indicator: ProgressIndicator) {
                mapProgressIndicator[ele.id] = indicator
                while (publishingHostIds.isNotEmpty()) {
                    ThreadUtil.sleep(1000L)
                }
            }
        }.queue()
    }

    private fun createWebSocket(env: EnvEnum, eleList: List<ShellElement>): WebSocket {
        val project = this.project
        val jobNo = AccountConfig.getInstance().userName.lowercase(Locale.getDefault())
        val projectCode = this.getMyProject().bugattiLink.code
        val randomInt = RandomUtil.randomInt(1, 999)
        val url = "${BugattiHttpUtil.wsDomainUrl}/webtask/joinProcess/${env.envCode}/$projectCode/$jobNo/$randomInt"

        return WebSocketUtil.builder.buildAsync(URI(url), object : WebSocket.Listener {

            override fun onBinary(webSocket: WebSocket?, data: ByteBuffer?, last: Boolean): CompletionStage<*> {
                return super.onBinary(webSocket, data, last)
            }

            override fun onText(webSocket: WebSocket?, data: CharSequence?, last: Boolean): CompletionStage<*>? {
                super.onText(webSocket, data, last)
                val isJson = JSONUtil.isJson(data.toString())
                if (!isJson) {
                    return null
                }
                //发布进度
                eleList.asSequence()
                    .map { "${env.envCode}_${getMyProject().bugattiLink.code}_${it.id}" }
                    .map { JSONUtil.parse(data.toString()).getByPath(".${it}") }
                    .filter { it != null }
                    .map { it.toString() }
                    .map { it.toBean<BugattiWebSocketMsgResult>() }
                    .forEach { result ->
                        if (result != null && StrUtil.isNotBlank(result.hostId) && mapProgressIndicator[result.hostId] != null) {
                            mapProgressIndicator[result.hostId]!!.text = result.command?.sls
                        }
                    }

                //发布结束移除任务
                eleList.filter {
                    val path = ".${env.envCode}_${getMyProject().bugattiLink.code}_${it.id}_last"
                    JSONUtil.parse(data.toString()).getByPath(path) != null
                }.forEach { publishingHostIds.remove(it.id) }
                return null
            }

            override fun onError(webSocket: WebSocket?, error: Throwable) {
                super.onError(webSocket, error)
                NotifyUtil.notifyError(project, "插件异常:${error.message}")
                error.printStackTrace()
            }
        }).join()
    }

    /**
     * 初始化 版本下拉框
     */
    private fun initPublishButton() {
        this.publishButton.addActionListener {
            publish(getCurrentSelectVersion().id)
        }
    }

    fun handlerPackageSource() {
        //打包时，无需操作版本
        this.publishVersionLabel.isVisible = false
        this.publishVersionComboBox.isVisible = false

        //处理发布按钮
        this.publishButton.text = "确定"

        //移除按钮事件
        this.publishButton.actionListeners.forEach {
            this.publishButton.removeActionListener(it)
        }
        this.publishButton.addActionListener {
            isVisible = false
        }
    }


    /**
     * 初始化 版本下拉框
     */
    private fun initVersionComboBox() {
        this.versionList = BugattiHttpUtil.getVersionList(this.getMyProject(), getCurrentSelectEnv())
        if (versionList.isNotEmpty()) {
            versionList.forEach { publishVersionComboBox.addItem(it.version) }
            //默认选中
            publishVersionComboBox.selectedItem = versionList[0].version
        }
    }

    private fun getCurrentSelectEnv(): EnvEnum {
        return EnvEnum.values().first { it.envName == envComboBox.selectedItem as String }
    }

    private fun getCurrentSelectVersion(): BugattiProjectVersionResult {
        return versionList.first { it.version == publishVersionComboBox.selectedItem as String }
    }

    /**
     * 初始化 环境选择下拉框
     */
    private fun initEnvComboBox() {
        EnvEnum.values()
            .filter { env -> env.envName != EnvEnum.PROD.envName }
            .forEach { envComboBox.addItem(it.envName) }
        //默认选中
        envComboBox.selectedItem = EnvConfig.getInstance().env.envName

        envComboBox.addActionListener { refreshTable() }
    }

    private fun initPublishWay() {
        val group = ButtonGroup()
        group.add(publishWayRadioByOrder)
        group.add(publishWayRadioBySameTime)
        publishWayRadioByOrder.isSelected = true
    }

    /**
     * 初始化表格
     */
    private fun refreshTable() {
        val eleList = BugattiHttpUtil.getShellElementList(this.getMyProject().bugattiLink.code, getCurrentSelectEnv())
        this.eleList = eleList

        val columns = arrayOf("勾选发布", "分组", "IP", "当前版本", "标签", "状态")
        val rows =
            eleList.map { ele -> arrayOf(true, ele.group, ele.ip, ele.version, ele.tag, ele.state) }
                .toArray(arrayOf())

        //填充数据
        jTable.model = DefaultTableModel(rows, columns)

        //复选框
        val jCheckBox = JCheckBox()
        jCheckBox.horizontalAlignment = tableAlign
        jTable.columnModel.getColumn(0).cellEditor = DefaultCellEditor(jCheckBox)
        jTable.columnModel.getColumn(0).cellRenderer = MyCheckBoxTableRenderer()

        //居中
        val cellRenderer = DefaultTableCellRenderer()
        cellRenderer.horizontalAlignment = tableAlign
        jTable.setDefaultRenderer(Object::class.java, cellRenderer)
        //动态宽高
        val dimension = Dimension(120 * columns.size, (jTable.rowHeight * (max(rows.size, 3) + 0.5)).toInt())
        jTable.preferredScrollableViewportSize = dimension
        jTable.preferredSize = dimension

    }


    internal class MyCheckBoxTableRenderer : JCheckBox(), TableCellRenderer {

        override fun getTableCellRendererComponent(
            table: JTable, value: Any, isSelected: Boolean,
            hasFocus: Boolean, row: Int, column: Int,
        ): Component {
            this.isSelected = value as Boolean
            this.horizontalAlignment = CENTER
            return this
        }
    }

    override fun getRootContainer(): JPanel {
        return contentPanel
    }
}