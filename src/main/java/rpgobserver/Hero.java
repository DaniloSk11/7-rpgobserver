package rpgobserver;

import java.util.ArrayList;

public class Hero implements Subject {
    private ArrayList<Observer> observers;
    private String name;
    private int health;
    private int mana;
    private int level;

    public Hero(String name, int health, int mana, int level) {
        this.observers = new ArrayList<>();
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.level = level;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(health, mana, level);
        }
    }

    public void setStats(int health, int mana, int level) {
        this.health = health;
        this.mana = mana;
        this.level = level;
        notifyObservers();
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
        notifyObservers();
    }

    public void heal(int amount) {
        this.health = Math.min(100 + (level * 10), this.health + amount);
        notifyObservers();
    }

    public void useMana(int amount) {
        this.mana = Math.max(0, this.mana - amount);
        notifyObservers();
    }

    public void restoreMana(int amount) {
        this.mana = Math.min(50 + (level * 5), this.mana + amount);
        notifyObservers();
    }

    public void levelUp() {
        this.level++;
        notifyObservers();
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMana() { return mana; }
    public int getLevel() { return level; }
}