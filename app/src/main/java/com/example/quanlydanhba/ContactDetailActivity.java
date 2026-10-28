package com.example.quanlydanhba;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT = "contact";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_FAVORITE = "favorite";
    public static final String EXTRA_DELETED = "deleted";

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Contact contact = (Contact) getIntent().getSerializableExtra(EXTRA_CONTACT);
        position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        if (contact == null) {
            finish();
            return;
        }

        TextView tvAvatar = findViewById(R.id.tvAvatar);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvPhone = findViewById(R.id.tvPhone);
        TextView tvGroupGender = findViewById(R.id.tvGroupGender);
        TextView tvNote = findViewById(R.id.tvNote);
        CheckBox cbFavorite = findViewById(R.id.cbFavorite);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnDelete = findViewById(R.id.btnDelete);

        tvAvatar.setText(contact.getInitial());
        tvName.setText(contact.getName());
        tvPhone.setText("Số điện thoại: " + contact.getPhone());
        tvGroupGender.setText("Nhóm: " + contact.getGroup()
                + "   |   Giới tính: " + contact.getGender());
        String note = contact.getNote();
        tvNote.setText("Ghi chú: " + (note == null || note.isEmpty() ? "(không có)" : note));
        cbFavorite.setChecked(contact.isFavorite());

        // Đổi yêu thích: trả kết quả về ngay để danh sách + thống kê cập nhật khi quay lại
        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent result = new Intent();
            result.putExtra(EXTRA_POSITION, position);
            result.putExtra(EXTRA_FAVORITE, isChecked);
            setResult(RESULT_OK, result);
        });

        // Intent ngầm định mở màn hình quay số
        btnCall.setOnClickListener(v -> {
            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getPhone()));
            try {
                startActivity(dial);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Không tìm thấy ứng dụng quay số", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra(EXTRA_POSITION, position);
            result.putExtra(EXTRA_DELETED, true);
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
