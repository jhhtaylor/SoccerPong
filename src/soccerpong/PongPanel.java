/*  Jonathan Taylor 
 Grade 12TK
 Mrs. Firman
 PAT 2016
 "Soccer Pong!"

 */
package soccerpong;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class PongPanel extends JPanel implements ActionListener, KeyListener {

    //keeps track on which game screen user is currently on
    private boolean showTitleScreen = true;
    private boolean showUserCreationOptScreen = false;
    private boolean showUserCreationInfoScreen = false;
    private boolean showSignInScreen = false;
    private boolean showHelpScreen = false;
    private boolean showStatScreen = false;
    private boolean playing = false;
    private boolean gameOver = false;

    //keeps track on user input
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    //keeps track on ball position and size
    private int ballX = 250;
    private int ballY = 250;
    private int diameter = 20;
    private int ballDeltaX = -3;
    private int ballDeltaY = 5;

    private int acc = 1;

    //keeps track of player position and size
    private int playerOneX = 25;
    private int playerOneY = 250;
    private int playerOneWidth = 10;
    private int playerOneHeight = 50;

    private int playerTwoX = 465;
    private int playerTwoY = 250;
    private int playerTwoWidth = 10;
    private int playerTwoHeight = 50;

    private int paddleSpeed = 5;

    //keeps track of players scores and stats
    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    private int gamesPlayed = 0;
    private int gamesPlayer1won = 0;
    private int gamesPlayer2won = 0;

    private BufferedImage soccerBall = null;
    private BufferedImage lhsPlayer = null;
    private BufferedImage rhsPlayer = null;
    private BufferedImage backgroundImage = null;

    private String displayUser = "Player 1";

    private boolean displayLog = false;


    //construct a PongPanel
    public PongPanel() {

        //attempts to load the png files
        try {
            soccerBall = ImageIO.read(new File("Soccerball.png"));
            lhsPlayer = ImageIO.read(new File("LHS player.png"));
            rhsPlayer = ImageIO.read(new File("RHS player.png"));
            backgroundImage = ImageIO.read(new File("grass.png"));

        } catch (IOException e) {
            System.out.println("Cannot load either soccerBall.png, LHS player.png or RHS player.png");
        }

        //listen to key presses
        setFocusable(true);
        addKeyListener(this);

        //call step() 60 fps
        Timer timer = new Timer(1000 / 60, this);
        timer.start();

    }


    //keeps track of games state and action, including movement of ball and paddle
    @Override
    public void actionPerformed(ActionEvent e) {
        if (playing) {
            //move player 1
            if (wPressed) {
                if (playerOneY - paddleSpeed > 0) {
                    playerOneY -= paddleSpeed;
                }
            }
            if (sPressed) {
                if (playerOneY + paddleSpeed + playerOneHeight < getHeight()) {
                    playerOneY += paddleSpeed;
                }
            }
            //playing against another player

            //move player 2
            if (upPressed) {
                if (playerTwoY - paddleSpeed > 0) {
                    playerTwoY -= paddleSpeed;
                }
            }
            if (downPressed) {
                if (playerTwoY + paddleSpeed + playerTwoHeight < getHeight()) {
                    playerTwoY += paddleSpeed;
                }
            }


            //Ball location after it moves is updated
            int nextBallLeft = ballX + ballDeltaX;
            int nextBallRight = ballX + diameter + ballDeltaX;
            int nextBallTop = ballY + ballDeltaY;
            int nextBallBottom = ballY + diameter + ballDeltaY;

            int playerOneRight = playerOneX + playerOneWidth + (playerOneWidth / 2);
            int playerOneTop = playerOneY;
            int playerOneBottom = playerOneY + playerOneHeight;

            float playerTwoLeft = playerTwoX + playerTwoWidth;
            float playerTwoTop = playerTwoY;
            float playerTwoBottom = playerTwoY + playerTwoHeight;

            //ball bounces off top and bottom of screen
            if (nextBallTop < 0 || nextBallBottom > getHeight()) {
                ballDeltaY *= -1;

            }

            //test if ball goes off to left
            if (nextBallLeft < playerOneRight) {
                //test if ball misses paddle
                if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

                    ballX = 250;
                    ballY = 250;
                    ballDeltaX = -3;

                    playerTwoScore++;
                    //game has been won by player 2 by getting 3 points
                    if (playerTwoScore == 3) {
                        wPressed = false;
                        sPressed = false;
                        playing = false;
                        gameOver = true;
                    }


                } else {

                    ballDeltaX = (ballDeltaX) * -1 + acc;
                }

            }

            //test if the ball will go over to the right side
            if (nextBallRight > playerTwoLeft) {
                //test if the ball will miss the paddle
                if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {
                    playerOneScore++;
                    //game has been won by player 1 by getting 3 points
                    if (playerOneScore == 3) {
                        wPressed = false;
                        sPressed = false;
                        playing = false;
                        gameOver = true;
                    }

                    ballX = 250;
                    ballY = 250;
                    ballDeltaX = -3;

                } else {
                    //ball travels in opposite direction

                    ballDeltaX = (ballDeltaX + acc) * -1;

                }

            }

            //move the ball
            ballX += ballDeltaX;
            ballY += ballDeltaY;

        }

        //tell this JPanel to repaint itself
        repaint();
    }

    //paint the game screen
    @Override
    public void paintComponent(Graphics g) {
        //as method is overwritten, super class must paint components
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //sets the background to the grass image
        g.drawImage(backgroundImage, 0, 0, null);
        //sets the colour of the writing
        g.setColor(Color.WHITE);

        //keeps track of game states and what to be displayed when
        if (showTitleScreen) {

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 72));
            g.drawString("Soccer Pong!", 20, 100);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
            g.drawString("Once playing, press 'W' and 'S' for player 1 and up and down arrow for player 2", 30, 135);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            g.drawString("Press 'C' to sign in or to create a new user.", 100, 200);
            g.drawString("Press 'P' to play.", 100, 225);
            g.drawString("Press 'H' for help.", 100, 250);
            g.drawString("Press 'S' for statistics.", 100, 275);
            g.drawString("Press 'E' to exit.", 100, 300);

        } else if (showUserCreationOptScreen) {
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString("User Creation", 150, 50);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            g.drawString("Press 'N' to create a new user.", 100, 200);
            g.drawString("Press 'L' to log in as a current user.", 100, 225);

        } else if (showUserCreationInfoScreen) {
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString("New User", 150, 50);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
            g.drawString("Press 'Enter' or click 'Ok' to continue once you have filled in your details.", 10, 400);

        } else if (showSignInScreen) {
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString("Sign in", 185, 50);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
            g.drawString("Press 'Enter' or click 'Ok' to continue once you have filled in your details.", 10, 400);

        } else if (showHelpScreen) {
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString("Help", 200, 50);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 10));
            g.drawString("About: ", 10, 100);

            g.drawString("My program is a simple game of pong, involving two vertically moving paddles on opposite sides ", 10, 120);
            g.drawString("of the screen hitting a moving ball back and forth between the two paddles. The menus and game ", 10, 130);
            g.drawString("itself will be used using certain keys (activating different menus) and the game itself will ", 10, 140);
            g.drawString("be controlled using the w and s key for player 1 and the up and down keys for player two. ", 10, 150);
            g.drawString("Once the ball goes behind player 1's paddle (LHS), player 2 (RHS) gains a point. ", 10, 160);
            g.drawString("If player 1 hits the ball behind player 2's paddle, player 1 gets a point. This ", 10, 170);
            g.drawString("will happen until either player 1 or 2 gets 3 points, the winner will then be displayed on ", 10, 180);
            g.drawString("screen. This will be achieved using simple GUIs displayed to the user in the form on", 10, 190);
            g.drawString("interconnected menus and screens.", 10, 200);

            g.drawString("FAQ: ", 10, 220);
            g.drawString("1. How to win:", 10, 230);
            g.drawString("Reach 3 points by hitting the ball behind the other player's paddle. ", 10, 240);
            g.drawString("2. How to use the menus: ", 10, 250);
            g.drawString("Press the specified key of each menu e.g. press H for help menu.", 10, 260);
            g.drawString(" 3. Who is the developer?", 10, 270);
            g.drawString("Jonathan Taylor 12TK, with the help of Mrs. Firman, the internet and lots of coffee.", 10, 280);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            g.drawString("Press 'Space' to exit to the starting screen", 10, 350);

        } else if (showStatScreen) {
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString("Statistics", 200, 30);

            //Read from file 'Stat.txt' for the statistics of the game using Scanner class
            try {
                Scanner myScanner;
                if (displayLog) {
                    myScanner = new Scanner(new File(displayUser + "Stat.txt"));
                } else {
                    myScanner = new Scanner(new File("Statistics.txt"));
                }

                while (myScanner.hasNextInt()) {
                    gamesPlayed = myScanner.nextInt();
                    g.drawString("Games Played: " + gamesPlayed, 10, 100);

                    gamesPlayer1won = myScanner.nextInt();
                    g.drawString("Games " + displayUser + " won: " + gamesPlayer1won, 10, 150);

                    gamesPlayer2won = myScanner.nextInt();
                    g.drawString("Games Player 2 won: " + gamesPlayer2won, 10, 200);

                }

            } catch (FileNotFoundException ex) {
                System.out.println("Cannot open file");
            }
            //tell user how to exit to starting screen
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            g.drawString("Press space to exit to starting screen.", 10, 400);

        } else if (playing) {
            int playerOneRight = playerOneX + playerOneWidth;
            int playerTwoLeft = playerTwoX;

            //draw line down center
            for (int lineY = 0; lineY < getHeight(); lineY += 1) {
                g.drawLine(250, lineY, 250, lineY + 25);
            }

            //display user at top of screen
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString(displayUser, 100, 40);
            g.drawString("Player 2", 300, 40);

            //draw center starting circle
            g.drawOval(200, 200, 100, 100);

            //draw "goal lines" on each side
            g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
            g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

            //draw the scores
            g.drawString(String.valueOf(playerOneScore), 100, 100);
            g.drawString(String.valueOf(playerTwoScore), 400, 100);

            //draws a soccer ball from the img png
            g.drawImage(soccerBall, ballX - 10, ballY - 10, 20, 20, this);

            //draw the paddles
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 9));

            String p1dn = "France";
            g.setColor(Color.BLUE);
            g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);

            g.setColor(Color.WHITE);
            //Draws the wording down the side of the blue paddle
            for (int i = 0; i < p1dn.length(); i++) {
                g.drawString(p1dn.charAt(i) + "\n", playerOneX + 2, (6 * i) + playerOneY + 10);

            }

            String p2dn = "Portugal";
            g.setColor(Color.RED);
            g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
            g.setColor(Color.WHITE);
            //Draws the wording down the side of the red paddle
            for (int i = 0; i < p2dn.length(); i++) {
                g.drawString(p2dn.charAt(i) + "\n", playerTwoX + 2, (6 * i) + playerTwoY + 10);

            }

            //draw player images
            g.setColor(Color.WHITE);
            g.drawImage(lhsPlayer, playerOneX + (playerTwoWidth / 2), playerOneY, this);
            g.drawImage(rhsPlayer, playerTwoX - (3 * playerTwoWidth), playerTwoY, this);

        } else if (gameOver) {
            //tell user who won and how to exit to starting screen
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString(String.valueOf(playerOneScore), 100, 100);
            g.drawString(String.valueOf(playerTwoScore), 400, 100);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            if (playerOneScore > playerTwoScore) {
                g.drawString(displayUser + " wins!", 165, 200);

            } else {
                g.drawString("Player 2 Wins!", 165, 200);

            }

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            g.drawString("Press space to exit to starting screen.", 100, 400);

        }
    }

    //empty method, but required as PongPanel Implements KeyListener (must have all original methods)
    @Override
    public void keyTyped(KeyEvent e) {
    }

    //listens for key presses, triggers change in game state and ...Pressed booleans when key press occurs
    @Override
    public void keyPressed(KeyEvent e) {
        if (showTitleScreen) {   //if user wants to create a new user account
            switch (e.getKeyCode()) {
                case KeyEvent.VK_C:
                    showTitleScreen = false;
                    showUserCreationOptScreen = true;
                    break;
                case KeyEvent.VK_P:
                    showTitleScreen = false;
                    playing = true;
                    break;
                case KeyEvent.VK_H:
                    showTitleScreen = false;
                    showHelpScreen = true;
                    break;
                case KeyEvent.VK_S:
                    showTitleScreen = false;
                    showStatScreen = true;
                    break;
                case KeyEvent.VK_E:
                    System.exit(0);
                default:
                    break;
            }
        } else if (showUserCreationOptScreen) {
            //if user has not signed in before
            switch (e.getKeyCode()) {
                case KeyEvent.VK_N: {
                    showUserCreationOptScreen = false;
                    showUserCreationInfoScreen = true;
                    String username = "";
                    String password = "";
                    username = JOptionPane.showInputDialog("Enter a new user name:");
                    password = JOptionPane.showInputDialog("Enter a new password:");
                    //Writing new user to 'Users.txt', note the 'true' parameter after the file in FileWriter
                    //this means the file is not replaced with new content, but keeping the existing content
                    //and append the new content in the end of the file.
                    try (BufferedWriter br = new BufferedWriter(new FileWriter("Users.txt", true))) {
                        //insert scores into users text file
                        br.newLine();
                        //encrypts the line using the bitshifter class
                        br.write(BitShifter.encrypt("" + username + "#####" + password + "#####"));

                    } catch (IOException q) {
                        System.out.println("Unable to read file: Users.txt");
                    }       //create a new stat file with the new username + "Stat.txt" in the project folder
                    try {
                        //stats: 0 gamespalyed, 0 games player one won, 0 games player two won
                        String content = "0\n0\n0";

                        File file = new File(username + "Stat.txt");

                        // if file doesnt exists, then create it
                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        FileWriter fw = new FileWriter(file.getAbsoluteFile());
                        try (BufferedWriter bw = new BufferedWriter(fw)) {
                            bw.write(content);
                        }

                        System.out.println("File successfully created and 0 stats written to!");

                    } catch (IOException newFile) {
                        System.out.println("Could not create file: " + username + "Stat.txt");
                    }       //new user array class
                    UserArray UArrcl = new UserArray();
                    //start the player playing the game, signed in
                    showUserCreationInfoScreen = false;
                    playing = true;
                    displayUser = username;
                    System.out.println("Logged in as: '" + username + "'!");
                    //Read from file username+'Stat.txt' for the statistics of the game using Scanner class
                    try {
                        Scanner myScanner = new Scanner(new File(username + "Stat.txt"));

                        while (myScanner.hasNextInt()) {
                            gamesPlayed = myScanner.nextInt();

                            gamesPlayer1won = myScanner.nextInt();

                            gamesPlayer2won = myScanner.nextInt();

                        }

                    } catch (FileNotFoundException ex) {
                        System.out.println("Cannot open stat file for user");
                    }
                    break;
                }
                case KeyEvent.VK_L: {
                    showUserCreationOptScreen = false;
                    showSignInScreen = true;
                    String username = "";
                    String password = "";
                    UserArray UArrcl = new UserArray();
                    int i = 0;
                    boolean isLoggedIn = false;
                    while (!isLoggedIn) {
                        username = JOptionPane.showInputDialog("Enter your username:");

                        password = JOptionPane.showInputDialog("Enter your password:");

                        //searching for user in the user array created from the users text file
                        if (UArrcl.searchForUser(username, password)) {
                            //start the player playing the game, signed in
                            showSignInScreen = false;
                            playing = true;

                            displayUser = username;

                            System.out.println("Logged in!");
                            isLoggedIn = true;

                            displayLog = true;

                            //Read from file 'Statistics.txt' for the statistics of the game using Scanner class
                            try {

                                Scanner myScanner = new Scanner(new File(username + "Stat.txt"));

                                while (myScanner.hasNextInt()) {
                                    gamesPlayed = myScanner.nextInt();

                                    gamesPlayer1won = myScanner.nextInt();

                                    gamesPlayer2won = myScanner.nextInt();

                                }

                            } catch (FileNotFoundException ex) {
                                System.out.println("Cannot open stat file for user");
                            }

                        } //testing to see how many times th euser has tried to log in
                        else {//if they get it wrong too many times, the program closes
                            if (i == 2) {
                                JOptionPane.showMessageDialog(null, "You have tried too many times. SoccerPong will now shut down.");
                                System.exit(0);
                            }//asks user to try again
                            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again!");
                            i += 1;
                        }
                    }
                    break;
                } //should probably put all of these at end of method
                case KeyEvent.VK_SPACE:
                    showUserCreationOptScreen = false;
                    showTitleScreen = true;
                    break;
                default:
                    break;
            }
        } else if (showUserCreationInfoScreen) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                showUserCreationInfoScreen = false;
                showUserCreationOptScreen = true;

            }
        } else if (showSignInScreen) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                showSignInScreen = false;
                showUserCreationInfoScreen = true;
            }
        } else if (showHelpScreen) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                showTitleScreen = true;
                showHelpScreen = false;
            }
        } else if (showStatScreen) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                showTitleScreen = true;
                showStatScreen = false;

            }
        } else if (playing) {

            if (!displayLog) {
                //Read from file 'Statistics.txt' for the statistics of the game using Scanner class
                try {

                    Scanner myScanner = new Scanner(new File("Statistics.txt"));

                    while (myScanner.hasNextInt()) {
                        gamesPlayed = myScanner.nextInt();

                        gamesPlayer1won = myScanner.nextInt();

                        gamesPlayer2won = myScanner.nextInt();

                    }

                } catch (FileNotFoundException ex) {
                    System.out.println("Cannot open Statistics file");
                }
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    wPressed = true;
                    break;
                case KeyEvent.VK_S:
                    sPressed = true;
                    break;
                case KeyEvent.VK_UP:
                    upPressed = true;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = true;
                    break;
                default:
                    break;
            }
        } else if (gameOver) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (playerOneScore > playerTwoScore) {
                    System.out.println(displayUser + " won!");

                    gamesPlayed++;
                    gamesPlayer1won++;
                    System.out.println(gamesPlayed + " " + gamesPlayer1won);
                    //Writing updated scores to current user signed in 'Stat.txt'
                    if (displayLog) {
                        try (BufferedWriter br = new BufferedWriter(new FileWriter(displayUser + "Stat.txt"))) {
                            //insert score and then skip a line and write the next score, can then be read from later
                            br.write("" + gamesPlayed + " ");
                            br.newLine();
                            br.write("" + gamesPlayer1won + " ");
                            br.newLine();
                            br.write("" + gamesPlayer2won);
                        } catch (IOException q) {
                            System.out.println("Unable to read file: " + displayUser + "Stat.txt");
                        }
                    } else {
                        try (BufferedWriter br = new BufferedWriter(new FileWriter("Statistics.txt"))) {
                            //insert score and then skip a line and write the next score, can then be read from later
                            br.write("" + gamesPlayed + " ");
                            br.newLine();
                            br.write("" + gamesPlayer1won + " ");
                            br.newLine();
                            br.write("" + gamesPlayer2won);
                        } catch (IOException q) {
                            System.out.println("Unable to read file: Statistics.txt");
                        }
                    }

                } else {
                    System.out.println("Player two won");

                    gamesPlayed++;
                    gamesPlayer2won++;
                    System.out.println(gamesPlayed + " " + gamesPlayer2won);
                    //Writing updated scores to 'Statistics.txt'
                    //Writing updated scores to current user signed in 'Stat.txt'
                    if (displayLog) {
                        try (BufferedWriter br = new BufferedWriter(new FileWriter(displayUser + "Stat.txt"))) {
                            //insert score and then skip a line and write the next score, can then be read from later
                            br.write("" + gamesPlayed + " ");
                            br.newLine();
                            br.write("" + gamesPlayer1won + " ");
                            br.newLine();
                            br.write("" + gamesPlayer2won);
                        } catch (IOException q) {
                            System.out.println("Unable to read file: " + displayUser + "Stat.txt");
                        }
                    } else {
                        try (BufferedWriter br = new BufferedWriter(new FileWriter("Statistics.txt"))) {
                            //insert score and then skip a line and write the next score, can then be read from later
                            br.write("" + gamesPlayed + " ");
                            br.newLine();
                            br.write("" + gamesPlayer1won + " ");
                            br.newLine();
                            br.write("" + gamesPlayer2won);
                        } catch (IOException q) {
                            System.out.println("Unable to read file: Statistics.txt");
                        }
                    }

                }
                //reset all scores, screens and positions for next game
                gameOver = false;
                showTitleScreen = true;
                playerOneY = 250;
                playerTwoY = 250;
                ballX = 250;
                ballY = 250;
                playerOneScore = 0;
                playerTwoScore = 0;
            }
        }
    }

    //listens for key release, stops movement of paddles when specific key is released
    @Override
    public void keyReleased(KeyEvent e) {
        if (playing) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    wPressed = false;
                    break;
                case KeyEvent.VK_S:
                    sPressed = false;
                    break;
                case KeyEvent.VK_UP:
                    upPressed = false;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = false;
                    break;
                default:
                    break;
            }
        }
    }

}
