package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lop_hoc")
public class LopHocEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "id_khoa_hoc")
    public int idKhoaHoc;

    @ColumnInfo(name = "ten_lop")
    public String tenLop;

    @ColumnInfo(name = "ngay_bat_dau")
    public String ngayBatDau;

    @ColumnInfo(name = "ngay_ket_thuc")
    public String ngayKetThuc;

    @ColumnInfo(name = "da_dang_ky")
    public int daDangKy;

    public LopHocEntity(int id, int idKhoaHoc, String tenLop, String ngayBatDau, String ngayKetThuc, int daDangKy) {
        this.id = id;
        this.idKhoaHoc = idKhoaHoc;
        this.tenLop = tenLop;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.daDangKy = daDangKy;
    }
}