package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class LopHocResponse {
    @SerializedName("id")
    private int id;
    
    @SerializedName("ten_lop")
    private String tenLop;
    
    @SerializedName("ngay_bat_dau")
    private String ngayBatDau;
    
    @SerializedName("ngay_ket_thuc")
    private String ngayKetThuc;
    
    @SerializedName("id_khoa_hoc")
    private int idKhoaHoc;

    public int getId() { return id; }
    public String getTenLop() { return tenLop; }
    public String getNgayBatDau() { return ngayBatDau; }
    public String getNgayKetThuc() { return ngayKetThuc; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
}
