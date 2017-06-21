package rado.robienie_fotki;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Uri cameraPictureUri;
    ImageView imageView;
    Button buttonRobZdjecie;
    Button buttonSlijFote;
    Bitmap photo;
    TextView textView;

    private static final int CAPTURE_FROM_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        buttonRobZdjecie = (Button) findViewById(R.id.button);
        buttonSlijFote = (Button) this.findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);

        buttonSlijFote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendMessage(textView, photo).execute(buttonSlijFote.getText().toString());
                buttonSlijFote.setText("Wysłano !");
            }
        });


        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                2);

        buttonRobZdjecie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cameraPictureUri = createImageUri();
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUri);
                startActivityForResult(i,CAPTURE_FROM_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_FROM_CAMERA:
                if (resultCode==RESULT_OK) {
                    InputStream inputStream;
                    BufferedInputStream bufferedInputStream;
                    try {
                        inputStream = getContentResolver().openInputStream(cameraPictureUri);
                        bufferedInputStream = new BufferedInputStream(inputStream);
                        photo = BitmapFactory.decodeStream(bufferedInputStream);
                        imageView.setImageBitmap(photo);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                     // now use this bitmap wherever you want
                }
                break;
        }
    }
    private Uri createImageUri() {
                ContentResolver contentResolver = getContentResolver();
                ContentValues cv = new ContentValues();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

                cv.put(MediaStore.Images.Media.TITLE, timeStamp);
                return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
