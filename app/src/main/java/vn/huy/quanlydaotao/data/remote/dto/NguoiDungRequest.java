package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class NguoiDungRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("ho_ten")
    private String hoTen;

    @SerializedName("email")
    private String email;

    public NguoiDungRequest(int id, String hoTen, String email) {
        this.id = id;
        this.hoTen = hoTen;
        this.email = email;
    }
}