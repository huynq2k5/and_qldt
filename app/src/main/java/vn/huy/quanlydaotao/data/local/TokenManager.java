package vn.huy.quanlydaotao.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String TEN_UU_TIEN = "AppPrefs";
    private static final String KHOA_TOKEN = "api_token";
    private static final String KHOA_THOI_GIAN_DANG_NHAP = "login_time";
    
    private static final long THOI_GIAN_HET_HAN = 3 * 24 * 60 * 60 * 1000L;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(TEN_UU_TIEN, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void luuToken(String token) {
        editor.putString(KHOA_TOKEN, token);
        editor.putLong(KHOA_THOI_GIAN_DANG_NHAP, System.currentTimeMillis());
        editor.apply();
    }

    public String layToken() {
        return sharedPreferences.getString(KHOA_TOKEN, null);
    }

    public boolean tokenHopLe() {
        String token = layToken();
        long thoiGianDangNhap = sharedPreferences.getLong(KHOA_THOI_GIAN_DANG_NHAP, 0);
        
        if (token == null || thoiGianDangNhap == 0) {
            return false;
        }

        long thoiGianHienTai = System.currentTimeMillis();
        return (thoiGianHienTai - thoiGianDangNhap) < THOI_GIAN_HET_HAN;
    }

    public void xoaToken() {
        editor.remove(KHOA_TOKEN);
        editor.remove(KHOA_THOI_GIAN_DANG_NHAP);
        editor.apply();
    }
}