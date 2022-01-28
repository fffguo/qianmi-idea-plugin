package com.github.qianmi.ui

import cn.hutool.core.thread.ThreadUtil
import cn.hutool.core.util.RandomUtil
import cn.hutool.json.JSONUtil
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


class PublishPage(var project: Project) : JDialog() {

    private var iProject: IdeaProject.MyProject
    private lateinit var eleList: List<ShellElement>

    //选中的ip列表
    private lateinit var selectedIpList: MutableList<String>

    //版本列表
    private lateinit var versionList: List<BugattiProjectVersionResult>

    //根容器
    private lateinit var contentPanel: JPanel

    //环境切换
    private lateinit var envComboBox: JComboBox<String>

    private lateinit var jTable: JTable

    //发布版本
    private lateinit var publishVersionLabel: JLabel
    private lateinit var publishVersion: JComboBox<String>

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
        //容器
        contentPane = this.contentPanel
        modalityType = ModalityType.APPLICATION_MODAL
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

    private fun publish() {
        isVisible = false

//        //顺序发布
//        if (this.publishWayRadioByOrder.isSelected) {
//
//        }
//
//        //同时发布
//        if (this.publishWayRadioBySameTime.isSelected) {
//
//        }
        val startTime = Date()
        val eleList = mutableListOf<ShellElement>()

        for (rowIdx in 0 until jTable.rowCount) {

            val value = jTable.getValueAt(rowIdx, 0)
            if (value as Boolean) {
                val ip = jTable.getValueAt(rowIdx, 2) as String
                eleList.add(this.eleList.first { it.ip == ip })
            }
        }
        val versionId = getCurrentSelectVersion().id
        val env = getCurrentSelectEnv()

        object : Task.Backgroundable(this.project, "Publish: 正在发布中[1/${eleList.size}]", true, DEAF) {

            override fun run(indicator: ProgressIndicator) {
                val webSocket = createWebSocket(indicator, env, eleList)

                ThreadUtil.sleep(600_000L)
//                for (idx in 0 until eleList.size) {
//
////                    indicator.text = "Install：正在安装${eleList[idx].ip}"
//                    val taskId = BugattiHttpUtil.taskOfInstall(iProject, eleList[idx].id, env, versionId)
//                }
                webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok").join()

            }
        }.queue()

    }

    private fun createWebSocket(indicator: ProgressIndicator, env: EnvEnum, eleList: List<ShellElement>): WebSocket {
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

                //消息key
                val list = eleList.asSequence().map { it.id }
                    .map { "${env.envCode}_${iProject.bugattiLink.code}_$it" }
                    .filter {
                        val isJson = JSONUtil.isJson(data.toString())
                        if (!isJson) {
                            println("nojson--------" + data.toString())
                        }
                        isJson
                    }
                    .map { JSONUtil.parse(data.toString()).getByPath(".${it}") }
                    .filter { it != null }
                    .map { it.toString() }
                    .map { it.toBean<BugattiWebSocketMsgResult>() }
                    .toList()


                if (list.isNotEmpty()) {
                    indicator.text = list[0]?.command?.sls
                }

                return null
            }

            override fun onError(webSocket: WebSocket?, error: Throwable?) {
                super.onError(webSocket, error)

                if (error != null) {
                    NotifyUtil.notifyError(project, "插件异常:${error.message}")
                    error.printStackTrace()
                    println(error)
                }
            }
        }).join()
    }

    /**
     * 初始化 版本下拉框
     */
    private fun initPublishButton() {
        this.publishButton.addActionListener {
            publish()
        }
    }


    /**
     * 初始化 版本下拉框
     */
    private fun initVersionComboBox() {
        this.versionList = BugattiHttpUtil.getVersionList(this.iProject, getCurrentSelectEnv())
        if (versionList.isNotEmpty()) {
            versionList.forEach { publishVersion.addItem(it.version) }
            //默认选中
            publishVersion.selectedItem = versionList[0].version
        }
    }

    private fun getCurrentSelectEnv(): EnvEnum {
        return EnvEnum.values().first { it.envName == envComboBox.selectedItem as String }
    }

    private fun getCurrentSelectVersion(): BugattiProjectVersionResult {
        return versionList.first { it.version == publishVersion.selectedItem as String }
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