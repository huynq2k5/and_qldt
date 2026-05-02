package vn.huy.quanlydaotao.domain.model;

public class BaiKiemTra {
    private int id;
    private int idKhoaHoc;
    private String tieuDe;
    private int diemDat;
    private int thoiGianLam;
    private String tenKhoaHoc;
    private Integer idKetQua;

    public BaiKiemTra(int id, int idKhoaHoc, String tieuDe, int diemDat, int thoiGianLam, String tenKhoaHoc, Integer idKetQua) {
        this.id = id;
        this.idKhoaHoc = idKhoaHoc;
        this.tieuDe = tieuDe;
        this.diemDat = diemDat;
        this.thoiGianLam = thoiGianLam;
        this.tenKhoaHoc = tenKhoaHoc;
        this.idKetQua = idKetQua;
    }

    public Integer getIdKetQua() { return idKetQua; }

    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getTieuDe() { return tieuDe; }
    public int getDiemDat() { return diemDat; }
    public int getThoiGianLam() { return thoiGianLam; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
}