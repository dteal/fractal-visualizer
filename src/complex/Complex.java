package complex;

/*
 * Class that represents a complex number.
 */
public class Complex {

	private double real_part, imag_part;
	
	public Complex(double real_part, double imag_part){
		this.real_part = real_part;
		this.imag_part = imag_part;
	}
	
	public double real(){
		return real_part;
	}
	
	public double imag(){
		return imag_part;
	}
	
	public double modulus(){
		return Math.sqrt(real_part*real_part+imag_part*imag_part);
	}
	
	public static Complex add(Complex x, Complex y){
		return new Complex(x.real()+y.real(), x.imag()+y.imag());
	}
	
	public static Complex multiply(Complex x, Complex y){
		double real_part = x.real()*y.real()-x.imag()*y.imag();
		double imag_part = x.real()*y.imag()+x.imag()*y.real();
		return new Complex(real_part, imag_part);
	}
	
}
