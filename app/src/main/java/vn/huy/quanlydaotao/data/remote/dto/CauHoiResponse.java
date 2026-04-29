package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class CauHoiResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("id_bai_kiem_tra")
    private int idBaiKiemTra;

    @SerializedName("noi_dung")
    private String noiDung;

    @SerializedName("cau_a")
    private String cauA;

    @SerializedName("cau_b")
    private String cauB;

    @SerializedName("cau_c")
    private String cauC;

    @SerializedName("cau_d")
    private String cauD;

    public int getId() {
        return id;
    }

    public int getIdBaiKiemTra() {
        return idBaiKiemTra;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String getCauA() {
        return cauA;
    }

    public String getCauB() {
        return cauB;
    }

    public String getCauC() {
        return cauC;
    }

    public String getCauD() {
        return cauD;
    }
}