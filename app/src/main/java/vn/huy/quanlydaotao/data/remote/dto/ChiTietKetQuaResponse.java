package vn.huy.quanlydaotao.data.remote.dto;

import java.util.List;

public class ChiTietKetQuaResponse {
    private double diem_so;
    private String trang_thai;
    private String thoi_gian_nop;
    private List<ChiTietItem> chi_tiet;

    public double getDiem_so() { return diem_so; }
    public String getTrang_thai() { return trang_thai; }
    public String getThoi_gian_nop() { return thoi_gian_nop; }
    public List<ChiTietItem> getChi_iet() { return chi_tiet; }

    public static class ChiTietItem {
        private int id_cau_hoi;
        private String cau_tra_loi;
        private int la_dap_an;
        private String noi_dung;
        private String dap_an_dung;

        public int getId_cau_hoi() { return id_cau_hoi; }
        public String getCau_tra_loi() { return cau_tra_loi; }
        public int getLa_dap_an() { return la_dap_an; }
        public String getNoi_dung() { return noi_dung; }
        public String getDap_an_dung() { return dap_an_dung; }
    }
}