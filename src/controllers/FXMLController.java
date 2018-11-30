package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.algorithms.AlgorithmModel;
import models.algorithms.Hill;
import models.algorithms.Random;
import models.algorithms.SquareDiamond;

public class FXMLController {
	
	// Naming Convention where_what_why
	
	@FXML
    private MenuItem main_menu_run_run;
	
	@FXML
	private VBox algorithm_vbox;
	
	@FXML
	private MenuButton main_menu_btn_algorithm_list;
	
	//Random Algorithm
	@FXML
	private VBox main_vbox_random;
		
	@FXML
	private TextField random_vbox_text_field_size;
	
	@FXML
	private TextField random_vbox_text_field_min;
	
	@FXML
	private Slider random_vbox_slider_min;
	
	@FXML
	private TextField random_vbox_text_field_max;
	
	@FXML
	private Slider random_vbox_slider_max;
	
	//SquareDiamond Algorithm
	@FXML
	private VBox main_vbox_square_diamond;
	
	@FXML
	private Label square_diamond_vbox_label_size;
	
	@FXML
	private TextField square_diamond_vbox_text_field_size;
	
	@FXML
	private Slider square_diamond_vbox_slider_size;
	
	@FXML
	private TextField square_diamond_vbox_text_field_variance;
	
	@FXML
	private Slider square_diamond_vbox_slider_variance;
	
	@FXML
	private TextField square_diamond_vbox_optionnal_text_field_top_left;
	
	@FXML
	private Slider square_diamond_vbox_optionnal_slider_top_left;
	
	@FXML
	private TextField square_diamond_vbox_optionnal_text_field_top_right;
	
	@FXML
	private Slider square_diamond_vbox_optionnal_slider_top_right;
	
	@FXML
	private TextField square_diamond_vbox_optionnal_text_field_bottom_left;
	
	@FXML
	private Slider square_diamond_vbox_optionnal_slider_bottom_left;
	
	@FXML
	private TextField square_diamond_vbox_optionnal_text_field_bottom_right;
	
	@FXML
	private Slider square_diamond_vbox_optionnal_slider_bottom_right;
	
	//Hill
	@FXML
	private VBox main_vbox_hill;
	
	@FXML
	private TextField hill_vbox_text_field_size;
	
	@FXML
	private TextField hill_vbox_text_field_kradius;
	
	@FXML
	private TextField hill_vbox_text_field_iteration;
	
	//AlgorithmModel
	@FXML
	private CheckBox algorithm_vbox_check_box_reformat_image;
	
	@FXML
	private ImageView algorithm_vbox_warning_reformat_image;
	
	@FXML
	private Text algorithm_vbox_text_reformat_image;
	
	//Center
	@FXML
	private ImageView main_image_view_map;
	
	//Footer
	@FXML
	private Button main_button_cancel;
	
	@FXML
	private Text main_text_size;
	
	@FXML
	private Label main_label_status;
	
	@FXML
	private Text main_text_status;
	
	@FXML
	private Text main_text_time;
	
	@FXML
	private ProgressBar main_progress_bar_progress_bar;
	
	DateFormat timeFormat = new SimpleDateFormat("mm:ss");
	Timeline timeline;
	
	//Algorithm Info
	StringProperty algorithmName = new SimpleStringProperty();
	AlgorithmModel algo;
	
	public void initialize() {
		algorithm_vbox.getChildren().remove(main_vbox_random);
		algorithm_vbox.getChildren().remove(main_vbox_square_diamond);
		algorithm_vbox.getChildren().remove(main_vbox_hill);
		
		setNumericField(random_vbox_text_field_size, 5000);

		setNumericFieldLinkToSlider(random_vbox_text_field_min, random_vbox_slider_min, (int)random_vbox_slider_min.getMax());
		setNumericFieldLinkToSlider(random_vbox_text_field_max, random_vbox_slider_max, (int)random_vbox_slider_max.getMax());
		
		setNumericFieldLinkToSlider(square_diamond_vbox_text_field_size, square_diamond_vbox_slider_size, square_diamond_vbox_label_size, (int)square_diamond_vbox_slider_size.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_text_field_variance,square_diamond_vbox_slider_variance,(int)square_diamond_vbox_slider_variance.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_optionnal_text_field_top_left, square_diamond_vbox_optionnal_slider_top_left, (int)square_diamond_vbox_optionnal_slider_top_left.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_optionnal_text_field_top_right, square_diamond_vbox_optionnal_slider_top_right, (int)square_diamond_vbox_optionnal_slider_top_right.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_optionnal_text_field_bottom_left, square_diamond_vbox_optionnal_slider_bottom_left, (int)square_diamond_vbox_optionnal_slider_bottom_left.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_optionnal_text_field_bottom_right, square_diamond_vbox_optionnal_slider_bottom_right, (int)square_diamond_vbox_optionnal_slider_bottom_right.getMax());
		
		setNumericField(hill_vbox_text_field_size, 5000);
		setDoubleField(hill_vbox_text_field_kradius, 100000);
		setNumericField(hill_vbox_text_field_iteration, 100000);
    }
	
