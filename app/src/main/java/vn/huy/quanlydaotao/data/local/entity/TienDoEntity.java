package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tien_do")
public class TienDoEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_nguoi_dung")
    public int idNguoiDung;

    @ColumnInfo(name = "id_bai_hoc")
    public int idBaiHoc;

    @ColumnInfo(name = "phan_tram")
    public int phanTram;

    @ColumnInfo(name = "ngay_cap_nhat")
    public String ngayCapNhat;
}