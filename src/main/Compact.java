package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

class Compact extends JFrame{
  private static final long serialVersionUID = 1L;
  Runnable closePhase = ()->{};
  Phase currentPhase;
  private Map<String, Integer> bindings;
  Compact(){
    assert SwingUtilities.isEventDispatchThread();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    phaseZero();
    setVisible(true);
    addWindowListener(new WindowAdapter(){
      public void windowClosed(WindowEvent e){closePhase.run();}
    });
    bindings = new HashMap<>();
  }

  // Bindings will just be set to whatever the last character is.
  private void setBindings(Map<String, JTextField> fields) {
    fields.forEach((e, f) -> {
      if(!f.getText().isEmpty())
        bindings.put(e, KeyEvent.getExtendedKeyCodeForChar(f.getText().charAt(f.getText().length() - 1)));
    });
  }

  private void phaseZero() {
    var welcome = new JLabel("Welcome to Compact. A compact Java game!");
    var start = new JButton("Start!");
    var p = new JPanel();
    p.setLayout(new GridLayout(0, 2));
    Map<String, JTextField> fields = new HashMap<>();
    fields.put("Up", new JTextField("W"));
    fields.put("Down", new JTextField("S"));
    fields.put("Left", new JTextField("A"));
    fields.put("Right", new JTextField("D"));
    fields.put("SLeft", new JTextField("O"));
    fields.put("SRight", new JTextField("P"));
    closePhase.run();
    closePhase=()->{
     remove(welcome);
     remove(start);
     remove(p);
     };
    fields.forEach((s, f) -> {p.add(new JLabel(s)); p.add(f);});
    add(BorderLayout.NORTH,welcome);
    add(BorderLayout.SOUTH,start);
    add(BorderLayout.CENTER,p);
    start.addActionListener(e -> { setBindings(fields); phaseOne(); });
    setPreferredSize(new Dimension(800,400));
    pack();
  }
  private void phaseOne(){
    setPhase(Phase.level1(this::phaseTwo,this::phaseZero,bindings));
  }
  private void phaseTwo(){
    setPhase(Phase.level2(this::phaseThree,this::phaseZero,bindings));
  }
  private void phaseThree(){
    setPhase(Phase.level3(this::phaseEnd,this::phaseZero,bindings));
  }

  private void phaseEnd() {
    var welcome = new JLabel("Victory!");
    closePhase.run();
    closePhase=()->{
      remove(welcome);
    };
    add(BorderLayout.CENTER,welcome);
    setPreferredSize(new Dimension(800,400));
    pack();
  }
  void setPhase(Phase p){
    //set up the viewport and the timer
    Viewport v = new Viewport(p.model());
    v.addKeyListener(p.controller());
    v.setFocusable(true);
    Timer timer = new Timer(34,unused->{
      assert SwingUtilities.isEventDispatchThread();
      p.model().ping();
      v.repaint();
    });
    closePhase.run();//close phase before adding any element of the new phase
    closePhase=()->{ timer.stop(); remove(v); };
    add(BorderLayout.CENTER,v);//add the new phase viewport
    setPreferredSize(getSize());//to keep the current size
    pack();                     //after pack
    v.requestFocus();//need to be after pack
    timer.start();
  }
}