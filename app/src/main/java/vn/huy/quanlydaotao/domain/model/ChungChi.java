package vn.huy.quanlydaotao.domain.model;

public class ChungChi {
    private int id;
    private int idKhoaHoc;
    private String maQr;
    private String urlHinhAnh;
    private String ngayCap;
    private String tenKhoaHoc;

    public ChungChi(int id, int idKhoaHoc, String maQr, String urlHinhAnh, String ngayCap, String tenKhoaHoc) {
        this.id = id;
        this.idKhoaHoc = idKhoaHoc;
        this.maQr = maQr;
        this.urlHinhAnh = urlHinhAnh;
        this.ngayCap = ngayCap;
        this.tenKhoaHoc = tenKhoaHoc;
    }

    public int getId() { return id; }
    public int getIdKhoaHoc() { return idKhoaHoc; }
    public String getMaQr() { return maQr; }
    public String getUrlHinhAnh() { return urlHinhAnh; }
    public String getNgayCap() { return ngayCap; }
    public String getTenKhoaHoc() { return tenKhoaHoc; }
}