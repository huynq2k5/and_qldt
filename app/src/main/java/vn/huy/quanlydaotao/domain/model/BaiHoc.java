package vn.huy.quanlydaotao.domain.model;

public class BaiHoc implements java.io.Serializable{
    private int id;
    private int idKhoaHoc;
    private String tieuDe;
    private String loaiNoiDung;
    private String duongDanTep;

    public BaiHoc(int id, int idKhoaHoc, String tieuDe, String loaiNoiDung, String duongDanTep) {
        this.id = id;
        this.idKhoaHoc = idKhoaHoc;
        this.tieuDe = tieuDe;
        this.loaiNoiDung = loaiNoiDung;
        this.duongDanTep = duongDanTep;
    }

    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getTieuDe() { return tieuDe; }
    public String getLoaiNoiDung() { return loaiNoiDung; }
    public String getDuongDanTep() { return duongDanTep; }
}