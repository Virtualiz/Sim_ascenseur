public class Constantes {

	static final long tempsPourEntrerDansLaCabine = 3;

	static final long tempsPourSortirDeLaCabine = 4;

	static final long tempsPourOuvrirLesPortes = 5;

	static final long tempsPourFermerLesPortes = 6;

	static final long tempsPourBougerLaCabineDUnEtage = 10;

	static final int nombreDePlacesDansLaCabine = 8;

	private static boolean modeParfait;

	public static boolean isModeParfait() {
		return modeParfait;
	}

	public static void setModeParfait(boolean p) {
		modeParfait = p;
	}

	public static void notYetImplemented () {
		assert false : "notYetImplemented"; 
	String s = null;
	s.charAt(0); // To force the crash when assert is disabled.
	}

}
