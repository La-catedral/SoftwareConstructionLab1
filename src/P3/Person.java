package P3;

import java.util.HashSet;

public class Person {

	private String person;
	private Person fromWho ;
	
	public Person(String person) {
		this.person = person;
	}

	
	public boolean addFrom(Person newPerson) //用于实现添加找到这个人的来源 从而方便Friendship类中先广遍历
	{
		this.fromWho =newPerson;
		return this.fromWho.equals(newPerson);
	}
	public Person getFrom() //返回上述的来源
	{
		return this.fromWho;
	}
	
	
	public String toString()
	{
		return this.person;
	}
	
	public boolean equals(Person personCompared)		//实现Person类的eqauls 可直接比较
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
