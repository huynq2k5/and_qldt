package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ket_qua_kiem_tra")
public class LichSuEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "id_nguoi_dung")
    public int idNguoiDung;

    @ColumnInfo(name = "id_bai_kiem_tra")
    public int idBaiKiemTra;

    @ColumnInfo(name = "diem_so")
    public float diemSo;

    @ColumnInfo(name = "trang_thai")
    public String trangThai;

    @ColumnInfo(name = "thoi_gian_nop")
    public String thoiGianNop;

    public LichSuEntity(int id, int idNguoiDung, int idBaiKiemTra, float diemSo, String trangThai, String thoiGianNop) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.idBaiKiemTra = idBaiKiemTra;
        this.diemSo = diemSo;
        this.trangThai = trangThai;
        this.thoiGianNop = thoiGianNop;
    }
}