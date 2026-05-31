package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class ChungChiResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("id_khoa_hoc")
    private int idKhoaHoc;
    @SerializedName("ma_qr")
    private String maQr;
    @SerializedName("url_hinh_anh")
    private String urlHinhAnh;
    @SerializedName("ngay_cap")
    private String ngayCap;
    @SerializedName("ten_khoa_hoc")
    private String tenKhoaHoc;

    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getMaQr() { return maQr; }
    public String getUrlHinhAnh() { return urlHinhAnh; }
    public String getNgayCap() { return ngayCap; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
}