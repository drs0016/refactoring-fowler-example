package ubu.gii.dass.refactoring;

/**
* Tema  Refactorizaciones 
*
* Ejemplo de aplicaci�n de refactorizaciones. Actualizado para colecciones gen�ricas de java 1.5
*
* @author M. Fowler y <A HREF="mailto:clopezno@ubu.es">Carlos L�pez</A>
* @version 1.1
* @see java.io.File
*
*/
import java.util.*;

public class Customer {
	private String _name;
	private Vector<Rental> _rentals;

	public Customer(String name) {
		_name = name;
		_rentals = new Vector<Rental>();

	};

	public void addRental(Rental arg) {
		_rentals.addElement(arg);
	}

	public String getName() {
		return _name;
	};
	
	public String HTMLStatement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration<Rental> rentals = _rentals.elements();
		String result = "<p>Rental Record for " + getName() + "</p>";
		while (rentals.hasMoreElements()) {
			double thisAmount = 0;
			Rental each = rentals.nextElement();
			thisAmount = getCharge(each);
			// add frequent renter points
			frequentRenterPoints = each.getFrequentRenterPoints(
					frequentRenterPoints);
			// show figures for this rental
			result += "<p>  " + each.getMovie().getTitle() + "  "
					+ String.valueOf(thisAmount) + "</p>";
			totalAmount += thisAmount;
		}
		// add footer lines
		result += "<p>Amount owed is " + String.valueOf(totalAmount) + "</p>";
		result += "<p>You earned " + String.valueOf(frequentRenterPoints)
				+ " frequent renter points</p>";
		return result;
	}

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration<Rental> rentals = _rentals.elements();
		String result = "Rental Record for " + getName() + "\n";
		while (rentals.hasMoreElements()) {
			double thisAmount = 0;
			Rental each = rentals.nextElement();
			thisAmount = getCharge(each);
			// add frequent renter points
			frequentRenterPoints = each.getFrequentRenterPoints(
					frequentRenterPoints);
			// show figures for this rental
			result += "\t" + each.getMovie().getTitle() + "\t"
					+ String.valueOf(thisAmount) + "\n";
			totalAmount += thisAmount;
		}
		// add footer lines
		result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
		result += "You earned " + String.valueOf(frequentRenterPoints)
				+ " frequent renter points";
		return result;
	}

	public double getCharge(Rental each) {
		double thisAmount = 0;
		// determine amounts for each line
		switch (each.getMovie().getPriceCode()) {
		case Movie.REGULAR:
			thisAmount += 2;
			if (each.getDaysRented() > 2)
				thisAmount += (each.getDaysRented() - 2) * 1.5;
			break;
		case Movie.NEW_RELEASE:
			thisAmount += each.getDaysRented() * 3;
			break;
		case Movie.CHILDRENS:
			thisAmount += 1.5;
			if (each.getDaysRented() > 3)
				thisAmount += (each.getDaysRented() - 3) * 1.5;
			break;
		}
		return thisAmount;
	}
}
