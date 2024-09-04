package trabbimm.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class JogoPrincipal extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private Table table;
    private SpriteBatch batch;
    private Texture texturaArmazem;
    private Armazem armazem;
    private Produtor produtorMadeira;
    private Produtor produtorPedra;
    private Consumidor consumidor;
    private Label armazemStatus;

    @Override
    public void create() {
        // Configuração do Stage e Skin
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("botoes.json"));

        // Configuração do SpriteBatch e Texturas
        batch = new SpriteBatch();
        texturaArmazem = new Texture(Gdx.files.internal("armazem.png"));

        // Inicialização do Armazem
        armazem = new Armazem(10);

        // Inicialização dos Produtores e Consumidor
        produtorMadeira = new Produtor(armazem, "Madeira", "produzindo_madeira.png", 100, 300);
        produtorPedra = new Produtor(armazem, "Pedra", "produzindo_pedra.png", 300, 300);
        consumidor = new Consumidor(armazem, "consumindo.png", 200, 100);

        // threads dos produtores e consumidor
        new Thread(produtorMadeira).start();
        new Thread(produtorPedra).start();
        new Thread(consumidor).start();

        // Configuração da Interface
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        armazemStatus = new Label("Armazém: " + armazem.getTamanho(), skin);
        table.top().left();
        table.padTop(10).padLeft(10);
        table.add(armazemStatus);

        // Atualizar status do armazém periodicamente
        new Thread(() -> {
            while (true) {
                Gdx.app.postRunnable(() -> {
                    armazemStatus.setText("Armazém: " + armazem.getTamanho());
                });
                try {
                    Thread.sleep(500); // Atualizar a cada 500 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void render() {
        // Limpar a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Renderizar imagens com SpriteBatch
        batch.begin();
        batch.draw(texturaArmazem, 150, 150, 200, 200);
        batch.draw(produtorMadeira.getTextura(), produtorMadeira.getPosX(), produtorMadeira.getPosY(), 100, 100);
        batch.draw(produtorPedra.getTextura(), produtorPedra.getPosX(), produtorPedra.getPosY(), 100, 100);
        batch.draw(consumidor.getTextura(), consumidor.getPosX(), consumidor.getPosY(), 100, 100);

        batch.end();

        // Renderizar Stage (para Labels)
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        batch.dispose();
        texturaArmazem.dispose();
        produtorMadeira.dispose();
        produtorPedra.dispose();
        consumidor.dispose();
    }
}
