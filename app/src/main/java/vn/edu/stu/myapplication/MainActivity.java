package vn.edu.stu.myapplication;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText txtUserName, txtPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anh xa

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //onclick
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "admin";
                String password = "123";
                if (txtUserName.getText().toString().equals(username) && txtPass.getText().toString().equals(password)) {
                    Toast.makeText(getApplicationContext(), R.string.loginsuccess, Toast.LENGTH_LONG).show();
                    //chuyen mang hinh
                    Intent mhTraiCay = new Intent(MainActivity.this, SanPhamActivity.class);
                    startActivity(mhTraiCay);
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.loginerror, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}