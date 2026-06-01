import java.util.logging.Logger;

public class Lavador implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Lavador.class.getName());
    
    private final Escorredor escorredor;
    private final PratosSujosFactory factory;
    private volatile boolean done = false;

    public Lavador(Escorredor escorredor, PratosSujosFactory factory) {
        this.escorredor = escorredor;
        this.factory = factory;
    }

    @Override
    public void run() {
        try {
            while (!done) {
                Prato prato = factory.criarPrato();
                LOGGER.info("Lavador requisitou: " + prato);
                
                long tempoLavagem = obterTempoLavagem(prato.getTamanho());
                LOGGER.fine("Lavador lavando: " + prato + " por " + tempoLavagem + "ms");
                Thread.sleep(tempoLavagem);
                
                escorredor.colocar(prato);
                LOGGER.info("Lavador colocou no escorredor: " + prato);
            }
        } catch (InterruptedException e) {
            LOGGER.fine("Lavador interrompido");
            Thread.currentThread().interrupt();
        }
    }

    private long obterTempoLavagem(TamanhoPrato tamanho) {
        return tamanho.getTempoMs();
    }

    public void parar() {
        done = true;
    }
}
