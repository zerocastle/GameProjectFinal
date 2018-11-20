/*
 * 
 * 삽질 한거 , 더블 버퍼링 구현 실패 겁나 끊겨서 나온다 . 메인 버퍼링 클래스에서 프레임을 끌고 와서 그래픽스를 얻어내고그 위에 드로우 하는건 되었으나 , repain() 메소드가 없어 
 * paint() 메소드와 , draw() 메소드로 구현 하려 했으나 안됨
 */

//package doublebuffering;
//
//import java.awt.Component;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import javax.swing.ImageIcon;
//
//public class Enemy implements Runnable {
//	private Map<Integer, Integer> map = null;
//	int x = 50;
//	int y = 50;
//	int w = 36;
//	int h = 36;
//	DoubleBuffering panel = null;
//	Image enemy = new ImageIcon(getClass().getResource("/image/enemy.png")).getImage();
//	
//	Image dbImage;
//	Graphics dbg;
//	public Enemy(DoubleBuffering frame) {
//		this.panel = frame;
//
//		map = new HashMap<Integer, Integer>();
//		this.enCreate();
//	}
//
//	public void enCreate() {
//		Random r = new Random();
//		for (int i = 0; i < 9; i++) {
//			int value = r.nextInt(DoubleBuffering.WIDTH);
//			map.put(i, value);
//		}
//	}
//
//	public void moveEn() {
//		y = y + (int) (Math.random() * 10);
//
//	}
//
////	private void paint(Graphics g) {
////		// TODO Auto-generated method stub
////		System.out.println("들어옴?");
////		dbImage = panel.createImage(DoubleBuffering.WIDTH,DoubleBuffering.HEIGHT);
////		dbg = dbImage.getGraphics();
////		this.draw(dbg);
////		g.drawImage(dbImage,0,0,panel);
////		System.out.println("test2");
////	}
//
//	public void draw(Graphics g) {
//		// TODO Auto-generated method stub
//		System.out.println("test1");
//		Graphics gs = panel.getGraphics();
//		for (int i = 0; i < map.size(); i++) {
//			gs.drawImage(enemy, map.get(i), y, w, h, panel);
//		}
//		try {
//
//			moveEn();
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		while (true) {
//			this.draw(panel.getGraphics());
//		}
//
//	}
//
//}
