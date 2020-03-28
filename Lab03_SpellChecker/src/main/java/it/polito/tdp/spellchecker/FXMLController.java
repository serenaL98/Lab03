package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Dictionary dizionario;
	// Flag per ricerca dicotomica
	private final static boolean dicotomica = true;
	private final static boolean lineare = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TextArea txtTesto;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtErrori;

    @FXML
    private Label txtNumeroErrori;

    @FXML
    private Button btnClear;

    @FXML
    private Label txtTempo;

    @FXML
    void doClearText(ActionEvent event) {
    	txtTesto.clear();
    	txtErrori.clear();
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	String testo = txtTesto.getText();
    	//sostituisco i segni di punteggiatura con lo spazio per poter far split più semplicemente
    	//testo.replaceAll("[.,\\\\/#!$%\\\\^&\\\\*;:{}=\\\\-_`~()\\\\[\\\\]\\", " ");
    	
    	//misuro il tempo: INIZIO AZIONE
    	double start = System.nanoTime();
    	
    	if(testo.isEmpty()) {
    		txtErrori.setText("Inserire testo/ Insert text!\n");
    	}
    	
    	//con loadDictionary vado a selezionare la lingua del dizionario
    	String lingua = combobox.getValue();
    	dizionario.loadDictionary(lingua);
    	
    	//Controllo delle parole: sono scritte in modo corretto o meno
    	String array[] = testo.split(" ");
    	LinkedList<String> controllo = new LinkedList<String>();
    	
    	for(int i=0; i<array.length; i++) {
    		controllo.add(array[i]);
    	}
    	
    	if(dicotomica == true) {
    		dizionario.spellCheckTextDichotomic(controllo);
    	}
    	else if(lineare == true) {
    		dizionario.spellCheckTextLinear(controllo);
    	}
    	else
    		dizionario.spellCheckText(controllo);
    	
    	//riportare le parole errate
    	int conta = 0;
    	for(RichWord r: dizionario.spellCheckText(controllo)) {
    		if(r.isCorretta() == false) {
    			txtErrori.appendText(r.getParola()+"\n");
    			conta++;
    		}
    	}
    	//contare il numero delle parole sbagliate
    	if(conta == 1) {
    		txtNumeroErrori.setText("This text contains "+conta+ " error.");
    	}
    	else
    		txtNumeroErrori.setText("This text contains "+conta+ " errors.");
    	
    	//misuro il tempo: FINE AZIONE
    	double stop = System.nanoTime();
    	txtTempo.setText("Spell check completed in "+ (stop-start) + " seconds");
    	
    }

    void insertLanguage() {
    	combobox.getItems().add("English");
    	combobox.getItems().add("Italian");
    	combobox.setValue("English");
    }
    
    @FXML
    void initialize() {
        assert combobox != null : "fx:id=\"combobox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTesto != null : "fx:id=\"txtTesto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumeroErrori != null : "fx:id=\"txtNumeroErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTempo != null : "fx:id=\"txtTempo\" was not injected: check your FXML file 'Scene.fxml'.";

        insertLanguage();
        dizionario = new Dictionary();
    }
}
