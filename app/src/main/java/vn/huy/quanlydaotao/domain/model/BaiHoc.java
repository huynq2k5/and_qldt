package vn.huy.quanlydaotao.domain.model;

public class BaiHoc implements java.io.Serializable {
    private int id;
    private int idKhoaHoc;
    private String tieuDe;
    private String loaiNoiDung;
    private String duongDanTep;
    private int phanTram;

    public BaiHoc(int id, int idKhoaHoc, String tieuDe, String loaiNoiDung, String duongDanTep, int phanTram) {
        this.id = id;
        this.idKhoaHoc = idKhoaHoc;
        this.tieuDe = tieuDe;
        this.loaiNoiDung = loaiNoiDung;
        this.duongDanTep = duongDanTep;
        this.phanTram = phanTram;
    }

    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getTieuDe() { return tieuDe; }
    public String getLoaiNoiDung() { return loaiNoiDung; }
    public String getDuongDanTep() { return duongDanTep; }
    public int getPhanTram() { return phanTram; }
}