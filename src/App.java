import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    
    private final Escorredor escorredor;
    private final PratosSujosFactory factory;
    private final List<Thread> threads = new ArrayList<>();
    private List<Lavador> lavadores = new ArrayList<>();
    private List<Enxugador> enxugadores = new ArrayList<>();

    public App(int maxEscorredor, int numLavadores, int numEnxugadores) {
        this.escorredor = new Escorredor(maxEscorredor);
        this.factory = new PratosSujosFactory();
        
        inicializarLavadores(numLavadores);
        inicializarEnxugadores(numEnxugadores);
    }

    private void inicializarLavadores(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            lavadores.add(new Lavador(escorredor, factory));
        }
    }

    private void inicializarEnxugadores(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            enxugadores.add(new Enxugador(escorredor));
        }
    }

    public void work() {
        LOGGER.info("Iniciando trabalhos");
        
        for (Lavador lavador : lavadores) {
            Thread thread = new Thread(lavador);
            threads.add(thread);
            thread.start();
        }
        
        for (Enxugador enxugador : enxugadores) {
            Thread thread = new Thread(enxugador);
            threads.add(thread);
            thread.start();
        }
    }

    public void stop() {
        LOGGER.info("Encerrando trabalhos");
        
        for (Lavador lavador : lavadores) {
            lavador.parar();
        }
        
        for (Enxugador enxugador : enxugadores) {
            enxugador.parar();
        }
        
        try {
            for (Thread thread : threads) {
                thread.interrupt();
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        escorredor.validar();
        LOGGER.info("Trabalhos encerrados");
    }

    public static void main(String[] args) throws InterruptedException {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        Logger.getLogger("").setLevel(Level.INFO);
        Logger.getLogger("").addHandler(handler);
        
        App app = new App(10, 2, 2);
        app.work();
        
        Thread.sleep(120000);
        
        app.stop();
    }
}
