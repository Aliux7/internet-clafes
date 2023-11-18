package Component;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public interface Attribute {
	public Color WHITE = new Color(0.973, 0.973, 1, 1);
	public Color DARK_PURPLE = new Color(0.369, 0.217, 0.427, 1);
	public Color PURPLE = new Color(0.831, 0.734, 0.867, 1);
	public Color YELLOW = new Color(0.969, 0.898, 0.286, 1);
	public Color BLACK = new Color(0, 0, 0, 1);
	
	public Color PRIMARY = new Color(3.0 / 255.0, 0.0 / 255.0, 28.0 / 255.0, 1.0);
	public Color SECONDARY = new Color(48.0 / 255.0, 30.0 / 255.0, 103.0 / 255.0, 1.0);
	public Color BASE = new Color(0.941, 0.957, 0.925, 1);
	
	public Background PURPLE_BACKGROUND = new Background(new BackgroundFill(PURPLE, null, null));
	public Background WHITE_BACKGROUND = new Background(new BackgroundFill(WHITE, null, null));
	public Background BLACK_BACKGROUND = new Background(new BackgroundFill(BLACK, null, null));
	public Background PRIMARY_BACKGROUND = new Background(new BackgroundFill(PRIMARY, null, null));
	public Background SECONDARY_BACKGROUND = new Background(new BackgroundFill(SECONDARY, null, null));
	public Background BASE_BACKGROUND = new Background(new BackgroundFill(BASE, null, null));
	
	public Font bellMT80 = new Font("Bell MT", 100);
	public Font bellMT60 = new Font("Bell MT", 60);
	
	public int SCENE_WIDTH = (int) (Screen.getPrimary().getBounds().getWidth() *0.7);
	public int SCENE_HEIGHT = (int) (Screen.getPrimary().getBounds().getHeight() *0.7);
}
