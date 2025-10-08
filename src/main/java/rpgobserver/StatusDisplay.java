package rpgobserver;

import javafx.scene.control.Label;

public class StatusDisplay implements Observer, DisplayElement {
    private int health;
    private int mana;
    private int level;
    private Subject hero;
    private Label displayLabel;

    public StatusDisplay(Subject hero) {
        this.hero = hero;
        this.displayLabel = new Label();
        displayLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: monospace;");
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
        int maxHealth = 100 + (level * 10);
        int maxMana = 50 + (level * 5);

        String healthBar = createBar(health, maxHealth, 20);
        String manaBar = createBar(mana, maxMana, 20);

        String text = String.format(
                "HP: %d/%d\n%s\n\n" +
                        "Mana: %d/%d\n%s\n\n" +
                        "Nivel: %d",
                health, maxHealth, healthBar,
                mana, maxMana, manaBar,
                level
        );
        displayLabel.setText(text);
    }

    private String createBar(int current, int max, int length) {
        int filled = (int) ((double) current / max * length);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            bar.append(i < filled ? "=" : "-");
        }
        bar.append("]");
        return bar.toString();
    }

    public Label getDisplay() {
        return displayLabel;
    }
}