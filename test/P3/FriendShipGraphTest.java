package P3;

import static org.junit.Assert.*;

import org.junit.Test;

import P2.turtle.TurtleSoup;

public class FriendShipGraphTest {

	@Test
	public void testAddEdge() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		assertTrue(graph.addEdge(rachel, ross));
		assertTrue(graph.addEdge(rachel, ben));
		assertTrue(graph.addEdge(ross, rachel));
	}

	@Test
	public void testAddVertex() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("david");
		Person ross = new Person("hock");
		assertTrue(graph.addVertex(rachel));
		assertTrue(graph.addVertex(ross));

	
	}

	@Test
	public void testDFSFind() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("mike");
		Person ross = new Person("jack");
		Person ben = new Person("kitty");
		Person kramer = new Person("Krame");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		assertTrue(graph.DFSFind(rachel,ross));
		assertFalse(graph.DFSFind(rachel,rachel));
		assertFalse(graph.DFSFind(rachel,kramer));

	}

	@Test
	public void testGetDistance1() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rache");
		Person ross = new Person("Ros");
		Person ben = new Person("Be");
		Person kramer = new Person("Kram");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		//should print 1
		System.out.println(graph.getDistance(rachel, ben));
		//should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		//should print 0
		System.out.println(graph.getDistance(rachel, kramer)); 
		//should print -1
	}

}
