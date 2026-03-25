package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class KhoaHocResponse {
    @SerializedName("id")
    private int id;
    
    @SerializedName("ten_khoa_hoc")
    private String tenKhoaHoc;
    
    @SerializedName("mo_ta")
    private String moTa;
    
    @SerializedName("ngay_tao")
    private String ngayTao;

    public int getId() { return id; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
    public String getMoTa() { return moTa; }
    public String getNgayTao() { return ngayTao; }
}
