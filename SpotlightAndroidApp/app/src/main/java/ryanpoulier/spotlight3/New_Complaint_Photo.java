package ryanpoulier.spotlight3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class New_Complaint_Photo extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView Image_1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__complaint__photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        Image_1 = (ImageView) findViewById(R.id.imgview_Image_1);
    }

    //code from https://www.youtube.com/watch?v=wBuWqqBWziU&list=FLsCn-tnRZVHIyKOq7o6b36Q&index=1
    public void  OpenImageGallery1 (View v) {
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
                    imageUri = data.getData();
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


    public void OpenNewComplaintVideoOption (View view){
        storePhotoURI();
        Intent intent=new Intent (this,New_Complaint_Video_Option.class);
        startActivity(intent);
    }

    public void storePhotoURI () {

        SharedPreferences prefs = getSharedPreferences("photo", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("photo", imageUri.toString());
        editor.apply();

        //Toast.makeText (this, "Photo URI Saved", Toast.LENGTH_LONG).show();

    }

}
