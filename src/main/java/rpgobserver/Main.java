package rpgobserver;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Hero hero;
    private StatusDisplay statusDisplay;
    private CombatLogDisplay combatLogDisplay;
    private QuestDisplay questDisplay;

    private TextField healthField;
    private TextField manaField;
    private TextField levelField;

    @Override
    public void start(Stage primaryStage) {
        hero = new Hero("Aragorn", 100, 50, 1);

        statusDisplay = new StatusDisplay(hero);
        combatLogDisplay = new CombatLogDisplay(hero);
        questDisplay = new QuestDisplay(hero);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #2b2b2b;");

        Label title = new Label("RPG OBSERVER - SISTEMA DE EVENTOS");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ffd700;");
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(titleBox);

        root.setLeft(createControlPanel());
        root.setCenter(createDisplayPanel());

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("RPG Observer Pattern");
        primaryStage.setScene(scene);
        primaryStage.show();

        hero.setStats(100, 50, 1);
    }

    private VBox createControlPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 10;");
        panel.setPrefWidth(280);

        Label controlTitle = new Label("CONTROLES DO HEROI");
        controlTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #90ee90;");

        healthField = new TextField("100");
        manaField = new TextField("50");
        levelField = new TextField("1");

        styleTextField(healthField);
        styleTextField(manaField);
        styleTextField(levelField);

        Button updateBtn = createButton("Atualizar Atributos", "#4a90e2");
        updateBtn.setOnAction(e -> updateHeroStats());

        Button damageBtn = createButton("Receber Dano (-20 HP)", "#e74c3c");
        damageBtn.setOnAction(e -> hero.takeDamage(20));

        Button healBtn = createButton("Curar (+30 HP)", "#27ae60");
        healBtn.setOnAction(e -> hero.heal(30));

        Button useManaBtn = createButton("Usar Magia (-15 Mana)", "#9b59b6");
        useManaBtn.setOnAction(e -> hero.useMana(15));

        Button restoreBtn = createButton("Restaurar Mana (+25)", "#3498db");
        restoreBtn.setOnAction(e -> hero.restoreMana(25));

        Button levelUpBtn = createButton("Subir de Nivel", "#f39c12");
        levelUpBtn.setOnAction(e -> hero.levelUp());

        panel.getChildren().addAll(
                controlTitle,
                createLabel("HP:"), healthField,
                createLabel("Mana:"), manaField,
                createLabel("Nivel:"), levelField,
                updateBtn, new Separator(),
                createLabel("ACOES RAPIDAS:"),
                damageBtn, healBtn, useManaBtn, restoreBtn, levelUpBtn
        );

        return panel;
    }

    private VBox createDisplayPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(10, 10, 10, 20));

        panel.getChildren().addAll(
                createObserverBox("STATUS DO HEROI", statusDisplay.getDisplay()),
                createObserverBox("LOG DE COMBATE", combatLogDisplay.getDisplay()),
                createObserverBox("PROGRESSO DE MISSOES", questDisplay.getDisplay())
        );

        return panel;
    }

    private VBox createObserverBox(String title, Label display) {
        VBox box = new VBox(10);
        box.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 10; -fx-padding: 15;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffd700;");

        box.getChildren().addAll(titleLabel, display);
        return box;
    }

    private void updateHeroStats() {
        try {
            int health = Integer.parseInt(healthField.getText());
            int mana = Integer.parseInt(manaField.getText());
            int level = Integer.parseInt(levelField.getText());
            hero.setStats(health, mana, level);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Valores invalidos");
            alert.setContentText("Por favor, insira numeros validos!");
            alert.showAndWait();
        }
    }

    private void styleTextField(TextField field) {
        field.setStyle("-fx-background-color: #4a4a4a; -fx-text-fill: white; -fx-font-size: 14px;");
    }

    private Button createButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 8;");
        btn.setMaxWidth(Double.MAX_VALUE);
        return btn;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        return label;
    }

    public static void main(String[] args) {
        launch(args);
    }
}