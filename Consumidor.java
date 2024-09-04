package trabbimm.com;

// Consumidor.java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Consumidor implements Runnable {
    private Armazem armazem;
    private Texture textura;
    private float posX, posY;
    private boolean consumindo;

    public Consumidor(Armazem armazem, String caminhoTextura, float posX, float posY) {
        this.armazem = armazem;
        this.textura = new Texture(Gdx.files.internal(caminhoTextura));
        this.posX = posX;
        this.posY = posY;
        this.consumindo = false;
    }

    @Override
    public void run() {
        while (true) {
            Recurso recurso = armazem.removerRecurso();
            if (recurso != null) {
                consumindo = true;
                System.out.println("Consumidor consumiu: " + recurso.getTipo());
            } else {
                consumindo = false;
                System.out.println("Armazém vazio, aguardando para consumir.");
            }
            try {
                Thread.sleep(1500); // Tempo de consumo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Texture getTextura() {
        return textura;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public boolean isConsumindo() {
        return consumindo;
    }

    public void dispose() {
        textura.dispose();
    }
}
