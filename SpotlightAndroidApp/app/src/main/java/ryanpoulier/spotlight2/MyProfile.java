package ryanpoulier.spotlight2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MyProfile extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    EditText FULLNAME,EMAIL, NUMBER;
    Button SAVE;
    private ImageView Image_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
        FULLNAME = (EditText) findViewById(R.id.txt_Full_Name);
        FULLNAME.setEnabled(false);
        EMAIL = (EditText) findViewById(R.id.txt_Email);
        EMAIL.setEnabled(false);
        NUMBER = (EditText) findViewById(R.id.txt_Mobile);
        NUMBER .setEnabled(false);
        SAVE= (Button) findViewById(R.id.btn_profile_save);
        SAVE.setVisibility(View.INVISIBLE);

        Image_1 = (ImageView) findViewById(R.id.imgview_profile);
    }


    //code from https://www.youtube.com/watch?v=wBuWqqBWziU&list=FLsCn-tnRZVHIyKOq7o6b36Q&index=1
    public void  OpenProfileImageGallery (View v) {
        //using an implicit intent
        Intent photoPickerIntent= new Intent (Intent.ACTION_PICK);

        File pictureDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath= pictureDirectory.getPath();

        Uri data= Uri.parse (pictureDirectoryPath);

        photoPickerIntent.setType("image/*");

        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST); //refactored - constant extracted
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode== IMAGE_GALLERY_REQUEST) {
                Uri imageUri = data.getData();
                InputStream inputStream;

                //Trycatch
                try {
                    inputStream=getContentResolver().openInputStream(imageUri);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    Image_1.setImageBitmap(image);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open this image", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public void ProfileEdit (View view) {
        FULLNAME.setEnabled(true);
        EMAIL.setEnabled(true);
        NUMBER.setEnabled(true);
        SAVE.setVisibility(View.VISIBLE);
    }

    public void ProfileSave(View view){
        FULLNAME.setEnabled(false);
        EMAIL.setEnabled(false);
        NUMBER.setEnabled(false);
        SAVE.setVisibility(View.INVISIBLE);
    }

}
