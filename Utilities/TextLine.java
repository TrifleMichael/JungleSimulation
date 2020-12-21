package Utilities;


import javafx.scene.text.Text;

public class TextLine {
    String firstText = "";
    String value = "";
    String secondText = "";

    Text text = new Text(firstText+value+secondText);

    public void update(String value) {
        this.value = value;
        text.setText(firstText+value+secondText);
    }

    public TextLine(int x, int y) {
        text.setX(x);
        text.setY(y);
    }

    public Text getText() {
        return text;
    }

    public void setText(String firstText, String value, String secondText) {
        this.firstText = firstText;
        this.value = value;
        this.secondText = secondText;
    }
}
