public enum TamanhoPrato {
    BAIXO(3),
    MEDIO(5),
    ENGORDURADO(10);

    private final int tempoMs;

    TamanhoPrato(int tempoMs) {
        this.tempoMs = tempoMs;
    }

    public int getTempoMs() {
        return tempoMs;
    }
}
