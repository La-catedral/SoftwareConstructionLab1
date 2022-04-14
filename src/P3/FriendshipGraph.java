package P3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class FriendshipGraph {
	private HashMap<Person,HashSet<Person>> friendedge =new HashMap<Person,HashSet<Person>>();
	private HashSet<Person> personInGraph =new HashSet<Person>();
	
	/*
	 * addEdge 原本为void函数，但为了测试方便，同时仍使得类中两个成员变量保持私有  因此将返回集合中元素数量 以表示确实增加了一条边
	*/
	public boolean addEdge(Person fromPerson,Person toPerson)
	{
		if(friendedge.get(fromPerson).contains(toPerson))
			return false;
		else
		{	friendedge.get(fromPerson).add(toPerson);
			return friendedge.get(fromPerson).contains(toPerson);
		}
	
	}
	
	/*addVertex 同上 但调用该函数会增加类中两部分的内容 因此需要判断两部分内容（两个set）的size是否都加1 若是 返回true
	*/
	public boolean  addVertex(Person newPerson)
	{
		for(Person personExisited:this.personInGraph)//遍历容器 检查图中是否有重名者
		{
			if(newPerson.equals(personExisited)) {
				System.out.println("VERTEX HAS EXISITED!");
				System.exit(0);
			}
		}
		
		int formerpersonSize =personInGraph.size();	//保留添加前规模大小 用于和添加后规模大小作比较 从而判断是否执行成功
		int formerfriendedgeSize = friendedge.size();
		personInGraph.add(newPerson);
		friendedge.put(newPerson, new HashSet<Person>());
		return (personInGraph.size()==formerpersonSize+1) &&(friendedge.size()==formerfriendedgeSize+1);
	}
	
	private HashSet<Person> visited =new HashSet<Person>();
	boolean DFSFind(Person fromPerson,Person toPerson)
	{
		Queue<Person> queue =new LinkedList<Person>();
		queue.offer(fromPerson);
		visited.add(fromPerson);
		Person testPerson;
		while(!queue.isEmpty())
		{
			testPerson = queue.poll();
			Iterator<Person> it =friendedge.get(testPerson).iterator();
			
			while(it.hasNext())
			{
				Person relativePerson =it.next();
				if(!visited.contains(relativePerson))
				{
					relativePerson.addFrom(testPerson);
					visited.add(relativePerson);
					if(relativePerson.equals(toPerson))
						return true;
					queue.offer(relativePerson);
				}
			}
		}
		return false;
	}
	public int getDistance(Person fromPerson, Person toPerson)
	{
		if(fromPerson.equals(toPerson))	//若二者为同一个人
			return 0;
		
		visited.clear();
		if(DFSFind(fromPerson,toPerson))	//如果函数返回true 只需回溯  记录回溯过程经历的边数
		{
			Person testPerson = toPerson.getFrom();	//利用Person的fromwho来找到上一级
			int i =1;								//这里增加了耦合度  因此可扩展性不够高 通过交流再想想办法
			while(!testPerson.equals(fromPerson))	//这是我的自己的实现方法，有些笨拙 但我在博客上看到一位学长的
			{										//另一种实现方法 只需在函数中再加入一个HashMap 用于记录
				i++;								//先广过程中当前走到的“层数”，走到下一层时为下一层person赋予当前 
				testPerson =testPerson.getFrom();	//层数加1，这种方法耦合度低 我将在下一版本套用
			}
			return i;
		}
		else
		{
			return -1;
		}
	}
	
	public static void main(String[] args) {
		
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person fakeRachel =new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(fakeRachel);
		System.out.println("can it get there?");
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
