<h1 align="center">Java & Python server for APNS</h1>

[English](./README.md) [简体中文](./README_CN.md)

## 这是什么？

这是一个简单的 Java 或 Python 服务器，用于通过苹果远程通知服务器向 iOS 设备发送推送通知。

## 开始之前

准备以下内容：

-
-   有效的 APNS **证书**
-   有效的 APNS **密钥**
-   有效的 APNS **主题** _(可选)_
-   有效的 APNS **设备令牌**
-   有效的 Apple [**开发者团队 ID**](https://developer.apple.com/account)

有关 APNS 的更多信息，请参阅[此处](https://developer.apple.com/documentation/usernotifications/setting_up_a_remote_notification_server/establishing_a_token-based_connection_to_apns)。

## 配置

1. 克隆项目
   `git clone https://github.com/TLSMars/Java-Python-server-for-APNS.git`

#### Python

-   安装所需的包
    ```shell
    cd Python
    pip install -r requirements.txt
    ```
-   将 `AuthKey.p8` 文件更改为您的密钥文件
-   打开 `sendNotificationToAPNS.py` 文件并更改以下变量：
    -   `key_id`
    -   `team_id`
    -   `APNS_TOPIC`
    -   `device_token`

#### Java

-   打开 `Java/src/com/apple.properties` 文件并更改以下变量：
    -   `key_id`
    -   `team_id`
    -   `APNS_TOPIC`
-   将 `Java/src/com/AuthKey.p8` 文件更改为您的密钥文件
-   打开 `Java/src/com/Main.java` 文件并更改以下变量：
    -   `deviceToken`

## 运行

> **! 重要提示**：必须使用安全连接（基于 HTTP/2 的 HTTPS）向 APNS 发出请求。
> 因此，如果出现任何问题，则可能归因于 HTTP/2 的使用。

#### Python

```shell
cd Python
python sendNotificationToAPNS.py
```

#### Java

> 抱歉，我对 Java 不熟悉，但是你可以使用任何 Java IDE 运行它。
> **但我可以使用 vscode 运行它**
> 如果您知道如何使用终端运行它，请告诉我。.

## License

[MIT](https://choosealicense.com/licenses/mit/)

## Please Star

如果觉得还不错的话，就给个 [Star](https://github.com/TLSMars/Java-Python-server-for-APNS.git) ⭐️ 鼓励一下我吧~
