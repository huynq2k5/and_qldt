package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chung_chi_local")
public class ChungChiEntity {
    @PrimaryKey
    private int id;
    private int idKhoaHoc;
    private String maQr;
    private String urlHinhAnh;
    private String ngayCap;
    private String tenKhoaHoc;

    public ChungChiEntity(int id, int idKhoaHoc, String maQr, String urlHinhAnh, String ngayCap, String tenKhoaHoc) {
        this.id = id;
        this.idKhoaHoc = idKhoaHoc;
        this.maQr = maQr;
        this.urlHinhAnh = urlHinhAnh;
        this.ngayCap = ngayCap;
        this.tenKhoaHoc = tenKhoaHoc;
    }

    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getMaQr() { return maQr; }
    public String getUrlHinhAnh() { return urlHinhAnh; }
    public String getNgayCap() { return ngayCap; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
}