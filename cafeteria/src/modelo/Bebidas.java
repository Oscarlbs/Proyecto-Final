package modelo;


public class Bebidas extends Articulo{

	private enum Tip_bebidas {agua,coca_cola,fanta,nestea,aquarius,sprint,limonada,gaseosa,batido,cerveza}
	private String tip_bebida;
	private String descr;
	
	public Bebidas(int cod_art, int stock, float precio, String tip_bebida, String descr) {
		super(cod_art, stock, precio);
		this.tip_bebida = tip_bebida;
		this.descr = descr;
	}
	
	public Bebidas () {
		super();
		this.tip_bebida = "";
	}

	public static boolean comprobarBebida (String valor) {
		
		for (Tip_bebidas c : Tip_bebidas.values()) {
			if(c.name().equalsIgnoreCase(valor)) {
				return true;
			}
		}
		return false;
	}
	
	public String getTip_bebida() {
		return tip_bebida;
	}

	public void setTip_bebida(String tip_bebida) {
		this.tip_bebida = tip_bebida;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String toString() {
		return super.cod_art+" Bebida: "+ tip_bebida+"\nDescripción: "+descr+
				"\nPrecio: "+super.precio+"€"+"\nStock: "+super.stock+" uds"+"\n====================="; 
	}
}