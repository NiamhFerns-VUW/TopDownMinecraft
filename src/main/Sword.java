package main;

import java.awt.Dimension;
import java.awt.Graphics;

import imgs.Img;

class Sword extends ControllableDirection implements Entity{
  private Entity wielder;
  private double weaponRadiant = 0;
  public double distance(){ return wielder instanceof  Monster ? 1.5d : 0.8d; }
  public double speed(){ return wielder instanceof  Monster ? 0.4d : 0.2d; }
  Sword(Entity wielder){ this.wielder=wielder; if(wielder instanceof Monster) { this.direction(Direction.Left); } }
  @Override public Point location(){
    var dir = new Point(Math.sin(weaponRadiant),Math.cos(weaponRadiant));
    return dir.times(distance()).add(wielder.location());
  }
  public void onHit(Model m, Entity e){
    if(e instanceof Monster){ ((Monster) e).setMonsterState(new MonsterDead()); }
    if(e instanceof Camera){ m.onGameOver(); }
  }

  public double effectRange(){ return 0.3d; }

  @Override public void ping(Model m){
    weaponRadiant+=direction().arrow(speed()).x();
    weaponRadiant%=Math.PI*2d;
    var l = this.location();
    m.entities().stream()
      .filter(e->e!=this)
      .filter(e->e.location().distance(l).size()<effectRange())
      .forEach(e->onHit(m,e));
    if (wielder instanceof Monster && ((Monster) wielder).getMonsterState().equals("MonsterDead")) { m.remove(this); }
  }
  @Override public void draw(Graphics g, Point center, Dimension size) {
    drawImg(Img.Sword.image, g, center, size);
  }
}