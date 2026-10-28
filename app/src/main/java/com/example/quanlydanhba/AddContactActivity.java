package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT = "contact";
    public static final String EXTRA_PHONES = "phones";

    private EditText etName, etPhone, etNote;
    private Spinner spGroup;
    private RadioGroup rgGender;
    private CheckBox cbFavorite;
    private ArrayList<String> existingPhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etNote = findViewById(R.id.etNote);
        spGroup = findViewById(R.id.spGroup);
        rgGender = findViewById(R.id.rgGender);
        cbFavorite = findViewById(R.id.cbFavorite);
        Button btnSave = findViewById(R.id.btnSave);

        existingPhones = getIntent().getStringArrayListExtra(EXTRA_PHONES);
        if (existingPhones == null) existingPhones = new ArrayList<>();

        ArrayAdapter<CharSequence> groupAdapter = ArrayAdapter.createFromResource(
                this, R.array.groups, android.R.layout.simple_spinner_item);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGroup.setAdapter(groupAdapter);

        btnSave.setOnClickListener(v -> save());
    }

    private void save() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Họ tên không được để trống");
            etName.requestFocus();
            return;
        }
        if (!phone.matches("0\\d{9}")) {
            etPhone.setError("Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0");
            etPhone.requestFocus();
            return;
        }
        if (existingPhones.contains(phone)) {
            etPhone.setError("Số điện thoại đã tồn tại trong danh bạ");
            etPhone.requestFocus();
            return;
        }

        String group = spGroup.getSelectedItem().toString();
        RadioButton rbGender = findViewById(rgGender.getCheckedRadioButtonId());
        String gender = rbGender.getText().toString();
        boolean favorite = cbFavorite.isChecked();

        Contact contact = new Contact(name, phone, group, gender, note, favorite);

        Intent result = new Intent();
        result.putExtra(EXTRA_CONTACT, contact);
        setResult(RESULT_OK, result);
        Toast.makeText(this, "Đã lưu liên hệ", Toast.LENGTH_SHORT).show();
        finish();
    }
}
