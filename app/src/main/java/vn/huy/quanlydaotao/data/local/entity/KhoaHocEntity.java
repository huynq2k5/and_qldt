package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "khoa_hoc")
public class KhoaHocEntity {
    @PrimaryKey
    private int id;
    private String tenKhoaHoc;
    private String moTa;
    private String ngayTao;
    private String duongDanAnh;

    public KhoaHocEntity(int id, String tenKhoaHoc, String moTa, String ngayTao, String duongDanAnh) {
        this.id = id;
        this.tenKhoaHoc = tenKhoaHoc;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.duongDanAnh = duongDanAnh;
    }

    public int getId() { return id; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
    public String getMoTa() { return moTa; }
    public String getNgayTao() { return ngayTao; }
    public String getDuongDanAnh() { return duongDanAnh; }
}