package app.inisiator.myapplication.models;

public class QrNotif {
    String qrcode, exp_date, event_title, lokasi, waktu, buyer_email, status;

    public QrNotif(String qrcode, String exp_date, String event_title, String lokasi, String waktu, String buyer_email, String status) {
        this.qrcode = qrcode;
        this.exp_date = exp_date;
        this.event_title = event_title;
        this.lokasi = lokasi;
        this.waktu = waktu;
        this.buyer_email = buyer_email;
        this.status = status;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
