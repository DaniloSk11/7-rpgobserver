package rpgobserver;

import javafx.scene.control.Label;

public class CombatLogDisplay implements Observer, DisplayElement {
    private int health;
    private int mana;
    private int level;
    private int lastHealth = -1;
    private int lastMana = -1;
    private Subject hero;
    private Label displayLabel;
    private StringBuilder log;

    public CombatLogDisplay(Subject hero) {
        this.hero = hero;
        this.displayLabel = new Label();
        this.log = new StringBuilder();
        displayLabel.setStyle("-fx-text-fill: #ffcc00; -fx-font-size: 13px; -fx-font-family: monospace;");
        hero.registerObserver(this);
    }

    @Override
    public void update(int health, int mana, int level) {
        if (lastHealth != -1) {
            if (health < lastHealth) {
                addLog("[DANO] Recebeu " + (lastHealth - health) + " de dano!");
            } else if (health > lastHealth) {
                addLog("[CURA] Restaurou " + (health - lastHealth) + " de HP!");
            }

            if (mana < lastMana) {
                addLog("[MAGIA] Usou " + (lastMana - mana) + " de mana!");
            } else if (mana > lastMana) {
                addLog("[MANA] Recuperou " + (mana - lastMana) + " de mana!");
            }

            if (level > this.level) {
                addLog("[LEVEL UP] Subiu para o nivel " + level + "!");
            }
        }

        this.health = health;
        this.mana = mana;
        this.level = level;
        this.lastHealth = health;
        this.lastMana = mana;
        display();
    }

    private void addLog(String message) {
        log.insert(0, message + "\n");
        String[] lines = log.toString().split("\n");
        if (lines.length > 8) {
            log = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                log.append(lines[i]).append("\n");
            }
        }
    }

    @Override
    public void display() {
        displayLabel.setText(log.toString());
    }

    public Label getDisplay() {
        return displayLabel;
    }
}