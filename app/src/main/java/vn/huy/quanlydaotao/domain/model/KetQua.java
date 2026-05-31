package vn.huy.quanlydaotao.domain.model;

public class KetQua {
    private String status;
    private int idKetQua;
    private String urlChungChi;

    public KetQua(String status, int idKetQua, String urlChungChi) {
        this.status = status;
        this.idKetQua = idKetQua;
        this.urlChungChi = urlChungChi;
    }

    public String getStatus() { return status; }
    public int getIdKetQua() { return idKetQua; }
    public String getUrlChungChi() { return urlChungChi; }
}