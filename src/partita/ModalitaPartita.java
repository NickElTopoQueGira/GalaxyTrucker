package partita;

public enum ModalitaPartita {
	SINGOLA 	(1),
	MULTIPLA	(2);

	private final int id;

	private ModalitaPartita(int id){
		this.id = id;
	}

	public int getId(){ return this.id; }

	public static ModalitaPartita toModalitaPartita(int mod){
		for(ModalitaPartita modal : ModalitaPartita.values()){
			if(modal.getId() == mod){
				return modal;
			}
		}
		return null;
	}
}
