package P3;

import java.util.HashSet;

public class Person {

	private String person;
	private Person fromWho ;
	
	public Person(String person) {
		this.person = person;
	}

	
	public boolean addFrom(Person newPerson) //����ʵ������ҵ�����˵���Դ �Ӷ�����Friendship�����ȹ����
	{
		this.fromWho =newPerson;
		return this.fromWho.equals(newPerson);
	}
	public Person getFrom() //������������Դ
	{
		return this.fromWho;
	}
	
	
	public String toString()
	{
		return this.person;
	}
	
	public boolean equals(Person personCompared)		//ʵ��Person���eqauls ��ֱ�ӱȽ�
	{
		if(this.toString().equals(personCompared.toString()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	

	

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
