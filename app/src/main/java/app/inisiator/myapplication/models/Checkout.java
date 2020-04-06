package app.inisiator.myapplication.models;

public class Checkout {
    private String product;
    private Integer jumlah;

    public Checkout(String product, Integer jumlah) {
        this.product = product;
        this.jumlah = jumlah;
    }

    public Checkout() {}

    public String getProduct() {

        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getJumlah() {

        return jumlah;
    }

    public void setJumlah(Integer jumlah) {

        this.jumlah = jumlah;
    }

}
