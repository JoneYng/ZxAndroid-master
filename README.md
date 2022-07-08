# ZxAndroid 快速开发框架
🔥Android MVVM 架构基于JetPack框架快速搭建高质量、高效率的APP应用,作为应用的基础组件化脚手架.
开箱即用:
- 友盟基础库 包含（推送、分享、三方登录）
- 图片选择库
- 微信、支付宝支付库
---
__reinforce.gradle 打包插件__
- ZxAndroidDebugUpload 自动打debug包，并上传蒲公英，可配置发送钉钉消息
- ZxAndroidRelease  自动打release包
- ZxAndroidReinforceRelease  使用乐固加固，并重新签名，walle打渠道包用于应用市场分发
>相关配置文件 gradle.properties
``` 
# 蒲公英key
PGY_API_KEY=089d32e81fcee0b92cc3b404e8c0dca4
# 乐固AppId
LEGU_APP_ID = ""
# 乐固AppKey
LEGU_APP_KEY = ""
# 钉钉Token
DINGDING_TOKEN = ""
``` 
---

![ZxAndroid](/image/ZxAndroid.jpg)