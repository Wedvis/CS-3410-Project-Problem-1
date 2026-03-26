package group_project;
	
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


//Requires JavaFX 3 library to run
//Will add comments below eveyr funciton soon, just getting everything uploaded

public class Main extends Application {
	protected Button btnLoad, btnSave, btnRestart, btnShowAll, btnAddCard, btnRemoveCard, btnHelp, btnResetSelection, btnSaveCard, btnAddCardHelp, btnGoBack;
	protected TextField txfName, txfType, txfStage, txfSpecial, txfURL;
	protected MenuButton typeMenuButton = new MenuButton();
	protected MenuButton stageMenuButton = new MenuButton();
	protected MenuButton specialMenuButton = new MenuButton();
	protected MenuButton cardMenuButton = new MenuButton();
	protected ScrollPane scrollpane  = new ScrollPane();
	protected ImageView cardImage;
	protected VBox cardsVBox = new VBox();
	protected HBox hBoxBottom = new HBox();
	protected TextArea txaResults;
	protected Label lblTypeSelected, lblStageSelected, lblSpecialSelected, lblName, lblType, lblStage, lblSpecial, lblURL;
	protected CardManager manager = new CardManager();
	private final String PATH = "src/group_project";
	protected String typeSelectedAttribute = "None";
	protected String stageSelectedAttribute = "None";
	protected String specialSelectedAttribute = "None";
	protected Stage primaryStage;
	
	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		try {
			this.primaryStage = primaryStage;
			Pane root = buildGui(primaryStage);
			root.setId("pane");
			Scene scene = new Scene(root,800,925);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pokémon Card Database Program");
			primaryStage.getIcons().add(new Image("https://purepng.com/public/uploads/large/purepng.com-pokeballpokeballdevicepokemon-ballpokemon-capture-ball-17015278259020osdb.png"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Pane buildGui(Stage stage) throws FileNotFoundException {
		GridPane grid = new GridPane();
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(buildUpperSection(stage), buildLowerSection());

	    grid.add(vBox, 0, 0);
		
		return grid;
	}
	
	private Pane buildUpperSection(Stage stage) {
		HBox hBox = new HBox();
		
		Button btnLoad = new Button("Load");
		btnLoad.setOnAction(new ReadCardEventHandler(stage));
		
		Button btnSave = new Button("Save");

		Button btnRestart = new Button("Resart");
		btnRestart.setOnAction(new restartEventHandler());
		
		Button btnHelp = new Button("Help");
		btnHelp.setOnAction(new helpEventHandler());
		
		
		lblTypeSelected = new Label("Type Selected:\n    None");
		typeMenuButton.setText("Type");
		HBox typeHBox = new HBox(lblTypeSelected, typeMenuButton);
				
		lblStageSelected = new Label("Stage Selected:\n    None");
		stageMenuButton.setText("Stage");
		HBox stageHBox = new HBox(lblStageSelected, stageMenuButton);
		
		lblSpecialSelected = new Label("Special Selected:\n    None");
		specialMenuButton.setText("Special");
		HBox specialHBox = new HBox(lblSpecialSelected, specialMenuButton);
		
		hBox.getChildren().addAll(btnLoad,btnSave,btnRestart,typeHBox,stageHBox, specialHBox, btnHelp);
		hBox.getStyleClass().add("boxBorder");
		return hBox;
	}
	
	private Pane buildLowerSection() throws FileNotFoundException {
		VBox vBox = new VBox();
		HBox hBoxTop = new HBox();
		HBox menu = new HBox();
		cardMenuButton.setText("Card");
		menu.getChildren().add(cardMenuButton);
		
		btnShowAll = new Button("Show All");
		btnShowAll.setOnAction(new showAllEventHandler());
		
		Button btnAddCard = new Button("Add a Card");
		btnAddCard.setOnAction(new addCardEventHandler());
		
		Button btnRemoveCard = new Button("Remove a Card");
		
		Button btnResetSelection = new Button("Reset Selection");
		btnResetSelection.setOnAction(new resetSelectionEventHandler());
		
		String poke = "https://purepng.com/public/uploads/large/purepng.com-pokeballpokeballdevicepokemon-ballpokemon-capture-ball-17015278259020osdb.png";
		Image pokeball = new Image(poke, 50, 50, true, true);
		ImageView pokeBall1 = new ImageView(pokeball);
		ImageView pokeBall2 = new ImageView(pokeball);
		ImageView pokeBall3 = new ImageView(pokeball);
		ImageView pokeBall4 = new ImageView(pokeball);
		ImageView pokeBall5 = new ImageView(pokeball);
		ImageView pokeBall6 = new ImageView(pokeball);
		
		hBoxTop.getChildren().addAll(menu, btnShowAll, btnAddCard, btnRemoveCard, btnResetSelection, pokeBall1, pokeBall2, pokeBall3, pokeBall4, pokeBall5, pokeBall6);
		
		String s1 = "https://i0.wp.com/sleevenocardbehind.com/wp-content/uploads/2022/11/thumbnail_IMG_8444.jpg";
		Image card = new Image(s1);
		cardImage = new ImageView(card);
		cardImage.setX(250);
		cardImage.setY(250);
		cardImage.setFitHeight(250); 
		cardImage.setFitWidth(250);
		cardImage.setPreserveRatio(true);
	    
		String beginningMsg = "Welcome to the Pokemon Card Database program!\n"
							+ "---------------------------------------------\n"
							+ "To begin: Please click the \"Load\" button to\n"
							+ "load a premade deck.\n"
							+ "Or select \"Add a Card\" to create a card!\n"
							+ "Note large decks will take longer to\n"
							+ "show every card.";
		
	    txaResults = new TextArea(beginningMsg);
	    txaResults.setPrefWidth(650);
	    txaResults.setEditable(false);
	    
	    hBoxBottom.getChildren().addAll(cardImage, txaResults);
	    
	    scrollpane.setContent(cardsVBox);
	    scrollpane.setPrefHeight(500);
		
	    vBox.getChildren().addAll(hBoxTop, hBoxBottom, scrollpane);
	    
	    return vBox;
	}
	

	private class ReadCardEventHandler implements EventHandler<ActionEvent> {
		Stage stage;
		public ReadCardEventHandler(Stage stage) {
			super();
			this.stage = stage;
		}
		@Override
		public void handle(ActionEvent event) {
			File file = getFile(stage, "Open"); // Display Open dialog
			if( file != null) {
	            try {
	            	// Call this method to do the actual reading 
					readFile(file);
				}
	            catch (FileNotFoundException e) {
	            	txaResults.setText("Error reading file");
					e.printStackTrace();
				}
			}
		}
	}
	
	private File getFile(Stage stage, String type) {
	    FileChooser fileChooser = configureFileChooser();
	    File file = null;
	    
	 	switch(type) {
	 	case "Open":
	        file = fileChooser.showOpenDialog(stage);
	 		break;
	 	case "Save":
	        file = fileChooser.showSaveDialog(stage);
	 		break;
	 	}
	 	return file;
	}
	
	private FileChooser configureFileChooser() {
		FileChooser fileChooser = new FileChooser();
	    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	    fileChooser.getExtensionFilters().add(extFilter);
	    File initPath = new File(PATH);
	    fileChooser.setInitialDirectory(initPath);
		return fileChooser;
	}
	
	
	protected void readFile(File file) throws FileNotFoundException {
		String msg = "Cards Added:\n";
		msg += "File: " + file.getName() + "\n";
		msg += "-----------------------\n";
		Set<String> typeArray = new HashSet<>();
		Set<String> stageArray = new HashSet<>();
		Set<String> specialArray = new HashSet<>();
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String line = input.nextLine();
				int id = -1;
				String name = "";
				String imageURL = "";
				if (line.equals("!&%8")) {
					id = Integer.parseInt(input.nextLine());
					name = input.nextLine();
					ArrayList<String> atributes = new ArrayList<String>();
					
					String[] tempArray = input.nextLine().split(",");
					typeArray.addAll(Arrays.asList(tempArray));
					atributes.addAll(Arrays.asList(tempArray));
					
					tempArray = input.nextLine().split(",");
					stageArray.addAll(Arrays.asList(tempArray));
					atributes.addAll(Arrays.asList(tempArray));
					
					line = input.nextLine();
					if (!line.equals("None")) {
						tempArray = line.split(",");
						specialArray.addAll(Arrays.asList(tempArray));
						atributes.addAll(Arrays.asList(tempArray));
					}
					
					imageURL = input.nextLine();
					CardObject temp = new CardObject(id, name, atributes, imageURL);
					msg += temp.toString() + "\n";
					msg += "-----------------------\n";
					manager.addCard(temp, false);
				}
			}
			buildAttributeDropDown(typeArray, stageArray, specialArray);
			input.close();
			txaResults.setText(msg);
		}
		catch(FileNotFoundException e) {
			System.out.println("Error reading file");
		}
	}
	
	private void buildAttributeDropDown(Set<String> type, Set<String> stage, Set<String> special) {
		for (String h : type) {
			MenuItem temp = new MenuItem(h);
			typeMenuButton.getItems().add(temp);
			temp.setOnAction(new getMenuItemEventHandler());
		}
		for (String j : stage) {
			MenuItem temp = new MenuItem(j);
			stageMenuButton.getItems().add(temp);
			temp.setOnAction(new getMenuItemEventHandler());
		}
		for (String k : special) {
			MenuItem temp = new MenuItem(k);
			specialMenuButton.getItems().add(temp);
			temp.setOnAction(new getMenuItemEventHandler());
		}
	}
	
	private class helpEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String msg = "Button Guide:\n"
					+ "---------------------------------------------\n"
					+ "Load: Load Pokémon deck into the program\n"
					+ "Save: Save current Pokémon deck to a file\n"
					+ "Restart: Resarts the program and removes current deck\n"
					+ "Attribute selectores Type/Stage/Special:\n"
					+ "  Filters what cards will be displayed\n"
					+ "Help: What you're doing right now!!\n"
					+ "Card: Select a specific card in the deck\n"
					+ "Show All: Shows all cards at once\n"
					+ "Add a Card: Manually add a card to the deck\n"
					+ "Remove a Card: Manually removes a card from the deck\n"
					+ "Reset Selection: Resets your card filter\n"
					+ "(Tip: Remember to save after altering your deck)\n"
					+ "(Tip: Select the Pokémon attribute only to get all cards)\n";
			txaResults.setText(msg);
		}
	}
	
	private class restartEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String beginningMsg = "Welcome to the Pokemon Card Database program!\n"
					+ "---------------------------------------------\n"
					+ "To begin: Please click the \"Load\" button to\n"
					+ "load a premade deck.\n"
					+ "Or select \"Add a Card\" to create a card\n"
					+ "Note large decks will take longer to\n"
					+ "show every card.";
			txaResults.setText(beginningMsg);
			
			lblTypeSelected.setText("Type selected:\n    " + "None");
			typeSelectedAttribute = "None";
			
			lblStageSelected.setText("Stage selected:\n    " + "None");
			stageSelectedAttribute = "None";
			
			lblSpecialSelected.setText("Special selected:\n    " + "None");
			specialSelectedAttribute = "None";
			
			flipCard();
			
			if(btnShowAll.getText().equals("Show One")) {
				cardsVBox.setVisible(false);
				btnShowAll.setText("Show All");
			}
			
			typeMenuButton.getItems().clear();
			stageMenuButton.getItems().clear();
			specialMenuButton.getItems().clear();
			cardMenuButton.getItems().clear();
			
			manager = new CardManager();
			
		}
	}
	
	private class resetSelectionEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			lblTypeSelected.setText("Type selected:\n    " + "None");
			typeSelectedAttribute = "None";
			
			lblStageSelected.setText("Stage selected:\n    " + "None");
			stageSelectedAttribute = "None";
			
			lblSpecialSelected.setText("Special selected:\n    " + "None");
			specialSelectedAttribute = "None";
			
			cardMenuButton.getItems().clear();
			flipCard();
			
			if(btnShowAll.getText().equals("Show One")) {
				cardsVBox.setVisible(false);
				btnShowAll.setText("Show All");
			}
			
			txaResults.setText("Selection cleared");
		}
	}
	
	private class getMenuItemEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String s = ((MenuItem) event.getSource()).getText();
			
			MenuButton parent = (MenuButton)((MenuItem) event.getSource()).getParentPopup().getOwnerNode();
			String b = parent.getText();
			
			if (b.equals("Type")) {
				lblTypeSelected.setText("Type Selected:\n    " + s);
				typeSelectedAttribute = s;
			}
			else if(b.equals("Stage")) {
				lblStageSelected.setText("Stage Selected:\n    " + s);
				stageSelectedAttribute = s;
			}
			else if(b.equals("Special")) {
				lblSpecialSelected.setText("Special Selected:\n    " + s);
				specialSelectedAttribute = s;
			}
			else {
				txaResults.setText("Oh boy I'd be real worried if this message somehow popped up! Haha!");
			}
			
			flipCard();
			
			List<CardObject> cardObjects = manager.getMatching(getGUIAttributes());
			setSelectedAttributes();
			buildCardDropDown(cardObjects);
		}
	}
	
	private void flipCard() {
		Image image = new Image("https://i0.wp.com/sleevenocardbehind.com/wp-content/uploads/2022/11/thumbnail_IMG_8444.jpg", 500, 500, true, true);
		cardImage.setImage(image);
	}
	
	private void buildCardDropDown(List<CardObject> s) {
		cardMenuButton.getItems().clear();
		if (s!=null) {
			for (CardObject h : s) {
				MenuItem temp = new MenuItem(h.getName());
				cardMenuButton.getItems().add(temp);
				temp.setOnAction(new showImageEventHandler());
			}
		}
		
	}
	
	private void setSelectedAttributes() {
		String [] temp = {typeSelectedAttribute, stageSelectedAttribute, specialSelectedAttribute};
		String msg = "";
		for (String s : temp) {
			if (!s.equals("None")) {
				msg += "Selected attribute: " + s + "\n";
			}
		}
		txaResults.setText(msg);
	}
	
	private class addCardEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			hBoxBottom.getChildren().clear();
			String s1 = "https://i0.wp.com/sleevenocardbehind.com/wp-content/uploads/2022/11/thumbnail_IMG_8444.jpg";
			Image card = new Image(s1);
			cardImage = new ImageView(card);
			cardImage.setX(250);
			cardImage.setY(250);
			cardImage.setFitHeight(250); 
			cardImage.setFitWidth(250);
			cardImage.setPreserveRatio(true);
			String msg = "Build a Card!\n"
					   + "------------------\n"
					   + "Use link below to\n"
					   + "get an image link\n"
					   + "for your card!\n";
			
			txaResults = new TextArea(msg);
		    txaResults.setPrefWidth(200);
		    txaResults.setEditable(false);
			
			Label lblName = new Label("Enter Card Name:        ");
			txfName = new TextField();
			HBox name = new HBox(lblName, txfName);
			
			Label lblType = new Label("Enter Type Attributes:  ");
			txfType = new TextField();
			HBox type = new HBox(lblType, txfType);
			
			Label lblStage = new Label("Enter Stage Attribute:  ");
			txfStage = new TextField();
			HBox stage = new HBox(lblStage, txfStage);
			
			Label lblSpecial = new Label("Enter Special Attribute:");
			txfSpecial = new TextField();
			HBox special = new HBox(lblSpecial, txfSpecial);
			
			Label lblURL = new Label("Enter Image URL:         ");
			txfURL = new TextField();
			HBox URL = new HBox(lblURL, txfURL);
			
			Button btnSaveCard = new Button("Save Card");
			btnSaveCard.setOnAction(new WriteoneCardEventHandler(primaryStage));
			Button btnAddCardHelp = new Button("Help");
			btnAddCardHelp.setOnAction(new buildCardHelpEventHandler());
			Button btnGoBack = new Button("Go Back");
			btnGoBack.setOnAction(new goBackEventHandler());
			HBox buttons = new HBox(btnSaveCard, btnAddCardHelp, btnGoBack);
			
			class hyperLinkEventHandler implements EventHandler<ActionEvent> {
				@Override
				public void handle(ActionEvent event) {
					getHostServices().showDocument("https://www.pokemon.com/us/pokemon-tcg/pokemon-cards");
				}
			}
			Hyperlink link = new Hyperlink("https://www.pokemon.com/us/pokemon-tcg/pokemon-cards");
			link.setOnAction(new hyperLinkEventHandler());

			
			VBox vBox = new VBox(name, type, stage, special, URL, buttons, link);
			hBoxBottom.getChildren().addAll(cardImage, txaResults, vBox);
		}
	}
	
	private class buildCardHelpEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String msg = "How to build a card:\n"
					   + "Check your card for its name\n"
					   + "Type:Your card's element\n"
					   + "Stage: Look at your card top left\n"
					   + "Special: Is it Mega? EX?\n"
					   + "Else type \"None\"\n"
					   + "Use link to find your card\n"
					   + "And copy/paste it's image link";
			txaResults.setText(msg);
		}
	}
	
	private class WriteoneCardEventHandler implements EventHandler<ActionEvent> {
		Stage stage;
		public WriteoneCardEventHandler(Stage stage) {
			super();
			this.stage = stage;
		}
		@Override
		public void handle(ActionEvent event) {
			File file = getFile(stage, "Save"); // Display Save dialog
			if( file != null) {
	            try {
	            	writeCardFile(file);
	            	txaResults.setText("Press \"Restart\" and \nload the file again!");
				}
	            catch (FileNotFoundException e) {
	            	txaResults.setText("Error writing file");
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void writeCardFile(File file) throws FileNotFoundException {
		int id = (int)(1+1000*Math.random());
		String name = txfName.getText();
		ArrayList<String> attributes = new ArrayList<>();
		attributes.add("Pokémon");
		attributes.add(txfType.getText());
		attributes.add(txfStage.getText());
		attributes.add(txfSpecial.getText());
		String url = txfURL.getText();
		CardObject dummy = new CardObject(id, name, attributes,url);
		manager.addCard(dummy,false);
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
			bw.newLine();
			bw.write("!&%8");
			bw.newLine();
			bw.write(Integer.toString(dummy.getId()));
			bw.newLine();
	        bw.write(name);
	        bw.newLine();
	        bw.write("Pokémon," + txfType.getText());
	        bw.newLine();
	        bw.write(txfStage.getText());
	        bw.newLine();
	        bw.write(txfSpecial.getText());
	        bw.newLine();
	        bw.write(url);
	        bw.close();
	    } catch (IOException e) {
	        e.printStackTrace();

	    }
	}
	
	
	private class goBackEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			hBoxBottom.getChildren().clear();
			String s1 = "https://i0.wp.com/sleevenocardbehind.com/wp-content/uploads/2022/11/thumbnail_IMG_8444.jpg";
			Image card = new Image(s1);
			cardImage = new ImageView(card);
			cardImage.setX(250);
			cardImage.setY(250);
			cardImage.setFitHeight(250); 
			cardImage.setFitWidth(250);
			cardImage.setPreserveRatio(true);
			
			String beginningMsg = "Welcome to the Pokemon Card Database program!\n"
					+ "---------------------------------------------\n"
					+ "To begin: Please click the \"Load\" button to\n"
					+ "load a premade deck.\n"
					+ "Or select \"Add a Card\" to create a card!\n"
					+ "Note large decks will take longer to\n"
					+ "show every card.";

			txaResults = new TextArea(beginningMsg);
			txaResults.setPrefWidth(650);
			txaResults.setEditable(false);

			hBoxBottom.getChildren().addAll(cardImage, txaResults);
		}
	}
	
	private class showImageEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String desiredCard = ((MenuItem) event.getSource()).getText();
			List<CardObject> cardObjects = manager.getMatching(getGUIAttributes());
			for(CardObject h : cardObjects) {
				if (h.getName().equals(desiredCard)) {
					Image image = new Image(h.getImageURL(), 500, 500, true, true);
					cardImage.setImage(image);
					txaResults.setText(h.toString());
				}
			}
		}
	}
	
	private class showAllEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (btnShowAll.getText().equals("Show All")) {
				List<CardObject> cardObjects = manager.getMatching(getGUIAttributes());
				try {
					if(cardObjects != null) {
						buildCardHBox(cardObjects);
					}
				} catch (FileNotFoundException e) {
					txaResults.setText("Error reading file");
					e.printStackTrace();
				}
				cardsVBox.setVisible(true);
				btnShowAll.setText("Show One");
			}
			else {
				cardsVBox.setVisible(false);
				btnShowAll.setText("Show All");
				txaResults.setText("Select a Card");
			}
		}
	}
	
	
	private AttributeSet getGUIAttributes() {
		Collection<String>  realAttributes = new ArrayList<>();
		
		if (!typeSelectedAttribute.equals("None")) {
			realAttributes.add(typeSelectedAttribute);
		}
		if (!stageSelectedAttribute.equals("None")) {
			realAttributes.add(stageSelectedAttribute);
		}
		if (!specialSelectedAttribute.equals("None")) {
			realAttributes.add(specialSelectedAttribute);
		}
		
		AttributeSet set = new AttributeSet(realAttributes);
		return set;
	}
	
	
	private void buildCardHBox(List<CardObject> h) throws FileNotFoundException {
		int i = 0;
		HBox hBox = new HBox();

		String msg = "Displayed Cards:\n";
		msg += "  Tip: If you want to display all cards\n"
			+  "  Selected only the attribute \"Pokémon\"\n"
			+  "-------------------------------------------\n";
		
		cardsVBox.getChildren().clear();
		for (CardObject c : h) {
			if (i < 4) {
				msg += c.getName() + "\n";
				Image image = new Image(c.getImageURL());
				ImageView imageView = new ImageView(image);
				imageView.setX(250);
				imageView.setY(250);
				imageView.setFitHeight(250); 
			    imageView.setFitWidth(250);
			    imageView.setPreserveRatio(true);
			    hBox.getChildren().add(imageView);
			    ++i;
			}
			else {
				cardsVBox.getChildren().add(hBox);
				hBox = new HBox();
				msg += c.getName() + "\n";
				Image image = new Image(c.getImageURL());
				ImageView imageView = new ImageView(image);
				imageView.setX(250);
				imageView.setY(250);
				imageView.setFitHeight(250); 
			    imageView.setFitWidth(250);
			    imageView.setPreserveRatio(true);
			    hBox.getChildren().add(imageView);
				i = 1;
			}
		}
		if (!hBox.getChildren().isEmpty()) {
			cardsVBox.getChildren().add(hBox);
		}

		txaResults.setText(msg);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
