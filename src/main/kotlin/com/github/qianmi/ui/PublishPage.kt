package com.github.qianmi.ui

import cn.hutool.core.thread.ThreadUtil
import cn.hutool.core.util.RandomUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import com.github.qianmi.action.link.BugattiAction
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.domain.project.IdeaProject
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
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import com.intellij.util.containers.toArray
import java.awt.Component
import java.awt.Dimension
import java.awt.event.KeyEvent
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


class PublishPage : JDialog {
    private var project: Project

    //指定发布版本（从打包口子）
    private var isPackageSource: Boolean = false

    private lateinit var iProject: IdeaProject.MyProject
    private lateinit var eleList: List<ShellElement>

    //进度条，hostId->进度条
    private var mapProgressIndicator: MutableMap<String, ProgressIndicator> = mutableMapOf()

    //发布中的hostId列表
    private var publishingHostIds: MutableList<String> = mutableListOf()

    //版本列表
    private lateinit var versionList: List<BugattiProjectVersionResult>

    //根容器
    private lateinit var contentPanel: JPanel

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

    constructor(project: Project) {
        this.project = project
        this.iProject = IdeaProject.getInstance(project)
        init()
    }

    constructor(project: Project, isPackageSource: Boolean) {
        this.project = project
        this.isPackageSource = isPackageSource
        init()
    }

    private fun init() {
        this.iProject = IdeaProject.getInstance(project)

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
        //esc退出
        initEscEvent()
    }

    init {
        //容器
        contentPane = this.contentPanel
        modalityType = ModalityType.APPLICATION_MODAL
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
        if (this.publishWayRadioByOrder.isSelected) {
            Thread {
                eleList.forEach { ele ->
                    //安装、启动
                    BugattiHttpUtil.taskOfInstall(iProject, ele.id, env, versionId)
                    BugattiHttpUtil.taskOfStart(iProject, ele.id, env, versionId)
                    publishingHostIds.add(ele.id)
                    //进度条
                    createProcessIndicator(ele)
                    while (publishingHostIds.isNotEmpty()) {
                        ThreadUtil.sleep(1000L)
                    }
                }
                webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok").join()
                this.notifyPublishEnd()
            }.start()
        }

        //同时发布
        if (this.publishWayRadioBySameTime.isSelected) {

            eleList.forEach { ele ->
                //安装、启动
                BugattiHttpUtil.taskOfInstall(iProject, ele.id, env, versionId)
                BugattiHttpUtil.taskOfStart(iProject, ele.id, env, versionId)
                publishingHostIds.add(ele.id)
                //进度条
                this.createProcessIndicator(ele)
            }
            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok").join()
            notifyPublishEnd()
        }

    }

    private fun notifyPublishEnd() {
        val env = getCurrentSelectEnv()
        val bugattiLinkAction = BugattiAction.defaultAction(env, iProject.bugattiLink.code)
        NotifyUtil.notifyInfoWithAction(project, "节点已全部发布成功", bugattiLinkAction)
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
        val jobNo = AccountConfig.getInstance().userName.lowercase(Locale.getDefault())
        val projectCode = iProject.bugattiLink.code
        val randomInt = RandomUtil.randomInt(1, 999)
        val url = "${BugattiHttpUtil.wsDomainUrl}/webtask/joinProcess/${env.envCode}/$projectCode/$jobNo/$randomInt"

        return WebSocketUtil.builder.buildAsync(URI(url), object : WebSocket.Listener {

            override fun onBinary(webSocket: WebSocket?, data: ByteBuffer?, last: Boolean): CompletionStage<*> {
                return super.onBinary(webSocket, data, last)
            }

            override fun onText(webSocket: WebSocket?, data: CharSequence?, last: Boolean): CompletionStage<*>? {
                super.onText(webSocket, data, last)
                println()
                println(data)

                val isJson = JSONUtil.isJson(data.toString())
                if (!isJson) {
                    println("noJson--------" + data.toString())
                    return null
                }

                //发布进度
                eleList.asSequence()
                    .map { "${env.envCode}_${iProject.bugattiLink.code}_${it.id}" }
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
                    val path = ".${env.envCode}_${iProject.bugattiLink.code}_${it.id}_last"
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
        if (isPackageSource) {
            this.publishButton.text = "确定"
            this.publishButton.addActionListener {
                isVisible = false
            }
        } else {
            this.publishButton.addActionListener {
                publish(getCurrentSelectVersion().id)
            }
        }
    }


    /**
     * 初始化 版本下拉框
     */
    private fun initVersionComboBox() {
        if (isPackageSource) {
            //打包时，无需操作版本
            this.publishVersionLabel.isVisible = false
            this.publishVersionComboBox.isVisible = false
        } else {
            this.versionList = BugattiHttpUtil.getVersionList(this.iProject, getCurrentSelectEnv())
            if (versionList.isNotEmpty()) {
                versionList.forEach { publishVersionComboBox.addItem(it.version) }
                //默认选中
                publishVersionComboBox.selectedItem = versionList[0].version
            }
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
        val eleList = BugattiHttpUtil.getShellElementList(this.iProject.bugattiLink.code, getCurrentSelectEnv())
        this.eleList = eleList

        val columns = arrayOf("勾选发布", "分组", "IP", "当前版本", "标签", "状态")
        val rows =
            eleList.map { ele -> arrayOf(true, ele.group, ele.ip, ele.version, ele.tag, "正常") }
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

    /**
     * 打开窗口
     */
    fun open() {
        title = "发布"
        pack()
        //两个屏幕处理出现问题，跳到主屏幕去了
        setLocationRelativeTo(WindowManager.getInstance().getFrame(project))
        isVisible = true
    }

    /**
     * esc键关闭窗口
     */
    private fun initEscEvent() {
        contentPanel.registerKeyboardAction({ this.dispose() },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
    }
}