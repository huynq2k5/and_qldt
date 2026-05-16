package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ThongBaoResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<ThongBaoDTO> data;

    public boolean isSuccess() { return success; }
    public List<ThongBaoDTO> getData() { return data; }

    public static class ThongBaoDTO {
        @SerializedName("id")
        private int id;
        @SerializedName("id_nguoi_dung")
        private int idNguoiDung;
        @SerializedName("tieu_de")
        private String tieuDe;
        @SerializedName("noi_dung")
        private String noiDung;
        @SerializedName("thoi_gian")
        private String thoiGian;
        @SerializedName("da_xem")
        private int daXem;

        public int getId() { return id; }
        public int getIdNguoiDung() { return idNguoiDung; }
        public String getTieuDe() { return tieuDe; }
        public String getNoiDung() { return noiDung; }
        public String getThoiGian() { return thoiGian; }
        public int getDaXem() { return daXem; }
    }
}