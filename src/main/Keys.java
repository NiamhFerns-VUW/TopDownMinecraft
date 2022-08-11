package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

class Keys implements KeyListener {
  private Map<Integer,Runnable> actionsPressed = new HashMap<>();
  private Map<Integer,Runnable> actionsReleased = new HashMap<>();
  public void setAction(int keyCode, Runnable onPressed, Runnable onReleased){
    actionsPressed.put(keyCode,onPressed);
    actionsReleased.put(keyCode,onReleased);
  }
  @Override // 5
  public void keyTyped(KeyEvent e){}
  @Override // 6
  public void keyPressed(KeyEvent e){
    assert SwingUtilities.isEventDispatchThread();
    actionsPressed.getOrDefault(e.getKeyCode(),()->{}).run();
  }
  @Override // 7
  public void keyReleased(KeyEvent e){
    assert SwingUtilities.isEventDispatchThread();
    actionsReleased.getOrDefault(e.getKeyCode(),()->{}).run();
  }
}