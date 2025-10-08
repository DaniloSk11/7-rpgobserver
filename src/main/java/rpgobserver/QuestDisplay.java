package rpgobserver;

import javafx.scene.control.Label;

public class QuestDisplay implements Observer, DisplayElement {
    private int health;
    private int mana;
    private int level;
    private Subject hero;
    private Label displayLabel;

    public QuestDisplay(Subject hero) {
        this.hero = hero;
        this.displayLabel = new Label();
        displayLabel.setStyle("-fx-text-fill: #90ee90; -fx-font-size: 14px;");
        hero.registerObserver(this);
    }

    @Override
    public void update(int health, int mana, int level) {
        this.health = health;
        this.mana = mana;
        this.level = level;
        display();
    }

    @Override
    public void display() {
        StringBuilder quests = new StringBuilder();

        if (level < 5) {
            quests.append("[MISSAO] Alcançar Nivel 5\n");
            quests.append("Progresso: Nivel " + level + "/5\n\n");
        } else if (level < 10) {
            quests.append("[COMPLETO] Missao Principal Completa!\n");
            quests.append("[MISSAO] Tornar-se Lendario (Nivel 10)\n");
            quests.append("Progresso: Nivel " + level + "/10\n\n");
        } else {
            quests.append("[LENDARIO] Voce e uma lenda!\n\n");
        }

        if (health > 80) {
            quests.append("[BONUS] Saude Excelente\n");
        } else if (health < 30) {
            quests.append("[ALERTA] Saude Critica! Cure-se!\n");
        }

        if (mana > 40) {
            quests.append("[BONUS] Mana Abundante\n");
        } else if (mana < 10) {
            quests.append("[ALERTA] Mana Baixa!\n");
        }

        displayLabel.setText(quests.toString());
    }

    public Label getDisplay() {
        return displayLabel;
    }
}