package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class BaiKiemTraResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("id_khoa_hoc")
    private int idKhoaHoc;
    @SerializedName("tieu_de")
    private String tieuDe;
    @SerializedName("diem_dat")
    private int diemDat;
    @SerializedName("thoi_gian_lam")
    private int thoiGianLam;
    @SerializedName("ten_khoa_hoc")
    private String tenKhoaHoc;
    @SerializedName("id_ket_qua")
    private Integer idKetQua;

    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getTieuDe() { return tieuDe; }
    public int getDiemDat() { return diemDat; }
    public int getThoiGianLam() { return thoiGianLam; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
    public Integer getIdKetQua() { return idKetQua; }
}