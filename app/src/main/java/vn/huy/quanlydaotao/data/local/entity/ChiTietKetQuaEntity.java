package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chi_tiet_ket_qua_local")
public class ChiTietKetQuaEntity {
    @PrimaryKey
    private int idKetQua;
    private double diemSo;
    private String trangThai;
    private String thoiGianNop;
    private String jsonChiTiet;

    public ChiTietKetQuaEntity(int idKetQua, double diemSo, String trangThai, String thoiGianNop, String jsonChiTiet) {
        this.idKetQua = idKetQua;
        this.diemSo = diemSo;
        this.trangThai = trangThai;
        this.thoiGianNop = thoiGianNop;
        this.jsonChiTiet = jsonChiTiet;
    }

    public int getIdKetQua() { return idKetQua; }
    public void setIdKetQua(int idKetQua) { this.idKetQua = idKetQua; }
    public double getDiemSo() { return diemSo; }
    public void setDiemSo(double diemSo) { this.diemSo = diemSo; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getThoiGianNop() { return thoiGianNop; }
    public void setThoiGianNop(String thoiGianNop) { this.thoiGianNop = thoiGianNop; }
    public String getJsonChiTiet() { return jsonChiTiet; }
    public void setJsonChiTiet(String jsonChiTiet) { this.jsonChiTiet = jsonChiTiet; }
}