package app.inisiator.myapplication.models;

public class AvailableKompetisi {
    String nama_komp, penyelenggara, batas_waktu, jenis, lokasi, keterangan, img_url;

    public AvailableKompetisi(String nama_komp, String penyelenggara, String batas_waktu, String jenis, String lokasi, String keterangan, String img_url) {
        this.nama_komp = nama_komp;
        this.penyelenggara = penyelenggara;
        this.batas_waktu = batas_waktu;
        this.jenis = jenis;
        this.lokasi = lokasi;
        this.keterangan = keterangan;
        this.img_url = img_url;
    }

    public String getNama_komp() {
        return nama_komp;
    }

    public void setNama_komp(String nama_komp) {
        this.nama_komp = nama_komp;
    }

    public String getPenyelenggara() {
        return penyelenggara;
    }

    public void setPenyelenggara(String penyelenggara) {
        this.penyelenggara = penyelenggara;
    }

    public String getBatas_waktu() {
        return batas_waktu;
    }

    public void setBatas_waktu(String batas_waktu) {
        this.batas_waktu = batas_waktu;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
