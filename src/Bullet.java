import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bullet {
  private Image image;
  private int x;
  private int y;
  private int velocity;
  private int height;
  private int width;
  private boolean hasHit;
  private boolean goodBullet;
  private double distance;

  public Bullet(String filePath, int x, int y, int width, int height, boolean goodBullet) {
    velocity = 5;
    this.x = x;
    this.y = y;
    this.goodBullet = goodBullet;
    try {
      this.image = ImageIO.read(new File(filePath));
    } catch (IOException e) {
    }
    this.height = height;
    this.width = width;
    hasHit = false;
    this.distance = 10000;
  }

  public Image getImage() {
    return this.image;
  }

  public void move() {
    if (goodBullet == true)
      y = y - velocity;
    else
      y = y + velocity;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  /**
   * Gets the boundaries of the SpaceShip
   * 
   * @return the boundaries
   */
  public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
  }

  /**
   * Sees if the fish is intersecting a
   * 
   * @param s - the rock
   * @return true if the fish intersects a rock
   */
  public boolean intersects(SpaceShip s) {
    int sX = s.getX();
    boolean check1 = false;
    boolean check2 = false;
    boolean check3 = false;
    boolean check4 = false;

    if (x < sX + 60)
      check1 = true;
    if (x > sX - 20)
      check2 = true;
    if (y > 0)
      check3 = true;
    if (y < 60)
      check4 = true;

    if (check1 && check2 && check3 && check4) {
      s.shipHit();
      hasHit = true;
      return true;
    }
    return false;
  }

  /**
   * Sees if the fish is intersecting a
   * 
   * @param s - the rock
   * @return true if the fish intersects a rock
   */
  public boolean intersects2(SpaceShip s) {
    int sX = s.getX();
    int sY = s.getY();
    boolean check1 = false;
    boolean check2 = false;
    boolean check3 = false;
    boolean check4 = false;

    if (x < sX + 50)
      check1 = true;
    if (x > sX - 10)
      check2 = true;
    if (y < sY + 30)
      check3 = true;
    if (y > sY - 15)
      check4 = true;

    if (check1 && check2 && check3 && check4) {
      s.shipHit();
      hasHit = true;
      return true;
    }
    return false;
  }

  public boolean hasHit() {
    return hasHit;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public boolean isGoodBullet() {
    return this.goodBullet;
  }

  public double getDistance() {
    return this.distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }
}
