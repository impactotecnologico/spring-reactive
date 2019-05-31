package net.impactotecnologico.stream_reactive;

public class Stock {

    private final long l;

    public Stock(long l) {
        this.l = l;
    }
    
    public long getPrice() {
        return l;
    }

}