package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Phase(Model model, Controller controller){
  private static Model getPlayer(Camera c, Sword s, Cells cells, Runnable next, Runnable first) {
    return new Model() {
      List<Entity> entities = List.of(c,s);
      public Camera camera(){ return c; }
      public List<Entity> entities(){ return entities; }
      public void remove(Entity e){
        entities = entities.stream()
                .filter(ei->!ei.equals(e))
                .toList();
      }
      public void add(List<Entity> e){
        entities = List.copyOf(Stream.of(entities, e).flatMap(Collection::stream).toList());
      }
      public Cells cells(){ return cells; }
      public void onGameOver(){ first.run(); }
      public void onNextLevel(){ next.run(); }
    };
  }
  static Phase level1(Runnable next, Runnable first) {
    Camera c = new Camera(new Point(5,5));
    Sword s = new Sword(c);
    Cells cells = new Cells();
    var m = getPlayer(c, s, cells, next, first);
    m.add(List.of(new Monster(new Point(0,0))));
    return new Phase(m,new Controller(c,s));
  }
  static Phase level2(Runnable next, Runnable first) {
    Camera c = new Camera(new Point(5,5));
    Sword s = new Sword(c);
    Cells cells = new Cells();
    var m = getPlayer(c, s, cells, next, first);
    var r = new Monster(new Point(16, 16));
    r.setMonsterState(new MonsterRoaming(new Point(8, 8)));
    m.add(List.of(
            new Monster(new Point(0,0)),
            new Monster(new Point(16,0)),
            new Monster(new Point(0,16)),
            r
            ));
    return new Phase(m,new Controller(c,s));
  }
  static Phase level3(Runnable next, Runnable first) {
    Camera c = new Camera(new Point(5,5));
    Sword s = new Sword(c);
    Cells cells = new Cells();
    var m = getPlayer(c, s, cells, next, first);
    m.add(List.of(
            new Monster(new Point(8,8))
            ));
    return new Phase(m,new Controller(c,s));
  }
}