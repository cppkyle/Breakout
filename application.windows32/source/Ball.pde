class Ball {
  float xSpeed;
  float ySpeed;
  float x;
  float y;
  int count;
  
  Ball() {
    xSpeed = 2.5;
    ySpeed = -2.5;
    x = width / 2;
    y = height / 2;
  }
  
  void update() {
    x = x + xSpeed;
    x = constrain(x, 5, width - 5);
    y = y + ySpeed;
    y = constrain(y, 5, height - 5);
  }
  
  void show() {
    rectMode(CENTER);
    fill(200, 72, 72);
    stroke(200, 72, 72);
    rect(x, y, 10, 10);
  }
  
  void checkCollision() {
    if (x >= width - 5 || x <= 5 || y >= height -5 || y <= 5) {
      xSpeed *= -1;
      //ySpeed *= -1;
    }
    if (y >= height -5 || y <= 5) {
      xSpeed *= -1;
      ySpeed *= -1;
    }
  }
}
