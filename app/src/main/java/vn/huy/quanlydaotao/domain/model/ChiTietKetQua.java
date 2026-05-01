package vn.huy.quanlydaotao.domain.model;

import java.util.List;

public class ChiTietKetQua {
    private double diemSo;
    private String trangThai;
    private String thoiGianNop;
    private List<CauHoiChiTiet> danhSachChiTiet;

    public ChiTietKetQua(double diemSo, String trangThai, String thoiGianNop, List<CauHoiChiTiet> danhSachChiTiet) {
        this.diemSo = diemSo;
        this.trangThai = trangThai;
        this.thoiGianNop = thoiGianNop;
        this.danhSachChiTiet = danhSachChiTiet;
    }

    public double getDiemSo() { return diemSo; }
    public String getTrangThai() { return trangThai; }
    public String getThoiGianNop() { return thoiGianNop; }
    public List<CauHoiChiTiet> getDanhSachChiTiet() { return danhSachChiTiet; }

    public static class CauHoiChiTiet {
        @com.google.gson.annotations.SerializedName("id_cau_hoi")
        private int idCauHoi;

        @com.google.gson.annotations.SerializedName("cau_tra_loi")
        private String cauTraLoi;

        @com.google.gson.annotations.SerializedName("la_dap_an")
        private int laDapAn;

        @com.google.gson.annotations.SerializedName("noi_dung")
        private String noiDung;

        @com.google.gson.annotations.SerializedName("dap_an_dung")
        private String dapAnDung;

        public CauHoiChiTiet(int idCauHoi, String cauTraLoi, int laDapAn, String noiDung, String dapAnDung) {
            this.idCauHoi = idCauHoi;
            this.cauTraLoi = cauTraLoi;
            this.laDapAn = laDapAn;
            this.noiDung = noiDung;
            this.dapAnDung = dapAnDung;
        }

        public int getIdCauHoi() { return idCauHoi; }
        public String getCauTraLoi() { return cauTraLoi; }
        public int getLaDapAn() { return laDapAn; }
        public String getNoiDung() { return noiDung; }
        public String getDapAnDung() { return dapAnDung; }
    }
}