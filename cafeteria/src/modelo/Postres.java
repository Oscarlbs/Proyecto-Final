package modelo;


public class Postres extends Articulo{

	private enum TipoPostre {helado,tarta,cafe,brownie,gofre,montadito,melocoton,manzana,flan,yogur}
	private String tip_postre;
	private String descr;
	
	public Postres(int cod_art, int stock, float precio, String tip_postre, String descr) {
		super(cod_art, stock, precio);
		this.tip_postre = tip_postre;
		this.descr = descr;
	}
	
	public Postres () {
		super();
		this.tip_postre="";
	}
	
	public static boolean comprobarBebida (String valor) {
		
		for (TipoPostre c : TipoPostre.values()) {
			if(c.name().equalsIgnoreCase(valor)) {
				return true;
			}
		}
		return false;
	}

	public String getTip_postre() {
		return tip_postre;
	}

	public void setTip_postre(String tip_postre) {
		this.tip_postre = tip_postre;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String toString() {
		return super.cod_art+" Postre: " + tip_postre + "\nDescripción: "+descr+
				"\nPrecio: "+super.precio+"€"+"\nStock: "+super.stock+" uds"+"\n=====================";
	}
	
	
}