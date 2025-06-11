package partita;

public enum ModalitaPartita {
	// modalita' partita per il gioco singolo
	SINGOLA 	(1),
	// modalita' partita per il gioco multiplo
	MULTIPLA	(2);

	// indicatore univoco per la modalita' del gioco
	private final int id;

	/**
	 * Metodo per costruire una nuova Modalita' Partita
	 * con l'id specifico (di cui sopra)
	 *
	 * @param id int identificatore univoco della modalita' partita
	 * */
	private ModalitaPartita(int id){
		this.id = id;
	}

	/**
	 * Metodo per acquisire la modalita' della parita
	 *
	 * @return int modalita' della partita
	 * */
	public int getId(){ return this.id; }


	/**
	 * Metodo per convertire un id (utilizzato all'interno della classe) con
	 * il valore del enum corrispondente
	 *
	 * @param mod int id intero da convertire nel valore corrispondente di tipo enum
	 * @return ModalitaPartita valore del enum corrispondente al valore specificato in input
	 * 			se null, il valore specificato non e' valido
	 * */
	public static ModalitaPartita toModalitaPartita(int mod){
		for(ModalitaPartita modal : ModalitaPartita.values()){
			if(modal.getId() == mod){
				return modal;
			}
		}
		return null;
	}
}
