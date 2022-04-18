package com.example.mycamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnCamera;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = (Button) findViewById(R.id.btn_camera);
        imgView = (ImageView) findViewById(R.id.imgView);
        btnCamera.setOnClickListener(this);
    }

    Uri photoUri;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 촬영 클릭
            case R.id.btn_camera:
                // 카메라 기능을 intent
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // 사진파일 변수 선언 및 경로세팅
                File photoFile = null;
                try { photoFile = createImageFile(); }
                   catch (IOException ex) { }

                // 사진 저장 이미지뷰에 출력
                if(photoFile != null) {
                    photoUri = FileProvider.getUriForFile(this, getPackageName() +
                            ".fileprovider", photoFile);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                    startActivityForResult(i, 0);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 카메라 촬영시 이미지뷰에 사진 삽입
        if(requestCode == 0 && resultCode == RESULT_OK) {
            // 이미지뷰에 파일경로의 사진을 가져와 출력
            imgView.setImageURI(photoUri);
        }
    }

    // 이미지파일의 경로를 가져올 메소드
    private File createImageFile() throws IOException {
        // 파일이름, 저장경로
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_"; File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File StorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return  image;
    }
}