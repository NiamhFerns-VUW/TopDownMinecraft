package main;

import java.awt.event.KeyEvent;

class Controller extends Keys{
  Controller(Camera c, Sword s){
    setAction(KeyEvent.VK_F, c.set( Direction::up    ), c.set( Direction::unUp    ));
    setAction(KeyEvent.VK_S, c.set( Direction::down  ), c.set( Direction::unDown  ));
    setAction(KeyEvent.VK_R, c.set( Direction::left  ), c.set( Direction::unLeft  ));
    setAction(KeyEvent.VK_T, c.set( Direction::right ), c.set( Direction::unRight ));
    setAction(KeyEvent.VK_N, s.set( Direction::left  ), s.set( Direction::unLeft  ));
    setAction(KeyEvent.VK_E, s.set( Direction::right ), s.set( Direction::unRight ));
  }
}