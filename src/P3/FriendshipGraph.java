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
	 * addEdge ԭ��Ϊvoid��������Ϊ�˲��Է��㣬ͬʱ��ʹ������������Ա��������˽��  ��˽����ؼ�����Ԫ������ �Ա�ʾȷʵ������һ����
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
	
	/*addVertex ͬ�� �����øú������������������ֵ����� �����Ҫ�ж����������ݣ�����set����size�Ƿ񶼼�1 ���� ����true
	*/
	public boolean  addVertex(Person newPerson)
	{
		for(Person personExisited:this.personInGraph)//�������� ���ͼ���Ƿ���������
		{
			if(newPerson.equals(personExisited)) {
				System.out.println("VERTEX HAS EXISITED!");
				System.exit(0);
			}
		}
		
		int formerpersonSize =personInGraph.size();	//�������ǰ��ģ��С ���ں���Ӻ��ģ��С���Ƚ� �Ӷ��ж��Ƿ�ִ�гɹ�
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
		if(fromPerson.equals(toPerson))	//������Ϊͬһ����
			return 0;
		
		visited.clear();
		if(DFSFind(fromPerson,toPerson))	//�����������true ֻ�����  ��¼���ݹ��̾����ı���
		{
			Person testPerson = toPerson.getFrom();	//����Person��fromwho���ҵ���һ��
			int i =1;								//������������϶�  ��˿���չ�Բ����� ͨ������������취
			while(!testPerson.equals(fromPerson))	//�����ҵ��Լ���ʵ�ַ�������Щ��׾ �����ڲ����Ͽ���һλѧ����
			{										//��һ��ʵ�ַ��� ֻ���ں������ټ���һ��HashMap ���ڼ�¼
				i++;								//�ȹ�����е�ǰ�ߵ��ġ����������ߵ���һ��ʱΪ��һ��person���赱ǰ 
				testPerson =testPerson.getFrom();	//������1�����ַ�����϶ȵ� �ҽ�����һ�汾����
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
