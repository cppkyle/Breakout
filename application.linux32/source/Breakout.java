import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Breakout extends PApplet {

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

public void setup() {
  
  background(0);
  cols = 32;
  rows = 6;
  isPlaying = false;
  isDead = false;
  isGameWon = false;
  noCursor();
  
  retroFont = createFont("prstartk.ttf", 32);
  textFont(retroFont);
  newGame();
}

public void draw() {
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

public void drawBoxes() {
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      boxes[i][j].show(i, j);
    }
  }
}

public void checkCollision() {
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

public void keyReleased() {
  paddle.x += 0;
}

public void keyPressed() {
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

public void newGame() {
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

public void checkIfDead() {
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

public void printLives() {
    textAlign(LEFT);
    fill(255);
    textSize(12);
    text("Lives: " + lives, 12, height - 12);
}

public void checkWin() {
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
class Ball {
  float xSpeed;
  float ySpeed;
  float x;
  float y;
  int count;
  
  Ball() {
    xSpeed = 2.5f;
    ySpeed = -2.5f;
    x = width / 2;
    y = height / 2;
  }
  
  public void update() {
    x = x + xSpeed;
    x = constrain(x, 5, width - 5);
    y = y + ySpeed;
    y = constrain(y, 5, height - 5);
  }
  
  public void show() {
    rectMode(CENTER);
    fill(200, 72, 72);
    stroke(200, 72, 72);
    rect(x, y, 10, 10);
  }
  
  public void checkCollision() {
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
class Box {

  Boolean isDead;
  Boolean didHit;
  float x;
  float y;

  Box() {
    isDead = false;
    didHit = false;
  }

  public void show(int col, int row) {
    if (!isDead) {
      makeColor(row);
      rectMode(CORNER);
      rect(col*20, row*20, 20, 20);
      x = col*20;
      y = row*20;
    }
  }

  public void checkCollision(Ball b) {
    if (b.x + 5 >= x && b.x - 5 <= x + 20 && b.y + 5 >= y && b.y - 5 <= y + 20) {
      isDead = true;
      x = 5000;
      y = 5000;
      b.ySpeed *= -1;
      b.xSpeed *= -1;
      didHit = true;
    }
  }

  public void makeColor(int row) {
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
class Paddle {
  float x;
  float y;
  float speed;

  Paddle() {
    x = width / 2;
    y = height - (height /8);
    speed = 10;
  }

  public void update() {
    rectMode(CENTER);
    fill(200, 72, 72);
    stroke(200, 72, 72);
    x = constrain(x, 50, width - 50);
    rect(x, y, 100, 10);
  }

  public void checkCollision(Ball b) {
    if (b.x + 5 >= x - 50 && b.x + 5 <= x + 50 && b.y + 5 >= y - 5 && b.y + 5 <= y + 5) {
      b.ySpeed *= -1;
    }
  }
  
}
  public void settings() {  size(640, 360);  noSmooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#8E8E8E", "--hide-stop", "Breakout" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
