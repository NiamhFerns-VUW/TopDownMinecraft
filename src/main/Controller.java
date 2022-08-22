package main;

import java.awt.event.KeyEvent;
import java.util.Map;

class Controller extends Keys{

  Controller(Camera c, Sword s, Map<String, Integer> bindings){
    setAction(bindings.getOrDefault("Up"    , KeyEvent.VK_W), c.set( Direction::up    ), c.set( Direction::unUp    ));
    setAction(bindings.getOrDefault("Down"  , KeyEvent.VK_S), c.set( Direction::down  ), c.set( Direction::unDown  ));
    setAction(bindings.getOrDefault("Left"  , KeyEvent.VK_A), c.set( Direction::left  ), c.set( Direction::unLeft  ));
    setAction(bindings.getOrDefault("Right" , KeyEvent.VK_D), c.set( Direction::right ), c.set( Direction::unRight ));
    setAction(bindings.getOrDefault("SLeft" , KeyEvent.VK_O), s.set( Direction::left  ), s.set( Direction::unLeft  ));
    setAction(bindings.getOrDefault("SRight", KeyEvent.VK_P), s.set( Direction::right ), s.set( Direction::unRight ));
 }
}