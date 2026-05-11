package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class DoiPassRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("mat_khau_cu")
    private String matKhauCu;

    @SerializedName("mat_khau_moi")
    private String matKhauMoi;

    public DoiPassRequest(int id, String matKhauCu, String matKhauMoi) {
        this.id = id;
        this.matKhauCu = matKhauCu;
        this.matKhauMoi = matKhauMoi;
    }
}