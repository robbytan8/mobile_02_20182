package com.robby.mobile_02_20182;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Robby
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ll_root)
    LinearLayout root;
    @BindView(R.id.et_first_name)
    EditText txtFirstName;
    @BindView(R.id.et_last_name)
    EditText txtLastName;
    @BindView(R.id.et_another_extra)
    EditText txtExtra;
    @BindView(R.id.iv_thumbnail)
    ImageView ivThumbnail;
    public static final String KEY_FIRST = "fName";
    public static final String KEY_LAST = "lName";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_call_second)
    public void actionCallSecond() {
        if (txtFirstName.getText().toString().isEmpty() || txtLastName.getText().toString().isEmpty()
        ) {
            Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(root, R.string.error_empty, Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_FIRST, txtFirstName.getText().toString());
            bundle.putString(KEY_LAST, txtLastName.getText().toString());

            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.putExtra(Intent.EXTRA_TEXT, txtExtra.getText().toString());
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_phone)
    public void actionPhone() {
        Uri uri = Uri.parse("tel:+62222012186");
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_web)
    public void actionWeb() {
        Uri uri = Uri.parse("http://it.maranatha.edu");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_map)
    public void actionMap() {
        // Uri location = Uri.parse("geo:0,0?q=Universitas+Kristen+Maranatha,+Bandung");
        // Or map point based on latitude/longitude
        Uri location = Uri.parse("geo:-6.924884,107.636569?q=-6.924884,107.636569&z=15"); // z param is zoom level
        Intent intent = new Intent(Intent.ACTION_VIEW, location);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_camera)
    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivThumbnail.setImageBitmap(imageBitmap);
        }
    }
}
