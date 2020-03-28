package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {
	
	//la mia struttura dati è una lista a cui viene copiato il dizionario selezionato
	List<String> dizionario= new ArrayList<String>();
	
	//salvare in memoria il dizionario della lingua passata come parametro
	public void loadDictionary(String language) {
		
		//lettura da file
		if(language.equals("English")) {
		try {
			FileReader fr = new FileReader("src/main/resources/English.txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			
			while( (word = br.readLine()) != null) {
				//aggiungi parola nella struttura dati
				dizionario.add(word);
			}
			br.close();
		} catch(IOException e) {
			System.out.println("Errore nella lettura da file.\n");
		}
		}
		else {
			if(language.equals("Italian")) {
				try {
					FileReader fr = new FileReader("src/main/resources/Italian.txt");
					BufferedReader br = new BufferedReader(fr);
					String word;
					
					while( (word = br.readLine()) != null) {
						//aggiungi parola nella struttura dati
						dizionario.add(word);
					}
					br.close();
				} catch(IOException e) {
					System.out.println("Errore nella lettura da file.\n");
				}
			}
		}
		
	}
	
	public List<RichWord> spellCheckText(LinkedList<String> inputTextList){
		
		List <RichWord> elenco = new ArrayList<RichWord>();
		RichWord rtemp = null;
		
		//aggiungere all'elenco tutte le parole passare in input (sia giuste che sbagliate)
		for(String s: inputTextList) {
			if(cercaParola(s) == null) {
				//la parola non è nel dizionario, quindi è sbagliata: il boolean è false
				rtemp = new RichWord(s, false);
			}
			else {
				rtemp = new RichWord(s, true);
			}
			elenco.add(rtemp);
		}
		
		return elenco;
	}
	
	public String cercaParola(String parola) {
		for(String s: dizionario) {
			if(parola.equals(s)) {
				return s;
			}
		}
		return null;
	}
	
	//ricerca lineare
	public List<RichWord> spellCheckTextLinear(LinkedList<String> inputTextList){
		List <RichWord> elenco = new LinkedList<RichWord>();
	/*	String array[] = null;
		RichWord rw = null;
		
		for(int i=0; i<dizionario.size(); i++) {
			for(String s: inputTextList) {
				if(s.equals(array[i])) {
					rw = new RichWord(s, true);
				}
				else
					rw = new RichWord(s, false);
			}
			elenco.add(rw);
		}
		
		return elenco;*/
		for(String s: inputTextList) {
			boolean trovata = false;
			for(String d: dizionario) {
				if(s.equals(d)) {
					trovata = true;
					break;
				}
			}
			
			RichWord rtemp = new RichWord(s, trovata);
			elenco.add(rtemp);
		}
		
		return elenco;
	}
	
	//ricerca dicotomica
	public List<RichWord> spellCheckTextDichotomic (LinkedList<String> inputTextList) {
		
		RichWord rw = null;
		boolean trovata = false;
		
		List <RichWord> elenco = new LinkedList<RichWord>();
		
		for(String s: inputTextList) {
			if(posizione(s.toLowerCase())) {
				trovata = true;
			}
			else
				trovata = false;
			rw = new RichWord(s, trovata);
			elenco.add(rw);
		}
		
		return elenco;
	}
	
	public boolean posizione(String stringa) {
		int inizio = 0;
		int fine = dizionario.size();
		
		while(inizio != fine) {
			int medio = inizio +(fine-inizio)/2 ;
			if(stringa.compareToIgnoreCase(dizionario.get(medio)) == 0) {
				return true;
			}
			else if(stringa.compareToIgnoreCase(dizionario.get(medio)) > 0) {
				inizio = medio +1;
			}
			else
				fine = medio;
		}
		return false;
	}
	
}
