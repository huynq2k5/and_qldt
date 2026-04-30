package vn.huy.quanlydaotao.data.remote.dto;

import java.util.List;

public class KetQuaRequest {
    private int id_nguoi_dung;
    private int id_bai_kiem_tra;
    private List<BaiLam> bai_lam;

    public KetQuaRequest(int id_nguoi_dung, int id_bai_kiem_tra, List<BaiLam> bai_lam) {
        this.id_nguoi_dung = id_nguoi_dung;
        this.id_bai_kiem_tra = id_bai_kiem_tra;
        this.bai_lam = bai_lam;
    }

    public static class BaiLam {
        private int id_cau_hoi;
        private String cau_tra_loi;

        public BaiLam(int id_cau_hoi, String cau_tra_loi) {
            this.id_cau_hoi = id_cau_hoi;
            this.cau_tra_loi = cau_tra_loi;
        }
    }
}
