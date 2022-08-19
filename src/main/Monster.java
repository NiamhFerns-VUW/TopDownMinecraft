package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import imgs.Img;

interface MonsterState {
  public BufferedImage img();
  public void update(Model player, Monster enemy);
}

record MonsterAwake() implements MonsterState {
  @Override public BufferedImage img() { return Img.AwakeMonster.image; }
  @Override public void update(Model player, Monster enemy) {
    var arrow = player.camera().location().distance(enemy.location());
    double size = arrow.size();
    if(size >= 6.0d) { enemy.setMonsterState(new MonsterAsleep()); return; }
    if(size < 0.6d){ player.onGameOver(); return; }
    enemy.location(enemy.location().add(arrow.times(enemy.speed() / size)));
  }
}

record MonsterAsleep() implements MonsterState {
  @Override public BufferedImage img() { return Img.SleepMonster.image;}
  @Override public void update(Model player, Monster enemy) {
    if (player.camera().location().distance(enemy.location()).size() < 6.0d) enemy.setMonsterState(new MonsterAwake());
  }
}
record MonsterDead() implements MonsterState {
  @Override public BufferedImage img() { return Img.DeadMonster.image; }
  @Override public void update(Model player, Monster enemy) {
    if(enemy.lifetime() <= 0) player.remove(enemy);
    else enemy.lifetime(enemy.lifetime() - 1);
  }
}

class Monster implements Entity{
  private int lifetime;
  private MonsterState monsterState;
  private Point location;
  @Override public Point location(){ return location; }
  @Override public void location(Point p){ location = p; }
  Monster(Point location){
    this.location = location;
    monsterState = new MonsterAwake();
    lifetime = 100; // Lifetime in pings.
  }
  public double speed(){ return 0.05d; }
  public void setMonsterState(MonsterState s) { monsterState = s;}
  int lifetime() { return lifetime; }
  void lifetime(int l) { lifetime = l; }

  @Override public void ping(Model m){
    monsterState.update(m, this);
  }
  @Override public void draw(Graphics g, Point center, Dimension size) {
    drawImg(monsterState.img(), g, center, size);
  }
}