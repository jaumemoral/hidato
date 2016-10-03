package challenge.hidato;

import static org.junit.Assert.*;

import org.junit.Test;

public class HidatoTest {

	@Test
	public void test() {
		String[] data={
				"1 . 3 . . . . .",
				"x x x x x x x .",
				". . . . . . . .",
				". x x x x x x x",
				". . . . . . . .",
				"x x x x x x x .",
				". . . . . . . .",
				". x x x x x x x",
				". . . . . . . ."
			};
		Hidato h=new Hidato(data);
		h.solve();
		System.out.println(h.toString());
	}

}
