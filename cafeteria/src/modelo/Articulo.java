package modelo;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Articulo {

	protected int cod_art, stock;
	protected float precio;
	
	private static final AtomicInteger count = new AtomicInteger(0); 
	
	public Articulo(int cod_art, int stock, float precio) {
		super();
		this.cod_art = count.incrementAndGet();
		this.stock = stock;
		this.precio = precio;
	}
	
	public Articulo() {
		super();
		this.cod_art = count.incrementAndGet();
		this.stock = 0;
		this.precio = 0;
	}

	public int getCod_art() {
		return cod_art;
	}

	public void setCod_art(int cod_art) {
		this.cod_art = cod_art;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Articulo [cod_art=" + cod_art + ", stock=" + stock + ", precio=" + precio + "]";
	}
	
}