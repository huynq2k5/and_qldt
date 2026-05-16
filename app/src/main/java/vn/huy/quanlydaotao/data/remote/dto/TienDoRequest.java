package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class TienDoRequest {
    @SerializedName("id_nguoi_dung")
    private int idNguoiDung;

    @SerializedName("id_bai_hoc")
    private int idBaiHoc;

    @SerializedName("phan_tram")
    private int phanTram;

    public TienDoRequest(int idNguoiDung, int idBaiHoc, int phanTram) {
        this.idNguoiDung = idNguoiDung;
        this.idBaiHoc = idBaiHoc;
        this.phanTram = phanTram;
    }
}