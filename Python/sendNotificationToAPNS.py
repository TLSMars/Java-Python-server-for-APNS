import time

import httpx
import jwt

APNS_URL_DEV = "https://api.sandbox.push.apple.com:443"
# APNS_URL_DEV = "https://api.development.push.apple.com:443"
APNS_URL = "https://api.push.apple.com:443"
Device_Token = "put your device token here"  # 64 characters, such as ced1869659ee5c2f827de51072bf9876b1ed8bdfb09e851632255eb0487f59d5
Key_ID = "put your key id here"  # 10 characters, such as I1RXHQP1Q2
Team_ID = "put your team id here"  # 10 characters, such as 3AQ915HX79
APNS_TOPIC = "put your package name here"  # such as com.name.applicationName


def generate_token():
    key = ""
    with open("AuthKey_L6KRPCB9B2.p8", "r") as file:
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


token = generate_token()
print(token)


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


send_notification(token)

# """
# curl -v \
#   --header "authorization: bearer eyJhbGciOiJFUzI1NiIsImtpZCI6Ikw2S1JQQ0I5QjIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiIzVE45UDJZQjgyIiwiaWF0IjoxNzEyMjMwNDEyfQ.SFr2hQOtI9zoXpcdL2SoZW5yZzpvaxVai8-8FdxrylNqDmUofryEZI_ogdeiMoY4Si3-3vMeL0nFqX8O6gafVw" \
#   --header "apns-topic: com.ths-express.stdexpress" \
#   --header "apns-push-type: alert" \
#   --header "apns-priority: 5" \
#   --header "apns-expiration: 0" \
#   --data '{"aps":{"alert":{"title":"title","subtitle":"subtitle","body":"body"}}}' \
#   --http2  https://api.development.push.apple.com:443/3/device/ced1949617ee5c2f827de51072bf3626b1ed8bdfb09e851631427eb0487f59d5
# """
