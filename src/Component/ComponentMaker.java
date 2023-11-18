package Component;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public interface ComponentMaker extends Attribute{
	public default TextField createTextField(double width, double height, double borderRadius, Color txtColor, Color shadowColor, String fontName, double fontSize, boolean bold) {
		TextField tf = new TextField();
		tf.setPrefSize(width, height);
		tf.setMaxSize(width, height);
		tf.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, shadowColor, 0.0, 25.0, 0.0,  3.0));

		Font font;
		if(bold)font = Font.font(fontName, FontWeight.BOLD, fontSize);
		else font = Font.font(fontName, FontWeight.NORMAL, fontSize);
		tf.setFont(font);

		CornerRadii cr = new CornerRadii(borderRadius);
		tf.setBackground(new Background(new BackgroundFill(txtColor, cr, null)));
		return tf;
	}

	public default PasswordField createPasswordField(double width, double height, double borderRadius, Color txtColor, Color shadowColor) {
		PasswordField pf = new PasswordField();
		pf.setPrefSize(width, height);
		pf.setMaxSize(width, height);
		pf.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, shadowColor, 0.0, 25.0, 0.0,  3.0));
		CornerRadii cr = new CornerRadii(borderRadius);
		pf.setBackground(new Background(new BackgroundFill(txtColor, cr, null)));
		return pf;
	}

	public default Button createButton(String text, int width, int height, int borderRadius, int px, int py, Color buttonColor, Color shadowColor, Color FontColor, String fontName, int fontSize, boolean bold) {
		Button b = new Button(text);
		b.setPrefWidth(width);
		b.setPrefHeight(height);
		b.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, shadowColor, 0.0, 25.0, 0.0,  2.0));
		b.setTextFill(FontColor);

		Font font;
		if(bold)font = Font.font(fontName, FontWeight.BOLD, fontSize);
		else font = Font.font(fontName, FontWeight.NORMAL, fontSize);
		b.setFont(font);

		CornerRadii cr = new CornerRadii(borderRadius);
		b.setBackground(new Background(new BackgroundFill(buttonColor, cr, new Insets(py, px, py, px))));
		return b;
	}

	public default Label createLabel(Color color, String text, String fontName, boolean bold, int size) {
		Label l = new Label(text);
		l.setTextFill(color);		

		Font font;
		if(bold)font = Font.font(fontName, FontWeight.BOLD, size);
		else font = Font.font(fontName, FontWeight.NORMAL, size);
		l.setFont(font);
		return l;
	}

	public default TextFlow createTextFlow(String str, String fontName, int fontSize, boolean bold) {
		Text text = new Text(str);
		if(bold)text.setFont(Font.font(fontName, FontWeight.BOLD, fontSize));
		else text.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
		TextFlow txtFlow = new TextFlow();
		txtFlow.getChildren().add(text);
		return txtFlow;
	}
	
	public default ImageView createImageView(String path, int height, int width) {
		Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
	}
}
