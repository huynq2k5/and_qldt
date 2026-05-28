package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class XoaDangKyRequest {
    @SerializedName("id_nguoi_dung")
    private int idNguoiDung;

    @SerializedName("id_lop_hoc")
    private int idLopHoc;

    public XoaDangKyRequest(int idNguoiDung, int idLopHoc) {
        this.idNguoiDung = idNguoiDung;
        this.idLopHoc = idLopHoc;
    }
}