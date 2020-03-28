package it.polito.tdp.spellchecker.model;

public class RichWord {

	private String parola;
	private boolean corretta;
	
	/**Costruttore vuoto in modo da poterlo modificare a piacimento
	 * 
	 * @param parola	del testo in input
	 * @param corretta	verificare se la parola è corretta o meno
	 */
/*	public RichWord() {
		super();
		this.parola = parola;
		this.corretta = corretta;
	}*/

	public RichWord(String word, boolean b) {
		// TODO Auto-generated constructor stub
		this.parola = word;
		this.corretta = b;
	}

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public boolean isCorretta() {
		return corretta;
	}

	public void setCorretta(boolean corretta) {
		this.corretta = corretta;
	}
	
	
	
}
