import json
import time

import httpx
import jwt

Key_ID = ""
Team_ID = ""
APNS_TOPIC = ""
APNS_URL_DEV = ""

Device_Token = "put your device Token here"


def Init():
    # 从config.json打开文件
    with open("config.json", "r") as file:
        config = json.load(file)
        global Key_ID, Team_ID, APNS_TOPIC, APNS_URL_DEV
        Key_ID = config["Key_ID"]
        Team_ID = config["Team_ID"]
        APNS_TOPIC = config["APNS_TOPIC"]
        APNS_URL_DEV = config["APNS_URL_DEV"]


def generate_token():
    key = ""
    with open("AuthKey.p8", "r") as file:
        key = file.read()
    header = {"alg": "ES256", "kid": Key_ID}
    payload = {"iss": Team_ID, "iat": int(time.time())}
    bash64_signature = jwt.encode(
        key=key,
        headers=header,
        algorithm="ES256",
        payload=payload,
    )
    return bash64_signature


def send_notification(token):
    headers = {
        "apns-topic": APNS_TOPIC,
        "apns-priority": "5",
        "apns-push-type": "alert",
        "authorization": "bearer " + token,
        "host": APNS_URL_DEV,
        "apns-expiration": "0",
    }
    payload = {
        "aps": {
            "alert": {
                "title": "Title",
                "subtitle": "Subtitle",
                "body": " Body",
                "action": "PLAY",
            },
            "badge": 5,
        }
    }
    url = APNS_URL_DEV + "/3/device/" + Device_Token

    with httpx.Client(http2=True) as client:
        resp = client.post(url, headers=headers, json=payload)
        print(resp.text)


if __name__ == "__main__":
    Init()
    token = generate_token()
    send_notification(token)

# """
# curl -v \
#   --header "authorization: bearer eyJhbzxcxssFUzI1NiIsImtpZCI6Ikw2S1JQQ0I5QjIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiIzVE45UDJZQjgyIiwiaWF0IjoxNzEyMjMwNDEyfQ.SFr2hQOtI9zoXpcdL2SoZW5yZzpvaxVai8-8FdxrylNqDmUofryEZI_ogdeiMoY4Si3-3vMeL0nFqX8O6gafVw" \
#   --header "apns-topic: com.ths-express.stdexpress" \
#   --header "apns-push-type: alert" \
#   --header "apns-priority: 5" \
#   --header "apns-expiration: 0" \
#   --data '{"aps":{"alert":{"title":"title","subtitle":"subtitle","body":"body"}}}' \
#   --http2  https://api.development.push.apple.com:443/3/device/ced1949617ee5c2f826eq51072bf5726b1ed8bdfb09e851631427eb0487f59d5
# """
