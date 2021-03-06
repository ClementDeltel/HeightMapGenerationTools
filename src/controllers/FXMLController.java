package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.algorithms.AlgorithmModel;
import models.algorithms.Hill;
import models.algorithms.Random;
import models.algorithms.SquareDiamond;
import models.database.DaoModel;

public class FXMLController {
	
	// Naming Convention where_what_why
	
	@FXML
	private MenuItem main_menu_file_save;
	
	@FXML
    private MenuItem main_menu_run_run;
	
	@FXML
	private MenuItem main_menu_database_store;
	
	@FXML
	private Menu main_menu_admin;
	
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
	private TextField hill_vbox_text_field_kradius_numerator;
	
	@FXML
	private TextField hill_vbox_text_field_kradius_denomitator;
	
	@FXML
	private Text hill_vbox_text_kradius_ratio;
	
	@FXML
	private TextField hill_vbox_text_field_iteration;
	
	@FXML
	private ImageView hill_vbox_error_parameters;
	
	@FXML
	private Text hill_vbox_text_error_parameters;
	
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
	private ImageView main_image_size;
	
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
	
	@FXML
	private HBox hbox_test;
	
	DateFormat timeFormat = new SimpleDateFormat("mm:ss");
	Timeline timeline;
	
	//Algorithm Info
	StringProperty algorithmName = new SimpleStringProperty();
	AlgorithmModel algo;
	
	Preferences sessionPreferences = Preferences.userRoot();
	
