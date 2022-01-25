<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# qianmi-idea-plugin Changelog

## [Unreleased]

## [2.0.0-RELEASE]

### English

- Bug: Fix the bug that the plugin logo does not display
- Bug: Fix pack Beta version by default does not display the version number of bugs
- Refactor: Code structure refactoring, abstract refactoring
- Feat：Use progress bar to optimize packaging progress display
- Feat：Shell multi-node selection box, support for displaying tags

### Chinese

- Bug: 修复插件logo不展示BUG
- Bug: 修复打包Beta版本默认不展示版本号bug
- Refactor: 代码结构重构、抽象重构
- Feat: 使用进度条，优化打包进度展示
- Feat: shell多节点选择框，支持展示tag标签

## [1.7.0-RELEASE]

### English

- refactor: rebuild code
- add：The environment configuration supports persistence to the local, so there is no need to switch the environment
  frequently every time
- optimize：Log in regularly to refresh cookies, and no longer prompt message notifications frequently
- optimize：Add an icon to the environment switch and move it to an inconspicuous position
- optimize：Qianmi ICON get a new icon

### Chinese

- 重构: 重构代码
- 新增：环境配置支持持久化到本地，不用每次都要频繁切换环境了
- 优化：定时登录刷新cookie，不再频繁提示消息通知
- 优化：环境切换加个icon并移动到不显眼的位置
- 优化：千米ICON换了一个新图标

## [1.6.0-RELEASE]

### English

#### ADD

- Project support: qianmi database

#### FIX

- Package: When first entered, the beta version number did not render BUG
- Package: Switch tab back and forth, branch list data doubled BUG
- Login: Successfully logged in to Bugatti, optimized copyWriting display

### 中文

#### 新增

- 项目支持：qianmi数据库

#### 修复BUG

- 打包：第一次进入时，beta版本号未渲染BUG
- 打包：tab来回切，分支列表数据倍增BUG
- 登录：登录Bugatti成功，优化文案展示

## [1.5.0-RELEASE]

### ADD

#### English

- Add Remote JVM function
- Support to configure the default port number of Remote JVM Debug
- When the project is closed, the SFTP and Remote JVM Debug configuration added this time will be removed

#### 中文

- 增加Remote JVM功能
- 支持配置Remote JVM Debug的默认端口号
- 项目关闭时，清退本次添加的SFTP和Remote JVM Debug配置

### FIX BUG

#### English

- Fix the problem that the project does not take effect immediately if the server password needs to be restarted to
  change the server password through SSH or SFTP

#### 中文

- 修复SSH、SFTP更改服务器密码，需重启项目不立即生效问题

## [1.4.0-RELEASE]

### FIX BUG

#### English

- Fix the problem that the packaging failure also prompts the packaging success
- Fix the bug of dubbo Invoke parsing parameters
- Optimize the display effect of dubbo Invoke bullet frame
- Remove unnecessary third-party toolkits, greatly reducing the size of the plug-in
- Domain account configuration error, provide quick configuration entry
- Supplementary qianmi tools icon
- Optimize the packaging experience: support the esc key to close the packaging window

#### 中文

- 修复打包失败也提示打包成功bug
- 修复dubboInvoke解析参数bug
- 优化dubboInvoke弹框展示效果
- 移除不必要三方工具包，极大减少插件体积
- 域账号配置错误，提供快捷配置入口
- 补充千米工具图标
- 优化打包体验：支持esc键关闭打包窗口

## [1.3.2-RELEASE]

### SUPPORT

- Support for 2021.3.1


- 支持2021.3.1版本

## [1.3.1-RELEASE]

### ADD

- Add the bugatti mapping of the ShuMou project


- 增加数谋项目的bugatti映射

## [1.3.0-RELEASE]

### FIX BUG

- Fix ssh terminal Chinese garbled problem


- 修复ssh终端中文乱码问题

### ADD

- Support one-click connection test environment sftp


- 支持一键连接测试环境sftp

## [1.2.0-RELEASE]

### FIX BUG

- The VPN is not connected to the external network, the idea is very slow to open, and the login bugatti timeout time is
  set to 3 seconds
- Bugatti project mapping d2p-admin-api changed to d2p-admin-bff
- Package tab switch, add button event repeatedly
- RELEASE support branch selection


- 外网未连接vpn场景，idea打开极慢，设置登录bugatti超时时间3秒
- 布加迪项目映射 d2p-admin-api 改为 d2p-admin-bff
- 打包tab切换，重复添加按钮事件问题
- 打包RELEASE，支持选择分支（小店场景）

## [1.1.0-RELEASE]

### ADD

- Domain account configuration：Support configuration of domain account information to log in to bugatti
- shell configuration：Support to configure shell default account, default port, default working directory
- build：Support construction of SNAPSHOT, BETA, RELEASE, and support package result callback
- jenkins：Support one-click jump to jenkins


- 域账号配置：支持配置域账号信息，用以登录bugatti
- shell配置：支持配置shell默认账号、默认端口、默认工作目录
- build：支持构建 SNAPSHOT、BETA、RELEASE，并支持打包结果回调
- jenkins：支持一键跳转至jenkins

## [1.0.0-RELEASE]

### ADD

- Shell：Support according to the project and environment, directly connect the corresponding node at the terminal
- DubboInvoke：Support constructing Dubbo invoke command according to the selected method
- Bugatti：Support one-click jump to Bugatti according to the project and environment
- Gitlab：Support one-click jump to gitlab according to the project
- DubboAdmin：Support one-click jump to DubboAdmin according to the environment
- RocketMq：Support one-click jump to RocketMq according to the environment
- ActiveMq：Support one-click jump to ActiveMq according to the environment
- Trace：Support one-click jump to Trace according to the environment
- Gavin：Support one-click jump to Gavin according to the environment
- QianmiAdmin: Support one-click jump to QianmiAdmin according to the environment
- ApmDashboard：Jump to ApmDashboard with one click
- MongoDBManager：Jump to MongoDBManager with one click
- Wiki：Jump to Wiki with one click
- gitBook：Jump to gitBook with one click
- ConsoleOfPc：Support one-click jump to the commodity console according to the environment
- ConsoleOfD2pMc：Support one-click jump to the distribution marketing console according to the environment
- ConsoleOfOms：Support one-click jump to the OMS console according to the environment


- Shell：支持根据项目和环境，直接在终端连接对应节点
- DubboInvoke：支持根据选中方法，构建 Dubbo invoke 命令
- Bugatti：支持根据项目和环境，一键跳转至 Bugatti
- Gitlab：支持根据项目，一键跳转至 gitlab
- DubboAdmin：支持根据环境，一键跳转至 DubboAdmin
- RocketMq：支持根据环境，一键跳转至 RocketMq
- ActiveMq：支持根据环境，一键跳转至 ActiveMq
- Trace：支持根据环境，一键跳转至 Trace
- Gavin：支持根据环境，一键跳转至 Gavin
- QianmiAdmin: 支持根据环境，一键跳转至 QianmiAdmin
- ApmDashboard：一键跳转至 ApmDashboard
- MongoDBManager：一键跳转至 MongoDBManager
- Wiki：一键跳转至 Wiki
- gitBook：一键跳转至 gitBook
- ConsoleOfPc：支持根据环境，一键跳转至 商品控制台
- ConsoleOfD2pMc：支持根据环境，一键跳转至 分销营销控制台
- ConsoleOfOms：支持根据环境，一键跳转至 OMS控制台