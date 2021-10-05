import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

  private SpaceShip goodShip;
  private SpaceShip badShip;
  private Image background;
  private ArrayList<Bullet> bulletList;
  private int numBullets;

  public Game() {
    try {
      this.background = ImageIO.read(new File("img/background.png"));
    } catch (IOException e1) {
    }
    goodShip = new SpaceShip("img/badufo.png", 270, 495, 60, 60, 0, 0);
    badShip = new SpaceShip("img/ufo.png", 270, 0, 60, 60, 5, 0);
    bulletList = new ArrayList<Bullet>();
    numBullets = 0;
    /*
     * addKeyListener(new KeyListener() {
     * 
     * @Override public void keyTyped(KeyEvent e) { }
     * 
     * @Override public void keyReleased(KeyEvent e) { goodShip.keyReleased(e); numBullets = 0; }
     * 
     * @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_SPACE) { if
     * (numBullets <= 15) { spacePressed(); numBullets++; } } else { goodShip.keyPressed(e); } }
     * 
     * });
     */
    setFocusable(true);
  }

  public void spacePressed() {
    Bullet newBullet =
        new Bullet("img/bullet.png", goodShip.getX() + 30, goodShip.getY(), 10, 10, true);
    bulletList.add(newBullet);
  }

  public void move() {
    this.goodShip.moveShip(bulletList);
    this.badShip.moveBad();
    Iterator<Bullet> bulletItr = bulletList.iterator();
    while (bulletItr.hasNext()) {
      try {
        Bullet nextBullet = bulletItr.next();
        nextBullet.move();
      } catch (Exception e) {
        break;
      }
    }
  }

  public void shoot() {
    Random rnd = new Random();
    int num = rnd.nextInt(32);

    if (num % 19 == 0) {
      Bullet newBullet =
          new Bullet("img/bullet2.png", badShip.getX() + 30, badShip.getY() + 60, 10, 10, false);
      bulletList.add(newBullet);
    }
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.drawImage(background, 0, 0, 600, 600, this);
    g2d.drawImage(goodShip.getImage(), goodShip.getX(), goodShip.getY(), 60, 60, this);
    g2d.drawImage(badShip.getImage(), badShip.getX(), 0, 60, 60, this);

    shoot();
    Iterator<Bullet> bulletItr = bulletList.iterator();
    while (bulletItr.hasNext()) {
      Bullet bullet = bulletItr.next();
      if (bullet.getY() > 0 && !bullet.hasHit()) {
        g2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), 10, 10, this);

        if (!bullet.isGoodBullet() && bullet.intersects2(goodShip)) {
          if (goodShip.getLives() <= 0) {
            goodShip.setLives(0);
            gameOver(0);
          }
        } else if (bullet.isGoodBullet() && bullet.intersects(badShip)) {
          if (badShip.getLives() <= 0) {
            badShip.setLives(0);
            gameOver(1);
          }
        }
      }else if(bullet.getY() <= 0) {
        bulletList.remove(bullet);
      }else {
        continue;
      }
    }
    g2d.setColor(Color.WHITE);g2d.setFont(new Font("Verdana",Font.BOLD,18));g2d.drawString("Your Lives: "+goodShip.getLives(),10,30);g2d.drawString("Enemy Lives: "+badShip.getLives(),10,60);

  }

  public void gameOver(int type) {
    String message = "";
    if (type == 0)
      message = "You Lost";
    else
      message = "You Won";

    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.YES_NO_OPTION);
    System.exit(ABORT);
  }

  public static void main(String[] args) throws InterruptedException {
    JFrame frame = new JFrame("Space Invaders");
    Game game = new Game();
    frame.add(game);
    frame.setSize(600, 600);
    Image image = null;

    try {
      image = ImageIO.read(new File("img/badufo.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    frame.setIconImage(image);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    while (true) {
      game.move();
      game.repaint();
      Thread.sleep(10);
    }

  }
}
