import java.util.Random;

public class PratosSujosFactory {
    private static final Random RANDOM = new Random();
    private int proxima_serie = 1;

    public synchronized Prato criarPrato() {
        int numeroDeSerie = proxima_serie++;
        TamanhoPrato tamanho = sortearTamanho();
        return new Prato(numeroDeSerie, tamanho);
    }

    private TamanhoPrato sortearTamanho() {
        double probabilidade = RANDOM.nextDouble();
        
        if (probabilidade < 0.30) {
            return TamanhoPrato.BAIXO;
        } else if (probabilidade < 0.90) {
            return TamanhoPrato.MEDIO;
        } else {
            return TamanhoPrato.ENGORDURADO;
        }
    }
}
