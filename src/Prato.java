public final class Prato {
    private final int numeroDeSerie;
    private final TamanhoPrato tamanho;

    public Prato(int numeroDeSerie, TamanhoPrato tamanho) {
        this.numeroDeSerie = numeroDeSerie;
        this.tamanho = tamanho;
    }

    public int getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public TamanhoPrato getTamanho() {
        return tamanho;
    }

    @Override
    public String toString() {
        return "Prato{" +
                "serie=" + numeroDeSerie +
                ", tamanho=" + tamanho +
                '}';
    }
}
