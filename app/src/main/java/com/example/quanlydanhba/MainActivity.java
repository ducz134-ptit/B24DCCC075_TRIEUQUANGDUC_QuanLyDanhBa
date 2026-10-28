package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Contact> contacts = new ArrayList<>();
    private ContactAdapter adapter;
    private TextView tvStats;

    private ActivityResultLauncher<Intent> addLauncher;
    private ActivityResultLauncher<Intent> detailLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStats = findViewById(R.id.tvStats);
        ListView lvContacts = findViewById(R.id.lvContacts);
        Button btnAdd = findViewById(R.id.btnAdd);

        // Dữ liệu mẫu
        contacts.add(new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", "", false));
        contacts.add(new Contact("Trần Thị Bình", "0912345678", "Gia đình", "Nữ", "", true));
        contacts.add(new Contact("Lê Minh Châu", "0987654321", "Công việc", "Nữ", "", false));

        adapter = new ContactAdapter(this, contacts);
        lvContacts.setAdapter(adapter);
        updateStats();

        // Nhận kết quả từ màn hình Thêm liên hệ
        addLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    Intent data = result.getData();
                    if (result.getResultCode() == RESULT_OK && data != null) {
                        Contact c = (Contact) data.getSerializableExtra(AddContactActivity.EXTRA_CONTACT);
                        if (c != null) {
                            contacts.add(c);
                            refresh();
                        }
                    }
                });

        // Nhận kết quả từ màn hình Chi tiết (đổi yêu thích / xóa)
        detailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    Intent data = result.getData();
                    if (result.getResultCode() != RESULT_OK || data == null) return;

                    int pos = data.getIntExtra(ContactDetailActivity.EXTRA_POSITION, -1);
                    if (pos < 0 || pos >= contacts.size()) return;

                    if (data.getBooleanExtra(ContactDetailActivity.EXTRA_DELETED, false)) {
                        contacts.remove(pos);
                    } else {
                        boolean fav = data.getBooleanExtra(
                                ContactDetailActivity.EXTRA_FAVORITE, contacts.get(pos).isFavorite());
                        contacts.get(pos).setFavorite(fav);
                    }
                    refresh();
                });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            // Gửi danh sách số điện thoại hiện có để kiểm tra trùng
            ArrayList<String> phones = new ArrayList<>();
            for (Contact c : contacts) phones.add(c.getPhone());
            intent.putStringArrayListExtra(AddContactActivity.EXTRA_PHONES, phones);
            addLauncher.launch(intent);
        });

        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ContactDetailActivity.class);
            intent.putExtra(ContactDetailActivity.EXTRA_CONTACT, contacts.get(position));
            intent.putExtra(ContactDetailActivity.EXTRA_POSITION, position);
            detailLauncher.launch(intent);
        });
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        updateStats();
    }

    private void updateStats() {
        int favorites = 0;
        for (Contact c : contacts) if (c.isFavorite()) favorites++;
        tvStats.setText("Tổng số liên hệ: " + contacts.size()
                + "   |   Yêu thích: " + favorites);
    }
}
