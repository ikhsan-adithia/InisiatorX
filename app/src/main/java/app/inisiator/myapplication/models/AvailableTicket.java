package app.inisiator.myapplication.models;

public class AvailableTicket {
    private String title, lokasi, tanggal, waktu;
    private Integer harga;

    public AvailableTicket(String title, String lokasi, String tanggal, String waktu, Integer harga) {
        this.title = title;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.harga = harga;
    }

    public AvailableTicket() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }
}
