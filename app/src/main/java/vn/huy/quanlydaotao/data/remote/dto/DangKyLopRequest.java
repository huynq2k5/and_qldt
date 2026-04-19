package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class DangKyLopRequest {
    @SerializedName("id_nguoi_dung")
    private int idNguoiDung;

    @SerializedName("id_lop_hoc")
    private int idLopHoc;

    public DangKyLopRequest(int idNguoiDung, int idLopHoc) {
        this.idNguoiDung = idNguoiDung;
        this.idLopHoc = idLopHoc;
    }
}