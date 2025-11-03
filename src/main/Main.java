package main;

import presenter.Presenter;
import view.View;

public class Main {

	public static void main(String[] args) {
		View vista = new View();
		Presenter presenter = new Presenter();
		presenter.inicializarInterfazPresenter(vista);
	}

}
