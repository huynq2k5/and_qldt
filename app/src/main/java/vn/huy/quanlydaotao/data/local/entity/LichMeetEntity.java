package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lich_meet")
public class LichMeetEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "id_lop_hoc")
    public int idLopHoc;

    @ColumnInfo(name = "ten_lop")
    public String tenLop;

    @ColumnInfo(name = "tieu_de")
    public String tieuDe;

    @ColumnInfo(name = "link_meet")
    public String linkMeet;

    @ColumnInfo(name = "thoi_gian")
    public String thoiGian;

    public LichMeetEntity(int id, int idLopHoc, String tenLop, String tieuDe, String linkMeet, String thoiGian) {
        this.id = id;
        this.idLopHoc = idLopHoc;
        this.tenLop = tenLop;
        this.tieuDe = tieuDe;
        this.linkMeet = linkMeet;
        this.thoiGian = thoiGian;
    }
}