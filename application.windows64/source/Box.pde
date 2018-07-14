class Box {

  Boolean isDead;
  Boolean didHit;
  float x;
  float y;

  Box() {
    isDead = false;
    didHit = false;
  }

  void show(int col, int row) {
    if (!isDead) {
      makeColor(row);
      rectMode(CORNER);
      rect(col*20, row*20, 20, 20);
      x = col*20;
      y = row*20;
    }
  }

  void checkCollision(Ball b) {
    if (b.x + 5 >= x && b.x - 5 <= x + 20 && b.y + 5 >= y && b.y - 5 <= y + 20) {
      isDead = true;
      x = 5000;
      y = 5000;
      b.ySpeed *= -1;
      b.xSpeed *= -1;
      didHit = true;
    }
  }

  void makeColor(int row) {
    stroke(0);
    if (row == 0) {
      fill(200, 72, 72);
      stroke(200, 72, 72);
    } else if (row == 1) {
      fill(198, 108, 58);
      stroke(198, 108, 58);
    } else if (row == 2) {
      fill(180, 122, 48);
      stroke(180, 122, 48);
    } else if (row == 3) {
      fill(162, 162, 42);
      stroke(162, 162, 42);
    } else if (row == 4) {
      fill(72, 160, 72);
      stroke(72, 160, 72);
    } else if (row == 5) {
      fill(66, 72, 200);
      stroke(66, 72, 200);
    }
  }
}
