package vn.huy.quanlydaotao.domain.model;

public class ThongBao {
    private final int id;
    private final int idNguoiDung;
    private final String tieuDe;
    private final String noiDung;
    private final String thoiGian;
    private final int daXem;

    public ThongBao(int id, int idNguoiDung, String tieuDe, String noiDung, String thoiGian, int daXem) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.daXem = daXem;
    }

    public int getId() { return id; }
    public int getIdNguoiDung() { return idNguoiDung; }
    public String getTieuDe() { return tieuDe; }
    public String getNoiDung() { return noiDung; }
    public String getThoiGian() { return thoiGian; }
    public int getDaXem() { return daXem; }
}