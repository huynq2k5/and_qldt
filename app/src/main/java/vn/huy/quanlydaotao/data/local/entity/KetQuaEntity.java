package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ket_qua_local")
public class KetQuaEntity {
    @PrimaryKey
    private int idKetQua;
    private String status;
    private String urlChungChi;

    public KetQuaEntity(int idKetQua, String status, String urlChungChi) {
        this.idKetQua = idKetQua;
        this.status = status;
        this.urlChungChi = urlChungChi;
    }

    public int getIdKetQua() { return idKetQua; }
    public void setIdKetQua(int idKetQua) { this.idKetQua = idKetQua; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getUrlChungChi() { return urlChungChi; }
    public void setUrlChungChi(String urlChungChi) { this.urlChungChi = urlChungChi; }
}