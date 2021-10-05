import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;

public class SpaceShip {

  private Image image;
  private int x;
  private int y;
  private int xVelocity;
  private int yVelocity;
  private int width;
  private int height;
  private int lives;

  public SpaceShip(String filePath, int x, int y, int width, int height, int xVelocity,
      int yVelocity) {
    this.x = x;
    this.y = y;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    try {
      this.image = ImageIO.read(new File(filePath));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.lives = 5;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void move() {
    y = y + yVelocity;
    x = x + xVelocity;

    if (x > 520) {
      x = 520;
      xVelocity = -3;
    }
    if (y > 495)
      y = 495;

    if (x < 0) {
      x = 0;
      xVelocity = 3;
    }

    if (y < 300)
      y = 300;

  }

  /*
   * public void keyReleased(KeyEvent e) { xVelocity = 0; yVelocity = 0; }
   * 
   * public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_LEFT) { xVelocity = -3;
   * } else if (e.getKeyCode() == KeyEvent.VK_UP) { yVelocity = -3; } else if (e.getKeyCode() ==
   * KeyEvent.VK_RIGHT) { xVelocity = 3; } else if (e.getKeyCode() == KeyEvent.VK_DOWN) { yVelocity
   * = 3; } }
   */
  public Image getImage() {
    return this.image;
  }

  public void moveBad() {
    if ((x <= 0) || x >= 520)
      xVelocity = -1 * xVelocity;

    x = x + xVelocity;
  }

  /**
   * Gets the boundaries of the SpaceShip
   * 
   * @return the boundaries
   */
  public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
  }

  public int getLives() {
    return lives;
  }

  public void shipHit() {
    lives--;
  }

  public void setLives(int lives) {
    this.lives = lives;
  }

  // trying to implement AI
  public void moveShip(ArrayList<Bullet> bulletList) {
    Iterator<Bullet> bulletItr = bulletList.iterator();
    boolean keepMoving = false;
    while (bulletItr.hasNext()) {
      try {
        Bullet bullet = bulletItr.next();
        System.out.println(bullet.getX() + " " + this.x + " "
            + ((bullet.getX() > (this.x - 40)) && (bullet.getX() < (this.x + 40))));
        if ((bullet.getX() > (this.x - 40)) && (bullet.getX() < (this.x + 40))) {
          keepMoving = true;
          if (this.x >= 520) {
            System.out.println("hello");
            this.xVelocity = -3;
          } else if (this.x < 0) {
            this.xVelocity = 3;
            System.out.println("hu");
          } else {

            if (this.x >= 520) {
              System.out.println("hello");
              this.xVelocity = -3;
            } else if (this.x < 0) {
              this.xVelocity = 3;
              System.out.println("hu");
            }
            
            Bullet closestBullet = bulletList.get(0);

            Iterator<Bullet> bulletItr2 = bulletList.iterator();
            while (bulletItr2.hasNext()) {
              Bullet nextB = bulletItr2.next();

              try {
                if (nextB != bullet) {
                  nextB.setDistance(calcDistance(nextB.getX(), this.x, nextB.getY(), this.y));

                  if (nextB.getDistance() < closestBullet.getDistance()) {
                    closestBullet = nextB;

                    if (closestBullet.getX() < this.x) {
                      this.xVelocity = 3;
                    } else if (closestBullet.getX() > this.x) {
                      this.xVelocity = -3;
                    }
                  }
                }
              } catch (Exception ee) {
                break;
              }
            }
          }
        }
      } catch (Exception e) {
        break;
      }
    }
    if (!keepMoving)
      xVelocity = 0;
    
    this.move();
  }

  private double calcDistance(int x1, int x2, int y1, int y2) {
    double tempx = x1 - x2;
    double tempy = y1 - y2;

    double squareX = tempx * tempx;
    double squareY = tempy * tempy;

    double total = squareX + squareY;

    double distance = Math.sqrt(total);
    return distance;
  }
}