	@FXML
    void menuRun(ActionEvent event) {
		int size = 0;
		Map <String, String> hm = new HashMap<String, String>();
		hm.clear();
		switch (algorithmName.getValueSafe()) {
			case "Random":
				//Checking size != 0
				size = Integer.valueOf(random_vbox_text_field_size.getText());
				if (size == 0) {
					alertDialog("Error in parameters", "Argument size cannot be 0.", "To generate a Map, you must enter a positive integer in the size field.", AlertType.ERROR);
					return;
				}
				//Check min<max
				int min = Integer.valueOf(random_vbox_text_field_min.getText());
				int max = Integer.valueOf(random_vbox_text_field_max.getText());
				if (min>max) {
					alertDialog("Error in parameters", "Argument min is greater than max.", "To generate a Map, you must enter 2 value : min and max.", AlertType.ERROR);
					return;
				}
				
				algo = new Random(size);
				hm.put("min", String.valueOf(min));
				hm.put("max", String.valueOf(max));
				break;
			case "Square Diamond":
				size = Integer.valueOf(square_diamond_vbox_text_field_size.getText());
				
				algo = new SquareDiamond(size);
				hm.put("variance", square_diamond_vbox_text_field_variance.getText());
				hm.put("topLeft", square_diamond_vbox_optionnal_text_field_top_left.getText());
				hm.put("topRight", square_diamond_vbox_optionnal_text_field_top_right.getText());
				hm.put("bottomLeft", square_diamond_vbox_optionnal_text_field_bottom_left.getText());
				hm.put("bottomRight", square_diamond_vbox_optionnal_text_field_bottom_right.getText());
				break;
			case "Hill":
				size = Integer.valueOf(hill_vbox_text_field_size.getText());
				if (size == 0) {
					alertDialog("Error in parameters", "Argument size cannot be 0.", "To generate a Map, you must enter a positive integer in the size field.", AlertType.ERROR);
					return;
				}
				algo = new Hill(20);
				hm.put("kradius", hill_vbox_text_field_kradius.getText());
				hm.put("iteration", hill_vbox_text_field_iteration.getText());
				break;
			default:
					alertDialog("Error unknown Algorithm", "Algorithm you have selected is not recognized.", "You must select one algorithm from the menu list.", AlertType.ERROR);
				return;
		}
		
		
		hm.put("reformat", String.valueOf(algorithm_vbox_check_box_reformat_image.isSelected()));
		algo.setParameters(hm);
		main_progress_bar_progress_bar.progressProperty().bind(algo.progressProperty());
		main_text_status.textProperty().bind(algo.messageProperty());
		algo.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
			    new EventHandler<WorkerStateEvent>() {
			    @Override
			    public void handle(WorkerStateEvent t) {
			    	main_image_view_map.setImage(algo.getValue());
			    	double height = main_image_view_map.getImage().getHeight();
			    	double width = main_image_view_map.getImage().getWidth();
			    	main_text_size.setText(String.format("%d x %dpx", (int)height, (int)width));
			    	main_progress_bar_progress_bar.progressProperty().unbind();
			    	main_text_status.textProperty().unbind();
			    	if (timeline != null)
			    		timeline.stop();
			    	main_text_status.setText("Done.");
			    }
			});
		
