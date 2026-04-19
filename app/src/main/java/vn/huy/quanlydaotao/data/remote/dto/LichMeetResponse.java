package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class LichMeetResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("id_lop_hoc")
    private int idLopHoc;
    @SerializedName("tieu_de")
    private String tieuDe;
    @SerializedName("link_meet")
    private String linkMeet;
    @SerializedName("thoi_gian")
    private String thoiGian;

    public int getId() {
        return id;
    }

    public int getIdLopHoc() {
        return idLopHoc;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getLinkMeet() {
        return linkMeet;
    }

    public String getThoiGian() {
        return thoiGian;
    }
}
