package vn.huy.quanlydaotao.domain.model;

public class CauHoi {
    private int id;
    private int idBaiKiemTra;
    private String noiDung;
    private String cauA;
    private String cauB;
    private String cauC;
    private String cauD;

    public CauHoi(int id, int idBaiKiemTra, String noiDung, String cauA, String cauB, String cauC, String cauD) {
        this.id = id;
        this.idBaiKiemTra = idBaiKiemTra;
        this.noiDung = noiDung;
        this.cauA = cauA;
        this.cauB = cauB;
        this.cauC = cauC;
        this.cauD = cauD;
    }

    public int getId() {
        return id;
    }

    public int getIdBaiKiemTra() {
        return idBaiKiemTra;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String getCauA() {
        return cauA;
    }

    public String getCauB() {
        return cauB;
    }

    public String getCauC() {
        return cauC;
    }

    public String getCauD() {
        return cauD;
    }
}