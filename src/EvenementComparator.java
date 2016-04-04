import java.util.Comparator;


public class EvenementComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		assert o1 instanceof Evenement;
		assert o2 instanceof Evenement;
		int res = 0;
		if(((Evenement) o1).date > ((Evenement) o2).date) res = 1;
		if(((Evenement) o1).date < ((Evenement) o2).date) res = -1;
		return res;
	}

}
