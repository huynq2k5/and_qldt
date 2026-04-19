package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dang_ky_lop")
public class DangKyLopEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "id_nguoi_dung")
    public int idNguoiDung;

    @ColumnInfo(name = "id_lop_hoc")
    public int idLopHoc;

    @ColumnInfo(name = "ngay_dang_ky")
    public String ngayDangKy;
}