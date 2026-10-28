package com.example.quanlydanhba;

import java.io.Serializable;

/** Biểu diễn một liên hệ. Implements Serializable để truyền qua Intent. */
public class Contact implements Serializable {
    private final String name;
    private final String phone;
    private final String group;
    private final String gender;
    private final String note;
    private boolean favorite;

    public Contact(String name, String phone, String group,
                   String gender, String note, boolean favorite) {
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.note = note;
        this.favorite = favorite;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getGroup() { return group; }
    public String getGender() { return gender; }
    public String getNote() { return note; }
    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    /** Chữ cái đầu của "tên" (từ cuối trong họ tên tiếng Việt), viết hoa. */
    public String getInitial() {
        String n = name == null ? "" : name.trim();
        if (n.isEmpty()) return "?";
        String last = n.substring(n.lastIndexOf(' ') + 1);
        return last.substring(0, 1).toUpperCase();
    }
}
