package vn.edu.stu.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView txtPhoneNum;
    ImageButton btnCall;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtPhoneNum = findViewById(R.id.txtPhoneNum);
        btnCall = findViewById(R.id.btnCall);
    }

    private void addEvents() {
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    makePhoneCall();
                } else {
                    requestPermissions();
                }
            }
        });
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                AboutActivity.this,
                Manifest.permission.CALL_PHONE
        )) {
            Toast.makeText(
                    AboutActivity.this,
                    "Vui lòng cấp quyền thủ công trong App Setting",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            ActivityCompat.requestPermissions(
                    AboutActivity.this,
                    new String[]{
                            Manifest.permission.CALL_PHONE
                    },
                    1303
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1303) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(
                        AboutActivity.this,
                        "Bạn đã từ chối cấp quyền gọi. Hủy thao tác.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(
                AboutActivity.this,
                Manifest.permission.CALL_PHONE
        );
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void makePhoneCall() {
        String phoneNum = txtPhoneNum.getText().toString();
        Intent callIntent = new Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:" + phoneNum)
        );
        startActivity(callIntent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng STU = new LatLng(10.737990732005878, 106.67781410326936);
        map.addMarker(new MarkerOptions().position(STU).title("Marker in Đại Học Công Nghệ Sài Gòn"));
        map.moveCamera(CameraUpdateFactory.newLatLng(STU));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(STU,18));
    }
}