
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RunStickRun extends Application{

	final Rectangle2D SCREEN_RES = Screen.getPrimary().getBounds();
	final int WIDTH = (int) ( SCREEN_RES.getWidth() * 1000 / SCREEN_RES.getWidth());
	final int HEIGHT = (int) ( SCREEN_RES.getWidth() * 400 / SCREEN_RES.getWidth());
	
	private Scene scene;
	
	private Dimension2D minSize;
	private Rectangle floor;
	private DoubleProperty sYOfFloor;
	private ImageView stickMan;
	private ImageView BgPhoto;
	private Image[] runningStickMan;
	private Image[] jumpingStickMan;
	private Effect motionBlur;
	private double motionBlurRadius = 0;
	private DoubleProperty fpsOfRunner = new SimpleDoubleProperty(3.9);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Pane root = new Pane();
		scene = new Scene(root, WIDTH, HEIGHT);
		scene.setFill(null);
		
		minSize = new Dimension2D(500, 200);
		
		runningStickMan = new Image[9];
		for ( int i = 0; i < 9; i++) {
			runningStickMan[i] = new Image(RunStickRun.class.getResourceAsStream( "images/stick_3/best/stick_" + i + ".png"));
		}
		
		jumpingStickMan = new Image[17];
		for ( int i = 0; i < 17; i++) {
			jumpingStickMan[i] = new Image(RunStickRun.class.getResourceAsStream( "images/jumpingStick_1/jumpingStick_1" + i + ".png"));
		}
		
		stickMan = new ImageView( runningStickMan[0]);
		stickMan.setPreserveRatio(true);
		stickMan.setFitHeight(100);
		stickMan.setX(15);
		stickMan.setY(scene.getHeight()*0.85 - stickMan.getFitHeight()-13 );
		stickMan.setEffect( new DropShadow(1.0, Color.ALICEBLUE));
		
		motionBlur = new MotionBlur();
		((MotionBlur) motionBlur).setRadius(motionBlurRadius);
		BgPhoto = new ImageView( new Image( RunStickRun.class.getResourceAsStream( "images/Background.jpg")));
		BgPhoto.setPreserveRatio(true);
		BgPhoto.setFitHeight(400);
		BgPhoto.setEffect( motionBlur);
		
		Timeline stickRunnerAnimator = new Timeline(
							new KeyFrame(Duration.millis(100), 
								new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										stickMan.setImage( runningStickMan[0]);
									}
								}, 
								null),
							new KeyFrame(Duration.millis(200), 
									new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											stickMan.setImage( runningStickMan[1]);
										}
									}, 
									null),
							new KeyFrame(Duration.millis(300), 
									new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											stickMan.setImage( runningStickMan[2]);
										}
									}, 
									null),
							new KeyFrame(Duration.millis(400), 
									new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											stickMan.setImage( runningStickMan[3]);
										}
									}, 
									null),
							new KeyFrame(Duration.millis(500), 
									new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											stickMan.setImage( runningStickMan[4]);
										}
									}, 
									null),
							new KeyFrame(Duration.millis(600), 
									new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											stickMan.setImage( runningStickMan[5]);
										}
									}, 
									null),
							new KeyFrame(Duration.millis(700), 
									new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											stickMan.setImage( runningStickMan[6]);
										}
									}, 
									null),
							new KeyFrame(Duration.millis(800), 
									new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											stickMan.setImage( runningStickMan[7]);
										}
									}, 
									null),
							new KeyFrame(Duration.millis(900), 
									new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											stickMan.setImage( runningStickMan[8]);
										}
									}, 
									null));
		stickRunnerAnimator.rateProperty().bind(fpsOfRunner);
		stickRunnerAnimator.setCycleCount(Timeline.INDEFINITE);
		stickRunnerAnimator.play();
		
		
		setFloor(scene);
		
		scene.setOnKeyPressed( new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if( event.getCode() == KeyCode.RIGHT) {
					fpsOfRunner.set(fpsOfRunner.get() + 0.2);
				}
				else if( event.getCode() == KeyCode.LEFT) {
					fpsOfRunner.set(fpsOfRunner.get() - 0.2);
				}
			}
		});
		
		root.getChildren().addAll(BgPhoto, floor, stickMan);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Run Stick Run - JavaFX version");
		primaryStage.setMinHeight(minSize.getHeight());
		primaryStage.setMinWidth( minSize.getWidth());
		primaryStage.initStyle( StageStyle.DECORATED);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
	}

	private void setFloor(Scene scene) {
		floor = new Rectangle(0, scene.getHeight()*0.85, 800, 9);
		sYOfFloor = new SimpleDoubleProperty(scene.getHeight()*0.8);
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sYOfFloor.set(scene.getHeight()*0.8);
				floor.setHeight(scene.getHeight() / (300 / 9));
			}
		});
		
		floor.yProperty().bind(sYOfFloor);
		floor.widthProperty().bind(scene.widthProperty());
		
		floor.setFill( Paint.valueOf("#304671"));
		floor.setEffect( new DropShadow());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
