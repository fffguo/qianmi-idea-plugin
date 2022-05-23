# qianmi-idea-plugin

![Build](https://github.com/fffguo/qianmi-idea-plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/17958-qianmi-tools.svg)](https://plugins.jetbrains.com/plugin/17958-qianmi-tools)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/17958-qianmi-tools.svg)](https://plugins.jetbrains.com/plugin/17958-qianmi-tools)

<!-- Plugin description -->

<p><a href="https://github.com/fffguo/qianmi-idea-plugin">GitHub</a></p>

<p><a href="https://github.com/fffguo/qianmi-idea-plugin/issues">常见问题</a></p>
<p>please give me a star, thank you very much! (*╹▽╹*)</p>
<p>如果感觉这个插件还不错，给个star以资鼓励吧~</p>

<h3>This plug-in provides a more immersive development method for Qianmi developers and provides some practical
functions to improve employee work efficiency.</h3>

<h3>English:</h3>
<ul>
<li> Package：Support direct packaging in idea, and directly package the result callback</li>
<li> Publish：Supports idea one-click publishing and real-time viewing of progress</li>
<li> SSH：Support SSH connection to the corresponding test environment node directly in the terminal according to the project and environment</li>
<li> SFTP：Support according to the project and environment, direct SFTP connection to the corresponding test environment node</li>
<li> Remote JVM Debug：Support according to the project and environment, directly remote JVM debugging corresponding test environment node</li>
<li> DubboInvoke：Support constructing Dubbo invoke command according to the selected method</li>
<li> Link：Support one-click jump to Bugatti、Gitlab、DubboAdmin、RocketMq、ActiveMq、Trace、Gavin、QianmiAdmin、ApmDashboard、MongoDBManager、Wiki、gitBook、Jenkins、ConsoleOfPc、ConsoleOfD2pMc、ConsoleOfOms</li>
<li> Arthas：Support one-click deployment of Arthas to the server</li>
<li> Log：Support one-click viewing of server logs</li>
</ul>

<h3>中文：</h3>
<ul>
<li> Package：支持idea中直接打包/懒人发布，并展示打包进度及结果</li>
<li> Publish：支持idea中一键发布，实时查看进度</li>
<li> SSH：支持根据项目和环境，直接在终端SSH连接对应测试环境节点</li>
<li> SFTP：支持根据项目和环境，直接SFTP连接对应测试环境节点</li>
<li> Remote JVM Debug：支持根据项目和环境，直接远程JVM调试对应测试环境节点</li>
<li> DubboInvoke：支持根据选中方法，构建 Dubbo invoke 命令</li>
<li> Arthas：支持一键部署Arthas到服务器上</li>
<li> Log：支持一键查看服务器日志</li>
<li> Link：支持根据项目和环境，一键跳转至Bugatti、Gitlab、DubboAdmin、RocketMq、ActiveMq、Trace、Gavin、QianmiAdmin、ApmDashboard、MongoDBManager、Wiki、gitBook、Jenkins、ConsoleOfPc、ConsoleOfD2pMc、ConsoleOfOms</li>
</ul>

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "
  Qianmi-Tools"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/595726017/qianmi-idea-plugin/releases/latest) and install it manually
  using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

## Plugin preview（插件预览）

### 1. Configuration settings（配置设置）

![配置设置](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-1.gif)

### 2. package（打包）

![打包](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-2.gif)

### 3. public（发布）

![发布](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-3.gif)

### 4. （SSH Terminal）SSH终端

![SSH](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-4.gif)

### 5. SFTP

![SFTP](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-5.gif)

### 6. Dubbo Invoke call（Dubbo Invoke 调用）

![Dubbo Invoke 调用](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-6.gif)

### 7. Arthas one-click deployment（Arthas一键部署）

![Arthas一键部署](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-7.gif)

### 8. Remote JVM debugging（远程JVM调试）

![远程JVM调试](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-8.gif)

### 9. View server logs with one click（一键查看服务器日志）

![一键查看服务器日志](https://raw.githubusercontent.com/fffguo/picture-warehouse/main/github/qianmi-idea-plugin-preview-9.gif)

<!-- Plugin description end -->

## 开发者事项

> 我跑路了，有兴趣的小伙伴可以继续提交pr，欢迎！
> 有疑问的地方可以在github上提issue，或者直接私聊我也可。
> 邮箱：595726017@qq.com
> 微信:l595726017

### 环境

- JDK 11+
- Grade JVM 11+
- IntelliJ IDEA 2022.1+
- Kotlin SDK

### 未实现功能

- 环境切换目前是写死的，没有根据当前用户刷新
- qianmi-link功能目前都是写死的，如果改成可配置的就好了。因为有些链接在其他业务线根本用不到，展示的很多余，而且link展示的并不完全

### 你有项目想要插件支持？(例如云小店)

- com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum 添加枚举即可
- 项目名：当前idea打开的项目名
- code: 布加迪的项目code,在bugatti打开项目，url上就有code

### 我的IDEA升级了新版本，插件不能用了？

- "gradle.properties"的 "pluginSinceBuild"/"pluginUntilBuild" 配置了该插件能使用的idea版本，按照对应格式，更新支持的idea版本号即可
- 新版本idea的api会有些变更，条件允许最好也同步更新idea的新api
- 打包可以本地打包，grade > intellij > buildPlugin
- 输出文件再 build/distributions 中

### 如何升级插件版本号？

- "gradle.properties" 的 "pluginVersion" 配置了版本号
- "CHANGELOG.md" 根据以往历史填写变更记录，先英文后中文（idea插件官方要求英文必须在前）

## Doc

- [idea plugin 官方文档](https://plugins.jetbrains.com/docs/intellij/welcome.html)
- [Icon 生成器](https://bjansen.github.io/intellij-icon-generator/)
- [issues 搜索](https://youtrack.jetbrains.com/issues)

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
