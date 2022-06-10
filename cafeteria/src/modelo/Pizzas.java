package modelo;

public class Pizzas extends Articulo {

	private enum Tamanho {pequenia,mediana,familiar}
	private enum Especialidad {cuatro_quesos, mexicana, espanhola, carbonara}
	
	private String ingredientes, tamanho, especialidad;
	private int num_ingredientes;
	
	
	public Pizzas(int cod_art, int stock, float precio, String ingredientes, String tamanho, String especialidad,
			int num_ingredientes) {
		super(cod_art, stock, precio);
		this.ingredientes = ingredientes;
		this.tamanho = tamanho;
		this.especialidad = especialidad;
		this.num_ingredientes = num_ingredientes;
	}
	
	public Pizzas() {
		super();
		this.ingredientes = "";
		this.tamanho = "";
		this.especialidad = "";
		this.num_ingredientes = 0;
	} 

	public static boolean comprobarTamanho (String valor) {
		
		for (Tamanho c : Tamanho.values()) {
			if(c.name().equalsIgnoreCase(valor)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean comprobarEspecialidad (String valor) {
		
		for (Especialidad c : Especialidad.values()) {
			if(c.name().equalsIgnoreCase(valor)) {
				return true;
			}
		}
		return false;
	}
	
	
	public String getIngredientes() {
		return ingredientes;
	}


	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}


	public String getTamanho() {
		return tamanho;
	}


	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}


	public String getEspecialidad() {
		return especialidad;
	}


	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}


	public int getNum_ingredientes() {
		return num_ingredientes;
	}


	public void setNum_ingredientes(int num_ingredientes) {
		this.num_ingredientes = num_ingredientes;
	}

	@Override
	public String toString() {
		return super.cod_art+" Pizza: "+ especialidad + ", " +tamanho +
	"\nPrecio: "+super.precio+"€"+"\nStock: "+super.stock+" uds\nIngredientes: "+ingredientes+" // "+num_ingredientes+" ingredientes"+"\n=====================";
			
	}
}
