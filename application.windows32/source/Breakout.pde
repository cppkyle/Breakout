PFont retroFont;
int cols;
int rows;
int lives;
float lastX;
Boolean isPlaying;
Boolean isDead;
Boolean isGameWon;
Box[][] boxes;
Paddle paddle;
Ball ball;

void setup() {
  size(640, 360);
  background(0);
  cols = 32;
  rows = 6;
  isPlaying = false;
  isDead = false;
  isGameWon = false;
  noCursor();
  noSmooth();
  retroFont = createFont("prstartk.ttf", 32);
  textFont(retroFont);
  newGame();
}

void draw() {
  if (!isPlaying && !isDead) {
    background(0);
    drawBoxes();
    rectMode(CENTER);
    fill(200, 72, 72);
    stroke(200, 72, 72);
    rect(width / 2, height - (height /8), 100, 10);
    ball.show();
    textAlign(CENTER);
    fill(255);
    textSize(15);
    text("Press the Space Button to play", width / 2, height / 2 - 25);
  } else if (isPlaying && isDead) {
    background(0);
    drawBoxes();
    printLives();
    rectMode(CENTER);
    fill(200, 72, 72);
    stroke(200, 72, 72);
    rect(lastX, height - (height /8), 100, 10);
    ball.show();
    textAlign(CENTER);
    fill(255);
    textSize(15);
    text("Press the Space Button to revive", width / 2, height / 2 - 25);
  } else if (!isPlaying && isDead) {
    background(0);
    drawBoxes();
    printLives();
    rectMode(CENTER);
    fill(200, 72, 72);
    stroke(200, 72, 72);
    rect(width / 2, height - (height /8), 100, 10);
    ball.show();
    textAlign(CENTER);
    fill(255);
    textSize(15);
    text("Game Over! Out of Lives!", width / 2, height / 2 - 25);
  } else if (isGameWon) {
    background(0);
    drawBoxes();
    printLives();
    rectMode(CENTER);
    fill(200, 72, 72);
    stroke(200, 72, 72);
    rect(lastX, height - (height /8), 100, 10);
    ball.show();
    textAlign(CENTER);
    fill(255);
    textSize(15);
    text("You Won!", width / 2, height / 2 - 25);
  } else {
    background(0);
    drawBoxes();
    printLives();
    paddle.update();
    ball.update();
    checkCollision();
    checkIfDead();
    checkWin();
    ball.show();
  }
}

void drawBoxes() {
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      boxes[i][j].show(i, j);
    }
  }
}

void checkCollision() {
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      boxes[i][j].checkCollision(ball);
    }
  }

  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      if (boxes[i][j].didHit) {
        ball.ySpeed *= -1;
        for (int k = 0; k < cols; k++) {
          for (int l = 0; l < rows; l++) {
            boxes[k][l].didHit = false;
          }
        }
      }
    }
  }

  paddle.checkCollision(ball);
  ball.checkCollision();
}

void keyReleased() {
  paddle.x += 0;
}

void keyPressed() {
  if (isPlaying && !isDead) {
    if (key == CODED) {
      if (keyCode == LEFT) {
        paddle.x -= paddle.speed;
      } else if (keyCode == RIGHT) {
        paddle.x += paddle.speed;
      }
    }
    if (key == 'a' || key == 'A') {
      paddle.x -= paddle.speed;
    } else if (key == 'd' || key == 'D') {
      paddle.x += paddle.speed;
    }
  }

  if (key == ' ') {
    if (!isPlaying && !isDead) {
      isPlaying = true;
      newGame();
    } else if (isPlaying && isDead) {
      isDead = false;
    } else if (!isPlaying && isDead) {
      isDead = false;
      newGame();
    } else if (isGameWon) {
      isPlaying = false;
      isGameWon = false;
      isDead = false;
      newGame();
    }
  }
}

void newGame() {
  boxes = new Box[cols][rows];
  paddle = new Paddle();
  ball = new Ball();
  lives = 3;
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      boxes[i][j] = new Box();
    }
  }
}

void checkIfDead() {
  if (ball.y >= height -5) {
    lives--;
    if (lives <= 0) {
      isPlaying = false;
    }
    isDead = true;
    ball.x = width / 2;
    ball.y = height / 2;
    lastX = paddle.x;
  }
}

void printLives() {
    textAlign(LEFT);
    fill(255);
    textSize(12);
    text("Lives: " + lives, 12, height - 12);
}

void checkWin() {
  int deadBoxes = 0;
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      if (boxes[i][j].isDead) {
        deadBoxes++;
      }
    }
  }
  if (deadBoxes == 192) {
    isGameWon = true;
  }
}
