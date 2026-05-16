package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class BaiHocResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("id_khoa_hoc")
    private int idKhoaHoc;
    @SerializedName("tieu_de")
    private String tieuDe;
    @SerializedName("loai_noi_dung")
    private String loaiNoiDung;
    @SerializedName("duong_dan_tep")
    private String duongDanTep;
    @SerializedName("phan_tram")
    private int phanTram;

    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getTieuDe() { return tieuDe; }
    public String getLoaiNoiDung() { return loaiNoiDung; }
    public String getDuongDanTep() { return duongDanTep; }
    public int getPhanTram() { return phanTram; }
}