	public void initialize() {
		main_image_view_map.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream("images/firstheightmap.jpg")));
		algorithm_vbox_warning_reformat_image.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream("images/ic_warning.png")));
		hill_vbox_error_parameters.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream("images/ic_error.png")));
		main_image_size.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream("images/ic_size.png")));
		
		if (sessionPreferences.getInt("LICENSE_TYPE", 1)==2) {
			main_menu_admin.setVisible(true);
			main_menu_admin.setDisable(false);
		}
		
		algorithm_vbox.getChildren().remove(main_vbox_random);
		algorithm_vbox.getChildren().remove(main_vbox_square_diamond);
		algorithm_vbox.getChildren().remove(main_vbox_hill);
		
		setNumericField(random_vbox_text_field_size, 5000);

		setNumericFieldLinkToSlider(random_vbox_text_field_min, random_vbox_slider_min, (int)random_vbox_slider_min.getMax());
		setNumericFieldLinkToSlider(random_vbox_text_field_max, random_vbox_slider_max, (int)random_vbox_slider_max.getMax());
		
		setNumericFieldLinkToSliderAndLabel(square_diamond_vbox_text_field_size, square_diamond_vbox_slider_size, square_diamond_vbox_label_size, (int)square_diamond_vbox_slider_size.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_text_field_variance,square_diamond_vbox_slider_variance,(int)square_diamond_vbox_slider_variance.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_optionnal_text_field_top_left, square_diamond_vbox_optionnal_slider_top_left, (int)square_diamond_vbox_optionnal_slider_top_left.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_optionnal_text_field_top_right, square_diamond_vbox_optionnal_slider_top_right, (int)square_diamond_vbox_optionnal_slider_top_right.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_optionnal_text_field_bottom_left, square_diamond_vbox_optionnal_slider_bottom_left, (int)square_diamond_vbox_optionnal_slider_bottom_left.getMax());
		setNumericFieldLinkToSlider(square_diamond_vbox_optionnal_text_field_bottom_right, square_diamond_vbox_optionnal_slider_bottom_right, (int)square_diamond_vbox_optionnal_slider_bottom_right.getMax());
		
		setNumericField(hill_vbox_text_field_size, 5000);
		setNumericFieldRatio(hill_vbox_text_field_kradius_numerator, hill_vbox_text_field_kradius_denomitator, hill_vbox_text_kradius_ratio, 10000);
		setNumericField(hill_vbox_text_field_iteration, 10000);
		setProductLimitWarning(hill_vbox_text_field_size, hill_vbox_text_field_iteration, 425000, hill_vbox_error_parameters, hill_vbox_text_error_parameters);
    
		main_image_view_map.setPreserveRatio(true);
    	main_image_view_map.fitWidthProperty().bind(((Pane)main_image_view_map.getParent()).widthProperty());
    	main_image_view_map.fitHeightProperty().bind(((Pane)main_image_view_map.getParent()).heightProperty());
	}
	
	@FXML
    void menuRun(ActionEvent event) {
		if (algo != null) {
			if (algo.isRunning())
				return;
		}
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
				int kradius_denominator = Integer.valueOf(hill_vbox_text_field_kradius_denomitator.getText());
				if (kradius_denominator == 0) {
					alertDialog("Error in parameters", "Argument kradius cannot be 0.", "Impossible to divide by 0.", AlertType.ERROR);
					return;
				}
				if (Integer.valueOf(hill_vbox_text_field_iteration.getText())*size>425000) {
					alertDialog("Error in parameters", "Size of the map or Iteration value is too high.", "The number of possible interations for this size of map is too high. You should reduce the size or the number of iteration.", AlertType.ERROR);
					return;
				}
				
				algo = new Hill(size);
				double kradius = Integer.valueOf(hill_vbox_text_field_kradius_numerator.getText())/(double)Integer.valueOf(hill_vbox_text_field_kradius_denomitator.getText());
				hm.put("kradius", String.valueOf(kradius));
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
			    	if (algo.getValue()!=null) {
			    		main_image_view_map.setImage(algo.getValue());
			    		main_menu_file_save.setDisable(false);
			    	}
			    	double height = main_image_view_map.getImage().getHeight();
			    	double width = main_image_view_map.getImage().getWidth();
			    	main_text_size.setText(String.format("%d x %dpx", (int)height, (int)width));
			    	main_progress_bar_progress_bar.progressProperty().unbind();
			    	main_text_status.textProperty().unbind();
			    	if (timeline != null)
			    		timeline.stop();
			    	main_text_status.setText("Done.");
			    	main_menu_database_store.setDisable(false);
			    }
			});
		algo.exceptionProperty().addListener((observable, oldValue, newValue) ->  {
			if(newValue != null) {
				this.cancel("Failed.");
				/* For debugging */
				Exception ex = (Exception) newValue;
				ex.printStackTrace();
			}});
		main_menu_database_store.setDisable(true);
		main_menu_file_save.setDisable(true);
		//Run Algorithm
		initializeTimeline();
		Thread t = new Thread(algo);
		timeline.play();
		t.start();
    }
	
	@FXML
    void cancelTask(ActionEvent event) {
		this.cancel("Cancelled.");
    }
	
	private void cancel(String msg) {
		if (algo != null)
			algo.cancel();
		if (timeline != null)
			timeline.stop();
		main_progress_bar_progress_bar.progressProperty().unbind();
		main_progress_bar_progress_bar.setProgress(0.0);
		main_text_status.textProperty().unbind();
    	main_text_status.setText(msg);
	}
	
	@FXML
    void setOnSave(ActionEvent event) {
		Image img = main_image_view_map.getImage();
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
        	String fileName = file.getName();
        	String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
        	try {
    			ImageIO.write(SwingFXUtils.fromFXImage(img, null),  fileExtension, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
	
	@FXML
    void setOnClose(ActionEvent event) {
		Platform.exit();
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
		main_menu_file_save.setDisable(true);
		main_image_view_map.setImage(null);
    }
	/**
	 * Generate Alert Dialog
	 * @param title (String) Title of the Alert Dialog
	 * @param header (String) Header of the Alert Dialog
	 * @param content (String) Content of the Alert Dialog
	 * @param type (AlertType) AlertType.INFORMATION, AlerType.ERROR, ...
	 */
	public static void alertDialog(String title, String header, String content, AlertType type) {
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
	
	private void setProductLimitWarning(TextField tf1, TextField tf2, int max, ImageView imgv, Text txt) {
		
		tf1.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("\\d*") & !newValue.equals("")) {
		        	boolean visibility = false;
		        	if (Integer.valueOf(newValue)*Integer.valueOf(tf2.getText())>=max)
		        		visibility = true;
		        	imgv.setVisible(visibility);
		        	txt.setVisible(visibility);
		        }
		    }
		});
		tf2.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("\\d*") & !newValue.equals("")) {
		        	boolean visibility = false;
		        	if (Integer.valueOf(tf1.getText())*Integer.valueOf(newValue)>=max)
		        		visibility = true;
		        	imgv.setVisible(visibility);
		        	txt.setVisible(visibility);
		        }
		    }
		});
	}
	
	private void setNumericFieldRatio(TextField tfn, TextField tfd, Text t, int maxValue) {
		tfn.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newValue = newValue.replaceAll("[^\\d]", "");
		        	tfn.setText(String.valueOf(newValue));
		        }
		        if (!newValue.equals("")) {
		        	int value = Integer.valueOf(newValue);
			        if (value <= maxValue) {
			        	tfn.setText(String.valueOf(value));
			        	if (!tfd.getText().equals(""))
			        		t.setText(String.valueOf(value/(double)Integer.valueOf(tfd.getText())));
			        } else {
			        	tfn.setText(oldValue);
			        	if (!tfd.getText().equals(""))
			        		t.setText(String.valueOf(Integer.valueOf(oldValue)/(double)Integer.valueOf(tfd.getText())));
			        }
		        }
		    }
		});
		tfd.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	newValue = newValue.replaceAll("[^\\d]", "");
		        	tfd.setText(String.valueOf(newValue));
		        }
		        if (!newValue.equals("")) {
		        	int value = Integer.valueOf(newValue);
			        if (value <= maxValue) {
			        	tfd.setText(String.valueOf(value));
			        	if (!tfn.getText().equals(""))
			        		t.setText(String.valueOf(Integer.valueOf(tfn.getText())/(double)value));
			        } else {
			        	tfd.setText(oldValue);
			        	if (!tfn.getText().equals(""))
			        		t.setText(String.valueOf(Integer.valueOf(tfn.getText())/(double)Integer.valueOf(oldValue)));
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
	
	private void setNumericFieldLinkToSliderAndLabel(TextField tf, Slider s, Label l, int maxValue) {
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
	
	@FXML
	private void showAlgorithmInfo(ActionEvent event) {
		FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        Text algorithm_info = new Text("In coming...");
        root.getChildren().addAll(algorithm_info);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Algorithms's Informations");
        stage.setWidth(400);
        stage.setHeight(200);
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
    private void showLogs(ActionEvent event) {
		Path watchPath = Paths.get(LoggerAlgorithm.fileLogName).toAbsolutePath();
		new WatchFileChanges(watchPath).start();
    }
		
	@FXML
	void adminAddLicense(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/adminAddView.fxml"));
			Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setTitle("[ADMIN] Add License");
	        stage.setResizable(false);
	        stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void databaseDeleteLastOne(ActionEvent event) {
		DaoModel.deleteLastMap();
    }

    @FXML
    void databaseShow(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/DatabaseView.fxml"));
			Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setTitle("Database HeighMap Generation Tools");
	        stage.setResizable(true);
	        stage.setMinHeight(600);
	        stage.setMinWidth(900);
	        stage.getIcons().add(new Image("/images/firstheightmap.jpg"));
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void databaseStore(ActionEvent event) {
    	//Should never happen.
    	if (algo==null) {
    		alertDialog("Nothing to store", "You should run at least on algorithm.", "You have not run an algorithm yet. Please select one in the list and run it (Ctrl+F5) to allow store feature.", AlertType.ERROR);
    		return;
    	}
    	String paramMap = algo.getParameters().toString();
    	String algoName = algo.getClass().getName().substring(18);
    	Map<String, String> statMap = algo.getStatistics();
    	String height = statMap.get("size");
    	String max = statMap.get("max");
    	String min = statMap.get("min");
    	String average = statMap.get("average");
    	String median = statMap.get("median");
    	DaoModel.insertTablesMap(algoName, height, height, paramMap, min, max, average, median);
    }
}