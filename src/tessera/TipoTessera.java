package tessera;

public enum TipoTessera { //tutte le scritte sono bold high intensity
	PORTA_MERCI			("\033[1;91m"+"M"+"\u001B[0m"),
	SCUDI				("\033[1;96m"+"S"+"\u001B[0m"), //ciano
	TUBI				("\033[1;97m"+"T"+"\u001B[0m"), //bianco
	MODULO_PASSEGGERI	("\033[1;94m"+"P"+"\u001B[0m"), //blu
	BATTERIA			("\033[1;92m"+"B"+"\u001B[0m"), //verde
	CANNONE				("\033[1;95m"+"C"+"\u001B[0m"), //viola
	MOTORE				("\033[1;93m"+"M"+"\u001B[0m"), //giallo
	CENTRO				("\033[1;90m"+"\u001B[47m"+"X"+"\u001B[0m"), //sfondo bianco e testo nero 
	VUOTA				(" ");

	private final String tipo;

	TipoTessera(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

}
