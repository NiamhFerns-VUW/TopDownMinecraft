package main;

import java.awt.Dimension;
import java.awt.Graphics;

import imgs.Img;

class Monster implements Entity{
  private Point location;
  @Override // 1
  public Point location(){ return location; }
  @Override // 2
  public void location(Point p){ location = p; }
  Monster(Point location){ this.location = location; }
  public double speed(){ return 0.05d; }

  @Override // 3
  public void ping(Model m){
    var arrow = m.camera().location().distance(location);
    double size = arrow.size();
    arrow = arrow.times(speed() / size);
    location = location.add(arrow); 
    if(size < 0.6d){ m.onGameOver(); }
  }

  public double chaseTarget(Monster outer, Point target){
    var arrow = target.distance(outer.location());
    double size = arrow.size();
    arrow = arrow.times(speed() / size);
    outer.location(outer.location().add(arrow));
    return size;
  }
  @Override // 4
  public void draw(Graphics g, Point center, Dimension size) {
    drawImg(Img.AwakeMonster.image, g, center, size);
  }
}