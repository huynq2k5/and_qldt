package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lop_hoc")
public class LopHocEntity {
    @PrimaryKey
    private int id;
    private String tenLop;
    private String ngayBatDau;
    private String ngayKetThuc;
    private int idKhoaHoc;

    public LopHocEntity(int id, String tenLop, String ngayBatDau, String ngayKetThuc, int idKhoaHoc) {
        this.id = id;
        this.tenLop = tenLop;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.idKhoaHoc = idKhoaHoc;
    }

    public int getId() { return id; }
    public String getTenLop() { return tenLop; }
    public String getNgayBatDau() { return ngayBatDau; }
    public String getNgayKetThuc() { return ngayKetThuc; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
}
