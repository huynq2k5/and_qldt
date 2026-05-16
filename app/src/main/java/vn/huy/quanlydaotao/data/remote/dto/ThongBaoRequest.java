package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class ThongBaoRequest {
    @SerializedName("id_nguoi_dung")
    private int idNguoiDung;

    public ThongBaoRequest(int idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }
}