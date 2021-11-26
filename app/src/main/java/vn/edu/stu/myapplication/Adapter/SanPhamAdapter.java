package vn.edu.stu.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.stu.myapplication.ChiTietSanphamActivity;
import vn.edu.stu.myapplication.Database.Database;
import vn.edu.stu.myapplication.Model.SanPham;
import vn.edu.stu.myapplication.R;
import vn.edu.stu.myapplication.UpdateSanPhamActivity;

public class SanPhamAdapter extends BaseAdapter {

    Activity context;
    ArrayList<SanPham> list;

    public SanPhamAdapter(Activity context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.dong_sanpham, null);

        ImageView imgAVT = (ImageView) row.findViewById(R.id.imageviewHinh);

        TextView txtTen = (TextView) row.findViewById(R.id.textviewTen);
        TextView txtLoai = (TextView) row.findViewById(R.id.textviewLoai);

        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);

        SanPham sanPham = list.get(position);

        txtTen.setText(sanPham.Ten);
        txtLoai.setText(sanPham.Loai);

        Bitmap bmAVT = BitmapFactory.decodeByteArray(sanPham.Anh, 0, sanPham.Anh.length);
        imgAVT.setImageBitmap(bmAVT);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle(R.string.del1);
                builder.setMessage(R.string.del2);
                builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(sanPham.ID);
                    }
                });
                builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        imgAVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateSanPhamActivity.class);
                intent.putExtra("ID", sanPham.ID);
                context.startActivity(intent);
            }
        });

        imgAVT.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, ChiTietSanphamActivity.class);
                intent.putExtra("ID", sanPham.ID);
                context.startActivity(intent);
                return false;
            }
        });

        return row;
    }

    private void delete(int id) {
        SQLiteDatabase database = Database.initDatabase(context, "data.db");
        database.delete("Phu", "ID = ?", new String[]{id + ""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT ID, Ten, MoTa, Anh, Loai, Gia FROM Phu", null);
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String Ten = cursor.getString(1);
            String MoTa = cursor.getString(2);
            byte[] Anh = cursor.getBlob(3);
            String Loai = cursor.getString(4);
            Integer Gia = cursor.getInt(5);

            list.add(new SanPham(ID, Ten, MoTa, Anh, Loai, Gia));
        }
        notifyDataSetChanged();
    }


}
