package aaaaaaaaaaaaaaaaaaaaaaa;

public class MeuArquivo {
	//classe para guardar arquivo, vou fazer um array no servidor para poder guardar multiplos
	// dados basicos que vou precisar de cada arquivo
	private int id;
	private String nome;
	private byte[] conteudo;
	private String extensaoArquivo;
	
	// contrutor da classe
	public MeuArquivo (int id, String nome, byte[] conteudo, String extensaoArquivo) {
		
		this.id = id;
		this.nome = nome;
		this.conteudo = conteudo;
		this.extensaoArquivo = extensaoArquivo;
	}
	
	//metodos get e set de cada uma das variaveis
	public void setId(int id) {
		this.id = id;		
	}
	public int getId() {
		return id;		
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;		
	}
	public String getNome() {
		return nome;		
	}
	
	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;		
	}
	public byte[] getConteudo() {
		return conteudo;		
	}
	
	
	public void setExtensaoArquivo(String extensaoArquivo) {
		this.extensaoArquivo = extensaoArquivo;		
	}
	public String getExtensaoArquivo() {
		return extensaoArquivo ;		
	}
	
	
	
}
