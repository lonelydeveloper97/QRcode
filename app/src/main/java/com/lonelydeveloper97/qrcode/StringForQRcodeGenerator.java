package com.lonelydeveloper97.qrcode;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Valeriy on 25.10.2016.
 */
public class StringForQRcodeGenerator {

    private final static String CURRENT_ENCRYPTED_USER_ID = "currentEncryptedUserId";
    private final static String CURRENT_GENERATED_QRCODE_ID = "currentGeneratedQRCodeId";

    private static SharedPreferences sharedPreferences;
    private static String currentEncryptedUserId;
    private static int currentGeneratedQRCodeId;

    public static void init(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        currentEncryptedUserId = getCurrentEncryptedUserIdSavedInPrefs();
        currentGeneratedQRCodeId = Integer.parseInt(getGeneratedQRCodeIdSavedInPrefs());
    }

    public static String getCurrentQRcodeContent() throws JSONException {
        return contentJsonString(String.valueOf(currentGeneratedQRCodeId));
    }

    public static String getNextQRcodeContent() throws JSONException {
        return contentJsonString(getNextGeneratedQRCodeId());
    }

    public static String contentJsonString(String generatedQRCodeId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CURRENT_ENCRYPTED_USER_ID, currentEncryptedUserId);
        jsonObject.put(CURRENT_GENERATED_QRCODE_ID, generatedQRCodeId);
        return jsonObject.toString();
    }

    private static String getCurrentEncryptedUserId() {
        return currentEncryptedUserId;
    }


    private static String getCurrentEncryptedUserIdSavedInPrefs() {
        return sharedPreferences.getString(CURRENT_ENCRYPTED_USER_ID, "Unknown");
    }

    private static String getGeneratedQRCodeIdSavedInPrefs() {
        return sharedPreferences.getString(CURRENT_GENERATED_QRCODE_ID, "0");
    }

    private static void updateInPrefs(String name, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static void setCurrentEncryptedUserId(String currentEncryptedUserId) {
        StringForQRcodeGenerator.currentEncryptedUserId = currentEncryptedUserId;
        updateInPrefs(CURRENT_ENCRYPTED_USER_ID, currentEncryptedUserId);
    }

    private static String getNextGeneratedQRCodeId() {
        updateInPrefs(CURRENT_GENERATED_QRCODE_ID, String.valueOf(++currentGeneratedQRCodeId));
        return String.valueOf(currentGeneratedQRCodeId);
    }


}
