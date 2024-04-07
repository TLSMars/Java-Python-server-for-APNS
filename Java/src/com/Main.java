package com.qzh;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import org.json.JSONException;

public class Main {

  public static void main(String[] args)
    throws KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, InvalidKeySpecException, IOException, JSONException {
    
    String deviceToken =
        "ced1949617ee5c2f827de51072bf3626b1ed8bdfb09e851631427eb0487f59d5";
    String title = "Title";
    String subTitle = "";
    String body = "Hello, this is a test message ";
    Integer badge = 1;
    sendNotificationToAPNS sendNotificationToAPNS = new sendNotificationToAPNS().setDev();
    sendNotificationToAPNS.sendNotification(
        deviceToken,
        title,
        subTitle,
        body,
    );
}
}
