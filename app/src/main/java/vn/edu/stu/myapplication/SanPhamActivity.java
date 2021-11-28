package vn.edu.stu.myapplication;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import vn.edu.stu.myapplication.Adapter.SanPhamAdapter;
import vn.edu.stu.myapplication.Database.Database;
import vn.edu.stu.myapplication.Model.SanPham;

public class SanPhamActivity extends AppCompatActivity {
    final String DATABASE_NAME = "data.db";
    SQLiteDatabase database;

    ListView lvSanPham;
    ArrayList<SanPham> list;
    SanPhamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        addControls();
        readData();
    }

    private void addControls() {
        lvSanPham = (ListView) findViewById(R.id.lvTraiCay);
        list = new ArrayList<>();
        adapter = new SanPhamAdapter(this, list);
        lvSanPham.setAdapter(adapter);
    }

    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT ID, Ten, MoTa, Anh, Loai, Gia FROM Phu", null);
        list.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int ID = cursor.getInt(0);
            String Ten = cursor.getString(1);
            String MoTa = cursor.getString(2);
            byte[] Anh = cursor.getBlob(3);
            String Loai = cursor.getString(4);
            Integer Gia = cursor.getInt(5);

            list.add(new SanPham(ID, Ten, MoTa, Anh, Loai, Gia));
        }
        adapter.notifyDataSetChanged();

        cursor.close();
        database.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_sanpham, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent manghinhAdd = new Intent(
                        SanPhamActivity.this,
                        AddSanphamActivity.class);
                startActivity(manghinhAdd);
                break;
            case R.id.type:
                Intent manghinhKieu = new Intent(
                        SanPhamActivity.this,
                        LoaiActivity.class);
                startActivity(manghinhKieu);
                break;
            case R.id.about:
                Intent manghinhAbout = new Intent(
                        SanPhamActivity.this,
                        AboutActivity.class);
                startActivity(manghinhAbout);
                break;
            case R.id.exit:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}