package autoclicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyCode.F1;
import static javafx.scene.input.KeyCode.F2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Alert alertOn = new Alert(Alert.AlertType.CONFIRMATION, "Clicker starts in 5 seconds!");
    Alert alertOff = new Alert(Alert.AlertType.CONFIRMATION, "Clicker is off!");
    private Timer timer;

    @FXML
    private TextField txtSeconds;

    @FXML
    private CheckBox chkRightClick;

    @FXML
    private CheckBox chkDoubleClick;

    @FXML
    private Button testBtn;

    public void initialize(URL location, ResourceBundle resources) {
        testBtn.setVisible(false);
    }

    @FXML
    void keyboardAction(KeyEvent event) {
        if (event.getCode() == F1) {
            createTimer();
            alertOn.show();
            System.out.println("Lähti päälle...");
        } else if (event.getCode() == F2 && timer != null) {
            System.out.println("Sammuu...");
            alertOff.show();
            timer.stop();
        }
    }

    @FXML
    void startClicking(ActionEvent event) {
        createTimer();
        System.out.println("Lähti päälle...");
        alertOn.show();
    }

    @FXML
    void stopClicking(ActionEvent event) {
        if (timer != null) {
            timer.stop();
            System.out.println("Sammuu...");
            alertOff.show();
        }
    }

    void click() throws AWTException {
        Robot bot = new Robot();
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    void rightClick() throws AWTException {
        Robot bot = new Robot();
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    void doubleClick() throws AWTException {
        Robot bot = new Robot();
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    void doubleRightClick() throws AWTException {
        Robot bot = new Robot();
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    @FXML
    void testAction(ActionEvent event) {
        System.out.println("Test");
    }

    void createTimer() {

        // otetaan delay timeriin
        int speed = Integer.parseInt(txtSeconds.getText()) * 1000;

        // jos speed on 0 niin ei lähde pyörimään
        if (speed < 1) {
            System.out.println("Luvun pitää olla isompi kuin 0");
            return;
        }

        ActionListener taskPerformer = new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (!chkRightClick.isSelected() && !chkDoubleClick.isSelected()) {
                        click();
                    } else if (chkRightClick.isSelected() && !chkDoubleClick.isSelected()){
                        rightClick();
                    } else if (!chkRightClick.isSelected() && chkDoubleClick.isSelected()) {
                        doubleClick();
                    } else if (chkRightClick.isSelected() && chkDoubleClick.isSelected()) {
                        doubleRightClick();
                    }
                } catch (AWTException a) {
                    System.out.println("AWT EXCEPTION");
                }
            }
        };
        // tämä on tässä jottei metodi tee useampaa timeriä, joka sekottaa ohjelman ja aiheuttaa
        // useita clickkauksia (1 per luotu timer)
        if (timer == null) {
            timer = new Timer(speed, taskPerformer);
            // odottaa 5s ennen ekaa clickkausta
            timer.setInitialDelay(5000);
            timer.start();
            if (!chkRightClick.isSelected()) {
                System.out.println("Käytetään mouse1 painiketta...");
            } else {
                System.out.println("Käytetään mouse2 painiketta...");
            }
        } else {
            timer.setDelay(speed);
            timer.start();
            if (!chkRightClick.isSelected()) {
                System.out.println("Käytetään mouse1 painiketta...");
            } else {
                System.out.println("Käytetään mouse2 painiketta...");
            }
        }
    }

}

