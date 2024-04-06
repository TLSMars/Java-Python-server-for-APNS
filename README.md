<h1 align="center">Java & Python server for APNS</h1>

[English](./README.md) [简体中文](./README_CN.md)

## what is this?

This is a simple Java or Python server for sending push notifications to iOS devices using apple remote notification server.

## Before you start

prepare the following:

-   A valid APNS **certificate**
-   A valid APNS **key**
-   A valid APNS **topic** _(not necessary)_
-   A valid APNS **device token**
-   A valid Apple [**Developer Team ID**](https://developer.apple.com/account)

more information about the APNS can be found [here](https://developer.apple.com/documentation/usernotifications/setting_up_a_remote_notification_server/establishing_a_token-based_connection_to_apns)

## Config

1. Clone the project
   `git clone https://github.com/TLSMars/Java-Python-server-for-APNS.git`

#### Python

-   Install the required packages
    ```shell
    cd Python
    pip install -r  requirements.txt
    ```
-   change the `AuthKey.p8` file to your key file
-   open `sendNotificationToAPNS.py` and change the following variables:
    -   `key_id`
    -   `team_id`
    -   `APNS_TOPIC`
    -   `device_token`

#### Java

-   open `Java/src/com/apple.properties` file and change the following variables:
    -   `key_id`
    -   `team_id`
    -   `APNS_TOPIC`
-   change the `Java/src/com/AuthKey.p8` file to your key file
-   open `Java/src/com/Main.java` and change the following variables:
    -   `deviceToken`

## Run

> **! Important**: Requests to APNS must be made using a secure connection (HTTPS over HTTP/2).
> Therefore, if any issues arise, they may be attributed to the usage of HTTP/2.

#### Python

```shell
cd Python
python sendNotificationToAPNS.py
```

#### Java

> I am sorry, I am not familiar with Java, but you can run it using any Java IDE.
> **I can run it using vscode**
> If you know how to run it using the terminal, please let me know.

## License

[MIT](https://choosealicense.com/licenses/mit/)

## Please Star

if you find it useful, please give me a [Star](https://github.com/TLSMars/Java-Python-server-for-APNS.git) ⭐️

## Others

[Push Notification Console](https://icloud.developer.apple.com/dashboard)
