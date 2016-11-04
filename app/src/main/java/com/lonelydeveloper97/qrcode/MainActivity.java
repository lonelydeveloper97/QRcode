package com.lonelydeveloper97.qrcode;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String encryptedSpecificKey = "12345qwerty"; // This keystring should be app id encrypted by server and uses for app authentication.
        // We can change it automatically every time user connects to server, by changing encryption key on server;
        StringForQRcodeGenerator.init(this);
        StringForQRcodeGenerator.setCurrentEncryptedUserId(encryptedSpecificKey);
        try {
            showQRcodeByContent(StringForQRcodeGenerator.getCurrentQRcodeContent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initButton();

    }

    void initButton(){
        Button button = (Button) findViewById(R.id.update_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showQRcodeByContent(StringForQRcodeGenerator.getNextQRcodeContent());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    void showQRcodeByContent(String content){
        Bitmap barcode_bitmap = null;
        try {
            barcode_bitmap = QRcodeGenerator.encodeAsBitmap(content, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ImageView targetImageView = (ImageView) findViewById(R.id.qrcode_imageView);
        targetImageView.setImageBitmap(barcode_bitmap);

    }



}
