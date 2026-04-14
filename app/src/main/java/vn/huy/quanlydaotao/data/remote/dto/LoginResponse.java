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

        @SerializedName("ho_ten")
        private String hoTen;

        @SerializedName("email")
        private String email;

        @SerializedName("ten_vai_tro")
        private String tenVaiTro;

        public String layToken() { return token; }
        public String layHoTen() { return hoTen; }
        public String layEmail() { return email; }
        public String layTenVaiTro() { return tenVaiTro; }
    }
}