		//Run Algorithm
		initializeTimeline();
		Thread t = new Thread(algo);
		timeline.play();
		t.start();
    }
	
	@FXML
    void cancelTask(ActionEvent event) {
		if (algo != null)
			algo.cancel();
		if (timeline != null)
			timeline.stop();
		main_progress_bar_progress_bar.progressProperty().unbind();
		main_progress_bar_progress_bar.setProgress(0.0);
		main_text_status.textProperty().unbind();
    	main_text_status.setText("Cancelled.");
    }
	
	@FXML
    void onReformatImage(ActionEvent event) {
		boolean visible = !algorithm_vbox_check_box_reformat_image.isSelected();
		algorithm_vbox_warning_reformat_image.setVisible(visible);
		algorithm_vbox_text_reformat_image.setVisible(visible);
    }
	
	@FXML
    private void setRandomMenuItem(ActionEvent event) {
		algorithmName.setValue("Random");
		main_menu_btn_algorithm_list.setText(algorithmName.getValueSafe());
		addAlgorithmChild(main_vbox_random);
    }
	
	@FXML
	private void setSquareDiamondMenuItem(ActionEvent event) {
		algorithmName.setValue("Square Diamond");
		main_menu_btn_algorithm_list.setText(algorithmName.getValueSafe());
		addAlgorithmChild(main_vbox_square_diamond);
	}
	
	@FXML
	private void setHillMenuItem(ActionEvent event) {
		algorithmName.setValue("Hill");
		main_menu_btn_algorithm_list.setText(algorithmName.getValueSafe());
		addAlgorithmChild(main_vbox_hill);
	}
	
	@FXML
    private void showAbout(ActionEvent event) {
		alertDialog("Height Map Generation Tool","Height Map Generation Tool can be used to generate aleatory\nheight maps based on procedural generation algorithms.\nThe generated images can be imported into 3D design software.","Height Map Generation Tool is an original idea of B. HYON & V. LEVIEUX and was directed by C. DELTEL & V. LEVIEUX.", AlertType.INFORMATION);
    }

	@FXML
    private void deleteImage(ActionEvent event) {
		main_image_view_map.setImage(null);
    }
	/**
	 * Generate Alert Dialog
	 * @param title (String) Title of the Alert Dialog
	 * @param header (String) Header of the Alert Dialog
	 * @param content (String) Content of the Alert Dialog
	 * @param type (AlertType) AlertType.INFORMATION, AlerType.ERROR, ...
	 */
	private void alertDialog(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
		alert.showAndWait();
	}
	
	private void addAlgorithmChild(VBox a) {
		if (algorithm_vbox.getChildren().size()>=3)
			algorithm_vbox.getChildren().remove(1);
		algorithm_vbox.getChildren().add(1, a);
	}
	
	// TODO: Problem if it is 0.0
	private void setDoubleField(TextField tf, int maxValue) {
		tf.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*.\\d*|\\d*")) {
		        	newValue = newValue.replaceAll("[^\\d*.\\d|\\d]", "");
		        	tf.setText(String.valueOf(newValue));
		        }
		        if (!newValue.equals("")) {
		        	double value = Double.valueOf(newValue);
			        if (value <= maxValue) {
			        	tf.setText(String.valueOf(value));
			        } else {
			        	tf.setText(oldValue);
			        }
		        }
		    }
		});
	}
	
	private void setNumericField(TextField tf, int maxValue) {
		tf.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newValue = newValue.replaceAll("[^\\d]", "");
		        	tf.setText(String.valueOf(newValue));
		        }
		        if (!newValue.equals("")) {
		        	int value = Integer.valueOf(newValue);
			        if (value <= maxValue) {
			        	tf.setText(String.valueOf(value));
			        } else {
			        	tf.setText(oldValue);
			        }
		        }
		    }
		});
	}
	
	private void setNumericFieldLinkToSlider(TextField tf, Slider s, int maxValue) {
		tf.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newValue = newValue.replaceAll("[^\\d]", "");
		        	tf.setText(String.valueOf(newValue));
		        }
		        if (!newValue.equals("")) {
		        	int value = Integer.valueOf(newValue);
			        if (value <= maxValue) {
			        	tf.setText(String.valueOf(value));
			        	s.setValue(value);
			        } else {
			        	tf.setText(oldValue);
			        }
		        }
		    }
		});
		
		s.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				tf.setText(String.valueOf((int)s.getValue()));
			}
			
		});
	}
	
	private void setNumericFieldLinkToSlider(TextField tf, Slider s, Label l, int maxValue) {
		tf.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newValue = newValue.replaceAll("[^\\d]", "");
		        	tf.setText(String.valueOf(newValue));
		        }
		        if (!newValue.equals("")) {
		        	int value = Integer.valueOf(newValue);
		        	int valueToPrint;
			        if (value <= maxValue) {
			        	tf.setText(String.valueOf(value));
			        	s.setValue(value);
			        	valueToPrint = (int) (Math.pow(2,value)+1);		        	
			        } else {
			        	tf.setText(oldValue);
			        	valueToPrint = (int) (Math.pow(2,Integer.valueOf(oldValue))+1);
			        }
			        l.setText("Size ("+valueToPrint+" x "+valueToPrint+" px)");
		        }
		    }
		});
		
		s.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				tf.setText(String.valueOf((int)s.getValue()));
				int valueToPrint = (int)(Math.pow(2,(int)s.getValue())+1);
				l.setText("Size ("+valueToPrint+" x "+valueToPrint+" px)");
			}
			
		});
	}
	
	private void initializeTimeline() {
		long startTime = System.currentTimeMillis();
		timeline = new Timeline(new KeyFrame(Duration.ZERO, e-> {
			main_text_time.setText(timeFormat.format(System.currentTimeMillis()-startTime));
		}), new KeyFrame(Duration.seconds(1))
		);
		timeline.setCycleCount(Animation.INDEFINITE);
	}
	
}