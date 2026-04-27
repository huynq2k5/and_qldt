package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bai_kiem_tra")
public class BaiKiemTraEntity {
    @PrimaryKey
    private int id;
    private int idKhoaHoc;
    private String tieuDe;
    private int diemDat;
    private int thoiGianLam;
    private String tenKhoaHoc;

    public BaiKiemTraEntity(int id, int idKhoaHoc, String tieuDe, int diemDat, int thoiGianLam, String tenKhoaHoc) {
        this.id = id;
        this.idKhoaHoc = idKhoaHoc;
        this.tieuDe = tieuDe;
        this.diemDat = diemDat;
        this.thoiGianLam = thoiGianLam;
        this.tenKhoaHoc = tenKhoaHoc;
    }

    // Getters
    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getTieuDe() { return tieuDe; }
    public int getDiemDat() { return diemDat; }
    public int getThoiGianLam() { return thoiGianLam; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
}