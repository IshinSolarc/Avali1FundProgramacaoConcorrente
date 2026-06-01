import java.util.logging.Logger;

public class Escorredor {
    private static final Logger LOGGER = Logger.getLogger(Escorredor.class.getName());
    
    private final Prato[] buffer;
    private final int max;
    private int inicio;
    private int fim;
    private int quantidade;

    public Escorredor(int max) {
        this.buffer = new Prato[max];
        this.max = max;
        this.inicio = 0;
        this.fim = -1;
        this.quantidade = 0;
    }

    public synchronized void colocar(Prato prato) throws InterruptedException {
        while (quantidade >= max) {
            wait();
        }
        
        fim = (fim + 1) % max;
        buffer[fim] = prato;
        quantidade++;
        
        if (quantidade == max) {
            LOGGER.info("Escorredor CHEIO: " + quantidade + "/" + max);
        }
        
        notifyAll();
    }

    public synchronized Prato tirar() throws InterruptedException {
        while (quantidade <= 0) {
            wait();
        }
        
        Prato prato = buffer[inicio];
        buffer[inicio] = null;
        inicio = (inicio + 1) % max;
        quantidade--;
        
        if (quantidade == 0) {
            LOGGER.info("Escorredor VAZIO: " + quantidade + "/" + max);
        }
        
        notifyAll();
        return prato;
    }

    public synchronized int getQuantidade() {
        return quantidade;
    }

    public synchronized void validar() {
        if (quantidade < 0 || quantidade > max) {
            throw new IllegalStateException("Limite do escorredor violado: " + quantidade);
        }
    }
}
