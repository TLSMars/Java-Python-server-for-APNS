package com.qzh;

import com.qzh.request_url;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts.SIG;

import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class sendNotificationToAPNS {
    private static String APNS_CERTIFICATE_PATH;
    private static String APNS_TOPIC;
    private static String Key_ID;
    private static String Team_ID;
    private static String token = "";
    private static String APNS_URL = "";
    private static String APNS_URL_PROD = "";
    private static String APNS_URL_DEV = "";

    public sendNotificationToAPNS()
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            UnrecoverableEntryException, IOException, InvalidKeySpecException {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(
                    new FileInputStream("/root/code/StdExpress/com/src/com/qzh/apple.properties"));
            prop.load(in);

            APNS_CERTIFICATE_PATH = prop.getProperty("APNS_CERTIFICATE_PATH");
            APNS_TOPIC = prop.getProperty("APNS_TOPIC");
            Key_ID = prop.getProperty("Key_ID");
            Team_ID = prop.getProperty("Team_ID");
            APNS_URL_PROD = prop.getProperty("APNS_URL_PROD");
            APNS_URL_DEV = prop.getProperty("APNS_URL_DEV");
            APNS_URL = APNS_URL_PROD;

            token = generateToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public sendNotificationToAPNS setDev() {
        APNS_URL = APNS_URL_DEV;
        return this;
    }

    public boolean sendNotification(String deviceToken, String title, String subtitle, String body, Integer badge) {
        // payload = {"aps": {"alert": {"title": str(int(time.time())), "subtitle":
        // "Subtitle", "body": " Body","action": "PLAY",},"badge": 50,}}
        try {

            JSONObject payload = payload(title, subtitle, body, badge);

            String url = APNS_URL + "/3/device/" + deviceToken;
            System.out.println(url);

            JSONObject headers = new JSONObject();
            headers.put("apns-topic", APNS_TOPIC);
            headers.put("authorization", "bearer " + token);
            headers.put("apns-push-type", "alert");
            headers.put("apns-priority", "5");

            String resp = request_url.sendPost(url, headers, payload.toString());
            System.out.println(resp);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private JSONObject payload(String title, String subtitle, String body, Integer badge) {
        try {

            JSONObject payload = new JSONObject();
            JSONObject aps = new JSONObject();
            JSONObject alert = new JSONObject();
            alert.put("title", title);

            if (subtitle != null) {
                alert.put("subtitle", subtitle);
            }

            alert.put("body", body);

            aps.put("alert", alert);
            if (badge != null) {
                aps.put("badge", badge);
            }
            payload.put("aps", aps);

            return payload;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateToken()
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
            UnrecoverableEntryException, InvalidKeySpecException {
        String signature = "";

        byte[] keyBytes = Files.readAllBytes(Paths.get(APNS_CERTIFICATE_PATH));
        String privateKeyContent = new String(keyBytes).replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance("EC");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        ECPrivateKey privateKey = (ECPrivateKey) kf.generatePrivate(keySpec);

        JwtBuilder jwtbuilder = Jwts.builder();
        signature = jwtbuilder
                .header()
                .add("kid", Key_ID)
                .add("typ", "JWT")
                .add("alg", "ES256")
                .and()
                .claims()
                .add("iss", Team_ID)
                .add("iat", System.currentTimeMillis() / 1000)
                .and()
                .signWith(privateKey, SIG.ES256)
                .compact();

        return signature;
    }

}
