package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bai_hoc")
public class BaiHocEntity {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "id_khoa_hoc")
    public int idKhoaHoc;
    @ColumnInfo(name = "tieu_de")
    public String tieuDe;
    @ColumnInfo(name = "loai_noi_dung")
    public String loaiNoiDung;
    @ColumnInfo(name = "duong_dan_tep")
    public String duongDanTep;
}