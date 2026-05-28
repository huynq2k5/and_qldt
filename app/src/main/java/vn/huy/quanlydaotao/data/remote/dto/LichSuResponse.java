package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LichSuResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<LichSuDTO> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<LichSuDTO> getData() {
        return data;
    }

    public static class LichSuDTO {
        @SerializedName("id")
        private int id;

        @SerializedName("id_nguoi_dung")
        private int idNguoiDung;

        @SerializedName("id_bai_kiem_tra")
        private int idBaiKiemTra;

        @SerializedName("diem_so")
        private float diemSo;

        @SerializedName("trang_thai")
        private String trangThai;

        @SerializedName("thoi_gian_nop")
        private String thoiGianNop;

        public int getId() {
            return id;
        }

        public int getIdNguoiDung() {
            return idNguoiDung;
        }

        public int getIdBaiKiemTra() {
            return idBaiKiemTra;
        }

        public float getDiemSo() {
            return diemSo;
        }

        public String getTrangThai() {
            return trangThai;
        }

        public String getThoiGianNop() {
            return thoiGianNop;
        }
    }
}