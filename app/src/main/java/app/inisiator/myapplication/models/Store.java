package app.inisiator.myapplication.models;

public class Store {
    private String product, photo, jenis;
    private Integer harga;

    public Store(String product, String photo, String jenis, Integer harga) {
        this.product = product;
        this.photo = photo;
        this.jenis = jenis;
        this.harga = harga;
    }

    public Store() {}

    public String getProduct() {

        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPhoto() {

        return photo;
    }

    public void setPhoto(String photo) {

        this.photo = photo;
    }

    public String getJenis() {

        return jenis;
    }

    public void setJenis(String jenis) {

        this.jenis = jenis;
    }

    public Integer getHarga() {

        return harga;
    }

    public void setHarga(Integer harga) {

        this.harga = harga;
    }

}
