class Paddle {
  float x;
  float y;
  float speed;

  Paddle() {
    x = width / 2;
    y = height - (height /8);
    speed = 10;
  }

  void update() {
    rectMode(CENTER);
    fill(200, 72, 72);
    stroke(200, 72, 72);
    x = constrain(x, 50, width - 50);
    rect(x, y, 100, 10);
  }

  void checkCollision(Ball b) {
    if (b.x + 5 >= x - 50 && b.x + 5 <= x + 50 && b.y + 5 >= y - 5 && b.y + 5 <= y + 5) {
      b.ySpeed *= -1;
    }
  }
  
}
