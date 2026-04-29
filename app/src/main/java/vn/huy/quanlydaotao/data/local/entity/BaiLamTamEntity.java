package vn.huy.quanlydaotao.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bai_lam_tam")
public class BaiLamTamEntity {
    @PrimaryKey
    private int idCauHoi;
    private String cauTraLoi;

    public BaiLamTamEntity(int idCauHoi, String cauTraLoi) {
        this.idCauHoi = idCauHoi;
        this.cauTraLoi = cauTraLoi;
    }

    public int getIdCauHoi() {
        return idCauHoi;
    }

    public void setIdCauHoi(int idCauHoi) {
        this.idCauHoi = idCauHoi;
    }

    public String getCauTraLoi() {
        return cauTraLoi;
    }

    public void setCauTraLoi(String cauTraLoi) {
        this.cauTraLoi = cauTraLoi;
    }
}