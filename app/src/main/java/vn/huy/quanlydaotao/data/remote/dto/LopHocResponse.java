package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LopHocResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<LopHocDTO> data;

    public boolean isSuccess() { return success; }
    public List<LopHocDTO> getData() { return data; }

    public static class LopHocDTO {
        @SerializedName("id")
        private int id;
        @SerializedName("id_khoa_hoc")
        private int idKhoaHoc;
        @SerializedName("ten_lop")
        private String tenLop;
        @SerializedName("ngay_bat_dau")
        private String ngayBatDau;
        @SerializedName("ngay_ket_thuc")
        private String ngayKetThuc;
        @SerializedName("da_dang_ky")
        private int daDangKy;

        public int getId() { return id; }
        public int getIdKhoaHoc() { return idKhoaHoc; }
        public String getTenLop() { return tenLop; }
        public String getNgayBatDau() { return ngayBatDau; }
        public String getNgayKetThuc() { return ngayKetThuc; }
        public int getDaDangKy() { return daDangKy; }
    }
}