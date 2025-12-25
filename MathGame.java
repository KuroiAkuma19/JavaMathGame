import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MathGame extends JFrame {
    private int score = 0, currentLevel = 1, questionsDone = 0, correctAnswer;
    private int timeLeft = 15;

    private JLabel questionLabel = new JLabel("Ready?", SwingConstants.CENTER);
    private JLabel statusLabel = new JLabel("Level 1: Addition", SwingConstants.CENTER);
    private JTextField answerField = new JTextField();
    private Timer gameTimer;
    private Random random = new Random();

    public MathGame() {
        // Basic Window Settings
        setTitle("Math Game");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Styling
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        answerField.setHorizontalAlignment(JTextField.CENTER);

        // Add components to window
        add(statusLabel);
        add(questionLabel);
        add(answerField);

        // Timer logic: runs every 1 second
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0)
                endGame("Time's up!");
        });

        // Checks answer when user presses Enter
        answerField.addActionListener(e -> checkAnswer());

        setVisible(true);
        nextQuestion();
    }

    private void nextQuestion() {
        if (questionsDone >= 3) {
            currentLevel++;
            questionsDone = 0;
            if (currentLevel > 3) {
                endGame("You Win!");
                return;
            }
        }

        int range = (currentLevel == 1) ? 10 : (currentLevel == 2 ? 12 : 50);
        String op = (currentLevel == 1) ? "+" : (currentLevel == 2 ? "*" : (random.nextBoolean() ? "+" : "-"));

        int n1 = random.nextInt(range) + 1;
        int n2 = random.nextInt(range) + 1;
        correctAnswer = op.equals("+") ? n1 + n2 : (op.equals("*") ? n1 * n2 : n1 - n2);

        questionLabel.setText(n1 + " " + op + " " + n2 + " = ?");
        answerField.setText("");
        statusLabel.setText("Level: " + currentLevel + " | Score: " + score);

        timeLeft = 15;
        gameTimer.restart();
    }

    private void checkAnswer() {
        try {
            if (Integer.parseInt(answerField.getText()) == correctAnswer) {
                score += 10;
                questionsDone++;
                nextQuestion();
            } else {
                endGame("Wrong Answer!");
            }
        } catch (Exception e) {
            /* Ignore non-number inputs */ }
    }

    private void endGame(String msg) {
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, msg + "\nFinal Score: " + score);
        System.exit(0);
    }

    public static void main(String[] args) {
        new MathGame();
    }
}