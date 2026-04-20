package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LichMeetResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<LichMeetDTO> data;

    public boolean isSuccess() {
        return success;
    }

    public List<LichMeetDTO> getData() {
        return data;
    }

    public static class LichMeetDTO {
        @SerializedName("id")
        private int id;

        @SerializedName("id_lop_hoc")
        private int idLopHoc;

        @SerializedName("ten_lop")
        private String tenLop;

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

        public String getTenLop() {
            return tenLop;
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
}