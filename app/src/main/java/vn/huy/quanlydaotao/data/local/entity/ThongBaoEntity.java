package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "thong_bao")
public class ThongBaoEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "id_nguoi_dung")
    public int idNguoiDung;

    @ColumnInfo(name = "tieu_de")
    public String tieuDe;

    @ColumnInfo(name = "noi_dung")
    public String noiDung;

    @ColumnInfo(name = "thoi_gian")
    public String thoiGian;

    @ColumnInfo(name = "da_xem")
    public int daXem;

    public ThongBaoEntity(int id, int idNguoiDung, String tieuDe, String noiDung, String thoiGian, int daXem) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.daXem = daXem;
    }
}