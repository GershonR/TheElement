package fifthelement.theelement.business;

public class ExampleMethodTest {
}

/*
package comp3350.srsys.tests.business;

import junit.framework.TestCase;

import org.junit.Test;

import comp3350.srsys.business.Calculate;
import comp3350.srsys.objects.SC;
import comp3350.srsys.objects.Student;

import java.util.List;
import java.util.ArrayList;

public class CalculateGPATest extends TestCase
{
	private List<SC> list;
	private SC sc;
	private String resultGPA;

	public CalculateGPATest(String arg0)
	{
		super(arg0);
	}

	@Test
	public void testNullList()
	{
		System.out.println("\nStarting testCalculateGPA: null list");

		resultGPA = "";
		resultGPA = Calculate.gpa(null);

		assertNotNull(resultGPA);
		assertTrue(" ".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: null list");
	}

	@Test
	public void testEmptyList()
	{
		System.out.println("\nStarting testCalculateGPA: empty list");

		resultGPA = "";
		list = new ArrayList<>();
		resultGPA = Calculate.gpa(list);

		assertNotNull(resultGPA);
		assertTrue(" ".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: empty list");
	}

	@Test
	public void testNullObject()
	{
		List <SC>list;

		System.out.println("\nStarting testCalculateGPA: null object");

		resultGPA = "";
		list = new ArrayList<>();
		list.add(null);
		resultGPA = Calculate.gpa(list);

		assertNotNull(resultGPA);
		assertTrue("?".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: null object");
	}

	@Test
	public void testMissingGrades()
	{
		System.out.println("\nStarting testCalculateGPA: missing grades");

		resultGPA = "";
		list = new ArrayList<>();
		final Student joe = new Student("123", "Joe", "123 fake st");
		sc = new SC(joe, null, " ");
		list.add(sc);
		final Student mary = new Student("456", "Mary", "123 fake st");
		sc = new SC(mary, null, " ");
		list.add(sc);
		resultGPA = Calculate.gpa(list);

		assertNotNull(resultGPA);
		assertTrue(" ".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: missing grades");
	}

	@Test
	public void testValidGrades()
	{
		System.out.println("\nStarting testCalculateGPA: valid grades");

		resultGPA = "";
		list = new ArrayList<SC>();
		final Student joe = new Student("123", "Joe", "123 fake st");
		sc = new SC(joe, null, "A");
		list.add(sc);
		final Student mary = new Student("456", "Mary", "123 fake st");
		sc = new SC(mary, null, "B");
		list.add(sc);
		resultGPA = Calculate.gpa(list);

		assertNotNull(resultGPA);
		assertTrue("3.5".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: valid grades");
	}

	@Test
	public void testInvalidGrades()
	{
		System.out.println("\nStarting testCalculateGPA: invalid grades");

		resultGPA = "";
		list = new ArrayList<SC>();
		final Student joe = new Student("123", "Joe", "123 fake st");
		sc = new SC(joe, null, "X");
		list.add(sc);
		final Student mary = new Student("456", "Mary", "123 fake st");
		sc = new SC(mary, null, "Y");
		list.add(sc);
		resultGPA = Calculate.gpa(list);

		assertNotNull(resultGPA);
		assertTrue("?".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: invalid grades");
	}

	@Test
	public void testSomeInvalidGrades()
	{
		System.out.println("\nStarting testCalculateGPA: some invalid grades");

		resultGPA = "";
		list = new ArrayList<SC>();
		final Student joe = new Student("123", "Joe", "123 fake st");
		sc = new SC(joe, null, "A");
		list.add(sc);
		final Student mary = new Student("456", "Mary", "123 fake st");
		sc = new SC(mary, null, "X");
		list.add(sc);
		final Student sally = new Student("457", "Sally", "123 fake st");
		sc = new SC(sally, null, "B");
		list.add(sc);
		resultGPA = Calculate.gpa(list);

		assertNotNull(resultGPA);
		assertTrue("?".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: some invalid grades");
	}

    @Test
	public void testBlankGrades()
	{
		System.out.println("\nStarting testCalculateGPA: blank grades");

		resultGPA = "";
		list = new ArrayList<SC>();
		final Student joe = new Student("123", "Joe", "123 fake st");
		sc = new SC(joe, null, " ");
		list.add(sc);
		final Student bill = new Student("124", "Bill", "123 fake st");
		sc = new SC(joe, null, " ");
		list.add(sc);
		final Student mary = new Student("456", "Mary", "123 fake st");
		sc = new SC(mary, null, "X");
		list.add(sc);
		final Student sally = new Student("457", "Sally", "123 fake st");
		sc = new SC(sally, null, "X");
		list.add(sc);
		resultGPA = Calculate.gpa(list);

		assertNotNull(resultGPA);
		assertTrue("?".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: blank grades");
	}

    @Test
	public void testMixedGrades()
	{
		System.out.println("\nStarting testCalculateGPA: mixed grades");

		resultGPA = "";
		list = new ArrayList<SC>();
        final Student joe = new Student("123", "Joe", "123 fake st");
        sc = new SC(joe, null, " ");
        list.add(sc);
        final Student bill = new Student("124", "Bill", "123 fake st");
        sc = new SC(joe, null, " ");
        list.add(sc);
        final Student mary = new Student("456", "Mary", "123 fake st");
        sc = new SC(mary, null, "A");
        list.add(sc);
        final Student sally = new Student("457", "Sally", "123 fake st");
        sc = new SC(sally, null, "F");
		list.add(sc);
		resultGPA = Calculate.gpa(list);

		assertNotNull(resultGPA);
		assertTrue("2.0".equals(resultGPA));

		System.out.println("Finished testCalculateGPA: mixed grades");
	}
}
 */