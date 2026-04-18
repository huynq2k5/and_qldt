package vn.huy.quanlydaotao.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String TEN_UU_TIEN = "AppPrefs";
    private static final String KHOA_TOKEN = "api_token";
    private static final String KHOA_HO_TEN = "user_name";
    private static final String KHOA_EMAIL = "email";
    private static final String KHOA_VAI_TRO = "user_role";
    private static final String KHOA_THOI_GIAN_DANG_NHAP = "login_time";
    private static final long THOI_GIAN_HET_HAN = 10 * 24 * 60 * 60 * 1000L;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(TEN_UU_TIEN, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void luuThongTinDangNhap(String token, String hoTen, String vaiTro, String email) {
        editor.putString(KHOA_TOKEN, token);
        editor.putString(KHOA_HO_TEN, hoTen);
        editor.putString(KHOA_VAI_TRO, vaiTro);
        editor.putLong(KHOA_THOI_GIAN_DANG_NHAP, System.currentTimeMillis());
        editor.putString(KHOA_EMAIL, email);
        editor.apply();
    }

    public String layToken() {
        return sharedPreferences.getString(KHOA_TOKEN, null);
    }

    public String layHoTen() {
        return sharedPreferences.getString(KHOA_HO_TEN, "Người dùng");
    }
    public String layEmail() {
        return sharedPreferences.getString(KHOA_EMAIL, "mail");
    }

    public boolean tokenHopLe() {
        String token = layToken();
        long thoiGian = sharedPreferences.getLong(KHOA_THOI_GIAN_DANG_NHAP, 0);
        return token != null && (System.currentTimeMillis() - thoiGian < THOI_GIAN_HET_HAN);
    }

    public String layVaiTro() {
        return sharedPreferences.getString("user_role", "Thành viên");
    }

    public void xoaToken() {
        editor.clear();
        editor.apply();
    }
}