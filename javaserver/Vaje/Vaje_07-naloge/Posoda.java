public class Posoda
{
	private double volumen;
	private double teza;

	public Posoda(double vol, double tez)
	{
		this.volumen = vol;
		this.teza = tez;
	}

	public void setVolumen(double vol)
	{
		this.volumen = vol;
	}

	public void setTeza(double tez)
	{
		this.teza = tez;
	}

	public double getVolumen()
	{
		return this.volumen;
	}

	public double getTeza()
	{
		return this.teza;
	}
}