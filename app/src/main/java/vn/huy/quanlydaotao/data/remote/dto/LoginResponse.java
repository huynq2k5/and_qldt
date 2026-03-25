package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    private boolean thanhCong;

    @SerializedName("message")
    private String thongBao;

    @SerializedName("data")
    private DuLieuNguoiDung duLieu;

    public boolean laThanhCong() { return thanhCong; }
    public String layThongBao() { return thongBao; }
    public DuLieuNguoiDung layDuLieu() { return duLieu; }

    public static class DuLieuNguoiDung {
        @SerializedName("token")
        private String token;

        public String layToken() { return token; }
    }
}