<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# qianmi-idea-plugin Changelog

## [Unreleased]

## [1.3.0-RELEASE]

### 修复BUG

- 修复ssh终端中文乱码问题

## [1.2.0-RELEASE]

### 优化

- 外网未连接vpn场景，idea打开极慢，设置登录bugatti超时时间3秒
- 打包RELEASE，支持选择分支（小店场景）
- 布加迪项目映射 d2p-admin-api 改为 d2p-admin-bff
- 打包tab切换，重复添加按钮事件问题

## [1.1.0-RELEASE]

### 新增功能

- 域账号配置：支持配置域账号信息，用以登录bugatti
- shell配置：支持配置shell默认账号、默认端口、默认工作目录
- build：支持构建 SNAPSHOT、BETA、RELEASE，并支持打包结果回调
- jenkins：支持一键跳转至jenkins

### 修复bug

- 外网未连接vpn，插件初始化失败，导致idea打不开问题

## [1.0.0-RELEASE]

### 初始化

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