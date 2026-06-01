import java.util.Random;
import java.util.logging.Logger;

public class Enxugador implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Enxugador.class.getName());
    
    private final Escorredor escorredor;
    private final Random random = new Random();
    private volatile boolean done = false;

    public Enxugador(Escorredor escorredor) {
        this.escorredor = escorredor;
    }

    @Override
    public void run() {
        try {
            while (!done) {
                Prato prato = escorredor.tirar();
                LOGGER.info("Enxugador retirou: " + prato);
                
                long tempoEnxugar = random.nextLong(3, 11);
                LOGGER.fine("Enxugador enxugando: " + prato + " por " + tempoEnxugar + "ms");
                Thread.sleep(tempoEnxugar);
                
                LOGGER.fine("Enxugador finalizou: " + prato);
            }
        } catch (InterruptedException e) {
            LOGGER.fine("Enxugador interrompido");
            Thread.currentThread().interrupt();
        }
    }

    public void parar() {
        done = true;
    }
}
