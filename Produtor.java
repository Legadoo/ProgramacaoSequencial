package trabbimm.com;


// Produtor.java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Produtor implements Runnable {
    private Armazem armazem;
    private String tipoRecurso;
    private Texture textura;
    private float posX, posY;
    private boolean produzindo;

    public Produtor(Armazem armazem, String tipoRecurso, String caminhoTextura, float posX, float posY) {
        this.armazem = armazem;
        this.tipoRecurso = tipoRecurso;
        this.textura = new Texture(Gdx.files.internal(caminhoTextura));
        this.posX = posX;
        this.posY = posY;
        this.produzindo = false;
    }

    @Override
    public void run() {
        while (true) {
            Recurso recurso = new Recurso(tipoRecurso);
            boolean adicionado = armazem.adicionarRecurso(recurso);
            if (adicionado) {
                produzindo = true;
                System.out.println("Produtor produziu: " + tipoRecurso);
            } else {
                produzindo = false;
                System.out.println("Armazém cheio, aguardando para produzir: " + tipoRecurso);
            }
            try {
                Thread.sleep(1000); // Tempo de produção
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

    public boolean isProduzindo() {
        return produzindo;
    }

    public void dispose() {
        textura.dispose();
    }
}
