/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;

import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.lang.Math;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	for(int i=0;i<4;i++)
    	{
    		turtle.forward(sideLength);
    		turtle.turn(-90);
    	}
//        throw new RuntimeException("implement me!");
      
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
    	return  (sides-2.0)*180/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	if(angle<=0 || angle >=180)
    	{
    		System.out.println("ILLEGAL ANGLE!");
    		return -1;
    	}
    	double a= 2.0/(1-angle / 180.0);
    	return (int)Math.round(a);
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        if (sideLength <=0)
        {
        	return ;
        }
    	for (int i = 0; i < sides;i++)
        {
        	turtle.forward(sideLength);
        	turtle.turn(180-calculateRegularPolygonAngle(sides));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	int  relativePositionX = targetX - currentX;
    	int  relativePositionY = targetY - currentY;
    	double relatAngle = Math.atan2(relativePositionY, relativePositionX);//求出目标点相对于原点的角度
    	//随后将其转化为以y正半轴为正方向0度线，0-360度范围的角度traverRelatAngle
    	double traverRelatAngle =((relatAngle>=-(Math.PI))&&relatAngle<=((Math.PI)/2))?((Math.PI)/2-relatAngle):((5.0/2*Math.PI)-relatAngle);
    	traverRelatAngle = traverRelatAngle*(180.0/Math.PI);
    	
    	if(traverRelatAngle >= currentBearing) //如果相对角度大于等于面向角度，直接相减即可
    	{
    		return traverRelatAngle-currentBearing;
    	}
    	else									//如果小于 则相减后为负 加360即为转向角
    	{
    		return 360.0+traverRelatAngle-currentBearing;
    	}
    	
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	List<Double > BearingList =new ArrayList <Double>();
    	//若输入不符合规范 则警告 并返回空列表
    	if(xCoords.size() != yCoords.size())
    	{
    		System.out.println("x列表元素数目与y列表元素数目不相等！");
    		return BearingList;
    	}
    	//若传入两个空列表 则直接返回空列表
    	if((xCoords.size()== 0)&&(yCoords.size()==0))
    	{
    		return BearingList;
    	}
    	else 			//否则按规格说明计算产生的序列 对calculateBearingToPoint进行n-1次调用
    	{
    		int sizeOfList = xCoords.size();
    		double newBearing = 0;
    		for (int i=0;i < sizeOfList-1;i++)
    		{    		
    			newBearing = calculateBearingToPoint(newBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1));
    			BearingList.add(newBearing);
    		}
    		return BearingList;
    	}
//        throw new RuntimeException("implement me!");
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Point theLeftLowPoint(Set<Point> points)
    {
    	java.util.Iterator<Point> it =points.iterator();
    	Point leftpoint =it.next(),newpoint;
    	
    	for(;it.hasNext();)
    	{	newpoint=it.next();
    		if (leftpoint.x() >= newpoint.x())
    		{
    			if(leftpoint.x() == newpoint.x())
    			{
    				leftpoint =(leftpoint.y() > newpoint.y())?newpoint:leftpoint;
    			}
    			else
    			{
    				leftpoint = newpoint;
    			}
    		}
    		
    	}
    	return leftpoint;	//返回最左下点	
    }
    private static double facingAngle = 0;
    public static double initFacingAngle()
    {
    	facingAngle = 0;
    	return facingAngle;
    }
    //输入点集 ，以及现在的边点 （也要用到全局变量 facingAngle） 
    //输出下一个凸包的边点
    //遍历所有剩余点求对应的相对需要偏移的角度，找最小的（顺时针），对应为下一个点
    public static Point nextPoint(Set<Point> points ,Point point,Point firstpoint)
    {
    	java.util.Iterator<Point> it =points.iterator();
    	Point nextpoint,thispoint = it.next();	
    	
    	if(thispoint.x()==point.x() && thispoint.y() == point.y()) //遍历的不应等于现在的边点point
    		thispoint = it.next();
    	nextpoint =thispoint;
    	
    	double thisAngle,mintraverAngle = calculateBearingToPoint(facingAngle, (int )point.x(),(int) point.y(),(int) thispoint.x(),(int) thispoint.y());
    	OUT:
    	for(;it.hasNext();)
    	{
    		thispoint = it.next();
    		if(thispoint.x()== point.x() && thispoint.y() == point.y() ) //同上 跳过当前边点
        		{
    			if(it.hasNext())
    			thispoint = it.next();
    			else
    				break OUT;
        		}
    		//下面计算偏转角最小的点 和对应偏转角
    		thisAngle = calculateBearingToPoint(facingAngle, (int )point.x(),(int) point.y(),(int) thispoint.x(),(int) thispoint.y());
    		if(mintraverAngle > thisAngle)
    		{	
    			nextpoint = thispoint;
    			mintraverAngle = thisAngle;
    		}
    	}
   
    	facingAngle +=mintraverAngle;
    	return nextpoint;
    }
    public static Set<Point> convexHull(Set<Point> points) {
    	initFacingAngle();
    	Set<Point > convexSet = new HashSet<Point>();
    	if(points.size() == 0 ||points.size()==1 ||points.size()==2)
    	{
    		return points;
    	}
    	else
    	{
    		Point leftPoint =theLeftLowPoint(points);
    		Point newpoint = leftPoint,tempoint;
    		convexSet.add(leftPoint);
    		tempoint=newpoint = nextPoint(points,newpoint,leftPoint);
    		double lastFacing =facingAngle;
    		while( !((leftPoint.x()==newpoint.x()) && (leftPoint.y()==newpoint.y())))
    	{
    			newpoint = nextPoint(points,newpoint,leftPoint);
    			if(lastFacing != facingAngle)
    			{
    				convexSet.add(tempoint);
    			}
    			tempoint =newpoint;
    			lastFacing =facingAngle;
    				}
    		return convexSet;
    	}
    			
//        throw new RuntimeException("implement me!");
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
     //画一个多边形
    	for(int j=1;j<=2;j++)
    	{
	    	for (int i =3;i<8;i++)
	    	{
	    		switch(i)
	    		{
	    			case 3:
	    				turtle.color(PenColor.BLUE);
	    				break;
	    			case 4:
	    				turtle.color(PenColor.YELLOW);
	    				break;
	    			case 5:
	    				turtle.color(PenColor.RED);
	    				break;
	    			case 6:
	    				turtle.color(PenColor.MAGENTA);
	    				break;
	    			case 7:
	    				turtle.color(PenColor.CYAN);
	    				break;
	    			default:break;
	    		}
	    	drawRegularPolygon(turtle, i, 30*i);
	    	}
	    	turtle.turn(180);
    	}
    //
    	
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        
//        drawSquare(turtle, 40); // draw the window
        drawPersonalArt(turtle);
        turtle.draw();
    }

}
